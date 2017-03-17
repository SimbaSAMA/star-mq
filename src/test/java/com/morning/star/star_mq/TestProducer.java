package com.morning.star.star_mq;

import com.morning.star.star_mq.rk.MQFactory;
import com.morning.star.star_mq.rk.bean.MQSend;
import com.morning.star.star_mq.rk.producer.MQSendTransactionCheck;
import com.morning.star.star_mq.rk.producer.MQSendTransactionExecuter;
import com.morning.star.star_mq.utils.MQEnums.SEND_TYPE;
import com.morning.star.star_mq.utils.MQEnums.TRANSACTION;

public class TestProducer {

	public static void main(String[] args) {
		MQFactory factory = new MQFactory("consumerGroup1", "106.75.134.251:9876;106.75.145.226:9876");
		for (int i = 0; i < 3; i++) {
			MQSend send = new MQSend();
			send.setTopic("TestTopic1015");
			send.setForceSequeueKey("PD123456");
			
			//消息类型设置为顺序消息
			send.setType(SEND_TYPE.DEFAULT);
			//(必须)设置顺序key,比如针对订单的顺序消息,可以使用订单号作为key值.
//			send.setForceSequeueKey("PD123456");
			send.setMsgBody(new String("TestMessage " + i));
//			send.setMsgTimeOut(2000);
			send.setQueueNum(1);
			//设置回查函数,消息服务器会执行用来检查是否该消息的业务逻辑是否执行成功(触发概率很低)
//			send.setCheck(new MQSendTransactionCheck() {
//
//				@Override
//				public TRANSACTION transactionCheck() {
//					// TODO Auto-generated method stub
//					try{
//						System.out.println("Will I be Rich?");
//						return TRANSACTION.COMMIT;
//					}catch(Exception e){
//						return TRANSACTION.ROLLBACK;
//					}
//				}
//			});
			//设置事务处理方法,包含具体的业务处理逻辑
//			send.setExecuter(new MQSendTransactionExecuter() {
//
//				@Override
//				public void executorTran(Object callBackParam) {
//					// TODO Auto-generated method stub
//					try{
//						System.out.println("Hello Morning Start");
//					}catch(Exception e){
//						e.printStackTrace();
//					}
//				}
//			});
			
			// send.setCheck(new MQSendTransactionCheck() {
			//
			// @Override
			// public TRANSACTION transactionCheck() {
			// // TODO Auto-generated method stub
			// System.out.println("发起检查");
			// return TRANSACTION.COMMIT;
			// }
			// });
			// send.setExecuter(new MQSendTransactionExecuter() {
			//
			// @Override
			// public void executorTran() {
			// // TODO Auto-generated method stub
			// System.out.println(1/0);
			// }
			// });
			factory.sendMsg(send);
			System.out.println("Message has been sended  " + i);
		}
	}

}
