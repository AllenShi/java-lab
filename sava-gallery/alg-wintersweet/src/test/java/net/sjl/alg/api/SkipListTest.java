package net.sjl.alg.api;

import org.junit.Test;

import net.sjl.alg.impl.SkipList;

public class SkipListTest {
	
	@Test
	public void testAdd() {
		
		SkipList<Integer, String> skipList1 = new SkipList<Integer, String>();
		skipList1.put(1, "one");
		skipList1.put(2, "two");
		skipList1.put(3, "three");
		skipList1.put(4, "four");
		
		System.out.println("1 => " + skipList1.get(1));
		
	}

}
