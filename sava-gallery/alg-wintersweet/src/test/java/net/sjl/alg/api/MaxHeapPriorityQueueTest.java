package net.sjl.alg.api;

import org.junit.Assert;
import org.junit.Test;

import net.sjl.alg.api.MaxPriorityQueue;
import net.sjl.alg.impl.MaxHeapPriorityQueue;


public class MaxHeapPriorityQueueTest {

	@Test
	public void testMaxPQ() {
		Integer[] items = {0, 2, 5, 10, 4, 3, 7, 6, 1, 9, 8};
		
		MaxPriorityQueue<Integer> pq = new MaxHeapPriorityQueue<Integer>();
		for(Integer item : items) {
			pq.insert(item);
		}
		
		int size = pq.size();
		Integer[] results = new Integer[size];
		for(int i = 0; i < size; i++) {
			results[i] = pq.delMax();
		}
		
		Integer[] expected = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
		Assert.assertArrayEquals(expected, results);
	}

}
