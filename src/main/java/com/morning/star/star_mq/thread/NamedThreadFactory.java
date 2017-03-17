package com.morning.star.star_mq.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {
	private static final AtomicInteger _instanceNumber = new AtomicInteger();
	private final AtomicInteger _threadNumber = new AtomicInteger();
	private final String _namePrefix;

	public NamedThreadFactory(String name) {
		this._namePrefix = name + "-" + _instanceNumber.incrementAndGet();
	}

	public Thread newThread(Runnable runnable) {
		String name = this._namePrefix + "-" + this._threadNumber.incrementAndGet();
		Thread newThread = new Thread(runnable, name);
		return newThread;
	}
	
}
