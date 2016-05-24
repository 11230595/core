package com.hexun.framework.core.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
/**
 * redis hash 操作
 * @author zhoudong
 *
 */
public class RedisHash {
	/**
	 * hash 结构添加数据
	 * @param key
	 * @param hash
	 * @return
	 */
	public static String hmset(String key,Map<String,String> hash){
		JedisPool pool = null;
		Jedis jedis = null;
		String statusCode = null;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			statusCode = jedis.hmset(key, hash);	//插入hash
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
	 * hash 结构取数据
	 * @param key
	 * @param fields
	 * @return
	 */
	public static List<String> hmget(String key,String... fields){
		JedisPool pool = null;
		Jedis jedis = null;
		List<String> results = null;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			results = jedis.hmget(key, fields);	//从hash取数据，取出的是list的结构
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);// 释放redis对象
			e.printStackTrace();
		} finally {
			// 返还到连接池
			RedisConfig.returnResource(pool, jedis);
		}

		return results;
	}
	/**
	 * hash 删除
	 * @param key
	 * @param fields
	 * @return
	 */
	public static Long hdel(String key,String... fields){
		JedisPool pool = null;
		Jedis jedis = null;
		Long statusCode = 0L;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			statusCode = jedis.hdel(key,fields); // 删除数据，可以同时删除多个
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
	 * 返回一个key下面字段的个数
	 * @param key
	 * @return
	 */
	public static Long hlen(String key){
		JedisPool pool = null;
		Jedis jedis = null;
		Long statusCode = 0L;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			statusCode = jedis.hlen(key); //返回一个key下面字段的个数
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
	 * 返回hash对象中的所有key
	 * @param key
	 * @return
	 */
	public static Set<String> hkeys(String key){
		JedisPool pool = null;
		Jedis jedis = null;
		Set<String> resultSet = null;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			resultSet = jedis.hkeys(key);	//返回map对象中的所有key
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);// 释放redis对象
			e.printStackTrace();
		} finally {
			// 返还到连接池
			RedisConfig.returnResource(pool, jedis);
		}

		return resultSet;
	}
	/**
	 * 返回hash对象中的所有value
	 * @param key
	 * @return
	 */
	public static List<String> hvals(String key){
		JedisPool pool = null;
		Jedis jedis = null;
		List<String> results = null;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			results = jedis.hvals(key);	//返回hash中所有的value
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);// 释放redis对象
			e.printStackTrace();
		} finally {
			// 返还到连接池
			RedisConfig.returnResource(pool, jedis);
		}

		return results;
	}
	
	/**
	 * 判断某个值是否存在
	 * @param key
	 * @param field
	 * @return
	 */
	public static Boolean hexists(String key,String field){
		JedisPool pool = null;
		Jedis jedis = null;
		Boolean statusCode = false;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			statusCode = jedis.hexists(key, field);	//判断hash中是否包含某个值
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);// 释放redis对象
			e.printStackTrace();
		} finally {
			// 返还到连接池
			RedisConfig.returnResource(pool, jedis);
		}

		return statusCode;
	}
}
