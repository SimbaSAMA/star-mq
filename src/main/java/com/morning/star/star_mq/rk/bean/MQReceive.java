package com.morning.star.star_mq.rk.bean;

import com.morning.star.star_mq.rk.consumer.MQReceiveCallback;
import com.morning.star.star_mq.rk.consumer.MQReceiveOrderly;
import com.morning.star.star_mq.utils.MQEnums.RECEIVE_TYPE;

public class MQReceive extends BaseBean {


	private MQReceiveOrderly orderlyCallBack;
	private MQReceiveCallback callback;
	private RECEIVE_TYPE type;
	
	public MQReceiveCallback getCallback() {
		return callback;
	}
	public void setCallback(MQReceiveCallback callback) {
		this.callback = callback;
	}
	public RECEIVE_TYPE getType() {
		return type;
	}
	public void setType(RECEIVE_TYPE type) {
		this.type = type;
	}
	public MQReceiveOrderly getOrderlyCallBack() {
		return orderlyCallBack;
	}
	public void setOrderlyCallBack(MQReceiveOrderly orderlyCallBack) {
		this.orderlyCallBack = orderlyCallBack;
	}

	
	
	
}
