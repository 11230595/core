package com.hexun.framework.core.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.SortingParams;

import com.hexun.framework.core.page.Page;
/**
 * 使用jedis操作redis集群工具类
 * @author zhoudong
 *
 */
public class RedisClusterUtils {

	/**
	 * 获取jedisCluster对象 从连接池获取
	 * 
	 * @return
	 */
	public static JedisCluster getJc() {
		return RedisClusterPool.getJcByPool();
	}
	
	public static void returnJcPool(JedisCluster jc){
		RedisClusterPool.returnJcToPool(jc);
	}
	

	/**
	 * 获取数据
	 * 此方法仅用于多线程测试
	 * @param key
	 * @return
	 */
	public static String getTest(String key) {
		/*
		try {
			if(key.startsWith("Thread-2") || key.startsWith("Thread-3"))
				Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		JedisCluster jc = getJc();
		String result = jc.get(key); // 获取数据
		returnJcPool(jc);
		return result;
	}
	
	/**
	 * 获取数据
	 * 
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		JedisCluster jc = getJc();
		String result = jc.get(key); // 获取数据
		returnJcPool(jc);
		return result;
	}

	/**
	 * 存储数据 如果key已经存在，直接覆盖原来的数据
	 * 
	 * @param key
	 * @param value
	 * @return Status code reply
	 */
	public static String set(String key, String value) {
		JedisCluster jc = getJc();
		String result = jc.set(key, value); // 存储数据
		returnJcPool(jc);
		return result;
	}

	/**
	 * 更新数据
	 * 
	 * @param key
	 * @param value
	 * @return Status code reply
	 */
	public static String update(String key, String value) {
		return set(key, value);
	}

	/**
	 * 删除数据
	 * 
	 * @param key
	 * @return
	 */
	public static Long del(String... keys) {
		JedisCluster jc = getJc();
		Long result = jc.del(keys);// 删除数据，可以同时删除多个;
		returnJcPool(jc);
		return  result;
	}

	/**
	 * 判断key是否存在
	 * 
	 * @return Status code reply
	 */
	public static Boolean exists(String key) {
		JedisCluster jc = getJc();
		Boolean result = jc.exists(key); // 判断key是否存在;
		returnJcPool(jc);
		return result;
	}

	/**
	 * 同时插入多个key,value
	 * 
	 * @return Status code reply
	 */
	public static String mset(String... keysValues) {
		JedisCluster jc = getJc();
		String result = jc.mset(keysValues); // 插入多个key.value;
		returnJcPool(jc);
		return result;
	}

	/**
	 * 同时获取多个key对应的value
	 * 
	 * @return Status code reply
	 */
	public static List<String> mget(String... keys) {
		JedisCluster jc = getJc();
		List<String> result = jc.mget(keys); // 插入多个key.value;
		returnJcPool(jc);
		return result;
	}

	/**
	 * 自动过期，单位秒
	 * 
	 * @param key
	 * @param seconds
	 * @param value
	 * @return
	 */
	public static String setex(String key, int seconds, String value) {
		JedisCluster jc = getJc();
		String result = jc.setex(key, seconds, value); // 保存数据，指定时间后到期;
		returnJcPool(jc);
		return result;
	}

	// ---------------------------list----------------------------------
	/**
	 * 保存list
	 * 
	 * @return Status code reply
	 */
	public static Long lpush(String key, String... values) {
		JedisCluster jc = getJc();
		Long result = jc.lpush(key, values); // 保存成list;
		returnJcPool(jc);
		return result;
	}

	/**
	 * 保存list
	 * 
	 * @return Status code reply
	 */
	public static Long lpush(String key, List<String> list) {
		JedisCluster jc = getJc();
		Long result = jc.lpush(key, list.toArray(new String[list.size()])); // 保存成list;
		returnJcPool(jc);
		return result;
	}

	/**
	 * 数组的长度
	 * 
	 * @param key
	 * @return
	 */
	public static Long llen(String key) {
		JedisCluster jc = getJc();
		Long result = jc.llen(key); // 查询数组的长度;
		returnJcPool(jc);
		return result;
	}

	/**
	 * 排序
	 * 
	 * @param key
	 * @param sortingParameters
	 *            可传可不传，传的时候最多一个，不传的时候默认升序
	 * @return
	 */
	public static List<String> sort(String key,
			SortingParams... sortingParameters) {
		List<String> results = null;
		JedisCluster jc = getJc();
		if (sortingParameters.length > 0) {
			sortingParameters[0].alpha();
			results = jc.sort(key, sortingParameters[0]); // 排序，自定义排序规则
		} else {
			SortingParams sorting = new SortingParams();
			sorting.alpha();
			results = jc.sort(key, sorting); // 排序，默认是升序
		}
		returnJcPool(jc);
		return results;
	}

	/**
	 * 修改list中的某个值
	 * 
	 * @param key
	 * @param index
	 * @param value
	 * @return
	 */
	public static String lset(String key, long index, String value) {
		JedisCluster jc = getJc();
		String result = jc.lset(key, index, value); // 修改list中的某个值;
		returnJcPool(jc);
		return result;
	}

	/**
	 * 获取列表指定下标的值
	 * 
	 * @param key
	 * @param index
	 * @return
	 */
	public static String lindex(String key, long index) {
		JedisCluster jc = getJc();
		String result = jc.lindex(key, index); // 获取列表执行下标的值;
		returnJcPool(jc);
		return result;
	}

	/**
	 * 删除列表指定下标的值
	 * 
	 * @param key
	 * @param count
	 * @param value
	 * @return
	 */
	public static Long lrem(String key, long count, String value) {
		JedisCluster jc = getJc();
		Long result = jc.lrem(key, count, value); // 删除列表指定下标的值;
		returnJcPool(jc);
		return result;
	}

	/**
	 * 取指定下范围的值
	 * 
	 * @param key
	 * @param start
	 *            从0开始
	 * @param end
	 *            当end为-1时，取全部数据
	 * @return
	 */
	public static List<String> lrange(String key, long start, long end) {
		JedisCluster jc = getJc();
		List<String> result = jc.lrange(key, start, end); //取指定下范围的值
		returnJcPool(jc);
		return result;
	}

	// ---------------------------------------list end---------------------------------
	//----------------------------------set------------------------------------------
	/**
	 * 保存set
	 * @param key
	 * @param members
	 * @return
	 */
	public static Long sadd(String key,String... members){
		JedisCluster jc = getJc();
		Long result = jc.sadd(key, members);	//保存成set;
		returnJcPool(jc);
		return result;
	}
	
	/**
	 * 保存set
	 * @param key
	 * @param members
	 * @return
	 */
	public static Long sadd(String key,Set<String> set){
		JedisCluster jc = getJc();
		Long result = jc.sadd(key, set.toArray(new String[set.size()]));	//保存成set;
		returnJcPool(jc);
		return result;
	}
	
	/**
	 * 判断set中是否包含某值
	 * @param key
	 * @param member
	 * @return
	 */
	public static Boolean sismember(String key,String member){
		JedisCluster jc = getJc();
		Boolean result = jc.sismember(key, member);	//判断set中是否包含某个值;
		returnJcPool(jc);
		return result;
	}
	/**
	 * 整个列表的值
	 * @param key
	 * @return
	 */
	public static Set<String> smembers(String key){
		JedisCluster jc = getJc();
		Set<String> result = jc.smembers(key);	//判断set中是否包含某个值;
		returnJcPool(jc);
		return result;
	}
	/**
	 * 删除指定元素   返回成功删除的个数
	 * @param key
	 * @param members
	 * @return
	 */
	public static Long srem(String key,String... members){
		JedisCluster jc = getJc();
		Long result = jc.srem(key, members);//删除指定元素;
		returnJcPool(jc);
		return result;
	}
	/**
	 * 出栈
	 * @param key
	 * @return
	 */
	public static String spop(String key){
		JedisCluster jc = getJc();
		String result = jc.spop(key);	//插入多个key.value;
		returnJcPool(jc);
		return result;
	}
	/**
	 * 交集
	 * @param keys
	 * @return
	 */
	public static Set<String> sinter(String... keys){
		JedisCluster jc = getJc();
		Set<String> result = jc.sinter(keys);	//交集;
		returnJcPool(jc);
		return result;
	}
	/**
	 * 并集
	 * @param keys
	 * @return
	 */
	public static Set<String> sunion(String... keys){
		JedisCluster jc = getJc();
		Set<String> result = jc.sunion(keys);	//并集;
		returnJcPool(jc);
		return result;
	}
	/**
	 * 差集
	 * @param keys
	 * @return
	 */
	public static Set<String> sdiff(String... keys){
		JedisCluster jc = getJc();
		Set<String> result = jc.sdiff(keys);	//并集;
		returnJcPool(jc);
		return result;
	}
	//------------------------------------set end----------------------------------------
	
	//------------------------------------hash------------------------------------------
	/**
	 * hash 结构添加数据
	 * @param key
	 * @param hash
	 * @return
	 */
	public static String hmset(String key,Map<String,String> hash){
		JedisCluster jc = getJc();
		String result = jc.hmset(key, hash);	//插入hash;
		returnJcPool(jc);
		return result;
	}
	/**
	 * hash 结构取数据
	 * @param key
	 * @param fields
	 * @return
	 */
	public static List<String> hmget(String key,String... fields){
		JedisCluster jc = getJc();
		List<String> result = jc.hmget(key, fields);	//从hash取数据，取出的是list的结构;
		returnJcPool(jc);
		return result;
	}
	/**
	 * hash 删除
	 * @param key
	 * @param fields
	 * @return
	 */
	public static Long hdel(String key,String... fields){
		JedisCluster jc = getJc();
		Long result = jc.hdel(key,fields); // 删除数据，可以同时删除多个;
		returnJcPool(jc);
		return result;
	}
	/**
	 * 返回一个key下面字段的个数
	 * @param key
	 * @return
	 */
	public static Long hlen(String key){
		JedisCluster jc = getJc();
		Long result = jc.hlen(key); //返回一个key下面字段的个数
		returnJcPool(jc);
		return result;
	}
	/**
	 * 返回hash对象中的所有key
	 * @param key
	 * @return
	 */
	public static Set<String> hkeys(String key){
		JedisCluster jc = getJc();
		Set<String> result = jc.hkeys(key);	//返回map对象中的所有key;
		returnJcPool(jc);
		return result;
	}
	/**
	 * 返回hash对象中的所有value
	 * @param key
	 * @return
	 */
	public static List<String> hvals(String key){
		JedisCluster jc = getJc();
		List<String> result = jc.hvals(key);	//返回hash中所有的value
		returnJcPool(jc);
		return result;
	}
	
	/**
	 * 判断某个值是否存在
	 * @param key
	 * @param field
	 * @return
	 */
	public static Boolean hexists(String key,String field){
		JedisCluster jc = getJc();
		Boolean result = jc.hexists(key, field);	//判断hash中是否包含某个值;
		returnJcPool(jc);
		return result;
	}
	//---------------------------------------------hash end----------------------------------
	
	/**
	 * 分页查询
	 * 
	 * @param key
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public static Page<String> findPage(String key, int pageNo, int pageSize,
			RedisSortEnum sort) {
		Page<String> page = new Page<String>();
		int start = (pageNo - 1) * pageSize;

		JedisCluster jc = getJc();
		SortingParams sortingParams = new SortingParams();
		sortingParams.alpha();
		if ("DESC".equals(sort))
			sortingParams.desc();
		else
			sortingParams.asc();
		sortingParams.limit(start, pageSize);

		List<String> results = jc.sort(key,
				sortingParams);
		page.setList(results);
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setTotalCount(jc.llen(key).intValue());
		
		returnJcPool(jc);
		
		return page;
	}

	public static void main(String[] args) {
		findPage("keyList", 1, 10, RedisSortEnum.DESC);
	}
}
