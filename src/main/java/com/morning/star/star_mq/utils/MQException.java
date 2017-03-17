package com.morning.star.star_mq.utils;

import com.morning.star.star_mq.utils.MQEnums.CODE;

public class MQException extends RuntimeException{

	private String code;
	private String msg;
	private String desc;
	
	
	public MQException(String code) {
		super();
		this.code = code;
	}
	
	public MQException(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	
	public MQException(String code, String msg, String desc) {
		super();
		this.code = code;
		this.msg = msg;
		this.desc = desc;
	}
	public MQException(CODE code) {
		super();
		this.code = code.getCode();
	}
	
	public MQException(CODE code, String msg) {
		super();
		this.code = code.getCode();
		this.msg = msg;
	}
	
	public MQException(CODE code, String msg, String desc) {
		super();
		this.code = code.getCode();
		this.msg = msg;
		this.desc = desc;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	
	
}
