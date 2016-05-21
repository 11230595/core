package com.hexun.framework.core.redis;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.SortingParams;

public class RedisList {
	/**
	 * 保存list
	 * @return Status code reply
	 */
	public static Long lpush(String key,String... values) {
		JedisPool pool = null;
		Jedis jedis = null;
		Long statusCode = 0L;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			
			statusCode = jedis.lpush(key, values);	//保存成list
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
	 * 数组的长度
	 * @param key
	 * @return
	 */
	public static Long llen(String key){
		JedisPool pool = null;
		Jedis jedis = null;
		Long result = 0L;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			result = jedis.llen(key);	//查询数组的长度
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);// 释放redis对象
			e.printStackTrace();
		} finally {
			// 返还到连接池
			RedisConfig.returnResource(pool, jedis);
		}

		return result;
	}
	/**
	 * 排序
	 * @param key
	 * @param sortingParameters 可传可不传，传的时候最多一个，不传的时候默认升序
	 * @return
	 */
	public static List<String> sort(String key,SortingParams... sortingParameters){
		JedisPool pool = null;
		Jedis jedis = null;
		List<String> results = null;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			if(sortingParameters.length > 0){
				sortingParameters[0].alpha();
				results = jedis.sort(key, sortingParameters[0]);	//排序，自定义排序规则
			}else {
				SortingParams sorting = new SortingParams();
				sorting.alpha();
				results = jedis.sort(key,sorting);	//排序，默认是升序
			}
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
	 * 修改list中的某个值
	 * @param key
	 * @param index
	 * @param value
	 * @return
	 */
	public static String lset(String key,long index,String value){
		JedisPool pool = null;
		Jedis jedis = null;
		String statusCode = null;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			statusCode = jedis.lset(key, index, value);	//修改list中的某个值
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
	 * 获取列表执行下标的值
	 * @param key
	 * @param index
	 * @return
	 */
	public static String lindex(String key,long index){
		JedisPool pool = null;
		Jedis jedis = null;
		String result = null;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			result = jedis.lindex(key, index);	//获取列表执行下标的值
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);// 释放redis对象
			e.printStackTrace();
		} finally {
			// 返还到连接池
			RedisConfig.returnResource(pool, jedis);
		}

		return result;
	}
	/**
	 * 删除列表指定下标的值
	 * @param key
	 * @param count
	 * @param value
	 * @return
	 */
	public static Long lrem(String key,long count,String value){
		JedisPool pool = null;
		Jedis jedis = null;
		Long result = 0L;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			result = jedis.lrem(key, count, value);	//删除列表指定下标的值
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);// 释放redis对象
			e.printStackTrace();
		} finally {
			// 返还到连接池
			RedisConfig.returnResource(pool, jedis);
		}

		return result;
	}
	/**
	 * 取指定下范围的值
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<String> lrange(String key,long start,long end){
		JedisPool pool = null;
		Jedis jedis = null;
		List<String> results = null;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			results = jedis.lrange(key, start, end);
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
