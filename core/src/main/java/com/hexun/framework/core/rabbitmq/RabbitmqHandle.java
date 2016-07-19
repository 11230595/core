package com.hexun.framework.core.rabbitmq;

import com.alibaba.fastjson.JSONObject;

/**
 * 处理消息
 * @author zhoudong
 *
 */
public interface RabbitmqHandle {
	/**
	 * 处理消息
	 * @param message
	 */
	public void exec(JSONObject message);
}
