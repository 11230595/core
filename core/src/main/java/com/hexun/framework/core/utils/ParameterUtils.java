package com.hexun.framework.core.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.hexun.framework.core.constants.Constants;

/**
 * map工具类
 * @author zhoudong
 *
 */
public class ParameterUtils {
	/**
	 * 生产加密字符串,返回a=b&c=d这样的字符串
	 * 
	 * @param map
	 * @return
	 */
	public static String createSignText(Map<String, String> map){
		String[] params = map2StringArray(map);
		Arrays.sort(params);
		return joinParameters(params);
	}
	
	private static String joinParameters(String[] parameters){
		StringBuilder parameter = new StringBuilder();
		int i=0;
		for(String param : parameters){
			i++;
			if(i<parameters.length){
				parameter.append(param).append("&");
			}else {
				parameter.append(param);
			}
		}
		return parameter.toString();
	}
	
	/**
	 * map转数组
	 * @param map
	 * @return
	 */
	private static String[] map2StringArray(Map<String, String> map){
		String[] strs = new String[map.size()];
		int i=0;
		for(Map.Entry<String, String> m : map.entrySet()){
			strs[i] = m.getKey()+"="+m.getValue();
			i++;
		}
		return strs;
	}
	/**
	 * 转换map
	 * @param reqMap
	 * @return
	 */
	public static Map<String, String> convertMap(Map<String, String[]> reqMap){
		Map<String, String> tempMap = new HashMap<String, String>();
        Set<Entry<String, String[]>> set = reqMap.entrySet();  
        Iterator<Entry<String, String[]>> it = set.iterator();  
        while (it.hasNext()) {  
            Entry<String, String[]> entry = it.next();  
            for (String str : entry.getValue()) {  
                tempMap.put(entry.getKey(), str);
            }  
        } 
        return tempMap;
 
	}
	/**
	 * 排序
	 * @param map
	 * @return
	 */
	public static Map<String, String> sort(Map<String, String> map){
		List<Map.Entry<String,String>> list = new ArrayList<Map.Entry<String,String>>(map.entrySet());
        Collections.sort(list,new Comparator<Map.Entry<String,String>>() {
            //升序排序
            public int compare(Entry<String, String> o1,
                    Entry<String, String> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
 
        });
        
        map.clear();
        
        for(Map.Entry<String,String> mapping:list){ 
        	map.put(mapping.getKey(), mapping.getValue());
        } 
        
        return map;
        
	}
	
    /**
     * 打印map
     * @param data
     * @return
     */
    public static String toString(Map<?, ?> data) {
        if (data == null || data.isEmpty()) {
            return Constants.empty;
        }
        Iterator<?> it = data.keySet().iterator();
        String key = null;
        String value = null;
        StringBuffer sb = new StringBuffer();
        boolean first = true;
        while (it.hasNext()) {
            key = (String) it.next();
            value = (String) data.get(key);
            if (!first) {
                sb.append(Constants.and);
            }
            sb.append(key).append(Constants.eq).append(value);
            first = false;
        }
        return sb.toString();
    }
}

