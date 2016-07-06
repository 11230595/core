package com.hexun.framework.core.redis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import com.hexun.framework.core.exception.MyException;
import com.hexun.framework.core.properties.RedisPropertiesUtils;
import com.hexun.framework.core.utils.DateUtils;

/**
 * redis 对象池连接池
 * @author zhoudong
 *
 */
public final class RedisClusterPool {
	/**
	 * 最小连接数
	 */
	private static int minConn = RedisPropertiesUtils.getInt("redis.cluster.minConn");
	/**
	 * 最大连接数
	 */
	private static int maxConn = RedisPropertiesUtils.getInt("redis.cluster.maxConn");
	/**
	 * 使用中的连接数
	 */
	private static int usedConn = 0;
	/**
	 * 每次创建多少链接放入连接池
	 */
	private static int createConn = RedisPropertiesUtils.getInt("redis.cluster.createConn");
	/**
	 * redis 集群所在的IP
	 */
	private static List<String> ips = RedisPropertiesUtils.getList("redis.cluster.ips");
	/**
	 * redis 集群所在的IP的端口
	 */
	private static List<String> ports = RedisPropertiesUtils.getList("redis.cluster.ports");
	
	/**
	 * 容器，盛放空闲的jedisCluster链接
	 */
	private static List<JedisCluster> freeJcs = new ArrayList<JedisCluster>();
	
	/**
	 * 报警线，当连接数小于该数字的时候，创建一批线程
	 */
	private static int countStatic = RedisPropertiesUtils.getInt("redis.cluster.warningConn");
	
	/**
	 * 连接池加1
	 */
	private synchronized static void usedConnAdd(){
		++ usedConn;
	}
	
	/**
	 * 连接池减1
	 */
	private synchronized static void usedConnRemove(){
		-- usedConn;
	}
	
	public static void main(String[] args) {
		System.out.println(minConn);
	}
	
	/**
	 * 初始化连接池
	 */
	static{
		//initPool();
		timeGC();
		timeCheckFreePool();
	}
	
	/**
	 * 初始化jedisClusterPool
	 */
	private static void initPool(){
		System.out.println("init JedisClusterPool.......");
		int createNum = minConn - (freeJcs.size() + usedConn);
		threadCreate(createNum);
		System.out.println("init JedisClusterPool end.......");
	}
	
	/**
	 * 创建jedis链接
	 * @return
	 * @throws MyException 
	 */
	private static JedisCluster createJc() throws MyException{
		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
		// 只给集群里一个实例就可以
		for(int i=0; i<ips.size(); i++){
			jedisClusterNodes.add(new HostAndPort(ips.get(i), Integer.parseInt(ports.get(i))));
		}
		
		return new JedisCluster(jedisClusterNodes);
	}
	/**
	 * 多线程创建对象
	 * @return
	 */
	private static void threadCreate(final int num){
		System.out.println(DateUtils.getDefaultDate() + "-----------------------------pool:" + freeJcs.size()+ "----usedConn:" + usedConn + "---------------------");
		// 创建一个线程池
		ExecutorService executor = Executors.newCachedThreadPool();
		CompletionService<JedisCluster> completionService = new ExecutorCompletionService<JedisCluster>(executor);
		List<Future<JedisCluster>> futures = new ArrayList<Future<JedisCluster>>();
		
    	for(int i=0; i<num; i++){
    		// 执行任务并获取Future对象
			Future<JedisCluster> future = completionService.submit(new Callable<JedisCluster>() {
	        	@Override
				public JedisCluster call() throws Exception {
	        		JedisCluster jc = null;
	        		try {
	        			jc = createJc();
					} catch (MyException e) {
						e.printStackTrace();
					} 
		 			return jc;
				}
	        });
			futures.add(future);
    	}
    	
    	for(Future<JedisCluster> f : futures){
    		try {
				freeJcs.add(f.get(6000, TimeUnit.MILLISECONDS));
			} catch (Exception e) {
				System.out.println("--------- create JedisCluster time out! --------");
			}
    	}
	}
	
