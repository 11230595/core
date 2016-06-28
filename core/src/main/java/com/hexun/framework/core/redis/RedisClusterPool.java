package com.hexun.framework.core.redis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import com.hexun.framework.core.exception.MyException;
import com.hexun.framework.core.properties.RedisPropertiesUtils;
import com.hexun.framework.core.utils.StringUtils;

/**
 * redis 对象池
 * @author zhoudong
 *
 */
public class RedisClusterPool {
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
	
	public static void main(String[] args) {
		System.out.println(minConn);
	}
	
	/**
	 * 初始化连接池
	 */
	static{
		initPool();
		timeGC();
	}
	
	/**
	 * 初始化jedisClusterPool
	 */
	private static void initPool(){
		System.out.println("线程初始化.......");
		int createNum = minConn - (freeJcs.size() + usedConn);
		for(int i=0; i < createNum; i++){
			try {
				freeJcs.add(createJc());
			} catch (MyException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 创建jedis链接
	 * @return
	 * @throws MyException 
	 */
	private static JedisCluster createJc() throws MyException{
		// 只给集群里一个实例就可以
		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
		for(int i=0; i<ips.size(); i++){
			jedisClusterNodes.add(new HostAndPort(ips.get(i), Integer.parseInt(ports.get(i))));
		}
		return new JedisCluster(jedisClusterNodes);
	}
	
	/**
	 * 从连接池中获取jedisCluster对象
	 * @return
	 * @throws MyException 
	 */
	public static synchronized JedisCluster getJcByPool() throws MyException{
		JedisCluster jc = null;
		if(freeJcs.size() > 0){ 					//如果有空闲的链接，直接从连接池里面
			jc = freeJcs.get(0);
			freeJcs.remove(0);
			usedConn++;
			return jc;
		}else if(freeJcs.size() == 0 && usedConn <= maxConn){ //如果已经存在的线程池小于定义的最大线程池，并且没有空闲线程池的情况下，创建一个
			try {
				freeJcs.add(createJc());
			} catch (MyException e) {
				e.printStackTrace();
			}
			return getJcByPool();
		}else{
			wait(100);
			return getJcByPool();
		}
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
	public static void returnJcToPool(JedisCluster jc){
		freeJcs.add(jc);
		usedConn --;
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
		System.out.println("递归清理--》当前连接池的数量：" + freeJcs.size());
		if(freeJcs.size() > minConn){
			System.out.println("超过最小空闲数量删除多余的连接池");
			try {
				freeJcs.get(0).close(); //关闭链接
			} catch (IOException e) {
				e.printStackTrace();
			}
			freeJcs.remove(0); 			//从链接池删除
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
		timer.schedule(task, 60 * 1000 * 60 * 1); //每1个小时执行一次 60 * 1000 * 60 * 24
	}
}
