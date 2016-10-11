package com.hexun.framework.core.mogodb;

import com.hexun.framework.core.mogodb.MongoDBUtils;

/**
 * 
 * @author zhoudong
 * 初始化mongoDB
 */
public class MongoInit {
	public static MongoDBUtils mongoDBUtils = null;
	
	
	static{
		mongoDBUtils = new MongoDBUtils();
	}
}
