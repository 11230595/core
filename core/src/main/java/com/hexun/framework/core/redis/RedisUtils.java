package com.hexun.framework.core.redis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis 操作工具类
 * 
 * @author zhoudong
 *
 */
public class RedisUtils extends RedisList {

	/**
	 * 获取数据
	 * 
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		String value = null;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			value = jedis.get(key); // 获取数据
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);// 释放redis对象
			e.printStackTrace();
		} finally {
			// 返还到连接池
			RedisConfig.returnResource(pool, jedis);
		}

		return value;
	}

	/**
	 * 存储数据
	 * 如果key已经存在，直接覆盖原来的数据
	 * @param key
	 * @param value
	 * @return Status code reply
	 */
	public static String set(String key, String value) {
		JedisPool pool = null;
		Jedis jedis = null;
		String statusCode = null;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			statusCode = jedis.set(key, value); // 存储数据
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);// 释放redis对象
			e.printStackTrace();
		} finally {
			// 返还到连接池
			RedisConfig.returnResource(pool, jedis);
		}

		return statusCode;
	}
	
	/**
	 * 更新数据
	 * @param key
	 * @param value
	 * @return Status code reply
	 */
	public static String update(String key, String value) {
		return set(key,value);
	}
	
	/**
	 * 删除数据
	 * 
	 * @param key
	 * @return
	 */
	public static Long del(String... keys) {
		JedisPool pool = null;
		Jedis jedis = null;
		Long statusCode = 0L;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			
			List<String> keyList = new ArrayList<String>();
			for(String key : keys){
				keyList.add(key);
			}
			
			statusCode = jedis.del(keyList.toArray(new String[keyList.size()])); // 删除数据，可以同时删除多个
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);// 释放redis对象
			e.printStackTrace();
		} finally {
			// 返还到连接池
			RedisConfig.returnResource(pool, jedis);
		}

		return statusCode;
	}
	
	/**
	 * 清空数据
	 * @return Status code reply
	 */
	public static String flushDB() {
		JedisPool pool = null;
		Jedis jedis = null;
		String statusCode = null;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			statusCode = jedis.flushDB(); 	// 清空数据
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);// 释放redis对象
			e.printStackTrace();
		} finally {
			// 返还到连接池
			RedisConfig.returnResource(pool, jedis);
		}

		return statusCode;
	}
	
	/**
	 * 判断key是否存在
	 * @return Status code reply
	 */
	public static Boolean exists(String key) {
		JedisPool pool = null;
		Jedis jedis = null;
		Boolean statusCode = null;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			statusCode = jedis.exists(key); 	// 判断key是否存在
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);// 释放redis对象
			e.printStackTrace();
		} finally {
			// 返还到连接池
			RedisConfig.returnResource(pool, jedis);
		}

		return statusCode;
	}
	
	
	/**
	 * 同时插入多个key,value
	 * @return Status code reply
	 */
	public static String mset(String... keysValues) {
		JedisPool pool = null;
		Jedis jedis = null;
		String statusCode = null;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			
			List<String> keyValueList = new ArrayList<String>();
			for(String keyValue : keysValues){
				keyValueList.add(keyValue);
			}
			
			statusCode = jedis.mset(keyValueList.toArray(new String[keyValueList.size()]));	//插入多个key.value
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);// 释放redis对象
			e.printStackTrace();
		} finally {
			// 返还到连接池
			RedisConfig.returnResource(pool, jedis);
		}

		return statusCode;
	}
	
	/**
	 * 同时获取多个key对应的value
	 * @return Status code reply
	 */
	public static List<String> mget(String... keys) {
		JedisPool pool = null;
		Jedis jedis = null;
		List<String> results = null;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			
			List<String> keyList = new ArrayList<String>();
			for(String key : keys){
				keyList.add(key);
			}
			
			results = jedis.mget(keyList.toArray(new String[keyList.size()]));	//插入多个key.value
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);// 释放redis对象
			e.printStackTrace();
		} finally {
			// 返还到连接池
			RedisConfig.returnResource(pool, jedis);
		}

		return results;
	}
}
