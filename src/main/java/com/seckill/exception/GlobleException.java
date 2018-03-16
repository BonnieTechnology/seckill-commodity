package com.seckill.exception;

public class GlobleException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private int status;
	private String msg;
	
	public GlobleException(int status, String msg) {
		this.status = status;
		this.msg = msg;
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
	

}
