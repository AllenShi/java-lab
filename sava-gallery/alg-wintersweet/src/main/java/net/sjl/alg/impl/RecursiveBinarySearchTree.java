package net.sjl.alg.impl;

import java.util.NoSuchElementException;

import net.sjl.alg.api.Queue;
import net.sjl.alg.api.SymbolTable;

public class RecursiveBinarySearchTree<Key extends Comparable<Key>, Value> implements SymbolTable<Key, Value> {
	private Node root;
	
	private class Node {
		Key key;
		Value value;
		Node leftChild;
		Node rightChild;
		int size;
		
		Node(Key key, Value value, int size) {
			this.key = key;
			this.value = value;
			this.leftChild = null;
			this.rightChild = null;
			this.size = 1;
		}
	}

	public Value get(Key key) {
		return get(root, key);
	}
	
	private Value get(Node x, Key key) {
		if(x == null) return null;
	
		int cmp = key.compareTo(x.key);
		if(cmp > 0) {
			return get(x.rightChild, key);
		}
		else if(cmp < 0) {
			return get(x.leftChild, key);
		}
		else {
			return x.value;
		}
	}

	public void put(Key key, Value value) {
		put(root, key, value);
	}
	
	private Node put(Node x, Key key, Value value) {
		if(x == null) return new Node(key, value, 1);
		
		int cmp = key.compareTo(x.key);
		if(cmp < 0) x.leftChild = put(x.leftChild, key, value);
		else if(cmp > 0) x.rightChild = put(x.rightChild, key, value);
		else x.value = value;
		
		x.size = getSize(x.leftChild) + getSize(x.rightChild) + 1;
		return x;
	}

	public void remove(Key key) {
		root = remove(root, key);
	}
	
	private Node remove(Node x, Key key) {
		if(x == null) return null;
		
		int cmp = key.compareTo(x.key);
		if(cmp > 0) {
			x.rightChild = remove(x.rightChild, key);
		}
		else if(cmp < 0) {
			x.leftChild = remove(x.leftChild, key);
		}
		else {
			if(x.leftChild == null) return x.rightChild;
			else if(x.rightChild == null) return x.leftChild;
			else {
				Node min = min(x.rightChild);
				min.rightChild = deleteMin(x.rightChild);
				min.leftChild = x.leftChild;
				x = min;
			}
		}
		
		x.size = getSize(x.leftChild) + getSize(x.rightChild) + 1;
		return x;
	}

	public boolean isEmpty() {
		return getSize(root) == 0;
	}

	public int getSize() {
		return getSize(root);
	}
	
	private int getSize(Node x) {
		if(x == null) return 0;
		return x.size;
	}

	public boolean contains(Key key) {		
		return get(key) != null;
	}

	public Key floor(Key key) {
		Node x = floor(root, key);
		if(x == null) return null;
		return x.key;
	}
	
	private Node floor(Node x, Key key) {
		if(x == null) return null;
		
		int cmp = key.compareTo(x.key);
		if(cmp == 0) return x;
		if(cmp < 0) return floor(x.leftChild, key);
		Node t = floor(x.rightChild, key);
		if(t != null) return t;
		else return x;
	}

	public Key ceiling(Key key) {
		Node t = ceiling(root, key);
		if(t != null) return t.key;
		return null;
	}
	
	private Node ceiling(Node x, Key key) {
		if(x == null) return null;
		
		int cmp = key.compareTo(x.key);
		if(cmp == 0)  return x;
		if(cmp > 0) return ceiling(x.rightChild, key);
		Node t = ceiling(x.leftChild, key);
		if(t != null) return t;
		return x;
	}

	public Key max() {
		if(isEmpty()) throw new NoSuchElementException("can't call max on an empty BST.");
		
		return max(root).key;
	}
	
	private Node max(Node x) {
		if(x.rightChild == null) return x;
		return max(x.rightChild);
	}

	public Key min() {
		if(isEmpty()) throw new NoSuchElementException("can't call min on an empty BST.");
		
		return min(root).key;
	}
	
	private Node min(Node x) {
		if(x.leftChild == null) return x;
		return min(x.leftChild);
	}
	
	public void deleteMax() {
		root = deleteMax(root);
	}
	
	private Node deleteMax(Node x) {
		if(x.rightChild == null) {
			return x.leftChild;
		}
		
		x.rightChild = deleteMax(x.rightChild);
		x.size = getSize(x.leftChild) + getSize(x.rightChild) + 1;
		return x;
	}
	
	public void deleteMin() {
		root = deleteMin(root);
	}
	
	private Node deleteMin(Node x) {
		if(x.leftChild == null) return x.rightChild;
		x.leftChild = deleteMin(x.leftChild);
		x.size = getSize(x.leftChild) + getSize(x.rightChild) + 1;
		return x;
	}

	public Iterable<Key> keys() {
		return keys(min(), max());
	}
	
	public Iterable<Key> keys(Key lo, Key hi) {
		Queue<Key> keySet = new ListQueue<Key>();
		keys(root, keySet, lo, hi);
		
		return keySet;
	}
	
	private void keys(Node x, Queue<Key> keySet, Key lo, Key hi) {
		if(x == null) return;
		
		int cmplo = lo.compareTo(x.key);
		int cmphi = hi.compareTo(x.key);
		if(cmplo < 0) keys(x.leftChild, keySet, lo, hi);
		if(cmplo <= 0 && cmphi >=0) keySet.enqueue(x.key);
		if(cmphi > 0) keys(x.rightChild, keySet, lo, hi);
	}
	
	public int rank(Key key) {
		return rank(root, key);
	}
	
	private int rank(Node x, Key key) {
		if(x == null) return 0;
		
		int cmp = key.compareTo(x.key);
		if(cmp < 0) return rank(x.leftChild, key);
		else if (cmp == 0) return getSize(x.leftChild);
		else return getSize(x.leftChild) + 1 + rank(x.rightChild, key);
 	}
	
	public Key select(int rank) {
		return select(root, rank);
	}
	
	private Key select(Node x, int rank) {
		if(x == null) return null;
		
		int lsize = getSize(x.leftChild);
		
		if(rank < lsize ) return select(x.leftChild, rank);
		else if(rank == lsize) return x.key;
		else return select(x.rightChild, rank - lsize - 1);
	}

}
