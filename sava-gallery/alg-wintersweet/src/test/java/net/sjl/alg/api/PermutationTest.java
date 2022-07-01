package net.sjl.alg.api;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import net.sjl.alg.impl.Permutation;

public class PermutationTest {
	
	@Test
	public void testPermutation() {
		Integer[] input1 = new Integer[] {1, 2, 3};
		List<Integer[]> output = Permutation.generate(input1);
		
		for(Integer[] elements : output) {
			print(elements);
		}
	}
	
	private void print(Integer[] elements) {
		System.out.print("[" + elements[0]);
		for(int i = 1; i < elements.length; i++) {
			System.out.print("," + elements[i]);
		}
		System.out.println("]");
	}

}
