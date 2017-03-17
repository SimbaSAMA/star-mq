package com.morning.star.star_mq.rk.producer;

import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.client.producer.TransactionCheckListener;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.morning.star.star_mq.utils.MQEnums.TRANSACTION;

public abstract class MQSendTransactionCheck implements TransactionCheckListener{

	public abstract TRANSACTION transactionCheck();
	
	@Override
	public LocalTransactionState checkLocalTransactionState(MessageExt paramMessageExt) {
		TRANSACTION tran = transactionCheck();
		switch (tran) {
		case UNKOWN:
			return LocalTransactionState.UNKNOW;
		case COMMIT:
			return LocalTransactionState.COMMIT_MESSAGE;
		default:
			return LocalTransactionState.ROLLBACK_MESSAGE;
		}
	}

}
