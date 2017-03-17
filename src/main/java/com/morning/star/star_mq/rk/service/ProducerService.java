package com.morning.star.star_mq.rk.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.morning.star.star_mq.rk.bean.ProducerBean;
import com.morning.star.star_mq.rk.bean.Result;
import com.morning.star.star_mq.utils.MQEnums.CODE;
import com.morning.star.star_mq.utils.MQException;
import com.morning.star.star_mq.utils.MQUtil;


public class ProducerService {

	/**
	 * 默认生产方法
	 * 
	 * @param producerBean
	 */
	public Result defaultSend(ProducerBean producerBean) {
		Message message = MQUtil.getMessage(producerBean.getSend());
		SendResult sendResult;
		try {
			sendResult = producerBean.getProducer().send(message);
			return getSendResult(sendResult);
		} catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
			e.printStackTrace();
			throw new MQException(CODE.FAIL, e.getMessage());
		}

	}

	/**
	 * 异步生产,没有MSGID返回
	 * 
	 * @param producerBean
	 * @return
	 */
	public Result sendAsyn(ProducerBean producerBean) {
		Message message = MQUtil.getMessage(producerBean.getSend());
		try {
			producerBean.getProducer().send(message, producerBean.getSend().getCallback());
			return new Result(CODE.SUCCESS);
		} catch (MQClientException | RemotingException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new MQException(CODE.FAIL, e.getMessage());
		}
	}

	/**
	 * 发送事务消息
	 * @param producerBean
	 * @return
	 */
	public Result sendInTran(ProducerBean producerBean) {
		Message message = MQUtil.getMessage(producerBean.getSend());
		try {
			SendResult sendResult = producerBean.getProducer().sendMessageInTransaction(
					message,
					producerBean.getSend().getExecuter(), 
					producerBean.getSend().getTranCallBackParam());
			return getSendResult(sendResult);
		} catch (MQClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new MQException(CODE.FAIL, e.getMessage());
		}
	}
	
	/**
	 * 发送强制顺序一致方法
	 * @param producerBean
	 * @return
	 */
	public Result sendOrderlyMessage(ProducerBean producerBean){
		Message message = MQUtil.getMessage(producerBean.getSend());
		try{
			SendResult sendResult = producerBean.getProducer().send(message, new MessageQueueSelector() {
				
				@Override
				public MessageQueue select(List<MessageQueue> arg0, Message arg1, Object arg2) {
					// TODO Auto-generated method stub
					Long forceKey = strToNumber((String)arg2);
					Long index = forceKey % arg0.size();
                    return arg0.get(Integer.valueOf(index.toString()));
				}
			}, producerBean.getSend().getForceSequeueKey());
			return getSendResult(sendResult);
		} catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new MQException(CODE.FAIL, e.getMessage());
		}
	}

	private static Result getSendResult(SendResult sendResult) {
		switch (sendResult.getSendStatus()) {
		case FLUSH_DISK_TIMEOUT:
			throw new MQException(CODE.FAIL, "FLISH_DISK_TIMEOUT", sendResult.getMsgId());
		case FLUSH_SLAVE_TIMEOUT:
			throw new MQException(CODE.FAIL, "FLUSH_SLAVE_TIMEOUT", sendResult.getMsgId());
		case SLAVE_NOT_AVAILABLE:
			throw new MQException(CODE.FAIL, "SLAVE_NOT_AVAILABLE", sendResult.getMsgId());
		default:
			return new Result(CODE.SUCCESS, sendResult.getMsgId());
		}
	}
	
	public static Long strToNumber(String str) {
		Long result = 0l;
		StringBuffer sb = new StringBuffer();
		String regex = "[^0-9]";
		Pattern parttern = Pattern.compile(regex);
		Matcher matcher = parttern.matcher(str);
		while(matcher.find()){
			char[] chars = matcher.group(0).toCharArray();
			for(char c :chars){
				result += (int)c;
			}
		}
		sb.append(result).append(str.replaceAll(regex, ""));
		return Long.valueOf(sb.toString());
	}
	
	public static void main(String[] args){
		System.out.println(ProducerService.strToNumber("PD9291038282718"));
	}
}
