package com.hexun.framework.core.mogodb;

import com.hexun.framework.core.properties.MongoPropertiesUtils;
/**
 * mongoDB配置
 * @author zhoudong
 *
 */
public class MongoConfig {
  
  private static String userName = MongoPropertiesUtils.getString("mongo.userName");//用户名
  private static String pwd = MongoPropertiesUtils.getString("mongo.pwd");//密码
  private static String[] host = MongoPropertiesUtils.config.getStringArray("mongo.host");//主机地址
  private static int[] port = str2int(MongoPropertiesUtils.config.getStringArray("mongo.port"));//端口地址
  private static String dbName = MongoPropertiesUtils.getString("mongo.dbName");//数据库名
  private static int connectionsPerHost = MongoPropertiesUtils.getInt("mongo.connectionsPerHost");//每台主机最大连接数
  private static int threadsAllowedToBlockForConnectionMultiplier = MongoPropertiesUtils.getInt("mongo.threadsAllowedToBlockForConnectionMultiplier");//线程队列数
  private static boolean authentication = MongoPropertiesUtils.config.getBoolean("mongo.authentication");//是否需要身份验证
  
  
  /*private static String userName;//用户名
  private static String pwd;//密码
  private static String[] host = {"172.28.1.31"};//主机地址
  private static int[] port = {27017};//端口地址
  private static String dbName = "test";//数据库名
  private static int connectionsPerHost = 2000;//每台主机最大连接数
  private static int threadsAllowedToBlockForConnectionMultiplier = 5000;//线程队列数
  private static boolean authentication = false;//是否需要身份验证*/ 
 
  /**
   * String数组转int数组
   * @param portStrings
   * @return
   */
  private static int[] str2int(String[] portStrings){
	  int[] ports = new int[portStrings.length];
	  for(int i=0;i < portStrings.length;i++){
		  ports[i]=Integer.parseInt(portStrings[i]);
	  }
	  return ports;
  }
  
  public static String getUserName() {
      return userName;
  }
  public static void setUserName(String userName) {
      MongoConfig.userName = userName;
  }
  public static String getPwd() {
      return pwd;
  }
  public static void setPwd(String pwd) {
      MongoConfig.pwd = pwd;
  }
  public static String[] getHost() {
      return host;
  }
  public static void setHost(String[] host) {
      MongoConfig.host = host;
  }
  public static int[] getPort() {
      return port;
  }
  public static void setPort(int[] port) {
      MongoConfig.port = port;
  }
  public static String getDbName() {
      return dbName;
  }
  public static void setDbName(String dbName) {
      MongoConfig.dbName = dbName;
  }
  public static int getConnectionsPerHost() {
      return connectionsPerHost;
  }
  public static void setConnectionsPerHost(int connectionsPerHost) {
      MongoConfig.connectionsPerHost = connectionsPerHost;
  }
  public static int getThreadsAllowedToBlockForConnectionMultiplier() {
      return threadsAllowedToBlockForConnectionMultiplier;
  }
  public static void setThreadsAllowedToBlockForConnectionMultiplier(
          int threadsAllowedToBlockForConnectionMultiplier) {
      MongoConfig.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
  }
  public static boolean isAuthentication() {
      return authentication;
  }
  public static void setAuthentication(boolean authentication) {
      MongoConfig.authentication = authentication;
  }
}