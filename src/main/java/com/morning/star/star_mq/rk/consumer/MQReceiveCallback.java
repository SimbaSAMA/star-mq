package com.morning.star.star_mq.rk.consumer;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.morning.star.star_mq.rk.bean.Response;
import com.morning.star.star_mq.utils.SerializeUtils;

public abstract class MQReceiveCallback implements MessageListenerConcurrently{

	
	public abstract void callback(List<Response> responses);
	
	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> paramList,
			ConsumeConcurrentlyContext paramConsumeConcurrentlyContext) {
		try{
			List<Response> respones = new ArrayList<Response>();
			for(MessageExt ext : paramList){
				Response response = new Response();
				response.setMsgId(ext.getMsgId());
				response.setObj(SerializeUtils.unSerialObjInBean(ext.getBody(), Object.class));
				respones.add(response);
			}
			callback(respones);
		}catch(Exception e){
			e.printStackTrace();
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

}
