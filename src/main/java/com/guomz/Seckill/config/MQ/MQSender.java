package com.guomz.Seckill.config.MQ;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guomz.Seckill.dto.Message;

@Component
public class MQSender {

	@Autowired
	private AmqpTemplate template;
	@Autowired
	private ObjectMapper om;
	
	/**
	 * 发送消息
	 * @param msg
	 * @throws JsonProcessingException
	 */
	public void sendMessage(Message msg) throws JsonProcessingException
	{
		String jsonStr = om.writeValueAsString(msg);
		template.convertAndSend(MQConfig.SECKILL_QUEUE, jsonStr);
	}
}
