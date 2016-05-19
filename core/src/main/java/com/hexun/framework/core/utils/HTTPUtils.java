package com.hexun.framework.core.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.apache.commons.io.IOUtils;

/**
 * http 请求工具类
 * 
 * @author zhoudong
 * 
 */
public class HTTPUtils {
	/**
	 * 发送get请求
	 * @author zhoudong
	 * @return
	 */
	public static String sendGet(String url) {
		StringBuilder result = new StringBuilder();
		HttpURLConnection connection = null;
		BufferedReader reader = null;
		try {
			URL get = new URL(url);
			connection = (HttpURLConnection) get.openConnection();

			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

			connection.connect();
			reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String lines;
			while ((lines = reader.readLine()) != null) {
				result.append(lines);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(reader);
			if (connection != null) {
				connection.disconnect();
			}
		}
		return result.toString();
	}
	
	
	/**
     * 向指定 URL 发送POST方法的请求
     * @author zhoudong
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String, Object> param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            
            StringBuilder sb = new StringBuilder();
            for(Map.Entry<String, Object> entry : param.entrySet()) {
            	sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            
            // 发送请求参数
            out.print(sb);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
        	IOUtils.closeQuietly(out);
        	IOUtils.closeQuietly(in);
        }
        return result;
    }    
    
    /**
	 * @param commString 需要发送的url参数串
	 * @param address 需要发送的url地址
	 * @return rec_string 返回的自定义格式的串
	 * @catch Exception
	 */
	public static String postURL(String address,Map<String, Object> param) {
		String rec_string = "";
		URL url = null;
		HttpURLConnection urlConn = null;
		try {
			/* 得到url地址的URL类 */
			url = new URL(address);
			/* 获得打开需要发送的url连接 */
			urlConn = (HttpURLConnection) url.openConnection();
			/* 设置连接超时时间 */
			urlConn.setConnectTimeout(30000);
			/* 设置读取响应超时时间 */
			urlConn.setReadTimeout(30000);
			/* 设置post发送方式 */
			urlConn.setRequestMethod("POST");
			/* 发送commString */
			urlConn.setDoOutput(true);
			OutputStream out = urlConn.getOutputStream();
			
			//拼装参数
			StringBuilder commString = new StringBuilder();
            for(Map.Entry<String, Object> entry : param.entrySet()) {
            	commString.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
			
			out.write(commString.toString().getBytes());
			out.flush();
			out.close();
			/* 发送完毕 获取返回流，解析流数据 */
			BufferedReader rd = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "GBK"));
			StringBuffer sb = new StringBuffer();
			int ch;
			while ((ch = rd.read()) > -1) {
				sb.append((char) ch);
			}
			rec_string = sb.toString().trim();
			/* 解析完毕关闭输入流 */
			rd.close();
		} catch (Exception e) {
			/* 异常处理 */
			rec_string = "-107";
			System.out.println(e);
		} finally {
			/* 关闭URL连接 */
			if (urlConn != null) {
				urlConn.disconnect();
			}
		}
		/* 返回响应内容 */
		return rec_string;
	}
}
