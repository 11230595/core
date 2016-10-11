package com.hexun.framework.core.mogodb;

/**
 * mongo使用说明
 * @author zhoudong
 *
 */
public class MongoTest {
	/**
	 * mongoDB工具类的简单操作
	 * 1、在项目的resources文件夹中添加mongo.properties
	 * 2、引入core-0.0.1-SNAPSHOT.jar
	 * 3、可以写个工具类初始化mongoDB链接，如下面的方式（MongoInit.mongoDBUtils，在core中已经封装），也可以用的时候（MongoDBUtils mongoDBUtils = new MongoDBUtils()）
	 * 4、具体封装的代码，详见core项目，还有更多方法，比如获取list，update等等
	 * 5、本例返回以Object接收
	 * 6、访问路径：http://localhost:8080/personalcms/test/mongo1.do?pageNo=1&pageSize=10&likeKey=phone&likeValue=188888888881&key=test0
	 * @return
	 *//*
	@RequestMapping(value="mongo1",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<String, Object> mongo1() {
		String collName = "test1"; //表名
		
		TsUser tsUser = null;
		for(int i=0; i< 20; i++){
			tsUser = new TsUser();
			tsUser.setCreateTime(new Date());
			tsUser.setEmall("1111 "+i+"@163.com");
			tsUser.setId(IDUtils.getId());
			tsUser.setLevel(1);
			tsUser.setPhone("18888888888" +i);
			tsUser.setUserCode("zhoudong");
			tsUser.setUserName("周栋");
			//保存，参数：1-表名（无需创建），2-KEY（唯一），3-VALUE（随意内容），4-过期时间，单位秒，到期后数据自动过期，如果设置为0或负数，则数据不过期
			MongoInit.mongoDBUtils.save(collName, "test" + i, tsUser, 1*60*60*24);
		}
		//从request中获取参数
		int pageNo = Integer.parseInt(getRequest().getParameter("pageNo"));
		int pageSize = Integer.parseInt(getRequest().getParameter("pageSize"));
		String likeKey = getRequest().getParameter("likeKey");
		String likeValue = getRequest().getParameter("likeValue");
		String key = getRequest().getParameter("key");
		
		//分页查询，参数：1-表名，2-pageNo,3-pageSize
		Page<Object> page = (Page<Object>) MongoInit.mongoDBUtils.findJSONByPage(collName, pageNo, pageSize);
		
		//分页查询，带查询条件。参数：1-表名，2-模糊查询的key,3-模糊查询的value，4-pageNo,5-pageSize
		//模糊查询的key  value  如  userName like '%aaa%'    likeKey=userName，likeValue=aaa
		Page<Object> page1 = (Page<Object>) MongoInit.mongoDBUtils.findJSONByPage(collName,likeKey, likeValue, pageNo, pageSize);
		
		//查询单个，参数1-表名，2-KEY
		Object object = MongoInit.mongoDBUtils.findOne(collName, key);
		
		//查询单个，返回指定的字段，参数1-表名，2-KEY,3-字段名（字段名不限制个数），如下，返回k,v字段的数据
		Object object1 = MongoInit.mongoDBUtils.findOne(collName, key,"k","v");
		
		//删除，参数1-表名，2-KEY
		MongoInit.mongoDBUtils.delete(collName, key);
		
		resultMap.put("分页", page);
		resultMap.put("分页带模糊查询", page1);
		resultMap.put("单个查询", object);
		resultMap.put("单个查询返回指定的列", object1);
		return resultMap;
	}*/
	
	
	public static void main(String[] args) {
		//MongoDBUtils mongoDBUtils = new MongoDBUtils();
		MongoInit.mongoDBUtils.findOne("", "key");
	}
	
}
