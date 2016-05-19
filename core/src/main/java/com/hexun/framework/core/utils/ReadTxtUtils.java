package com.hexun.framework.core.utils;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

/**
 * 读TXT文件
 * 
 * @author zhoudong
 * 
 */
public class ReadTxtUtils {
	private static String encoding = "UTF-8";
	/**
	 * 读文件
	 * 
	 * @param filePath
	 * @throws IOException 
	 */
 	public static String readTxtFile(String filePath) throws IOException {
		    String synTimeStr = "";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				synTimeStr =  FileUtils.readFileToString(file, encoding);
 			}  
		return synTimeStr;
	}

	/**
	 * 写文件.
	 * 
	 */
	public static void writeTxtFile(String filePath, String newStr) {
		File file = new File(filePath);
		try {
			FileUtils.writeStringToFile(file, newStr, encoding);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("写文件失败");
		}
	}
}
