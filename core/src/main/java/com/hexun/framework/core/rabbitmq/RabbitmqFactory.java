package com.hexun.framework.core.rabbitmq;

import com.hexun.framework.core.spring.SpringContextHolder;

/**
 * 选择消费者工厂
 * @author zhoudong
 *
 */
public abstract class RabbitmqFactory {

	/**
	 * @title 应用工厂
	 */
	public static RabbitmqHandle generateAppConsumer(Integer appType) {
		switch (appType) {
			case RabbitmqConstant.ORDER : return SpringContextHolder.getBean("rabbitmqOrdersHandle");
		}
		
		return null;
	}
}
