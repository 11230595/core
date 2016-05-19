package com.hexun.framework.core.exception;
/**
 * 自定义异常
 * @author zhoudong
 *
 */
@SuppressWarnings("serial")
public class MyException extends Exception{
	public MyException(String msg){
		super(msg);
	}
}
