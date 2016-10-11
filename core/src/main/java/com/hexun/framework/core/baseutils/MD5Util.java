package com.hexun.framework.core.baseutils;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;

public class MD5Util {
	
	public static String MD5key = "hexun";
	
	public final static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       

        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	/** 
	 * 生成待加密字符串,返回a=b&c=d这样的字符串 
	 *  
	 * @param map 
	 * @return 
	*/ 
	public  static  String createSignText(Map<String, String> map){ 
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

    public static void main(String[] args) {
        System.out.println(MD5Util.MD5("20121221"));
        System.out.println(MD5Util.MD5("加密"));
    }
}
