package com.morning.star.star_mq.rk;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.rocketmq.client.consumer.DefaultMQPullConsumer;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.MQPullConsumerScheduleService;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MQProducer;
import com.alibaba.rocketmq.client.producer.TransactionMQProducer;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import com.morning.star.star_mq.rk.bean.ConsumerBean;
import com.morning.star.star_mq.rk.bean.MQCode;
import com.morning.star.star_mq.rk.bean.MQReceive;
import com.morning.star.star_mq.rk.bean.MQSend;
import com.morning.star.star_mq.rk.bean.ProducerBean;
import com.morning.star.star_mq.rk.bean.Result;
import com.morning.star.star_mq.rk.service.ConsumerService;
import com.morning.star.star_mq.rk.service.ProducerService;
import com.morning.star.star_mq.utils.MQEnums.CODE;
import com.morning.star.star_mq.utils.MQEnums.RECEIVE_TYPE;
import com.morning.star.star_mq.utils.MQEnums.SEND_TYPE;

public class MQFactory {

	private Logger logger = LoggerFactory.getLogger(MQFactory.class);
	
	private String groupName;
	private String namesrvAddrs;
	private static ConcurrentHashMap<String, MQProducer> producerMap = new ConcurrentHashMap<String, MQProducer>();
	private static ConcurrentHashMap<String, DefaultMQPullConsumer> pullMap = new ConcurrentHashMap<String, DefaultMQPullConsumer>();
	private static ConcurrentHashMap<String, DefaultMQPushConsumer> pushMap = new ConcurrentHashMap<String, DefaultMQPushConsumer>();
	private static ConcurrentHashMap<String, MQPullConsumerScheduleService> scheduleMap = new ConcurrentHashMap<String, MQPullConsumerScheduleService>();

	public MQFactory(String groupName,String namesrvAddrs){
		this.setGroupName(groupName);
		this.setNamesrvAddrs(namesrvAddrs);
		
	}
	
//	private static class FactoryHandle {
//		private static MQFactory instance = new MQFactory();
//	}
//
//	public static MQFactory getInstance() {
//		return FactoryHandle.instance;
//	}

	private  ProducerBean initProducer(MQSend send) throws MQClientException {
		if(null==send){
			throw new MQClientException(MQCode.MQ_PARAMETER_ERR, "MQSend is requeried");
		}else{
			if(StringUtils.isEmpty(send.getTopic())){
				throw new MQClientException(MQCode.MQ_PARAMETER_ERR, "Topic is requeried");
			}else if(null == send.getMsgBody()){
				throw new MQClientException(MQCode.MQ_PARAMETER_ERR, "MsgBody is requeried");
			}else if(send.getType()==SEND_TYPE.TRANSACTION){
				if(send.getCheck()==null||send.getExecuter()==null){
					throw new MQClientException(MQCode.MQ_PARAMETER_ERR, "Can't find 'Executer' or 'Check' in this Transactional Producer");
				}
			}else if(send.getType()==SEND_TYPE.ASYN){
				if(send.getCallback()==null){
					throw new MQClientException(MQCode.MQ_PARAMETER_ERR, "Can't find 'Callback' in this Asyn Producer");
				}
			}else if(send.getType()==SEND_TYPE.FORCE_SEQUE){
				if(StringUtils.isEmpty(send.getForceSequeueKey())){
					throw new MQClientException(MQCode.MQ_PARAMETER_ERR, "Can't find 'ForceSequeueKey' in this ForceSeq Producer");
				}
			}
		}
		ProducerBean producerBean = new ProducerBean();
		producerBean.setSend(send);
		String gname = this.getGroupName()+"_producer";
		String key = producerKey(send);
		if (!producerMap.containsKey(key)) {
			synchronized (producerMap) {
				if(!producerMap.containsKey(key)){
					DefaultMQProducer tempProducer = new DefaultMQProducer();
					tempProducer.setProducerGroup(gname);
					tempProducer.setNamesrvAddr(namesrvAddrs);
					if (StringUtils.isNotEmpty(send.getInstanceName())) {
						tempProducer.setInstanceName(send.getInstanceName());
					}
					tempProducer.setDefaultTopicQueueNums(send.getQueueNum());
					switch (send.getType()) {
					case DEFAULT:
						producerMap.put(key, tempProducer);
						break;
					case ASYN:
						producerMap.put(key, tempProducer);
						break;
					default:
						TransactionMQProducer tranProducer = new TransactionMQProducer();
						tranProducer.setNamesrvAddr(namesrvAddrs);
						tranProducer.setProducerGroup(gname);
						tranProducer.setCheckThreadPoolMinSize(2);
						tranProducer.setCheckThreadPoolMaxSize(2);
						tranProducer.setCheckRequestHoldMax(2000);
						tranProducer.setTransactionCheckListener(send.getCheck());
						producerMap.put(key, tranProducer);
						break;
					}
					producerMap.get(key).start();
				}
			}
			producerBean.setProducer(producerMap.get(key));
			return producerBean;
		} else {
			producerBean.setProducer(producerMap.get(key));
			return producerBean;
		}
	}

