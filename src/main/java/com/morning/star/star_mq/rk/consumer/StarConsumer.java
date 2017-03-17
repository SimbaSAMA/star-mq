package com.morning.star.star_mq.rk.consumer;

import java.util.Set;

import com.alibaba.rocketmq.client.QueryResult;
import com.alibaba.rocketmq.client.consumer.MQConsumer;
import com.alibaba.rocketmq.client.consumer.MQPullConsumerScheduleService;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

public class StarConsumer extends MQPullConsumerScheduleService implements MQConsumer{

	public StarConsumer(String consumerGroup) {
		super(consumerGroup);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createTopic(String paramString1, String paramString2, int paramInt) throws MQClientException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createTopic(String paramString1, String paramString2, int paramInt1, int paramInt2)
			throws MQClientException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long searchOffset(MessageQueue paramMessageQueue, long paramLong) throws MQClientException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long maxOffset(MessageQueue paramMessageQueue) throws MQClientException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long minOffset(MessageQueue paramMessageQueue) throws MQClientException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long earliestMsgStoreTime(MessageQueue paramMessageQueue) throws MQClientException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public MessageExt viewMessage(String paramString)
			throws RemotingException, MQBrokerException, InterruptedException, MQClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QueryResult queryMessage(String paramString1, String paramString2, int paramInt, long paramLong1,
			long paramLong2) throws MQClientException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageExt viewMessage(String paramString1, String paramString2)
			throws RemotingException, MQBrokerException, InterruptedException, MQClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendMessageBack(MessageExt paramMessageExt, int paramInt)
			throws RemotingException, MQBrokerException, InterruptedException, MQClientException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessageBack(MessageExt paramMessageExt, int paramInt, String paramString)
			throws RemotingException, MQBrokerException, InterruptedException, MQClientException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<MessageQueue> fetchSubscribeMessageQueues(String paramString) throws MQClientException {
		// TODO Auto-generated method stub
		return null;
	}

}
