package com.morning.star.star_mq.rk;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.MQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.LocalTransactionExecuter;
import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.client.producer.MQProducer;
import com.alibaba.rocketmq.client.producer.SendCallback;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.morning.star.star_mq.rk.bean.MQSend;
import com.morning.star.star_mq.rk.consumer.MQReceiveCallback;
import com.morning.star.star_mq.utils.SerializeUtils;
import com.morning.star.star_mq.utils.MQEnums.SEND_TYPE;

public class Producer {
	
	public static void receive(MQPushConsumer consumer,MQReceiveCallback callBack){
		consumer.registerMessageListener(callBack);
	}
	
    public static void main(String[] args) throws MQClientException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("test");
        producer.setNamesrvAddr("120.76.229.139:9876;120.55.66.131:9876");
        producer.setInstanceName("Producer");
        producer.setDefaultTopicQueueNums(2);
        producer.start();

        for (int i = 0; i < 10; i++) {
            try {
            	SerializeUtils.SerializeObject(("Hello RocketMQ " + i));
                Message msg = new Message("TestTopic1",// topic
                        "TagA",// tag
                        SerializeUtils.SerializeObject(("Hello RocketMQ " + i))// body
                );
                SendResult sendResult = producer.send(msg);
                LocalTransactionExecuter tranExecuter = new LocalTransactionExecuter() {

                    @Override
                    public LocalTransactionState executeLocalTransactionBranch(Message msg, Object arg) {
                        // TODO Auto-generated method stub
                        return null;
                    }
                };

                //producer.sendMessageInTransaction(msg, tranExecuter, arg)
                System.out.println(sendResult);
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
            }
        }
    	
        producer.shutdown();
    	MQFactory factory = new MQFactory("","");
    	MQSend send = new MQSend();
    	send.setTopic("TestTopic1");
    	send.setMsgBody("This is a message~balalala");
    	send.setType(SEND_TYPE.DEFAULT);
    	factory.sendMsg(send);
    }
}