package net.sjl.alg.impl;

import net.sjl.alg.api.SymbolTable;

public class IterativeBinarySearchTree<Key extends Comparable<Key>, Value> implements SymbolTable<Key, Value> {
	private Node root;
	
	private class Node {
		Key key;
		Value value;
		Node leftChild;
		Node rightChild;
		int descendant;
		
		Node(Key key, Value value, Node left, Node right) {
			this.key = key;
			this.value = value;
			this.leftChild = left;
			this.rightChild = right;
		}
	}
	

	public Value get(Key key) {
		Node currentNode = root;
		while(currentNode != null) {
			if(key.compareTo(currentNode.key) < 0) {
				currentNode = currentNode.leftChild;
			}
			else if(key.compareTo(currentNode.key) > 0) {
				currentNode = currentNode.rightChild;
			}
			else {
				return currentNode.value;
			}
		}
		
		return null;
	}

	public void put(Key key, Value value) {
		Node currentNode = root;
		while(currentNode != null) {
			if(key.compareTo(currentNode.key) < 0) {
				if(currentNode.leftChild != null) {
					currentNode = currentNode.leftChild;
				}
				else {
					currentNode.leftChild = new Node(key, value, null, null);
					return;
				}
			}
			else if(key.compareTo(currentNode.key) > 0) {
				if(currentNode.rightChild != null) {
					currentNode = currentNode.rightChild;
				}
				else {
					currentNode.rightChild = new Node(key, value, null, null);
					return;
				}
			}
			else {
				currentNode.value = value;
				return;
			}
		}
		
	}

	public void remove(Key key) {
	}

	public boolean isEmpty() {
		return false;
	}

	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		
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
