package com.morning.star.star_mq.rk.bean;

import com.morning.star.star_mq.utils.MQEnums.CODE;

public class Result {

	private CODE code;
	private String msgId;
	private String desc;
	
	public Result(CODE code){
		this.code = code;
	}
	public Result(CODE code,String msgId){
		this.code=code;
		this.msgId=msgId;
	}
	public Result(CODE code,String msgId,String desc){
		this.code=code;
		this.msgId=msgId;
		this.desc=desc;
	}
	
	public CODE getCode() {
		return code;
	}
	public void setCode(CODE code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	
	
}
