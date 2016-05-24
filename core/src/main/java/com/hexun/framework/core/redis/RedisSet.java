package com.hexun.framework.core.redis;

import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
/**
 * Redis Set 的相关操作
 * @author zhoudong
 *
 */
public class RedisSet extends RedisHash {
	/**
	 * 保存set
	 * @param key
	 * @param members
	 * @return
	 */
	public static Long sadd(String key,String... members){
		JedisPool pool = null;
		Jedis jedis = null;
		Long statusCode = 0L;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			statusCode = jedis.sadd(key, members);	//保存成set
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
	 * 保存set
	 * @param key
	 * @param members
	 * @return
	 */
	public static Long sadd(String key,Set<String> set){
		JedisPool pool = null;
		Jedis jedis = null;
		Long statusCode = 0L;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			statusCode = jedis.sadd(key, set.toArray(new String[set.size()]));	//保存成set
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
	 * 判断set中是否包含某值
	 * @param key
	 * @param member
	 * @return
	 */
	public static Boolean sismember(String key,String member){
		JedisPool pool = null;
		Jedis jedis = null;
		Boolean statusCode = false;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			statusCode = jedis.sismember(key, member);	//判断set中是否包含某个值
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
	 * 整个列表的值
	 * @param key
	 * @return
	 */
	public static Set<String> smembers(String key){
		JedisPool pool = null;
		Jedis jedis = null;
		Set<String> resultSet = null;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			resultSet = jedis.smembers(key);	//判断set中是否包含某个值
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
	 * 删除指定元素   返回成功删除的个数
	 * @param key
	 * @param members
	 * @return
	 */
	public static Long srem(String key,String... members){
		JedisPool pool = null;
		Jedis jedis = null;
		Long statusCode = 0L;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			statusCode = jedis.srem(key, members);//删除指定元素
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
	 * 出栈
	 * @param key
	 * @return
	 */
	public static String spop(String key){
		JedisPool pool = null;
		Jedis jedis = null;
		String statusCode = null;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			statusCode = jedis.spop(key);	//插入多个key.value
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
	 * 交集
	 * @param keys
	 * @return
	 */
	public static Set<String> sinter(String... keys){
		JedisPool pool = null;
		Jedis jedis = null;
		Set<String> resultSet = null;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			resultSet = jedis.sinter(keys);	//交集
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
	 * 并集
	 * @param keys
	 * @return
	 */
	public static Set<String> sunion(String... keys){
		JedisPool pool = null;
		Jedis jedis = null;
		Set<String> resultSet = null;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			resultSet = jedis.sunion(keys);	//并集
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
	 * 差集
	 * @param keys
	 * @return
	 */
	public static Set<String> sdiff(String... keys){
		JedisPool pool = null;
		Jedis jedis = null;
		Set<String> resultSet = null;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			resultSet = jedis.sdiff(keys);	//并集
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);// 释放redis对象
			e.printStackTrace();
		} finally {
			// 返还到连接池
			RedisConfig.returnResource(pool, jedis);
		}

		return resultSet;
	}
}
