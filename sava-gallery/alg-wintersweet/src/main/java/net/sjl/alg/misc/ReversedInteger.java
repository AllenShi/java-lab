package net.sjl.alg.misc;

public class ReversedInteger {
	
	public static int reverse(int x) {
		
		long reversed = 0;
		while(x != 0) {
			reversed = reversed * 10 + x % 10;
			x = x / 10;
		}
		
		if(reversed > Integer.MAX_VALUE || reversed < Integer.MIN_VALUE) {
			reversed = 0;
		}
		
		return (int)reversed;
		
	}

}
