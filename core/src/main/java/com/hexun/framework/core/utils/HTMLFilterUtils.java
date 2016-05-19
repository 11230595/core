package com.hexun.framework.core.utils;

 
import java.util.regex.Matcher;
import java.util.regex.Pattern;

 

/**
 * 过滤网页内容，也就是去掉网页标签
 * 
 * @author zhoudong
 * 
 */
public class HTMLFilterUtils {
	/**
	 * 过滤String中所有的html标签
	 */
	public static String getPureStr(String htmlText) {
		String reg = "<([^>]*)>";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(getNoStyle(htmlText));
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString().replaceAll("&nbsp;|\\\\n|\\\\t|\\n|\\t|\\\\r|\\r", "");
	}
	/**
	 * 过滤style
	 */
	public static String getNoStyle(String htmlText) {
		//忽略大小写reg = "(?i)<style.*?>.*?</style>";或Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		String reg = "<style[^>]*?>[\\s\\S]*?</style>";
		Pattern p = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(htmlText);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
	 
	
	public static void main(String[] args) {
	 
	}
 
}