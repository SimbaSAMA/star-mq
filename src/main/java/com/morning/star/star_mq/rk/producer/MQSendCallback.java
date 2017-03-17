package com.morning.star.star_mq.rk.producer;

import com.alibaba.rocketmq.client.producer.SendCallback;
import com.alibaba.rocketmq.client.producer.SendResult;

/**
 * 生产异步回调
 * @author meizs
 *
 */
public abstract class MQSendCallback implements SendCallback{

	public abstract void hasSuccess(SendResult result);
	
	public abstract void hasError(Throwable error);
	
	
	@Override
	public void onSuccess(SendResult paramSendResult) {
		// TODO Auto-generated method stub
		try{
			hasSuccess(paramSendResult);
		}catch(Exception e){
			
		}
		
	}

	@Override
	public void onException(Throwable paramThrowable) {
		try{
			hasError(paramThrowable);
		}catch(Exception e){
			
		}
		
	}

}
