package com.hexun.framework.core.mogodb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.hexun.framework.core.exception.MyException;
import com.hexun.framework.core.page.Page;
import com.hexun.framework.core.utils.JsonUtils;
import com.hexun.framework.core.utils.StringUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBObject;

/**
 * mongo工具类，在MongoManager的基础上，暴露出几个方便调用的接口。
 * @author zhoudong
 *
 */
public class MongoDBUtils extends MongoManager{
	
	public MongoDBUtils(){
		super();
	}
	
	public MongoDBUtils(String dbName,String userName, String pwd){
		super(dbName, userName, pwd);
	}
	
	/**
     * 保存
     * @param collName
     * @param ttl 过期时间，设置为0及以下的时候为不过期，单位是秒
     * @param key
	 * @throws MyException 
     */
    public void save(String collName, String key,Object value,int ttl){
    	
    	if(StringUtils.isBlank(key)){
    		return;
    	}
    	if(StringUtils.isBlank(value)){
    		return;
    	}
    	
    	BasicDBObject doc = new BasicDBObject();
    	doc.append("k", key).append("v", JSON.toJSON(value)).append("create_time", new Date());
    	
    	super.save(collName, doc, ttl);
    }
    
    /**
     * 删除
     * @param collName
     * @param query
     */
    public void delete(String collName, String key) {
    	BasicDBObject query = new BasicDBObject("k", key);
        db.getCollection(collName).remove(query);
    }
    
    /**
     * 查询单个
     * @param collection
     * @param key
     * @param fields 字段
     * @return
     */
    public Object findOne(String collName, String key, String ...fields) {
    	BasicDBObject fieldDB = new BasicDBObject();
    	for(String field : fields){
    		fieldDB.put(field, 1);
    	}
    	DBObject dbObject = db.getCollection(collName).findOne(new BasicDBObject("k",key), fieldDB);
    	if (dbObject == null){
    		return null;
    	}
    	
        return JSON.toJSON(dbObject);
    }
    
    /**
     * 查询单个
     * @param collName
     * @param key
     * @return
     */
    public Object findOne(String collName, String key) {
    	DBObject dbObject = db.getCollection(collName).findOne(new BasicDBObject("k",key));
    	if (dbObject == null){
    		return null;
    	}
        return JSON.toJSON(dbObject);
    }
    
    /**
     * 分页查询
     * @param collName
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page<?> findJSONByPage(String collName, int pageNo, int pageSize) {
    	Page<Object> page = new Page<Object>();
    	List<Object> list = new ArrayList<Object>();
    	
        Cursor cursor = db.getCollection(collName).find().skip((pageNo - 1) * pageSize).limit(pageSize).sort(new BasicDBObject("id", 1));
        while (cursor.hasNext()) {
            list.add(JSON.toJSON(cursor.next()));
        }
        page.setList(list);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        Long count = count(collName);
        page.setTotalCount(count.intValue());
        
        return page;
    }
    
    /**
     * 分页查询
     * @param collName
     * @param likeKey		模糊查询哪个字段
     * @param likeString	模糊查询字段的值
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page<?> findJSONByPage(String collName,String likeKey, String likeString, int pageNo, int pageSize) {
    	Page<Object> page = new Page<Object>();
    	List<Object> list = new ArrayList<Object>();
    	
    	BasicDBObject query = new BasicDBObject();
		Pattern pattern = Pattern.compile("^.*" + likeString+ ".*$", Pattern.CASE_INSENSITIVE);
		query.put("v." + likeKey, pattern);
    	
        Cursor cursor = db.getCollection(collName).find(query).skip((pageNo - 1) * pageSize).limit(pageSize).sort(new BasicDBObject("id", 1));
        while (cursor.hasNext()) {
            list.add(JSON.toJSON(cursor.next()));
        }
        page.setList(list);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        Long count = count(collName);
        page.setTotalCount(count.intValue());
        
        return page;
    }
}
