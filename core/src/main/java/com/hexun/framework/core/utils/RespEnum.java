package com.hexun.framework.core.utils;
/**
 * 返回码枚举
 * @author zhoudong
 *
 */
public enum RespEnum {
	RESP_SUCCESS(0,200,"T","SUCCESS","成功"),RESP_FAIL(1,500,"F","FAIL","失败");
	
	private int code;
	private int httpCode;
	private String enCode;
	private String msg;
	private String cnMsg;
	
	RespEnum(int code, int httpCode,String enCode,String msg,String cnMsg) {
		this.code = code;
		this.httpCode=httpCode;
		this.enCode=enCode;
		this.msg=msg;
		this.cnMsg=cnMsg;
	}
	public int getCode() {
		return code;
	}
	public int getHttpCode() {
		return httpCode;
	}
	public String getEnCode() {
		return enCode;
	}
	public String getMsg() {
		return msg;
	}
	public String getCnMsg() {
		return cnMsg;
	}
}
