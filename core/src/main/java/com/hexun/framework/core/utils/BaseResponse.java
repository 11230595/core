package com.hexun.framework.core.utils;


/**
 * 网关请求主返回体
 * @author zhoudong
 *
 */
public class BaseResponse {
	private String respCode;   	//返回码
	private String respMgs;		//返回信息
	private String errorMgs;	//错误信息
	private String result;		//返回结果
	private String charset = "utf-8";		//编码
	
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespMgs() {
		return respMgs;
	}
	public void setRespMgs(String respMgs) {
		this.respMgs = respMgs;
	}
	public String getErrorMgs() {
		return errorMgs;
	}
	public void setErrorMgs(String errorMgs) {
		this.errorMgs = errorMgs;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	
}

