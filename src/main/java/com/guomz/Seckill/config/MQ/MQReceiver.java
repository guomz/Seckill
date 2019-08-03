package com.guomz.Seckill.config.MQ;

import java.io.IOException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guomz.Seckill.dto.Message;
import com.guomz.Seckill.dto.ProductDto;
import com.guomz.Seckill.entity.User;
import com.guomz.Seckill.service.ProductService;

@Component
public class MQReceiver {

	@Autowired
	private ProductService productService;
	@Autowired
	private ObjectMapper om;
	
	@RabbitListener(queues = MQConfig.SECKILL_QUEUE)
	public void receive(String msg) throws JsonParseException, JsonMappingException, IOException
	{
		Message message = om.readValue(msg, Message.class);
		ProductDto product = message.getProductDto();
		User user = message.getUser();
		productService.handleSeckill(user.getId(), product.getId());
	}
}
