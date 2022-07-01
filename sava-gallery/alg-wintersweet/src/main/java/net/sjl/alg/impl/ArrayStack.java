package net.sjl.alg.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.sjl.alg.api.Stack;

public class ArrayStack<T> implements Stack<T> {
	private T[] items;
	private int size;
	
	public ArrayStack() {
		items = (T[])new Object[1];
		size = 0;
	}

	public void push(T item) {
		if(items.length == size) {
			int nSize = size << 1;
			ensureCapacity(nSize);
		}
		items[size++] = item;
	}
	

	public T pop() {
		if(isEmpty()) return null;
		
		if(size == items.length/4) ensureCapacity(items.length/2);
		
		return items[--size];
	}

	public T top() {
		if(isEmpty()) return null;
		
		int top = size - 1;
		return items[top];
	}
	
	public boolean isEmpty() {
		return size == 0; 
	}
	
	private void ensureCapacity(int newCapacity) {
		if(newCapacity <= items.length) throw new IllegalArgumentException("The specified new size is less than or equal to original");
		
		T[] newItems = (T[])new Object[newCapacity];
		System.arraycopy(items, 0, newItems, 0, size );
		items = newItems;
	}

	public Iterator<T> iterator() {
		return new ArrayStackIterator();
	}
	
	private class ArrayStackIterator implements Iterator<T> {
		private int currentIndex;
		
		public ArrayStackIterator() {
			currentIndex = 0;
		}

		public boolean hasNext() {
			return currentIndex < size;
		}

		public T next() {
			if(!hasNext()) throw new NoSuchElementException();
			
			return items[currentIndex++];
		}
	}

	public int size() {
		return size;
	}

}
