package net.sjl.alg;

public class KthElementofSortedArray {

  public static int firstRun(int[] first, int[] second, int k) {
    assert(first != null && second != null && k > 0 && (k < (first.length + second.length)));

    int result = 0;
    int f = 0, s = 0, fl = first.length, sl = second.length;
    for(int i = 0; i < k; i++) {
      if(f >= fl) {
        result = second[s++];
      } else if (s >= sl) {
        result = first[f++];
      } else if (first[f] <= second[s]) {
        result = first[f++];
      } else {
        result = second[s++];
      }
    }    

    return result;
  }

  
  public static int secondRun(int[] first, int[] second, int k) {
    assert(first != null && second != null && k > 0 && (k < (first.length + second.length)));
    return secondRun(first, 0, second, 0, k);
  }

  private static int secondRun(int[] first, int fstOffset, int[] second, int sndOffset, int k) {
    if(first.length > second.length) return secondRun(second, sndOffset, first, fstOffset, k);

    if(k == 1) return Math.min(first[fstOffset], second[sndOffset]);

    int pf = Math.min(first.length - fstOffset, k/2), ps = k - pf; 
    if(first[fstOffset + pf - 1] < second[sndOffset + ps - 1]) {
      return secondRun(first, fstOffset + pf, second, sndOffset, k/2);
    } else if(first[fstOffset + pf - 1] > second[sndOffset + ps - 1]) {
      return secondRun(first, fstOffset, second, sndOffset + ps, k/2);
    } else {
      return first[fstOffset + pf - 1];
    }
  }

  public static void main(String[] args) {
    int[] f = {1, 3, 4, 8, 10};
    int[] s = {2, 9, 11, 13, 14, 15};
    int k = 3;
    int result = firstRun(f, s, k);
    System.out.println("first run for the " + k + " is " + result);
    k = (f.length + s.length)/2 + 1;
    result = firstRun(f, s, k);
    System.out.println("first run for the median (" + k + ") is " + result);
    k = 3;
    result = secondRun(f, s, k);
    System.out.println("second run for the " + k + " is " + result);
    k = (f.length + s.length)/2 + 1;
    result = secondRun(f, s, k);
    System.out.println("second run for the median (" + k + ") is " + result);
  }
}
