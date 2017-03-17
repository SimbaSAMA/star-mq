//package com.morning.star.star_mq.rk.listener;
//
//import java.util.List;
//
//import javax.sound.sampled.ReverbType;
//
//import org.springframework.context.ApplicationEvent;
//import org.springframework.context.ApplicationListener;
//
//import com.morning.star.star_mq.rk.MQFactory;
//import com.morning.star.star_mq.rk.bean.MQReceive;
//import com.morning.star.star_mq.utils.MQEnums.RECEIVE_TYPE;
//
//public abstract class ConsumerListener implements ApplicationListener<ApplicationEvent>{
//
//	private String groupName;
//	private String instanceName;
//	private String topic;
//	private String tags;
//	private String keys;
//	private RECEIVE_TYPE type;
//
//	
//	@Override
//	public void onApplicationEvent(ApplicationEvent arg0) {
//		try{
//			MQFactory factory = MQFactory.getInstance();
//			MQReceive receive = new MQReceive();
//			receive.setGroupName(this.getGroupName());
//			receive.setInstanceName(this.getInstanceName());
//			receive.setTopic(this.getTopic());
//			receive.setKey(this.getKeys());
//			receive.setType(this.getType());
//			factory.receiveMsg(receive);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		
//	}
//
//
//	public String getGroupName() {
//		return groupName;
//	}
//
//
//	public void setGroupName(String groupName) {
//		this.groupName = groupName;
//	}
//
//
//	public String getTopic() {
//		return topic;
//	}
//
//
//	public void setTopic(String topic) {
//		this.topic = topic;
//	}
//
//
//	public String getTags() {
//		return tags;
//	}
//
//
//	public void setTags(String tags) {
//		this.tags = tags;
//	}
//
//
//	public String getKeys() {
//		return keys;
//	}
//
//
//	public void setKeys(String keys) {
//		this.keys = keys;
//	}
//
//
//	public String getInstanceName() {
//		return instanceName;
//	}
//
//
//	public void setInstanceName(String instanceName) {
//		this.instanceName = instanceName;
//	}
//
//
//	public RECEIVE_TYPE getType() {
//		return type;
//	}
//
//
//	public void setType(RECEIVE_TYPE type) {
//		this.type = type;
//	}
//	
//	
//
//}
