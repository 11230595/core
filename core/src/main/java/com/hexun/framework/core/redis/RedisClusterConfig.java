package com.hexun.framework.core.redis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * 使用Jedis操作redis 集群配置
 * 
 * @author zhoudong
 *
 */
public class RedisClusterConfig {
	//private static BinaryJedisCluster bjc = null;
	private static JedisCluster jc = null;

	/** 
     * 获取jedisCluster对象
     */  
    public static JedisCluster getJc() {
        if (jc == null) {
        	// 只给集群里一个实例就可以
    		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
    		jedisClusterNodes.add(new HostAndPort("192.168.35.100", 7000));
    		jedisClusterNodes.add(new HostAndPort("192.168.35.100", 7001));
    		jedisClusterNodes.add(new HostAndPort("192.168.35.100", 7002));
    		jedisClusterNodes.add(new HostAndPort("192.168.35.100", 7003));
    		jedisClusterNodes.add(new HostAndPort("192.168.35.100", 7004));
    		jedisClusterNodes.add(new HostAndPort("192.168.35.100", 7005));
    		
    		/*//从配置文件中读取
    		List<String> ips = RedisPropertiesUtils.getList("redis.cluster.url");
    		List<String> ports = RedisPropertiesUtils.getList("redis.cluster.port");
    		for(int i=0; i<ips.size(); i++){
    			jedisClusterNodes.add(new HostAndPort(ips.get(i), Integer.parseInt(ports.get(i))));
    		}*/
    		
    		//bjc = new BinaryJedisCluster(jedisClusterNodes);
    		jc = new JedisCluster(jedisClusterNodes);
        }
        return jc;
    }
    /**
     * 关闭jedisCluster
     * @param jc
     */
    public static void colse(JedisCluster jc){
    	if(jc != null){
    		try {
				jc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }

	public static void testBenchRedisSet() {
		long start = System.currentTimeMillis();
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 1000; i++) {
			String key = "keyList:" + i;
			//getJc().setex(key.getBytes(), 60 * 60, String.valueOf(i).getBytes());
			list.add(String.valueOf(i));
		}
		getJc().lpush("keyList", list.toArray(new String[list.size()]));
		System.out.println("time=" + (System.currentTimeMillis() - start));
	}

	public static void main(String[] args) {
		testBenchRedisSet();
	}
}
