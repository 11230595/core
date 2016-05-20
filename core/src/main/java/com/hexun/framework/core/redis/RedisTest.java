package com.hexun.framework.core.redis;

import java.util.List;

/**
 * 测试
 * @author zhoudong
 *
 */
public class RedisTest {
	private static String key = "zhou";
	private static String key1 = "zhou01";
	private static String key2 = "zhou02";
	private static String keyList = "keyList";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//System.out.println(set());//set
		//System.out.println(get());//get
		//System.out.println(update()); //update
		//System.out.println(del()); //del
		//System.out.println(exists()); //exists
		//System.out.println(mset()); //mset
		/*List<String> values = mget();
		for(String value : values){
			System.out.println(value);
		}*/
		//System.out.println(lpush()); //list
		
		System.out.println(llen()); //list的长度
		List<String> values = sort();
		for(String value : values){
			System.out.println(value);
		}
		
	}
	
	//保存 成功返回OK
	public static String set(){
		return RedisUtils.set(key, "zhoudong");
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
}
