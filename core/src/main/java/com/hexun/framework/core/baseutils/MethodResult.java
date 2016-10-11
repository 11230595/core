package com.hexun.framework.core.baseutils;

import com.hexun.framework.core.utils.BaseResponse;
import com.hexun.framework.core.utils.RespEnum;

/**
 * 说明：方法调用返回的结果对象
 * @author zhaominghao
 * @version 2016年8月9日 上午10:43:46
 */

public class MethodResult {
    //返回结果
    private boolean success;

    //返回ID值
    private int returnID;

    //返回消息
    private String msg;

    //返回数据
    private Object data;

    public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getReturnID() {
		return returnID;
	}

	public void setReturnID(int returnID) {
		this.returnID = returnID;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	//构造函数
    public MethodResult()
    {
        //
        // TODO: 在此处添加构造函数逻辑
        //
    }
	//构造函数
    public MethodResult(boolean success, String msg)
    {
    	this.success = success;
    	this.msg = msg;
    }    
	//构造函数
    public MethodResult(boolean success, int returnID, String Msg, Object data)
    {
    	this.success = success;
    	this.returnID = returnID;
    	this.msg = msg;
    	this.data = data;
    }   
    
    //返回一个调用失败的结果对象
    public static MethodResult GetFail(String msg){
    	return new MethodResult(false, msg);
    }

    //返回一个调用成功的结果对象
    public static MethodResult GetSuccess(){
    	return new MethodResult(true, "");
    }

    //返回一个调用成功的结果对象
    public static MethodResult GetSuccess(Object data){
    	return new MethodResult(true, 0, "", data);
    }
    
    public boolean Success()
    {
    	return this.success;
    }   
    
    public boolean Fail()
    {
    	return !this.success;
    }   

    //根据methodResult的结果来填充BaseResponse
    public void SetBaseResponse(BaseResponse resp){
		if(this.Success()){
			resp.setRespCode(String.valueOf(RespEnum.RESP_SUCCESS.getCode()));
			resp.setRespMgs("");
			resp.setResult(this.getData());
		}
		else{
			resp.setRespCode(String.valueOf(RespEnum.RESP_FAIL.getCode()));
			resp.setErrorMgs(this.getMsg());
		}
    }
}
