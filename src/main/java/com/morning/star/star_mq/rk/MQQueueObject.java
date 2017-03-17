package com.morning.star.star_mq.rk;

public class MQQueueObject<T> {

	private Long born;
	private T t;

	public MQQueueObject(T t) {
		this.t = t;
		this.setBorn(System.currentTimeMillis());
	}

	public Long getBorn() {
		return born;
	}

	public void setBorn(Long born) {
		this.born = born;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

}
