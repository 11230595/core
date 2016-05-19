package com.hexun.framework.core.mogodb;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.hexun.framework.core.page.Page;
import com.mongodb.AggregationOptions;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.util.JSON;
/**
 * mongoDB管理
 * @author zhoudong
 *
 */
@SuppressWarnings("deprecation")
public class MongoManager {
     
    protected static Mongo mongo;
    protected DB db;
     
    static{
        init();
    }
     
    /**
    * @param dbName
    * @param userName
    * @param pwd
    * 实例化dbName一个DB
     */
    public MongoManager(String dbName, String userName, String pwd) {
        if (dbName == null || "".equals(dbName)) {
            throw new NumberFormatException("dbName is null");
        }
        db = mongo.getDB(dbName);
        if(MongoConfig.isAuthentication() && !db.isAuthenticated()){
            if (userName == null || "".equals(userName)) {
                throw new NumberFormatException("userName is null");
            }
            if (pwd == null || "".equals(pwd)) {
                throw new NumberFormatException("pwd is null");
            }
            db.authenticate(userName, pwd.toCharArray());
        }
    }
     
    /**
     * 使用配置参数实例化
     */
    public MongoManager() {
        this(MongoConfig.getDbName(), MongoConfig.getUserName(), MongoConfig.getPwd());
    }
     
    /**
     * @param tableName
     * @return
     * @Author:zhoudong
     * @Description: 获取表tableName的链接DBCollection
     */
    public DBCollection getDBCollection(String tableName) {
        return db.getCollection(tableName);
    }
     
