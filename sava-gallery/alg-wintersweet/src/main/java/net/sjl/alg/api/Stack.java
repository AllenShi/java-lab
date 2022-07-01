package net.sjl.alg.api;

public interface Stack<T> extends Iterable<T> {

	public void push(T item);
	public T pop();
	public T top();
	public boolean isEmpty();
	public int size();
}
