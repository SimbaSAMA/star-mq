package com.morning.star.star_mq.rk.bean;

import com.alibaba.rocketmq.client.consumer.DefaultMQPullConsumer;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.MQPullConsumer;
import com.alibaba.rocketmq.client.consumer.MQPullConsumerScheduleService;
import com.alibaba.rocketmq.client.consumer.MQPushConsumer;

public class ConsumerBean {

	private DefaultMQPushConsumer pushConsumer;
	private DefaultMQPullConsumer pullConsumer;
	private MQPullConsumerScheduleService scheduleConsumer;
	private MQReceive receive;
	public MQPullConsumerScheduleService getScheduleConsumer() {
		return scheduleConsumer;
	}
	public void setScheduleConsumer(MQPullConsumerScheduleService scheduleConsumer) {
		this.scheduleConsumer = scheduleConsumer;
	}
	public MQReceive getReceive() {
		return receive;
	}
	public void setReceive(MQReceive receive) {
		this.receive = receive;
	}
	public DefaultMQPushConsumer getPushConsumer() {
		return pushConsumer;
	}
	public void setPushConsumer(DefaultMQPushConsumer pushConsumer) {
		this.pushConsumer = pushConsumer;
	}
	public DefaultMQPullConsumer getPullConsumer() {
		return pullConsumer;
	}
	public void setPullConsumer(DefaultMQPullConsumer pullConsumer) {
		this.pullConsumer = pullConsumer;
	}
	
}
