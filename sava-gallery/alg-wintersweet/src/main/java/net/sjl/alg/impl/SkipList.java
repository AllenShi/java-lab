package net.sjl.alg.impl;

import java.util.Random;

final class NEGTIVE_INFINITY<K> implements Comparable<K> {
	public int compareTo(K o) {
		return -1;
	}
}

final class POSITIVE_INFINITY<K> implements Comparable<K> {
	public int compareTo(K o) {
		return 1;
	}
}

public class SkipList<K extends Comparable<K>, V> {
	private SkipListEntry head, tail;
	private Random r;
	private int size;
	private int height;
	
	public SkipList() {
		r = new Random();
		size = 0;
		height = 0;
		head = new SkipListEntry((K)new NEGTIVE_INFINITY<K>(), null);
		tail = new SkipListEntry((K)new POSITIVE_INFINITY<K>(), null);
		head.right = tail;
		tail.left = head;
	}
	
	public int getSize() {
		return size;
	}
	
	public boolean isEmpty() {
		return size <= 0;
	}
	
	public V get(K key) {
		SkipListEntry p = findEntry(key);
		if(p.key.compareTo(key) == 0) return p.value;
		
		return null;
	}
	
	public V put(K key, V value) {
		SkipListEntry p = findEntry(key);
		
		if(p.key.compareTo(key) == 0) {
			V oldV = p.value;
			p.value = value;
			return oldV;
		}
		
		SkipListEntry q = new SkipListEntry(key, value);
		q.right = p.right;
		q.left = p;
		p.right.left = q;
		p.right = q;
		
		int i = 0;
		while(r.nextDouble() < 0.5) {
			
			if(i >= height) {
				increaseLayer();
			}
			
			while(p.up == null) {
				p = p.left;
			}
			
			p = p.up;
			SkipListEntry e = new SkipListEntry(key, value);
			e.down = q;
			q.up = e;
			
			e.right = p.right;
			e.left = p;
			
			p.right.left = e;
			p.right = e;
			
			q = e;
			
			i++;
		}
		
		height++;
		
		return null;
	}
	
	public void remove(K key) {
		
	}
	
	/**
	 * Return floor(Key)
	 * @param key
	 * @return
	 */
	private SkipListEntry findEntry(K key) {
		SkipListEntry p = head;
		
		while(true) {
			while(p.right.key.compareTo(key) <= 0) {
				p.right = p;
			}
			
			if(p.down == null) break;
			
			p = p.down;
		}
		
		return p;
	}
	
	private void increaseLayer() {
		SkipListEntry p1 = new SkipListEntry((K)new NEGTIVE_INFINITY(), null);
		SkipListEntry p2 = new SkipListEntry((K)new POSITIVE_INFINITY(), null);
		
		head.up = p1;
		p1.down = head;
		
		tail.up = p2;
		p2.down = tail;
		
		p1.right = p2;
		p2.left = p1;
		
		head = p1;
		tail = p2;
	}
	
	private class SkipListEntry {
		private SkipListEntry left, right, up, down;
		private K key;
		private V value;
		
		SkipListEntry(K key, V value) {
			this.key = key;
			this.value =value;
			this.left = null;
			this.right = null;
			this.up = null;
			this.down = null;
		}
	}
}
