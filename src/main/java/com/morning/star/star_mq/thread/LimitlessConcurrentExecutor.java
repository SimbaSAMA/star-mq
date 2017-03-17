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
public class LimitlessConcurrentExecutor extends AbstractConcurrentExecutor {
	private static final int MAXNUM = Runtime.getRuntime().availableProcessors() * 2;
	private static final long ALIVE = 60000L;
	private static Logger logger = LoggerFactory.getLogger(LimitlessConcurrentExecutor.class);
	private static int MINNUM = Runtime.getRuntime().availableProcessors();
	private static ExecutorService executorService;

	public static LimitlessConcurrentExecutor build() {
		return new LimitlessConcurrentExecutor();
	}

	protected ExecutorService getExecutor() {
		return executorService;
	}

	static {
		LinkedBlockingQueue queue = new LinkedBlockingQueue();
		NamedThreadFactory factory = new NamedThreadFactory("limitless_concurrent_executor");
		AbortPolicy handler = new AbortPolicy();
		executorService = new ThreadPoolExecutor(MINNUM, MAXNUM, 60000L, TimeUnit.MILLISECONDS, queue, factory,
				handler);
	}
}