    /**
     * @Author:zhoudong
     * @Description: mongo连接池初始化
     */
    @SuppressWarnings("deprecation")
	private static void init() {
        if (MongoConfig.getHost() == null || MongoConfig.getHost().length == 0) {
            throw new NumberFormatException("host is null");
        }
        if (MongoConfig.getPort() == null || MongoConfig.getPort().length == 0) {
            throw new NumberFormatException("port is null");
        }
        if (MongoConfig.getHost().length != MongoConfig.getPort().length) {
            throw new NumberFormatException("host's length is not equals port's length");
        }
        try {
            //服务列表
            List<ServerAddress> replicaSetSeeds = new ArrayList<ServerAddress>();
            for (int i = 0; i < MongoConfig.getHost().length; i++) {
                replicaSetSeeds.add(new ServerAddress(MongoConfig.getHost()[i], MongoConfig.getPort()[i]));
            }
            //连接池参数设置
            @SuppressWarnings("deprecation")
			MongoOptions options = new MongoOptions();
            options.connectionsPerHost = MongoConfig.getConnectionsPerHost();
            options.threadsAllowedToBlockForConnectionMultiplier = MongoConfig.getThreadsAllowedToBlockForConnectionMultiplier();
            mongo = new Mongo(replicaSetSeeds, options);
            //从服务器可读
            mongo.setReadPreference(ReadPreference.SECONDARY);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * 保存
     * @param collection
     * @param ttl 过期时间，设置为0及以下的时候为不过期，单位是秒
     * @param dbObject
     */
    public void save(String collName, DBObject dbObject,int ttl) {
    	//存在为true 不存在为false
		boolean flag = db.collectionExists(collName);
		DBCollection dbCollection = db.getCollection(collName);
		dbCollection.save(dbObject);
		
		//因为之前没有存在过，所以创建索引
		if(!flag){
			if(ttl > 0){
				//ttl超时索引的建立
				BasicDBObject indx1 = new BasicDBObject("create_time",1);
				BasicDBObject indx2 = new BasicDBObject("expireAfterSeconds",ttl);
				dbCollection.createIndex(indx1, indx2);	
			}
			//k索引的建立
			BasicDBObject indexDBObject = new BasicDBObject();
			indexDBObject.put("k", 1);
			dbCollection.createIndex(indexDBObject);
		}
    }
    
    /**
     * 删除
     * @param collection
     * @param query
     */
    public void delete(String collName, DBObject query) {
        db.getCollection(collName).remove(query);
    }
 
    /**
     *  此处不使用toArray()方法直接转换为List,是因为toArray()会把结果集直接存放在内存中，
     	如果查询的结果集很大，并且在查询过程中某一条记录被修改了，就不能够反应到结果集中，从而造成"不可重复读"
     	而游标是惰性获取数据
     * @param collection
     * @param query
     * @param fields
     * @param limit
     * @return
     */
    public List<DBObject> find(String collName, DBObject query, DBObject fields, int limit) {
        List<DBObject> list = new LinkedList<DBObject>();
        Cursor cursor = db.getCollection(collName).find(query, fields).limit(limit);
        while (cursor.hasNext()) {
            list.add(cursor.next());
        }
        return list.size() > 0 ? list : null;
    }
    /**
     * find 只有条件
     * @param collection
     * @param query
     * @return
     */
    public List<DBObject> find(String collName, DBObject query) {
        List<DBObject> list = new LinkedList<DBObject>();
        Cursor cursor = db.getCollection(collName).find(query);
        while (cursor.hasNext()) {
            list.add(cursor.next());
        }
        return list.size() > 0 ? list : null;
    }
    
    /**
     * find 只有条件和limit
     * @param collection
     * @param query
     * @return
     */
    public List<DBObject> find(String collName, DBObject query,int limit) {
        List<DBObject> list = new LinkedList<DBObject>();
        Cursor cursor = db.getCollection(collName).find(query).limit(limit);
        while (cursor.hasNext()) {
            list.add(cursor.next());
        }
        return list.size() > 0 ? list : null;
    }
    
    /**
     * 分页查询(只显示指定的行)
     * @param collection
     * @param query
     * @param fields
     * @param orderBy
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page<DBObject> findByPage(String collName, DBObject query, DBObject fields, int pageNo, int pageSize) {
    	Page<DBObject> page = new Page<DBObject>();
    	List<DBObject> list = new ArrayList<DBObject>();
        Cursor cursor = db.getCollection(collName).find(query, fields).skip((pageNo - 1) * pageSize).limit(pageSize).sort(new BasicDBObject("id", 1));
        while (cursor.hasNext()) {
            list.add(cursor.next());
        }
        page.setList(list);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        Long count = count(collName, query);
        page.setTotalCount(count.intValue());
        
        return page;
    }
    
    /**
     * 分页查询
     * @param collection
     * @param query
     * @param fields
     * @param orderBy
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page<DBObject> findByPage(String collName, DBObject query, int pageNo, int pageSize) {
    	Page<DBObject> page = new Page<DBObject>();
    	List<DBObject> list = new ArrayList<DBObject>();
        Cursor cursor = db.getCollection(collName).find(query).skip((pageNo - 1) * pageSize).limit(pageSize).sort(new BasicDBObject("id", 1));
        while (cursor.hasNext()) {
            list.add(cursor.next());
        }
        page.setList(list);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        Long count = count(collName, query);
        page.setTotalCount(count.intValue());
        
        return page;
    }
    /**
     * 分页查询,没有条件
     * @param collection
     * @param query
     * @param fields
     * @param orderBy
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page<DBObject> findByPage(String collName, int pageNo, int pageSize) {
    	Page<DBObject> page = new Page<DBObject>();
    	List<DBObject> list = new ArrayList<DBObject>();
        Cursor cursor = db.getCollection(collName).find().skip((pageNo - 1) * pageSize).limit(pageSize).sort(new BasicDBObject("id", 1));
        while (cursor.hasNext()) {
            list.add(cursor.next());
        }
        page.setList(list);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        Long count = count(collName);
        page.setTotalCount(count.intValue());
        
        return page;
    }
    /**
     * 查询单个
     * @param collection
     * @param query
     * @param fields
     * @return
     */
    public DBObject findOne(String collName, DBObject query, DBObject fields) {
        return db.getCollection(collName).findOne(query, fields);
    }
    /**
     * 查询单个
     * @param collection
     * @param query
     * @param fields
     * @return
     */
    public DBObject findOne(String collName, DBObject query) {
        return db.getCollection(collName).findOne(query);
    }
    /**
     * 更新
     * @param collection
     * @param query
     * @param update
     * @param upsert
     * @param multi
     */
    public void update(String collName, DBObject query, DBObject update, boolean upsert, boolean multi) {
        db.getCollection(collName).update(query, update);
    }
    /**
     * 统计
     * @param collection
     * @return
     */
    public long count(String collName) {
        return db.getCollection(collName).count();
    }
    /**
     * 统计
     * @param collection
     * @param query
     * @return
     */
    public long count(String collName, DBObject query) {
        return db.getCollection(collName).count(query);
    }
 
    /**
     * 查询出key字段,去除重复，返回值是{_id:value}形式的list
     * @param collection
     * @param key
     * @param query
     * @return
     */
    public List distinct(String collName, String key, DBObject query) {
        return db.getCollection(collName).distinct(key, query);
    }
}