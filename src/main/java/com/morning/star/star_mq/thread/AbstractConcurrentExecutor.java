package com.morning.star.star_mq.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author meizs
 *
 */
public abstract class AbstractConcurrentExecutor<T> {
	private static Logger logger = LoggerFactory.getLogger(ConcurrentExecutor.class);
	private Queue<AtomicTask<T>> taskQueue = new LinkedBlockingQueue<AtomicTask<T>>();
	protected abstract ExecutorService getExecutor();

	/**
	 * 添加新线程
	 * @param atomicTask
	 * @return
	 */
	public AbstractConcurrentExecutor<T> add(AtomicTask<T> atomicTask) {
		this.taskQueue.add(atomicTask);
		return this;
	}

	/**
	 * 异步提交任务
	 * @param task
	 */
	public void priorityExecute(Runnable task) {
		this.getExecutor().execute(task);
	}

	/**
	 * 阻塞执行多线程任务
	 * @param jobname 线程前缀名
	 * @param timeoutmilliSeconds 超时时间(ms)
	 * @return
	 */
	public Map<String, List<T>> execute(String jobname, long timeoutmilliSeconds) {
		if (null != this.taskQueue && this.taskQueue.size() != 0) {
			HashMap<String,List<T>> map = new HashMap<String,List<T>>(this.taskQueue.size());
			long startTime = System.currentTimeMillis();
			long timeout = timeoutmilliSeconds;
			ArrayBlockingQueue<Future<Optional<Entry<String,T>>>> futures = new ArrayBlockingQueue<Future<Optional<Entry<String,T>>>>(this.taskQueue.size());

			Future<Optional<Entry<String,T>>> future;
			while (!this.taskQueue.isEmpty()) {
				AtomicTask<T> task = this.taskQueue.poll();
				future = this.getExecutor().submit(task);
				futures.add(future);
			}

			while (!futures.isEmpty()) {
				Future<Optional<Entry<String,T>>> future1 = futures.poll();

				try {
					future = null;
					long now = System.currentTimeMillis();
					long lastTime = now - startTime;
					Optional<Entry<String,T>> optinal;
					if (lastTime < timeout) {
						optinal = future1.get(timeout - lastTime, TimeUnit.MILLISECONDS);
					} else {
						if (!future1.isDone()) {
							future1.cancel(Boolean.FALSE.booleanValue());
							continue;
						}

						optinal = future1.get(1L, TimeUnit.MILLISECONDS);
					}

					if (optinal.isPresent()) {
						Entry<String,T> entry = optinal.get();
						if (null != entry && null != entry.getValue()) {
							String key = (String) entry.getKey();
							if (!map.containsKey(key)) {
								map.put(key, new ArrayList<T>());
							}
							map.get(key).add(entry.getValue());
						}
					}
				} catch (TimeoutException | InterruptedException arg19) {
					try {
						future1.cancel(Boolean.FALSE.booleanValue());
					} catch (Exception arg18) {
						logger.error(arg18.getMessage(), arg18);
						logger.debug("future canel error, ignore it.");
					}

					logger.error(jobname + " task time out,but never mind, ignore it.");
				} catch (ExecutionException arg20) {
					try {
						future1.cancel(Boolean.FALSE.booleanValue());
					} catch (Exception arg17) {
						logger.error(arg17.getMessage(), arg17);
						logger.error("future canel error, ignore it.");
					}

					logger.error("Error when execute inner task.", arg20);
				} catch (Throwable arg21) {
					logger.debug(arg21.getMessage(), arg21);
				}
			}

			return map;
		} else {
			return null;
		}
	}

	/**
	 * 阻塞执行
	 * @param timeoutmilliSeconds
	 * @return
	 */
	public Map<String, List<T>> execute(long timeoutmilliSeconds) {
		return this.execute("", timeoutmilliSeconds);
	}

	public static <T> List<T> pollFromQueue(BlockingQueue<Optional<T>> queue, int expiredSize, long timeout) {
		ArrayList<T> list = new ArrayList<T>();
		long startTime = System.currentTimeMillis();
		long cursor = System.currentTimeMillis();

		try {
			for (long e = cursor - startTime; e < timeout && expiredSize > 0; cursor = System.currentTimeMillis()) {
				Optional<T> t = (Optional<T>) queue.poll(timeout - e, TimeUnit.MILLISECONDS);
				if (null != t) {
					list.add(t.orElse(null));
					--expiredSize;
				}
			}
		} catch (InterruptedException arg11) {
			logger.error("interrupt by time out.");
		}

		return list;
	}
}
