package net.sjl.alg.impl;

import net.sjl.alg.api.MaxPriorityQueue;

public class MaxHeapPriorityQueue<Key extends Comparable<? super Key>> implements MaxPriorityQueue<Key> {
	private Key[] keys;
	private int size;
	
	public MaxHeapPriorityQueue() {
		keys = (Key[])new Comparable[2];
		size = 0;
	}

	public void insert(Key key) {
		if(size == (keys.length - 1)) {
			resize(2*size + 1);
		}
		
		keys[++size] = key;
		swim(size);
	}

	public Key delMax() {
		if(size == 0) 
			return null;
		
		if(size == (keys.length - 1)/4) 
			resize(2*size + 1);
		Key max = keys[1];
		exchange(1, size);
		keys[size--] = null;
		sink(1);
		return max;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}
	
	private void swim(int k) {
		while(k > 1 && lessThan(k/2, k)) {
			exchange(k/2, k);
			k = k/2;
		}
	}
	
	private void sink(int k) {
		while(2*k <= size) {
			int j = 2*k;
			if((j+1 <= size) && lessThan(j, j+1)) j++;
			
			if(lessThan(j, k)) break;
			
			exchange(k, j);
			k = j;
		}
	}
	
	private boolean lessThan(int idx1, int idx2) {
		return keys[idx1].compareTo(keys[idx2]) < 0;
	}
	
	private void exchange(int idx1, int idx2) {
		Key key = keys[idx1];
		keys[idx1] = keys[idx2];
		keys[idx2] = key;
	}
	
	private void resize(int newSize) {
		assert(newSize > 0 && newSize > size);
		
		Key[] newKeys = (Key[])new Comparable[newSize + 1];
		System.arraycopy(keys, 1, newKeys, 1, size);
		keys = newKeys;
	}
	
}
