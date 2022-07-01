package net.sjl.alg.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.sjl.alg.api.Stack;

public class ListStack<T> implements Stack<T> {
	private Node<T> top;
	private Node<T> base;
	private int size;
	
	public ListStack() {
		this.top = null;
		this.base = null;
		this.size = 0;
	}
	
	public void push(T item) {
		Node<T> oldHead = top;
		top = new Node<T>(item);
		
		if(isEmpty()) base = top;
		else top.next = oldHead;
		
		size++;
	}

	public T pop() {
		if(isEmpty()) throw new NoSuchElementException();
		
		T item = top.item;
		top = top.next;
		size--;
		
		if(isEmpty()) base = null;
		
		return item;
	}

	public T top() {
		return top.item;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}
	

	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private class Node<Item> {
		Item item;
		Node<Item> next;
		
		Node(Item item) {
			this.item = item;
			this.next = null;
		}
	}
	
	private class ListStackIterator implements Iterator<T> {
		private Node<T> current;

		ListStackIterator(Node<T> top) {
			current = top;
		}
		
		public boolean hasNext() {
			return current != null;
		}

		public T next() {
			if(!hasNext()) throw new NoSuchElementException();
			
			T item = current.item;
			current = current.next;
			return item;
		}
		
	}
	

}
