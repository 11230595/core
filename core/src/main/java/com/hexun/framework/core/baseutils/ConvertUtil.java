package com.hexun.framework.core.baseutils;

import java.sql.Timestamp;
import java.util.Date;



public class ConvertUtil {

	public static boolean IntToBoolean(int value)  
    {
		return value > 0;
    }  

	
	/**
	 * 通过字符串转换成相应的长整型，并返回。
	 * @param strValue String 待转换的字符串
	 * @return long 转换完成的长整型
	 * */
	public static long StrToLong(String strValue)
	{
		if (null == strValue)
		{
			return 0;
		}
		long lValue = 0;
		try
		{
			lValue = new java.lang.Long(strValue.trim()).longValue();
		}
		catch (Exception ex)
		{
			lValue = 0;
		}
		return lValue;
	}
	
	public static Date TimestampToDate(Timestamp value)
	{
		Date date = new Date();   
		try {   
			date = value;   
			//System.out.println(date);
		} catch (Exception e) {   
			e.printStackTrace();   
		} 	
		return date;
	}
	
	public static Timestamp DateToTimestamp(Date value){
		Timestamp result = null;
		try{
			String strDate = DateUtil.getPlusTime(value);
			result = DateUtil.getDateByString(strDate);
		} 
		catch (Exception e) {   
			e.printStackTrace();   
		} 	
		
		return result;
	} 
}