	private ConsumerBean initConsumer(MQReceive receive) throws MQClientException {
		if(null==receive){
			throw new MQClientException(MQCode.MQ_PARAMETER_ERR, "MQReceive is requeried");
		}else{
			if(StringUtils.isEmpty(receive.getTopic())){
				throw new MQClientException(MQCode.MQ_PARAMETER_ERR, "Topic is requeried");
			}else if(receive.getType()==RECEIVE_TYPE.DEFAULT && null == receive.getCallback()){
				throw new MQClientException(MQCode.MQ_PARAMETER_ERR, "CallBack is requeried");
			}else if(receive.getType()==RECEIVE_TYPE.ORDERLY && null == receive.getOrderlyCallBack()){
				throw new MQClientException(MQCode.MQ_PARAMETER_ERR, "OrderlyCallBack is requeried in 'ORDERLY' receive");
			}
		}
		String gname = this.getGroupName()+"_consumer";
		String key = consumerKey(receive);
		ConsumerBean consumerBean = new ConsumerBean();
		consumerBean.setReceive(receive);
		boolean exists = receiveExists(receive);
		if (!exists) {
			synchronized (this) {
				if (!receiveExists(receive)) {
					switch (receive.getType()) {
					case SCHEDULE:
						MQPullConsumerScheduleService scheduleConsumer = new MQPullConsumerScheduleService(
								gname);
						scheduleMap.put(key, scheduleConsumer);
						break;
					case PULL:
						DefaultMQPullConsumer pullConsumer = new DefaultMQPullConsumer(gname);
						pullConsumer.setNamesrvAddr(namesrvAddrs);
						pullMap.put(key, pullConsumer);
					case BROADCAST:
						DefaultMQPushConsumer brodConsumer = new DefaultMQPushConsumer(gname);
						brodConsumer.setNamesrvAddr(namesrvAddrs);
						brodConsumer.setMessageModel(MessageModel.BROADCASTING);
				        pushMap.put(key, brodConsumer);
				        break;
					default:
						DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer(gname);
						pushConsumer.setNamesrvAddr(namesrvAddrs);
						pushMap.put(key, pushConsumer);
						break;
					}
				}
			}
		}
		switch (receive.getType()) {
		case SCHEDULE:
			consumerBean.setScheduleConsumer(scheduleMap.get(key));
			break;
		case PULL:
			consumerBean.setPullConsumer(pullMap.get(key));
			break;
		default:
			consumerBean.setPushConsumer(pushMap.get(key));
			break;
		}
		return consumerBean;
	}

