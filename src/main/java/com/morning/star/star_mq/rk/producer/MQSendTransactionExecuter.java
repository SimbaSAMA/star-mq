package com.morning.star.star_mq.rk.producer;

import com.alibaba.rocketmq.client.producer.LocalTransactionExecuter;
import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.common.message.Message;

public abstract class MQSendTransactionExecuter implements LocalTransactionExecuter {

	public abstract void executorTran(Object callBackParam);

	@Override
	public LocalTransactionState executeLocalTransactionBranch(Message paramMessage, Object paramObject) {
		try {
			executorTran(paramObject);
			return LocalTransactionState.COMMIT_MESSAGE;
		} catch (Exception e) {
			return LocalTransactionState.ROLLBACK_MESSAGE;
		}
	}

}
