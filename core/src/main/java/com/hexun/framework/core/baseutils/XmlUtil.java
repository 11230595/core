package com.hexun.framework.core.baseutils;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;



/**
 * 说明：xml格式数据处理
 * @author zhaominghao
 * @version 2016年8月12日 下午1:23:07
 */

public class XmlUtil {
	/** 
     * JavaBean转换成xml 
     * 默认编码UTF-8 
     * @param obj 
     * @param writer 
     * @return  
     */  
    public static String convertToXml(Object obj) {  
        return convertToXml(obj, "UTF-8");  
    }  
  
    /** 
     * JavaBean转换成xml 
     * @param obj 
     * @param encoding  
     * @return  
     */  
    public static String convertToXml(Object obj, String encoding) {  
        String result = null;  
        try {  
            JAXBContext context = JAXBContext.newInstance(obj.getClass());  
            Marshaller marshaller = context.createMarshaller();
            //xml是否自动换行，还是直接一行String
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //编码方式
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);  
  
            StringWriter writer = new StringWriter();  
            marshaller.marshal(obj, writer);  
            result = writer.toString();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        return result;  
    }  
  
    /** 
     * xml转换成JavaBean 
     * @param xml 
     * @param c 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static <T> T converyToJavaBean(String xml, Class<T> c) {  
        T t = null;  
        try {  
            JAXBContext context = JAXBContext.newInstance(c);  
            Unmarshaller unmarshaller = context.createUnmarshaller();  
            t = (T) unmarshaller.unmarshal(new StringReader(xml));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        return t;  
    }  
    
    /** 
     * xml转换成JavaBean，去掉root标记 
     * @param xml 
     * @param c 
     * @return 
     */  
    public static <T> T xmlToJBeanCutRoot(String xml, Class<T> c) {
    	xml = xml.replaceAll("<root>", "").replaceAll("</root>", "");

    	return converyToJavaBean(xml, c);
    }  
}
