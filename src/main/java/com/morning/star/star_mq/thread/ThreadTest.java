package com.morning.star.star_mq.thread;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadTest {

	public static void main(String[] args) {
		ConcurrentExecutor<String> executor = ConcurrentExecutor.build();
		for (int i = 0; i < 10; i++) {
			executor.add(new AtomicTask<String>() {

				@Override
				public Entry<String, String> execute()
						throws IllegalAccessException, IOException, InstantiationException {
					return new DefaultEntry<String,String>("Test", "AAA"+ThreadLocalRandom.current().nextInt(10000, 99999));
				}

			});
		}
		Map<String,List<String>> resultMap = executor.execute(10);
		List<String> result = resultMap.get("Test");
		Logger logger = LoggerFactory.getLogger(ThreadTest.class);
		logger.debug("测试");
		for(String temp: result){
			System.out.println(temp);
		}
		
		ConcurrentExecutor<String> executor2 = ConcurrentExecutor.build();
		for (int i = 0; i < 10; i++) {
			executor2.add(new AtomicTask<String>() {

				@Override
				public Entry<String, String> execute()
						throws IllegalAccessException, IOException, InstantiationException {
					return new DefaultEntry<String,String>("Test", "BBB"+ThreadLocalRandom.current().nextInt(10000, 99999));
				}

			});
		}
		Map<String,List<String>> resultMap2 = executor2.execute(10);
		List<String> result2 = resultMap2.get("Test");
		for(String temp: result2){
			System.out.println(temp);
		}
	}
	

}
