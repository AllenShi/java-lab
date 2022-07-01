package net.sjl.alg.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.sjl.alg.api.Queue;

public class ListQueue<T> implements Queue<T> {
	private Node<T> head;
	private Node<T> tail;
	private int size;
	
	public ListQueue() {
		head = null;
		tail = null;
		size = 0;
	}

	public void enqueue(T item) {
		Node<T> oldTail = tail;
		tail = new Node<T>(item);
		
		if(isEmpty()) 
			head = tail; 
		else 
			oldTail.next = tail;
		
		size++;
	}

	public T dequeue() {
		if(isEmpty()) throw new NoSuchElementException();
		
		T item = head.item;
		head = head.next;
		size--;
		if(isEmpty()) tail = null;
		
		return item;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public Iterator<T> iterator() {
		return new ListQueueIterator<T>(head);
	}

	public int size() {
		return size;
	}
	
	private class Node<Item> {
		Item item;
		Node<Item> next;
		
		Node(Item item) {
			this.item = item;
			this.next = null;
		}
	}
	
	private class ListQueueIterator<T> implements Iterator<T> {
		private Node<T> current;
		
		public ListQueueIterator(Node<T> first) {
			current = first;
		}

		public boolean hasNext() {
			return current != null;
		}

		public T next() {
			if(!hasNext()) throw new NoSuchElementException();
			
			Node<T> x = current;
			current = current.next;
			return x.item;
		}
		
	}

}
