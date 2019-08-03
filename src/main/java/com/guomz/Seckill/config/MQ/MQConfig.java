package com.guomz.Seckill.config.MQ;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * 配置队列信息，采用最简单的直接交换机模式
 * @author 12587
 *
 */
@Configuration
public class MQConfig {

	public static final String SECKILL_QUEUE = "Seckill_Queue";
	
	@Bean
	public Queue generateQueue()
	{
		return new Queue(SECKILL_QUEUE, true);
	}
}
