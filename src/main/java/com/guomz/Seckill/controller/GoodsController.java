package com.guomz.Seckill.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guomz.Seckill.anotations.AccessLimit;
import com.guomz.Seckill.config.MQ.MQSender;
import com.guomz.Seckill.dto.Message;
import com.guomz.Seckill.dto.ProductDto;
import com.guomz.Seckill.dto.Result;
import com.guomz.Seckill.entity.Order;
import com.guomz.Seckill.entity.SeckillOrder;
import com.guomz.Seckill.entity.User;
import com.guomz.Seckill.enums.CodeMsg;
import com.guomz.Seckill.enums.RedisPrefix;
import com.guomz.Seckill.service.OrderService;
import com.guomz.Seckill.service.ProductService;
import com.guomz.Seckill.util.RedisUtil;

@Controller
@RequestMapping("/goods")
public class GoodsController implements InitializingBean{

	@Autowired
	private ProductService productService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private ObjectMapper om;
	@Autowired
	private MQSender sender;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/list")
	public String toGoodList(HttpServletRequest req, HttpServletResponse resp, Model model) throws JsonParseException, JsonMappingException, IOException
	{
		List<ProductDto> productList = null;
		JavaType javaType = om.getTypeFactory().constructParametricType(ArrayList.class, ProductDto.class);
		productList = (List<ProductDto>) redisUtil.getCollectionValue(RedisPrefix.GOODS_LIST_PREFIX.getPrefix(), javaType);
		if(productList == null)
		{
			productList = productService.listAllProduct();
			redisUtil.setValue(RedisPrefix.GOODS_LIST_PREFIX.getPrefix(), productList, RedisPrefix.GOODS_LIST_PREFIX.getTimeOut());
		}
		model.addAttribute("productList", productList);
		return "/goods_list";
	}
	
	@RequestMapping("/detail")
	public String toGoodsDetail(Model model,
			@RequestParam(value="id",required = false)Long id) throws JsonParseException, JsonMappingException, IOException
	{
		ProductDto product = null;
		product = redisUtil.getValue(RedisPrefix.GOODS_DETAIL_PREFIX.getPrefix(), ProductDto.class);
		if(product == null)
		{
			product = productService.getProductDtoById(id);
			redisUtil.setValue(RedisPrefix.GOODS_DETAIL_PREFIX.getPrefix(), product, RedisPrefix.GOODS_DETAIL_PREFIX.getTimeOut());
		}
		model.addAttribute("product", product);
		return "/goods_detail";
	}
	
	/**
	 * 秒杀处理，限制五秒内访问五次
	 * @param req
	 * @param id
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@AccessLimit(limitTime = 5, maxCount = 5)
	@RequestMapping("/doseckill")
	@ResponseBody
	public Result<String> doSeckill(HttpServletRequest req,
			@RequestParam(value="id",required = false)Long id) throws JsonParseException, JsonMappingException, IOException
	{
		//判断库存
		/*
		 * ProductDto product = productService.getProductDtoById(id);
		 * if(product.getStockCount()<=0) { req.setAttribute("errMsg",
		 * CodeMsg.NO_STOCK.getMsg()); return "/seckill_failed"; }
		 */
		User user = (User) req.getAttribute("user");
		System.out.println("user: " + user.getId() + "开始秒杀判定");
		//判断用户是否已经入队
		Boolean inline = redisUtil.getValue(RedisPrefix.IN_LINE.getPrefix() + user.getId() + "_" + id, Boolean.class);
		if(inline != null && inline == true)
		{
			req.setAttribute("errMsg", CodeMsg.REPEAT_CLICK);
			return Result.success(CodeMsg.REPEAT_CLICK, "排队中");
		}
		//Order tempOrder = redisUtil.getValue(RedisPrefix.ORDER_PREFIX.getPrefix() + user.getId() + "_" + id, Order.class);
		//使用缓存判断当前用户是否已经秒杀过该商品
		SeckillOrder seckillOrder = orderService.getSeckillOrderByUserAndProduct(user.getId(), id);
		if(seckillOrder != null)
		{
			req.setAttribute("errMsg", CodeMsg.REPEAT_KILL.getMsg());
			return Result.success(CodeMsg.REPEAT_KILL, "重复秒杀");
		}
		
		//使用缓存判断预减库存
		long stockCount = reduceRedisStockCount(id);
		System.out.println("预判断库存: " + stockCount);
		if(stockCount < 0)
		{
			req.setAttribute("errMsg", CodeMsg.NO_STOCK.getMsg());
			return Result.success(CodeMsg.NO_STOCK, "没有库存");
		}
		Message message = new Message();
		ProductDto product = new ProductDto();
		product.setId(id);
		message.setUser(user);
		message.setProductDto(product);
		//进入消息队列
		sender.sendMessage(message);
		//入队信息写入缓存
		redisUtil.setValue(RedisPrefix.IN_LINE.getPrefix() + user.getId() + "_" + id, true);
		/*
		 * //减库存生成订单 Order order = productService.handleSeckill(user.getId(), id);
		 * //秒杀失败会返回null if(order == null) { //req.setAttribute(name, o); return
		 * "seckill_failed"; } req.setAttribute("order", order);
		 * //req.setAttribute("product", product); req.setAttribute("user", user);
		 */
		return Result.success(CodeMsg.SUCCESS, "排队中");
	}

	/**
	 * 用于处理轮询秒杀结果
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping(value = "checkseckillresult", method = RequestMethod.GET)
	@ResponseBody
	public Result<Long> checkSeckillResult(@RequestParam(value = "productId", required = false)Long productId,
			HttpServletRequest req) throws JsonParseException, JsonMappingException, IOException
	{
		User user = (User) req.getAttribute("user");
		SeckillOrder seckillOrder = orderService.getSeckillOrderByUserAndProduct(user.getId(), productId);
		if(seckillOrder != null)
		{
			//秒杀成功
			return Result.success(CodeMsg.SUCCESS, seckillOrder.getOrderId());
		}
		else
		{
			//秒杀失败
			//对于没有找到订单的情况需要综合考虑，待添加
			Boolean seckillStatus = redisUtil.getValue(RedisPrefix.SECKILL_STATUS.getPrefix() + user.getId() + "_" + productId, Boolean.class);
			if(seckillStatus == null)
			{
				//排队中
				return Result.success(CodeMsg.SUCCESS, 0L);
			}
			else if(seckillStatus == false)
			{
				//秒杀失败
				return Result.success(CodeMsg.NO_STOCK, -1L);
			}
		}
		return Result.success(CodeMsg.NO_STOCK, -1L);
	}
	
	/**
	 * 跳转到订单详情页面
	 * @return
	 */
	@RequestMapping("/orderdetail")
	public String toOrderDetail(@RequestParam(value = "orderId", required = false)Long orderId, 
			Model model)
	{
		Order order = orderService.getOrderById(orderId);
		model.addAttribute("order", order);
		return "order_detail";
	}
	
	/**
	 * 预加载库存
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		List<ProductDto> productList = productService.listAllProduct();
		for(ProductDto product: productList)
		{
			Integer stockCount = product.getStockCount();
			Long productId = product.getId();
			redisUtil.setValue(RedisPrefix.PRODUCT_COUNT_PREFIX.getPrefix() + productId, stockCount);
		}
	}
	
	/**
	 * 预减redis库存，返回减1后的值
	 * @param productId
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	private long reduceRedisStockCount(Long productId) throws JsonParseException, JsonMappingException, IOException
	{
		return redisUtil.decrValue(RedisPrefix.PRODUCT_COUNT_PREFIX.getPrefix() + productId);
		
	}
	
}
