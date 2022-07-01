package net.sjl.alg.api;

import org.junit.Test;

import net.sjl.alg.api.Queue;
import net.sjl.alg.impl.DStackQueue;

public class DStackQueueTest {
	
	@Test
	public void testDStackQueue() {
		Queue<Integer> q1 = new DStackQueue<Integer>();
		q1.enqueue(1);
		q1.enqueue(2);
		q1.enqueue(3);
		q1.enqueue(4);
		q1.enqueue(5);
		q1.enqueue(6);
		
		while(!q1.isEmpty()) {
			System.out.println("---->" + q1.dequeue());
		}
	}

}
