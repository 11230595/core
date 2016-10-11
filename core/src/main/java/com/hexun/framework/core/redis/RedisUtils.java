package com.hexun.framework.core.redis;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.SortingParams;

import com.hexun.framework.core.page.Page;

/**
 * redis 操作工具类
 * 
 * 使用该工具类操作redis时 需要在项目的Resource目录下新建  redis.properties 文件
 * 
 * 内容如下：
 * ----------------------------
 * #最大线程数
 * redis.maxTotal=500
 * 
 * #地址
 * redis.url=10.4.12.147
 * 
 * #端口号
 * redis.port=6379
 * ----------------------------
 * @author zhoudong
 *
 */
public class RedisUtils extends RedisList{

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
	 * 更新数据，底层调用set方法
	 * @param key
	 * @param value
	 * @return Status code reply
	 */
	public static String update(String key, String value) {
		return set(key,value);
	}
	
	/**
	 * 删除数据，可一次传入多个key
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
			statusCode = jedis.del(keys); // 删除数据，可以同时删除多个
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
			statusCode = jedis.mset(keysValues);	//插入多个key.value
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
	 * @return 返回List<String>
	 */
	public static List<String> mget(String... keys) {
		JedisPool pool = null;
		Jedis jedis = null;
		List<String> results = null;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			results = jedis.mget(keys);	//插入多个key.value
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
	 * 自动过期，单位秒
	 * @param key
	 * @param seconds
	 * @param value
	 * @return
	 */
	public static String setex(String key, int seconds, String value){
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			value = jedis.setex(key,seconds,value); // 保存数据，指定时间后到期
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
	 * 分页查询
	 * @param key
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public static Page<String> findPage(String key,int pageNo, int pageSize){
		JedisPool pool = null;
		Jedis jedis = null;
		Page<String> page = new Page<String>();
		try {
			pool = RedisConfig.getPool(); // 获取连接池
			jedis = pool.getResource(); // 获取redis对象
			
			int start = (pageNo - 1) * pageSize;
			
			SortingParams sortingParams = new SortingParams();
			sortingParams.alpha();
			sortingParams.desc();
			sortingParams.limit(start, pageSize);
			
			List<String> results = sort(key, sortingParams);
			page.setList(results);
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
			page.setTotalCount(llen(key).intValue());
			
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);// 释放redis对象
			e.printStackTrace();
		} finally {
			// 返还到连接池
			RedisConfig.returnResource(pool, jedis);
		}

		return page;
	}
}
