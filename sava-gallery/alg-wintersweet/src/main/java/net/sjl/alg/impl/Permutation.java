package net.sjl.alg.impl;

import java.util.ArrayList;
import java.util.List;

public class Permutation {
	
	public static <T> List<T[]> generate(T[] original) {
		if(original == null || original.length == 0) return null;
		List<T[]> result = new ArrayList<>();
		
		generateRecurrence(original, 0, original.length - 1, result);
		return result;
	}
	
	private static <T> void generateRecurrence(T[] original, int k, int m, List<T[]> result) {
		if(k == m) {
			result.add(original.clone());
		}
		
		if(k < m) {
			for(int i = k; i <= m; i++) {
				swap(original, i, k);
				generateRecurrence(original, k+1, m, result);
				swap(original, i, k);
			}
		}
	}
	
	private static <T> void swap(T[] original, int i, int j) {
		T temp = original[i];
		original[i] = original[j];
		original[j] = temp;
	}

}
