package net.sjl.alg.api;

public interface SymbolTable <Key extends Comparable<Key>, Value> {
	
	public Value get(Key key);
	public void put(Key key, Value value);
	public void remove(Key key);
	public boolean isEmpty();
	public int getSize();
	public boolean contains(Key key);
	public Key floor(Key key);
	public Key ceiling(Key key);
	public Key max();
	public Key min();
	public void deleteMax();
	public void deleteMin();
	public Iterable<Key> keys();
	public Iterable<Key> keys(Key lo, Key hi);
	public int rank(Key key);
	public Key select(int rank);
}
