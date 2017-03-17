package com.morning.star.star_mq.rk.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.morning.star.star_mq.rk.bean.ConsumerBean;
import com.morning.star.star_mq.rk.bean.MQReceive;
import com.morning.star.star_mq.rk.bean.Result;
import com.morning.star.star_mq.utils.MQEnums.CODE;
import com.morning.star.star_mq.utils.MQException;

public class ConsumerService {

	public Result defaultReveive(ConsumerBean consumerBean){
		MQReceive receive = consumerBean.getReceive();
		try {
			consumerBean.getPushConsumer().setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
			consumerBean.getPushConsumer().subscribe(receive.getTopic(),
					receive.getTags());
			if(StringUtils.isNotEmpty(receive.getInstanceName())){
				consumerBean.getPushConsumer().setInstanceName(receive.getInstanceName());
			}
			consumerBean.getPushConsumer().registerMessageListener(receive.getCallback());
			consumerBean.getPushConsumer().start();
			return new Result(CODE.SUCCESS);
		} catch (MQClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new MQException(CODE.FAIL,e.getErrorMessage());
		}
	}
	
	public Result orderlyReveive(ConsumerBean consumerBean){
		MQReceive receive = consumerBean.getReceive();
		try {
			consumerBean.getPushConsumer().setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
			consumerBean.getPushConsumer().subscribe(receive.getTopic(),
					receive.getTags());
			if(StringUtils.isNotEmpty(receive.getInstanceName())){
				consumerBean.getPushConsumer().setInstanceName(receive.getInstanceName());
			}
			consumerBean.getPushConsumer().registerMessageListener(receive.getOrderlyCallBack());
			consumerBean.getPushConsumer().start();
			return new Result(CODE.SUCCESS);
		} catch (MQClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new MQException(CODE.FAIL,e.getErrorMessage());
		}
	}
	
	public Result pullReceive(ConsumerBean consumerBean){
		
		return null;
	}
	
	public Result scheduleReceive(ConsumerBean consumerBean){
		
		return null;
	}
	
}
