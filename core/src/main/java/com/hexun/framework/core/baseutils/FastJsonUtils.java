package com.hexun.framework.core.baseutils;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 fastjson的加强版
 * Date:     2016年9月27日 下午4:44:30 <br/>
 * @author   lvhongli
 * @version  JDK 1.7
 */
public class FastJsonUtils {
	private static SerializeConfig mapping = new SerializeConfig();  
	private static String dateFormat;  
	static {  
		dateFormat = "yyyy-MM-dd HH:mm:ss";  
	}  

	/**
	 * 重写fastjson中的方法
	 */
	private ValueFilter filter = new ValueFilter() {
		@Override
		public Object process(Object obj, String s, Object v) {
			if (v == null)
				return "";
			return v;
		}
	};
  
    /** 
     * 对于null的的处理
     *  
     * @param jsonText 
     * @return 
     */  
    public static String toJSON(Object jsonText) {  
        return JSON.toJSONString(jsonText,  
        		SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteMapNullValue);  
    }  
  
    /** 
     * 自定义时间格式 
     *  
     * @param jsonText 
     * @return 
     */  
    public static String toJSON(String dateFormat, String jsonText) {  
        mapping.put(Date.class, new SimpleDateFormatSerializer(dateFormat));  
        return JSON.toJSONString(jsonText, mapping,SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteMapNullValue);  
    }  
}
