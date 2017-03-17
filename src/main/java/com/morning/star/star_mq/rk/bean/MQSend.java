package com.morning.star.star_mq.rk.bean;

import com.morning.star.star_mq.rk.producer.MQSendCallback;
import com.morning.star.star_mq.rk.producer.MQSendTransactionCheck;
import com.morning.star.star_mq.rk.producer.MQSendTransactionExecuter;
import com.morning.star.star_mq.utils.MQEnums.SEND_TYPE;

public class MQSend extends BaseBean {
	
	private MQSendCallback callback;
	private MQSendTransactionCheck check;
	private MQSendTransactionExecuter executer;
	private Object tranCallBackParam;
	private SEND_TYPE type;
	private int queueNum = 10;
	private int msgTimeOut = 30000;
	private String forceSequeueKey;
	private Object msgBody;
	
	public MQSendCallback getCallback() {
		return callback;
	}
	/**
	 * 设置ASYN下的回调
	 * @param callback
	 */
	public void setCallback(MQSendCallback callback) {
		this.callback = callback;
	}
	public MQSendTransactionCheck getCheck() {
		return check;
	}
	/**
	 * 设置事务消息回查方法
	 * @param check
	 */
	public void setCheck(MQSendTransactionCheck check) {
		this.check = check;
	}
	public MQSendTransactionExecuter getExecuter() {
		return executer;
	}
	/**
	 * 设置事务处理方法
	 * @param executer
	 */
	public void setExecuter(MQSendTransactionExecuter executer) {
		this.executer = executer;
	}
	public SEND_TYPE getType() {
		return type;
	}
	public void setType(SEND_TYPE type) {
		this.type = type;
	}
	
	public Object getMsgBody() {
		return msgBody;
	}
	/**
	 * 设置消息体
	 * @param msgBody
	 */
	public void setMsgBody(Object msgBody) {
		this.msgBody = msgBody;
	}
	public Object getTranCallBackParam() {
		return tranCallBackParam;
	}
	/**
	 * 设置ASYN下的回调参数
	 * @param tranCallBackParam
	 */
	public void setTranCallBackParam(Object tranCallBackParam) {
		this.tranCallBackParam = tranCallBackParam;
	}
	public String getForceSequeueKey() {
		return forceSequeueKey;
	}
	/**
	 * 设置强制顺序消费的key
	 * @param forceSequeueKey
	 */
	public void setForceSequeueKey(String forceSequeueKey) {
		this.forceSequeueKey = forceSequeueKey;
	}
	public int getMsgTimeOut() {
		return msgTimeOut;
	}
	/**
	 * 消息超时时间(发送广播消息最好设置该值)
	 * @param msgTimeOut
	 */
	public void setMsgTimeOut(int msgTimeOut) {
		this.msgTimeOut = msgTimeOut;
	}
	public int getQueueNum() {
		return queueNum;
	}
	/**
	 * 生产队列大小(默认为10),不宜过大,视消息量与消费者数量衡量
	 * @param queueNum
	 */
	public void setQueueNum(int queueNum) {
		this.queueNum = queueNum;
	}
	
	
	

	
	
}
