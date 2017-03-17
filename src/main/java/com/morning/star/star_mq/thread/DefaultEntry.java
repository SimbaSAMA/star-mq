package com.morning.star.star_mq.thread;

import java.util.Map.Entry;

/**
 * @author meizs
 *
 */
public class DefaultEntry<K, V> implements Entry<K, V> {
	private K key;
	private V value;

	public DefaultEntry() {
	}

	public DefaultEntry(K key, V value) {
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return this.key;
	}

	public V getValue() {
		return this.value;
	}

	public V setValue(V value) {
		this.value = value;
		return value;
	}

	public void setKey(K key) {
		this.key = key;
	}
}
