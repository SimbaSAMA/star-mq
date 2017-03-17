package com.morning.star.star_mq.utils;

public class MQEnums {

	public static enum SEND_TYPE{
		DEFAULT,
		/**
		 *强制顺序消费 
		 */
		FORCE_SEQUE,
		/**
		 * 事务消息
		 */
		TRANSACTION,
		/**
		 * 异步消息
		 */
		ASYN
	}
	
	public static enum TRANSACTION{
		UNKOWN,COMMIT,ROLLBACK
	}
	
	public static enum RECEIVE_TYPE{
		DEFAULT,
		/**
		 * 广播消费
		 */
		BROADCAST,
		/**
		 * 顺序消费
		 */
		ORDERLY,
		@Deprecated
		PULL,
		@Deprecated
		SCHEDULE
	}
	
	public static enum CODE{
		//SEND
		SUCCESS("0","OK"),
		FAIL("MQ001","FAILED");
		
		private CODE(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		private String code;
		private String msg;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		
	}
	
}
