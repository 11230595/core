package com.hexun.framework.core.rabbitmq;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * 发送消息
 * 
 * @author zhoudong
 *
 */
public final class SendMQ {
	
	private static boolean isDurable = true; //是否对消息队列进行持久化
	
	private static ExecutorService fixedThreadPool = null;
	static{
		fixedThreadPool = Executors.newFixedThreadPool(500);
	}
	
	
	public static void send(final String queueName,final String message) throws IOException, TimeoutException {
		fixedThreadPool.execute(new Runnable() {
			public void run() {
				try {
					Channel channel = RabbitmqConfig.getReqvChannel();
					channel.queueDeclare(queueName, isDurable, false, false, null);
					//消息持久化    MessageProperties.PERSISTENT_TEXT_PLAIN
					channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
				} catch (Exception e) {
					System.out.println("-------send MQ message fail!---------");
					e.printStackTrace();
				}
			}
		});
	}
}
