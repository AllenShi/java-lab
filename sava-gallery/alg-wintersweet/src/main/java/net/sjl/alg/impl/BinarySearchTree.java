package net.sjl.alg.impl;

import net.sjl.alg.api.SymbolTable;

public class BinarySearchTree<Key extends Comparable<Key>, Value> implements SymbolTable<Key, Value> {
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
			this.size = size;
		}
	}

	public Value get(Key key) {
		
		return get(root, key);
	}
	
	private Value get(Node parent, Key key) {
		if(parent == null) return null;
		int cmp = parent.key.compareTo(key);
		if(cmp < 0) {
			return get(parent.rightChild, key);
		} else if(cmp > 0) {
			return get(parent.leftChild, key);
		} else {
			return parent.value;
		}
	}

	public void put(Key key, Value value) {
		// TODO Auto-generated method stub
		root = put(root, key, value);
	}
	
	private Node put(Node parent, Key key, Value value) {
		if(parent == null) return new Node(key, value, 1);
		
		int cmp = parent.key.compareTo(key);
		if(cmp < 0) {
			parent.rightChild = put(parent.rightChild, key, value);
		} else if(cmp > 0) {
			parent.leftChild = put(parent.leftChild, key, value);
		} else {
			parent.value = value;
		}
		
		parent.size = 1 + getSize(parent.leftChild) + getSize(parent.rightChild);
		return parent;
	}

	public void remove(Key key) {
		// TODO Auto-generated method stub
		
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private int getSize(Node x) {
		if(x == null) return 0;
		return x.size;
	}

	public boolean contains(Key key) {
		// TODO Auto-generated method stub
		return false;
	}

	public Key floor(Key key) {
		// TODO Auto-generated method stub
		return null;
	}

	public Key ceiling(Key key) {
		// TODO Auto-generated method stub
		return null;
	}

	public Key max() {
		// TODO Auto-generated method stub
		return null;
	}

	public Key min() {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteMax() {
		Node x = root;
		while(x.rightChild != null) {
			x = x.rightChild;
		}
		
	}

	public void deleteMin() {
		// TODO Auto-generated method stub
		
	}

	public Iterable<Key> keys() {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterable<Key> keys(Key lo, Key hi) {
		// TODO Auto-generated method stub
		return null;
	}

	public int rank(Key key) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Key select(int rank) {
		// TODO Auto-generated method stub
		return null;
	}

}