	/**
	 * 多线程创建对象
	 * @return
	 */
	private static void threadCreateForCheck(final int num){
		System.out.println(DateUtils.getDefaultDate() + "-----------------------------check:freePoolSize:" + freeJcs.size()+ "----usedConn:" + usedConn + "---------------------");
		// 创建一个线程池
		ExecutorService executor = Executors.newCachedThreadPool();
		CompletionService<JedisCluster> completionService = new ExecutorCompletionService<JedisCluster>(executor);
		List<Future<JedisCluster>> futures = new ArrayList<Future<JedisCluster>>();
		
    	for(int i=0; i<num; i++){
    		// 执行任务并获取Future对象
			Future<JedisCluster> future = completionService.submit(new Callable<JedisCluster>() {
	        	@Override
				public JedisCluster call() throws Exception {
	        		JedisCluster jc = null;
	        		try {
	        			jc = createJc();
					} catch (MyException e) {
						e.printStackTrace();
					} 
		 			return jc;
				}
	        });
			futures.add(future);
    	}
    	
    	for(Future<JedisCluster> f : futures){
    		try {
				freeJcs.add(f.get(6000, TimeUnit.MILLISECONDS));
			} catch (Exception e) {
				System.out.println("--------- create JedisCluster time out! --------");
			}
    	}
	}
	
	/**
	 * 从连接池中获取jedisCluster对象
	 * @return
	 * @throws MyException 
	 */
	public static synchronized JedisCluster getJcByPool(){
		if(freeJcs.size() > 0){ 					//如果有空闲的链接，直接从连接池里面
			/*if(freeJcs.size() < minConn && usedConn <= maxConn && count < countStatic){
				new Thread(new Runnable(){
		            public void run(){
		            	count ++;
		            	threadCreate(createConn);
		            }
		        }).start();
			}*/
			return getJcByFreeJedisCluster();
		}else if(freeJcs.size() == 0 && usedConn <= maxConn){ //如果已经存在的线程池小于定义的最大线程池，并且没有空闲线程池的情况下，创建一个
			threadCreate(2);
			return getJcByFreeJedisCluster();
		}else{
			System.out.println("JedisClusterPool full wait!");
			//wait(1);
			return getJcByFreeJedisCluster();
		}
	}
	
	/**
	 * 检查空连接池里面还有多少个链接，不足的时候，自动添加
	 */
	private static void checkFreePool(){
		if(freeJcs.size() < countStatic && usedConn <= maxConn){
			System.out.println("--------------- checkFreePool is lack!--------------------");
			threadCreateForCheck(createConn);
		}
	}
	
	/**
	 * 从空闲的链接池里面取链接
	 * @return
	 */
	private static synchronized JedisCluster getJcByFreeJedisCluster(){
		JedisCluster jc = null;
		if(freeJcs.size() <= 0){
			return getJcByPool();
		}
		jc = freeJcs.get(0);
		freeJcs.remove(0);
		usedConnAdd();
		return jc;
	}
	
	/**
	 * 使程序等待指定的秒数
	 * @param seconds
	 */
    private static void wait(int seconds){
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * 将jc对象返回到freeJcpool
	 */
	public static void returnJcToPool(final JedisCluster jc){
		new Thread(new Runnable(){
			@Override
			public void run() {
				freeJcs.add(jc);
				usedConnRemove();
			}
		}).start();
	}
	
	/**
	 * 将jc对象返回到freeJcpool
	 */
	public static int getFreePoolSize(){
		return freeJcs.size();
	}
	
	/**
	 * 使用递归清理连接池
	 */
	private static void gcPool(){
		System.out.println(DateUtils.getDefaultDate() + "--free pool clear --> now pool count:" + freeJcs.size());
		if(freeJcs.size() > minConn){
			System.out.println("超过最小空闲数量删除多余的连接池");
			try {
				freeJcs.get(0).close();  //关闭链接
				freeJcs.remove(0);		 //从链接池删除
			} catch (IOException e) {
				e.printStackTrace();
			}
			gcPool();
		}
	}
	/**
	 * 定时执行
	 */
	private static void timeGC(){
		TimerTask task = new TimerTask() {  
            @Override  
            public void run() {  
            	gcPool();
            }  
        };  
		Timer timer = new Timer();
		timer.schedule(task, 1000, 60 * 1000 * 30); //1秒以后每30分钟执行一次 60 * 1000 * 30
	}
	
	/**
	 * 定时执行  检查连接池的数量
	 */
	private static void timeCheckFreePool(){
		TimerTask task = new TimerTask() {  
            @Override  
            public void run() {  
            	checkFreePool();
            }  
        };  
		Timer timer = new Timer();
		timer.schedule(task, 1000,1000); //1秒后开始每个2秒钟执行一次TimerTaskTest中的run()方法。
	}
}
