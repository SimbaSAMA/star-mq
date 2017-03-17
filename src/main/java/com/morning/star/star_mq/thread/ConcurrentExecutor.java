package com.morning.star.star_mq.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author meizs
 *
 */
public class ConcurrentExecutor<T> extends AbstractConcurrentExecutor<T> {
	private static final int MAXNUM = 1024;
	private static final long ALIVE = 60000L;
	private static Logger logger = LoggerFactory.getLogger(ConcurrentExecutor.class);
	private static int MINNUM = 1024;
	private static ExecutorService executorService;

	public static <T> ConcurrentExecutor<T> build() {
		return new ConcurrentExecutor<T>();
	}

	protected ExecutorService getExecutor() {
		return executorService;
	}

	static {
		LinkedBlockingQueue queue = new LinkedBlockingQueue(4096);
		NamedThreadFactory factory = new NamedThreadFactory("concurrent_executor");
		AbortPolicy handler = new AbortPolicy();
		executorService = new ThreadPoolExecutor(MINNUM, MAXNUM, ALIVE, TimeUnit.MILLISECONDS, queue, factory, handler);
	}
}
