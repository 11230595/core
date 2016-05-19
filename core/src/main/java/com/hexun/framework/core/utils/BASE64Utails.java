package com.hexun.framework.core.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

/**
 * BASE64 加密解密
 * @author zhoudong
 *
 */
public class BASE64Utails {
	/** 
	 * BASE64解密 
	 * @author zhoudong
	 * @param key 解密字段
	 * @return 
	 */  
	public static String decryptBASE64(String key){  
		String str = "";
	    try {
	    	str = new String(Base64.decodeBase64(key.getBytes()),"utf-8");
			//str = new String((new BASE64Decoder()).decodeBuffer(key),"utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return str;
	}  
	/** 
	 * BASE64加密 
	 * @author zhoudong
	 * @param key 
	 * @return 
	 */  
	public static String encryptBASE64(String parm){  
		try {
			return Base64.encodeBase64String(StringUtils.isBlank(parm) ? "".getBytes() : parm.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}  
}
