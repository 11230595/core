package com.hexun.framework.core.redis;

/**
 * redis 集群测试
 * @author zhoudong
 *
 */
public class RedisClusterTest extends Thread{
	public static void main(String[] args) {
		RedisClusterTest test1 = new RedisClusterTest();
		RedisClusterTest test2 = new RedisClusterTest();
		RedisClusterTest test3 = new RedisClusterTest();
		RedisClusterTest test4 = new RedisClusterTest();
		RedisClusterTest test5 = new RedisClusterTest();
		RedisClusterTest test6 = new RedisClusterTest();
		test1.start();
		test2.start();
		test3.start();
		test4.start();
		test5.start();
		test6.start();
	}

	@Override
	public void run() {
		/*for(int i=0;i<60;i++){
			RedisClusterUtils.set(Thread.currentThread().getName()+i, Thread.currentThread().getName()+i);
		}*/
		System.out.println(Thread.currentThread().getName() + "------" + RedisClusterUtils.get(Thread.currentThread().getName()+0));
	}
}
