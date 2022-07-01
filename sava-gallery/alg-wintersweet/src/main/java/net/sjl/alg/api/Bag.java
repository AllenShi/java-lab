package net.sjl.alg.api;

import java.util.Iterator;

public class Bag<Item> implements Iterable<Item> {
	
	private Item[] items;
	private int count;
	private int capacity;
	
	public Bag() {
		capacity = 2;
		items = (Item[])new Object[capacity];
	}
	
	public Bag(int initCapacity) {
		assert(initCapacity > 0);
		this.capacity = initCapacity;
		items = (Item[])new Object[this.capacity];
	}
	
	public void add(Item item) {
		
	}
	
	public Iterator<Item> iterator() {
		return new BagIterator();
	}
	
	private void resize() {
		
	}
	
	private class BagIterator implements Iterator<Item> {
		private int currentIndex = -1;

		public boolean hasNext() {
			return currentIndex < count - 1;
		}

		public Item next() {
			assert(hasNext());
			
			return items[++currentIndex];
		}
	}

}
