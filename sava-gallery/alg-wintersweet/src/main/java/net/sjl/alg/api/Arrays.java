package net.sjl.alg.api;

import net.sjl.alg.impl.ListStack;

public class Arrays {

	private Arrays() {
	}

	public static <T extends Comparable<? super T>> void selectionSort(T[] items) {
		assert (items != null);
		for (int i = 0; i < items.length; i++) {
			int min = i;
			for (int j = i + 1; j < items.length; j++) {
				if (lessThan(items[j], items[min])) {
					min = j;
				}
			}
			exchange(items, i, min);
		}
	}

	public static <T extends Comparable<? super T>> void insertionSort(T[] items) {
		assert (items != null);

		for (int i = 1; i < items.length; i++) {
			for (int j = i; j > 0 && lessThan(items[j], items[j-1]); j--) {
				exchange(items, j, j-1);
			}
		}
	}

	public static <T extends Comparable<? super T>> void shellSort(T[] items) {
		assert (items != null);

		int h = 0;
		int length = items.length;
		while (h < length / 3)
			h = h * 3 + 1;

		while (h >= 1) {
			for (int i = h; i < length; i++) {
				for (int j = i; j >= h && lessThan(items[j], items[j-h]); j -= h) {
					exchange(items, j, j-h);
				}
			}
			h /= 3;
		}
	}
	
	public static <T extends Comparable<? super T>> void mergeSort(T[] items) {
		assert(items != null);
		
		T[] aux = (T[])new Comparable[items.length];
		recursiveMergeSort(items, aux, 0, items.length - 1);
	}
	
	public static <T extends Comparable<? super T>> void iterativeMergeSort(T[] items) {
		assert(items != null);
		
		int N = items.length;
		T[] aux = (T[]) new Comparable[N];
		for(int sz = 1; sz < N; sz <<= 1) {
			for(int lo = 0; lo < N - sz; lo += sz + sz) {
				mergeOrderedSubList(items, aux, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, N - 1));
			}
		}
	}
	
	public static <T extends Comparable<? super T>> void quickSort(T[] items) {
		assert(items != null);
		
		recursiveQuickSort(items, 0, items.length - 1);
	}
	
	public static <T extends Comparable<? super T>> void iterativeQuickSort(T[] items) {
		assert(items != null);
		
		Stack<Pair<Integer, Integer>> stack = new ListStack<Pair<Integer, Integer>>();
		stack.push(new Pair<Integer, Integer>(0, items.length - 1));
		while(!stack.isEmpty()) {
			Pair<Integer, Integer> range = stack.pop();
			
			if(range.first >= range.second) continue;
			
			int pivotIndex = partition(items, range.first, range.second);
			stack.push(new Pair<Integer, Integer>(pivotIndex + 1, range.second));
			stack.push(new Pair<Integer, Integer>(range.first, pivotIndex - 1));
		}
	}
	
	
	private static <T extends Comparable<? super T>> void recursiveMergeSort(T[] items, T[] aux, int lo, int hi) {
		if(hi <= lo) return;
		
		int mid = lo + (hi - lo)/2;
		recursiveMergeSort(items, aux, lo, mid);
		recursiveMergeSort(items, aux, mid+1, hi);
		mergeOrderedSubList(items, aux, lo, mid, hi);
	}
	
	private static <T extends Comparable<? super T>> void mergeOrderedSubList(T[] items, T[] aux, int lo, int mid, int hi) {
		int i = lo;
		int j = mid + 1;
		for(int k = lo; k <= hi; k++) {
			aux[k] = items[k];
		}
		
		for(int k = lo; k <= hi; k++) {
			if(i > mid) {
				items[k] = aux[j++];
			}
			else if(j > hi) {
				items[k] = aux[i++];
			}
			else if(lessThan(aux[i], aux[j])) {
				items[k] = aux[i++];
			}
			else {
				items[k] = aux[j++];
			}
		}
	}
	
	private static <T extends Comparable<? super T>> void recursiveQuickSort(T[] items, int lo, int hi) {
		assert(items != null);
		if(hi <= lo) return;
		
		int pivot = partition(items, lo, hi);
		
		recursiveQuickSort(items, lo, pivot - 1);
		recursiveQuickSort(items, pivot + 1, hi);
	}
	
	private static <T extends Comparable<? super T>> int partition(T[] items, int lo, int hi) {
		T v = items[lo];
		
		int i = lo, j = hi + 1;
		
		while(true) {
			
			while(lessThan(items[++i], v)) if(i == hi) break;
			
			while(lessThan(v, items[--j])) if(j == lo) break;
	
			if(i >= j) break;

			exchange(items, i, j);
		}
		
		exchange(items, lo, j);
		
		return j;
	}

	private static <T extends Comparable<? super T>> boolean lessThan(final T one, final T two) {
		if (one.compareTo(two) < 0)
			return true;

		return false;
	}

	private static <T extends Comparable<? super T>> void exchange(T[] o, final int index1, final int index2) {
		assert (index1 < o.length && index1 > -1);
		assert (index2 < o.length && index2 > -1);
		T temp = o[index1];
		o[index1] = o[index2];
		o[index2] = temp;
	}

}
