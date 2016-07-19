package com.hexun.framework.core.rabbitmq;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.alibaba.fastjson.JSONObject;
import com.hexun.framework.core.rabbitmq.RabbitmqConfig;
import com.hexun.framework.core.rabbitmq.RabbitmqFactory;
import com.hexun.framework.core.rabbitmq.RabbitmqHandle;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

/**
 * 接收消息
 * 
 * @author zhoudong
 *
 */
public class ListenerReqv implements ServletContextListener{
	private static ExecutorService fixedThreadPool = null;
	static{
		fixedThreadPool = Executors.newFixedThreadPool(1);
	}
	
	private final static String QUEUE_NAME = "orders";
	
	/**
	 * 监听MQ获得消息
	 * @param argv
	 * @throws Exception
	 */
	public static void onMessage() throws Exception {

		Channel channel = RabbitmqConfig.getReqvChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		// 指定该消费者同时只接收一条消息
		channel.basicQos(1);
		// 打开消息应答机制
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(QUEUE_NAME, false, consumer);
		

		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println(" [x] 接收到消息 ：" + message);
			JSONObject jsonObject = JSONObject.parseObject(message);
			int appId = jsonObject.getInteger("appId");
			RabbitmqHandle rabbitmqHandle = RabbitmqFactory.generateAppConsumer(appId);
			rabbitmqHandle.exec(jsonObject);
			// 返回接收到消息的确认信息
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		fixedThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				try {
					onMessage();//服务器启动时就开始监听
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
