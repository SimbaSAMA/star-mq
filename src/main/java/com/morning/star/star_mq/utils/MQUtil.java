package com.morning.star.star_mq.utils;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.rocketmq.common.message.Message;
import com.morning.star.star_mq.rk.bean.MQSend;

public class MQUtil {

	public static Message getMessage(MQSend send){
		byte[] msg = SerializeUtils.SerialObjInBean(send.getMsgBody());
		if(StringUtils.isNotEmpty(send.getTags())&&
				StringUtils.isEmpty(send.getKey())){
			return new Message(send.getTopic(),
					send.getTags(),
					msg);
		}else if(StringUtils.isNotEmpty(send.getTags())&&
				StringUtils.isNotEmpty(send.getKey())){
			return new Message(send.getTopic(),
					send.getTags(),
					send.getKey(),
					msg);
		}else{
			return new Message(send.getTopic(),
					msg);
		}
	}
	
}
