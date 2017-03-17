package com.morning.star.star_mq;

import java.util.List;

import com.morning.star.star_mq.rk.MQFactory;
import com.morning.star.star_mq.rk.bean.MQReceive;
import com.morning.star.star_mq.rk.bean.Response;
import com.morning.star.star_mq.rk.consumer.MQReceiveCallback;
import com.morning.star.star_mq.rk.consumer.MQReceiveOrderly;
import com.morning.star.star_mq.utils.MQEnums.RECEIVE_TYPE;

public class TestConsumer {

	public static void main(String[] args){
		MQFactory factory = new MQFactory("consumerGroup1", "106.75.134.251:9876;106.75.145.226:9876");
    	MQReceive recive = new MQReceive();
    	recive.setTopic("TestTopic1015");
    	recive.setTags("*");
    	
    	//设置消费者类型为ORDERLY ,表示消费顺序消息
    	recive.setType(RECEIVE_TYPE.DEFAULT);
    	//设置顺序消费OrderlyCallBack,不用再设置Callback
    	recive.setCallback(new MQReceiveCallback() {
			
    		@Override
			public void callback(List<Response> responses) {
				for(Response response : responses){
					System.out.println(response.getMsgId()+"  "+(String)response.getObj());
				}
			}
		});

    	factory.receiveMsg(recive);

        System.out.println("Consumer Started.");
	}
	
}
