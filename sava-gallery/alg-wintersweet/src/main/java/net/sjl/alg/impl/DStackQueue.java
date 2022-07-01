package net.sjl.alg.impl;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.util.Iterator;

import net.sjl.alg.api.Queue;
import net.sjl.alg.api.Stack;

public class DStackQueue<V> implements Queue<V> {
	
	private Stack<V> input;
	private Stack<V> output;
	private int size;
	
	public DStackQueue() {
		input = new ListStack<V>();
		output = new ListStack<V>();
		size = 0;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public int size() {
		return size;
	}
	

	public void enqueue(V item) {
		input.push(item);
		size++;
	}
	
	public V dequeue() {
		if(isEmpty()) return null;
		
		if(!output.isEmpty()) {
			size--;
			return output.pop();
		}
		
		while(!input.isEmpty()) {
			output.push(input.pop());
		}
		
		size--;
		return output.pop();
	}
	
	public Iterator<V> iterator() {
		throw new UnsupportedOperationException("not implemented");
	}

}
