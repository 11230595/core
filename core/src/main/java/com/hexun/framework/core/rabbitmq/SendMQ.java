package com.hexun.framework.core.rabbitmq;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 发送消息
 * 
 * @author zhoudong
 *
 */
public final class SendMQ {
	private static ExecutorService fixedThreadPool = null;
	static{
		fixedThreadPool = Executors.newFixedThreadPool(500);
	}
	
	
	public static void send(final String queueName,final String message) throws IOException, TimeoutException {
		fixedThreadPool.execute(new Runnable() {
			public void run() {
				try {
					Channel channel = RabbitmqConfig.getReqvChannel();
					channel.queueDeclare(queueName, false, false, false, null);
					channel.basicPublish("", queueName, null, message.getBytes());
				} catch (Exception e) {
					System.out.println("-------send MQ message fail!---------");
					e.printStackTrace();
				}
			}
		});
	}
}
