package com.hexun.framework.core.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hexun.framework.core.page.Page;

/**
 * 测试
 * @author zhoudong
 *
 */
public class RedisTest {
	private static String key = "zhou";
	private static String key1 = "zhou01";
	private static String key2 = "zhou02";
	private static String keyex = "keyex";
	private static String keyList = "keyList";
	private static String keySet = "keySet";
	private static String keySet1 = "keySet01";
	private static String keyHash = "keyHash";
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//System.out.println(set());//set
		//System.out.println(setex()); //set 指定过期时间，单位秒
		//System.out.println(get());//get
		//System.out.println(update()); //update
		//System.out.println(del()); //del
		//System.out.println(exists()); //exists
		//System.out.println(mset()); //mset
		/*List<String> values = mget();
		for(String value : values){
			System.out.println(value);
		}*/
		///////////////////////////////////////////////list///////////////////////////////////////////
		//System.out.println(lpush()); //list
		
		//System.out.println(llen()); //list的长度
		/*List<String> values = sort();
		for(String value : values){
			System.out.println(value);
		}*/
		//System.out.println(lset());//修改指定下标的值
		//System.out.println(lindex()); //获取指定下标的值
		//System.out.println(lrem()); //删除指定下标的值
		/*List<String> values = lrange();
		for(String value : values){
			System.out.println(value);
		}*/
		//////////////////////////////////////////////set////////////////////////////////////////////
		//System.out.println(sadd()); //set add
		//System.out.println(sismember()); //set中是否包含某值
		/*//遍历set
		Set<String> sets = smembers();
		for (String str : sets){
			System.out.println("遍历set>>" + str);
		}*/
		//System.out.println(srem());//删除
		//System.out.println(spop());//出栈
		/*//交集
		Set<String> sinter = sinter();
		for (String str : sinter){
			System.out.println("交集>>" + str);
		}
		//并集
		Set<String> sunion = sunion();
		for (String str : sunion){
			System.out.println("并集>>" + str);
		}
		//差集
		Set<String> sdiff = sdiff();
		for (String str : sdiff){
			System.out.println("差集>>" + str);
		}*/
		
		//System.out.println(hmset());//map中添加数据
		
		/*List<String> values = hmget();//map中取数据
		for(String value : values){
			System.out.println(value);
		}*/
		
		//System.out.println(hdel());//从hash中删除某字段
		//System.out.println(hlen()); //字段的个数
		
		/*Set<String> hkeys = hkeys(); //所有的keys
		for (String key : hkeys){
			System.out.println("所有的key>>" + key);
		}*/
		
		/*List<String> values = hvals(); //所有的value
		for(String value : values){
			System.out.println("所有的value>>" + value);
		}*/
		
		//System.out.println(hexists());
		
		/////////////////////////////////////////////分页///////////////////////////////////////////
		/*Page<String> page = findPage();
		System.out.println(JSON.toJSONString(page));*/
	}
	
	//保存 成功返回OK
	public static String set(){
		return RedisUtils.set(key, "zhoudong");
	}
	//保存，指定时间后过期
	public static String setex(){
		return RedisUtils.setex(keyex, 15, "哈哈哈哈");
	}
	//获取
	public static String get(){
		return RedisUtils.get(key);
	}
	//覆盖、更新 key不存在直接插入  成功返回OK
	public static String update(){
		return RedisUtils.update(key,"zhoudong01");
	}
	//删除，返回删除成功的个数
	public static Long del(){
		return RedisUtils.del(key,key1,key2);
	}
	//key是否存在    返回 true 或  false
	public static Boolean exists(){
		return RedisUtils.exists(key);
	}
	//保存多个 成功返回OK
	public static String mset(){
		RedisUtils.flushDB();//先清空库
		return RedisUtils.mset(key, "zhoudong",key1,"zhoudong01",key2,"zhoudong02");
	}
	//同时查询多个key
	public static List<String> mget(){
		return RedisUtils.mget(key,key1,key2);
	}
	
	//------------------------------------list start---------------------------------------------------
	
	//存储为list，返回存储成功的个数
	public static Long lpush(){
		Long statusCode = RedisUtils.lpush(keyList, "v01","v02","v03","v04","v05","v06");
		//RedisUtils.lpush(keyList, new String[]{"v01","v02","v03","v04","v05","v06"});
		return statusCode;
	}
	//数组长度
	public static Long llen(){
		return RedisUtils.llen(keyList);
	}
	//排序
	public static List<String> sort(){
		List<String> list = RedisUtils.sort(keyList);
		return list;
	}
	//修改指定下标的值
	public static String lset(){
		return RedisUtils.lset(keyList, 0, "v006");
	}
	//获取指定下标的值
	public static String lindex(){
		return RedisUtils.lindex(keyList, 0);
	}
	//删除指定下标的值
	public static Long lrem(){
		return RedisUtils.lrem(keyList, 0, "v006");
	}
	//取指定范围的值
	public static List<String> lrange(){
		return RedisUtils.lrange(keyList, 0, 2);
	}
	//----------------------------------list end-------------------------------------------------------
	
	//----------------------------------set start------------------------------------------------------
	//存储为set，返回存储成功的个数
	public static Long sadd(){
		//Long statusCode = RedisUtils.sadd(keySet, "v01","v02","v03","v04","v05","v06");
		Long statusCode = RedisUtils.sadd(keySet1, "v03","v04","v06","v07","v08","v09");
		return statusCode;
	}
	//set中是否包含某值
	public static Boolean sismember(){
		return RedisUtils.sismember(keySet, "v01");
	}
	//整个列表的值
	public static Set<String> smembers(){
		return RedisUtils.smembers(keySet);
	}
	//删除指定元素 返回删除成功的个数
	public static Long srem(){
		return RedisUtils.srem(keySet, "v01"); //参数支持多个 RedisUtils.srem(keySet, "v01","v02","v03");
	}
	//出栈
	public static String spop(){
		return RedisUtils.spop(keySet); //出栈
	}
	//交集
	public static Set<String> sinter(){
		return RedisUtils.sinter(keySet,keySet1);
	}
	//并集
	public static Set<String> sunion(){
		return RedisUtils.sunion(keySet,keySet1);
	}
	//差集
	public static Set<String> sdiff(){
		return RedisUtils.sdiff(keySet,keySet1);
	}
	
	//---------------------------------hash start-----------------------------------------------------
	//往hash中存数据
	public static String hmset(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("userName", "aaa");
		map.put("userCode", "bbb");
		map.put("sex", "0");
		return RedisUtils.hmset(keyHash, map);
	}
	//从map中取数据
	public static List<String> hmget(){
		return RedisUtils.hmget(keyHash, "userName","userCode");
	}
	//从map中删除某字段
	public static Long hdel(){
		return RedisUtils.hdel(keyHash, "sex");
	}
	//hash中某key下面字段的个数
	public static Long hlen(){
		return RedisUtils.hlen(keyHash);
	}
	//所有的key
	public static Set<String> hkeys(){
		return RedisUtils.hkeys(keyHash);
	}
	//获取所有的values
	public static List<String> hvals(){
		return RedisUtils.hvals(keyHash);
	}
	//判断字段是否存在，存在返回true
	public static Boolean hexists(){
		return RedisUtils.hexists(keyHash, "userCode");
	}
	
	
	//--------------------------------------分页---------------------------------------------------------
	public static Page<String> findPage(){
		return RedisUtils.findPage(keyList, 2, 3);
	}
}
