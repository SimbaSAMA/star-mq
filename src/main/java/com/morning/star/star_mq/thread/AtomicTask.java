package com.morning.star.star_mq.thread;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;

/**
 * @author meizs
 *
 */
public abstract class AtomicTask<T> implements Callable<Optional<Entry<String, T>>> {
	private String type;

	public AtomicTask() {
	}

	public AtomicTask(String type) {
		this.type = type;
	}

	public Optional<Entry<String, T>> call() throws Exception {
		if (StringUtils.isNotEmpty(this.type)) {
			Thread.currentThread().setName(this.type);
		}
		Entry<String,T> entry = this.execute();
		return Optional.ofNullable(entry);
	}

	public abstract Entry<String, T> execute() throws IllegalAccessException, IOException, InstantiationException;

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	protected Entry<String, T> entry(String key, T value) {
		return new DefaultEntry<String,T>(key, value);
	}
}
