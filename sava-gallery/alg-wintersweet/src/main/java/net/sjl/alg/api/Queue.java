package net.sjl.alg.api;

public interface Queue<T> extends Iterable<T> {

	public void enqueue(T item);
	public T dequeue();
	public boolean isEmpty();
	public int size();
}
