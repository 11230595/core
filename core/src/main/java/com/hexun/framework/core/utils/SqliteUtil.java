package com.hexun.framework.core.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * 连接sqlite
 * @author zhoudong
 *
 */
public class SqliteUtil {
	private static Logger logger=Logger.getLogger(SqliteUtil.class);
	/**
	 * 获取数据库连接
	 * @param path 路径
	 * @return
	 */
	public static Connection getConnection(String path){
		 try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			logger.error("sqlite class load error:", e);
		}    
		 Connection conn=null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+path);
		} catch (SQLException e) {
			logger.error("sqlite get connetion error:", e);
		}  
		 return conn;
	}
}
