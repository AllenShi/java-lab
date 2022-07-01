package net.sjl.alg.api;

public interface MaxPriorityQueue<Key extends Comparable<? super Key>> {
	
	public void insert(Key key);
	public Key delMax();
	public boolean isEmpty();
	public int size();
}
