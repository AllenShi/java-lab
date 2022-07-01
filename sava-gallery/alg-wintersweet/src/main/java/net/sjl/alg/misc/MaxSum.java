package net.sjl.alg.misc;

public class MaxSum {

	public static long searchN2(int[] input) {
		long max = Long.MIN_VALUE;

		for (int i = 0; i < input.length; i++) {
			long sum = 0;
			for (int j = i; j < input.length; j++) {
				sum += input[j];
				max = Math.max(max, sum);
			}
		}

		return max;
	}

	public static long searchN3(int[] input) {
		long max = Long.MIN_VALUE;

		for (int i = 0; i < input.length; i++) {
			for (int j = i; j < input.length; j++) {
				long sum = 0;
				for (int k = i; k <= j; k++) {
					sum += input[k];
				}
				max = Math.max(max, sum);
			}
		}

		return max;
	}

}
