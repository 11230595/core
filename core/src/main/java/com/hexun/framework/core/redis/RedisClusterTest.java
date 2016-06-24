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
		RedisClusterTest test7 = new RedisClusterTest();
		RedisClusterTest test8 = new RedisClusterTest();
		RedisClusterTest test9 = new RedisClusterTest();
		RedisClusterTest test10 = new RedisClusterTest();
		RedisClusterTest test11 = new RedisClusterTest();
		RedisClusterTest test12 = new RedisClusterTest();
		RedisClusterTest test13 = new RedisClusterTest();
		RedisClusterTest test14 = new RedisClusterTest();
		RedisClusterTest test15 = new RedisClusterTest();
		RedisClusterTest test16 = new RedisClusterTest();
		RedisClusterTest test17 = new RedisClusterTest();
		RedisClusterTest test18 = new RedisClusterTest();
		RedisClusterTest test19 = new RedisClusterTest();
		RedisClusterTest test20 = new RedisClusterTest();
		test1.start();
		test2.start();
		test3.start();
		test4.start();
		test5.start();
		test6.start();
		test7.start();
		test8.start();
		test9.start();
		test10.start();
		test11.start();
		test12.start();
		test13.start();
		test14.start();
		test15.start();
		test16.start();
		test17.start();
		test18.start();
		test19.start();
		test20.start();
	}

	@Override
	public void run() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("init 当前空闲连接池：" + RedisClusterPool.getFreePoolSize());
		
		/*for(int i=0;i<60;i++){
			RedisClusterUtils.set(Thread.currentThread().getName()+i, Thread.currentThread().getName()+i);
		}*/
		System.out.println(Thread.currentThread().getName() + "------" + RedisClusterUtils.getTest(Thread.currentThread().getName()+0));
		System.out.println("操作完 当前空闲连接池：" + RedisClusterPool.getFreePoolSize());
	}
}
