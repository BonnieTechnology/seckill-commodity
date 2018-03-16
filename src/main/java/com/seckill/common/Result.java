package com.seckill.common;

public class Result {
	
	private int status;
	private String msg;
	private Object data;
	
	public Result(Object data) {
		this.status = 200;
		this.msg = "OK";
		this.data = data;
	}
	public Result(int status, String msg) {
		this.status = status;
		this.msg = msg;
	}
	/**
	 * 成功时调用
	 * @param data
	 * @return
	 */
	public static Result success(Object data){
		return new Result(data);
	}
	
	/**
	 * 失败调用
	 * @param data
	 * @return
	 */
	public static Result error(int status, String msg){
		return new Result(status,msg);
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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

}
