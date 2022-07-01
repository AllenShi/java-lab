package net.sjl.alg.api;

import org.junit.Test;
import org.junit.Assert;

import net.sjl.alg.api.Arrays;

public class ArraysTest {

	@Test
	public void testSelectionSort() {
		Integer[] items = {0, 2, 5, 10, 4, 3, 7, 6, 1, 9, 8};
		Arrays.selectionSort(items);
		
		Integer[] expected = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		Assert.assertArrayEquals(expected, items);
	}
	
	@Test
	public void testInsersionSort() {
		Integer[] items = {0, 2, 5, 10, 4, 3, 7, 6, 1, 9, 8};
		Arrays.insertionSort(items);
		
		Integer[] expected = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		Assert.assertArrayEquals(expected, items);
	}
	
	@Test
	public void testShellSort() {
		Integer[] items = {0, 2, 5, 10, 4, 3, 7, 6, 1, 9, 8};
		Arrays.shellSort(items);
		
		Integer[] expected = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		Assert.assertArrayEquals(expected, items);
	}
	
	@Test
	public void testMergeSort() {
		Integer[] items = {0, 2, 5, 10, 4, 3, 7, 6, 1, 9, 8};
		Arrays.mergeSort(items);
		
		Integer[] expected = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		Assert.assertArrayEquals(expected, items);
	}
	
	@Test
	public void testIterativeMergeSort() {
		Integer[] items = {0, 2, 5, 10, 4, 3, 7, 6, 1, 9, 8};
		Arrays.iterativeMergeSort(items);
		
		Integer[] expected = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		Assert.assertArrayEquals(expected, items);
	}

	@Test
	public void testQuickSort() {
		Integer[] items = {0, 2, 5, 10, 4, 3, 7, 6, 1, 9, 8};
		Arrays.quickSort(items);
		
		Integer[] expected = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		Assert.assertArrayEquals(expected, items);
	}
	
	@Test
	public void testIterativeQuickSort() {
		Integer[] items = {0, 2, 5, 10, 4, 3, 7, 6, 1, 9, 8};
		Arrays.iterativeQuickSort(items);
		
		Integer[] expected = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		Assert.assertArrayEquals(expected, items);
	}
}
