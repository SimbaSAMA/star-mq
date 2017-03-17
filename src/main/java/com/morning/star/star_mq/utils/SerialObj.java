package com.morning.star.star_mq.utils;

import java.io.Serializable;

public class SerialObj<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3665873650376591256L;

	private T t;

	public SerialObj(T t) {
		this.t = t;
	}

	public T getT() {
		return this.t;
	}

	public void setT(T t) {
		this.t = t;
	}
}
