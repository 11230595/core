package com.hexun.framework.core.properties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * 
 * 从properties中获取数据
 * @author zhoudong
 *
 */
public class PropertiesUtils {
	/**
	 * 读取配置文件，程序里面可以使用
	 * PropertiesUtils.getString("文件中的key");
	 * 来获取配置文件中配置的数据
	 */
	public static Configuration config =null;
 	static{
		try {
			config= new PropertiesConfiguration("config.properties");
		}catch(Exception e){
			e.printStackTrace();
		}
 	}
 	
 	/**
 	 * 获取String字符串
 	 * @author zhoudong
 	 * @param key
 	 * @return
 	 */
 	public static String getString(String key){
 		return config.getString(key);
 	}
 	/**
 	 * 获取list
 	 * @author zhoudong
 	 * @param key
 	 * @return
 	 */
 	public static List<String> getList(String key){
 		return config.getList(key);
 	}
 	/**
 	 * 获取double
 	 * @author zhoudong
 	 * @param key
 	 * @return
 	 */
 	public static double getDouble(String key){
 		return config.getDouble(key);
 	}
 	
 	/**
 	 * 获取int
 	 * @author zhoudong
 	 * @param key
 	 * @return
 	 */
 	public static int getInt(String key){
 		return config.getInt(key);
 	}
 	
 	/**
 	 * 获取所有的key
 	 * @author zhoudong
 	 * @param key
 	 * @return
 	 */
 	public static List<String> getKeys(){
 		Iterator iterator = config.getKeys();
 		List<String> list = new ArrayList<String>();
 		while (iterator.hasNext()) {
			list.add(String.valueOf(iterator.next()));
		}
 		return list;
 	}
 	
}