	/**
	 * 启动生产者开始发布消息
	 * 
	 * @param send
	 * @return
	 */
	public Result sendMsg(MQSend send) {

		ProducerService service = new ProducerService();

		try {
			ProducerBean proBean = initProducer(send);

			switch (send.getType()) {
			case DEFAULT:
				service.defaultSend(proBean);
//				proBean.getProducer().shutdown();
//				producerMap.remove(send.getGroupName());
				return new Result(CODE.SUCCESS);
			case ASYN:
				return service.sendAsyn(proBean);
			case FORCE_SEQUE:
				return service.sendOrderlyMessage(proBean);
			default:
				return service.sendInTran(proBean);
			}

		} catch (MQClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Result(CODE.FAIL);
		}
	}

	/**
	 * 启动消费者开始订阅消息
	 * @param receive
	 * @return
	 */
	public Result receiveMsg(MQReceive receive) {
		ConsumerService service = new ConsumerService();
		try {
			ConsumerBean consumerBean = initConsumer(receive);
			switch (receive.getType()) {
			case SCHEDULE:
				return new Result(CODE.FAIL, "暂不支持定时");
			case PULL:
				return new Result(CODE.FAIL, "暂不支持拉取");
			case ORDERLY:
				return service.orderlyReveive(consumerBean);
			default:
				return service.defaultReveive(consumerBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(CODE.FAIL);
		}
	}

	/**
	 * 停止生产
	 * @param send
	 */
	public void producerShutdown(MQSend send) {
		if(producerMap.containsKey(send.getTopic())){
			synchronized (producerMap) {
				if(producerMap.containsKey(send.getTopic())){
					producerMap.get(send.getTopic()).shutdown();
					producerMap.remove(send.getTopic());
				}
			}
		}
	}

	/**
	 * 停止消费者
	 * 
	 * @param receive
	 */
	public void consumerShutdown(MQReceive receive) {
		boolean exists = receiveExists(receive);
		String key = consumerKey(receive);
		if (exists) {
			synchronized (this) {
				if (receiveExists(receive)) {
					switch (receive.getType()) {
					case SCHEDULE:
						scheduleMap.get(key).shutdown();
						scheduleMap.remove(key);
						break;
					case PULL:
						pullMap.get(key).shutdown();
						pullMap.remove(key);
						break;
					default:
						pushMap.get(key).shutdown();
						pushMap.remove(key);
						break;
					}
				}
			}
		}
	}

	private boolean receiveExists(MQReceive receive) {
		boolean exists = false;
		switch (receive.getType()) {
		case SCHEDULE:
			exists = scheduleMap.containsKey(consumerKey(receive));
			break;
		case PULL:
			exists = pullMap.containsKey(consumerKey(receive));
			break;
		default:
			exists = pushMap.containsKey(consumerKey(receive));
			break;
		}
		return exists;
	}
	
	private String producerKey(MQSend send){
		StringBuffer sb = new StringBuffer();
		sb.append(send.getTopic());
		sb.append(send.getType().toString());
		if (StringUtils.isNotEmpty(send.getTags())) {
			for (String tag : send.getTags().split(",")) {
				sb.append(tag);
			}
		}
		return sb.toString();
	}
	private String consumerKey(MQReceive receive){
		StringBuffer sb = new StringBuffer();
		sb.append(receive.getTopic());
		sb.append(receive.getType().toString());
		if (StringUtils.isNotEmpty(receive.getTags())) {
			for (String tag : receive.getTags().split(",")) {
				sb.append(tag);
			}
		}
		return sb.toString();
	}

//	public static void main(String[] args) {
//		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();
//		map.put("test", "1");
//		new Thread() {
//			@Override
//			public void run() {
//				map.remove("test");
//				super.run();
//			}
//		}.start();
//
//		new Thread() {
//			@Override
//			public void run() {
//				map.remove("test");
//				super.run();
//			}
//		}.start();
//		System.out.println(map.get("test").length());
//	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getNamesrvAddrs() {
		return namesrvAddrs;
	}

	public void setNamesrvAddrs(String namesrvAddrs) {
		this.namesrvAddrs = namesrvAddrs;
	}


}
