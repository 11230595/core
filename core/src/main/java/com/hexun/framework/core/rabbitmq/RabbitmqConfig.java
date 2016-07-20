package com.hexun.framework.core.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


/**
 * rabbitmq相关配置
 * @author zhoudong
 *
 */
public final class RabbitmqConfig{

	private static ConnectionFactory factory = null;
	
	/**
	 * 获取channel
	 * @return
	 * @throws TimeoutException 
	 * @throws IOException 
	 */
	public static Channel getReqvChannel() throws IOException, TimeoutException{
		if(factory == null){
			factory = new ConnectionFactory();
			factory.setHost("10.0.202.152");
			factory.setPort(5672);
			factory.setUsername("admin");
			factory.setPassword("admin");
		}
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		return channel;
	}
	
	
}
