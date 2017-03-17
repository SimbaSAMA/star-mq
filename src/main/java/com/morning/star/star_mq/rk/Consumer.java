package com.morning.star.star_mq.rk;

import java.util.List;

import org.springframework.util.ConcurrentReferenceHashMap.ReferenceType;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.morning.star.star_mq.rk.bean.MQReceive;
import com.morning.star.star_mq.rk.bean.Response;
import com.morning.star.star_mq.rk.consumer.MQReceiveCallback;
import com.morning.star.star_mq.utils.MQEnums.RECEIVE_TYPE;
import com.morning.star.star_mq.utils.SerializeUtils;

public class Consumer {

    public static void main(String[] args) throws InterruptedException, MQClientException {
//        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test");
//
//        consumer.setNamesrvAddr("120.76.229.139:9876;120.55.66.131:9876");
//        consumer.setInstanceName("Consumer");
//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
//
//        consumer.subscribe("TestTopic1", "*");
//
//        consumer.registerMessageListener(new MessageListenerConcurrently() {
//
//            @Override
//            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
//                                                            ConsumeConcurrentlyContext context) {
//            	for(MessageExt ext : msgs){
//            		try {
//            			
//            			System.out.println(Thread.currentThread().getName() + " Receive New Messages: " + SerializeUtils.unSerialObjInBean(ext.getBody(), String.class));
//					} catch (InstantiationException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (IllegalAccessException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//            	}
////                System.out.println(Thread.currentThread().getName() + " Receive New Messages: " + msgs);
//                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//            }
//        });
//
//        consumer.start();
//    	
    	MQFactory factory =new MQFactory("","");
    	MQReceive recive = new MQReceive();
    	recive.setTopic("TestTopic1");
    	recive.setType(RECEIVE_TYPE.DEFAULT);
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