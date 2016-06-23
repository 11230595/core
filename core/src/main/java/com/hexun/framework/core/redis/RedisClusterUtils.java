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
public class RedisClusterUtils extends RedisClusterConfig {

	/**
	 * 获取jedisCluster对象
	 * 
	 * @return
	 */
	public static JedisCluster getJc() {
		return RedisClusterConfig.getJc();
	}

	/**
	 * 获取数据
	 * 
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		if (key.equals("Thread-10")) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return getJc().get(key); // 获取数据
	}

	/**
	 * 存储数据 如果key已经存在，直接覆盖原来的数据
	 * 
	 * @param key
	 * @param value
	 * @return Status code reply
	 */
	public static String set(String key, String value) {
		return getJc().set(key, value); // 存储数据
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
		return getJc().del(keys); // 删除数据，可以同时删除多个;
	}

	/**
	 * 清空数据
	 * 
	 * @return Status code reply
	 */
	@SuppressWarnings("deprecation")
	public static String flushDB() {
		return getJc().flushDB(); // 清空数据;
	}

	/**
	 * 判断key是否存在
	 * 
	 * @return Status code reply
	 */
	public static Boolean exists(String key) {
		return getJc().exists(key); // 判断key是否存在;
	}

	/**
	 * 同时插入多个key,value
	 * 
	 * @return Status code reply
	 */
	public static String mset(String... keysValues) {
		return getJc().mset(keysValues); // 插入多个key.value;
	}

	/**
	 * 同时获取多个key对应的value
	 * 
	 * @return Status code reply
	 */
	public static List<String> mget(String... keys) {
		return getJc().mget(keys); // 插入多个key.value;
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
		return getJc().setex(key, seconds, value); // 保存数据，指定时间后到期;
	}

	// ---------------------------list----------------------------------
	/**
	 * 保存list
	 * 
	 * @return Status code reply
	 */
	public static Long lpush(String key, String... values) {
		return getJc().lpush(key, values); // 保存成list;
	}

	/**
	 * 保存list
	 * 
	 * @return Status code reply
	 */
	public static Long lpush(String key, List<String> list) {
		return getJc().lpush(key, list.toArray(new String[list.size()])); // 保存成list;
	}

	/**
	 * 数组的长度
	 * 
	 * @param key
	 * @return
	 */
	public static Long llen(String key) {
		return getJc().llen(key); // 查询数组的长度;
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
		if (sortingParameters.length > 0) {
			sortingParameters[0].alpha();
			results = getJc().sort(key, sortingParameters[0]); // 排序，自定义排序规则
		} else {
			SortingParams sorting = new SortingParams();
			sorting.alpha();
			results = getJc().sort(key, sorting); // 排序，默认是升序
		}
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
		return getJc().lset(key, index, value); // 修改list中的某个值;
	}

	/**
	 * 获取列表指定下标的值
	 * 
	 * @param key
	 * @param index
	 * @return
	 */
	public static String lindex(String key, long index) {
		return getJc().lindex(key, index); // 获取列表执行下标的值;
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
		return getJc().lrem(key, count, value); // 删除列表指定下标的值;
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
		return getJc().lrange(key, start, end);
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
		return getJc().sadd(key, members);	//保存成set;
	}
	
	/**
	 * 保存set
	 * @param key
	 * @param members
	 * @return
	 */
	public static Long sadd(String key,Set<String> set){
		return getJc().sadd(key, set.toArray(new String[set.size()]));	//保存成set;
	}
	
	/**
	 * 判断set中是否包含某值
	 * @param key
	 * @param member
	 * @return
	 */
	public static Boolean sismember(String key,String member){
		return getJc().sismember(key, member);	//判断set中是否包含某个值;
	}
	/**
	 * 整个列表的值
	 * @param key
	 * @return
	 */
	public static Set<String> smembers(String key){
		return getJc().smembers(key);	//判断set中是否包含某个值;
	}
	/**
	 * 删除指定元素   返回成功删除的个数
	 * @param key
	 * @param members
	 * @return
	 */
	public static Long srem(String key,String... members){
		return getJc().srem(key, members);//删除指定元素;
	}
	/**
	 * 出栈
	 * @param key
	 * @return
	 */
	public static String spop(String key){
		return getJc().spop(key);	//插入多个key.value;
	}
	/**
	 * 交集
	 * @param keys
	 * @return
	 */
	public static Set<String> sinter(String... keys){
		return getJc().sinter(keys);	//交集;
	}
	/**
	 * 并集
	 * @param keys
	 * @return
	 */
	public static Set<String> sunion(String... keys){
		return getJc().sunion(keys);	//并集;
	}
	/**
	 * 差集
	 * @param keys
	 * @return
	 */
	public static Set<String> sdiff(String... keys){
		return getJc().sdiff(keys);	//并集;
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
		return getJc().hmset(key, hash);	//插入hash;
	}
	/**
	 * hash 结构取数据
	 * @param key
	 * @param fields
	 * @return
	 */
	public static List<String> hmget(String key,String... fields){
		return getJc().hmget(key, fields);	//从hash取数据，取出的是list的结构;
	}
	/**
	 * hash 删除
	 * @param key
	 * @param fields
	 * @return
	 */
	public static Long hdel(String key,String... fields){
		return getJc().hdel(key,fields); // 删除数据，可以同时删除多个;
	}
	/**
	 * 返回一个key下面字段的个数
	 * @param key
	 * @return
	 */
	public static Long hlen(String key){
		return getJc().hlen(key); //返回一个key下面字段的个数
	}
	/**
	 * 返回hash对象中的所有key
	 * @param key
	 * @return
	 */
	public static Set<String> hkeys(String key){
		return getJc().hkeys(key);	//返回map对象中的所有key;
	}
	/**
	 * 返回hash对象中的所有value
	 * @param key
	 * @return
	 */
	public static List<String> hvals(String key){
		return getJc().hvals(key);	//返回hash中所有的value
	}
	
	/**
	 * 判断某个值是否存在
	 * @param key
	 * @param field
	 * @return
	 */
	public static Boolean hexists(String key,String field){
		return getJc().hexists(key, field);	//判断hash中是否包含某个值;
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

		SortingParams sortingParams = new SortingParams();
		sortingParams.alpha();
		if ("DESC".equals(sort))
			sortingParams.desc();
		else
			sortingParams.asc();
		sortingParams.limit(start, pageSize);

		List<String> results = RedisClusterConfig.getJc().sort(key,
				sortingParams);
		page.setList(results);
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setTotalCount(RedisClusterConfig.getJc().llen(key).intValue());

		return page;
	}

	public static void main(String[] args) {
		findPage("keyList", 1, 10, RedisSortEnum.DESC);
	}
}
