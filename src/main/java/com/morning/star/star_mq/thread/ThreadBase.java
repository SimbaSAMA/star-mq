package com.morning.star.star_mq.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadBase {
	
	private static final int mix=20;
	private static final int max=80;
	private static final int alive=5;

	private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(mix, max, alive, TimeUnit.SECONDS,
			new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory(),
			new MorningStartPolicy()) ;
	

	private static class ThreadBaseHandle {
		private static ThreadBase instance = new ThreadBase();
	}

	public ThreadBase getInstance() {
		return ThreadBaseHandle.instance;
	}
	
	public ThreadPoolExecutor getThreadPool() {
		return threadPool;
	}

	public void setThreadPool(ThreadPoolExecutor threadPool) {
		this.threadPool = threadPool;
	}



	public static class MorningStartPolicy implements RejectedExecutionHandler{
		
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			//发送异常消息并记录日志
		}
		
	}

}
