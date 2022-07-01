package net.sjl.alg;

import org.junit.*;
import static org.junit.Assert.*;

public class KthElementTestCase {

  @Test
  public void testKthElement() {
    int[] f = {1, 3, 4, 8, 10};
    int[] s = {2, 9, 11, 13, 14, 15};
    int k = 3;
    int result = KthElementofSortedArray.firstRun(f, s, k);
    System.out.println("first run for the " + k + " is " + result);
    k = (f.length + s.length)/2 + 1;
    result = KthElementofSortedArray.firstRun(f, s, k);
    System.out.println("first run for the median (" + k + ") is " + result);
    k = 3;
    result = KthElementofSortedArray.secondRun(f, s, k);
    System.out.println("second run for the " + k + " is " + result);
    k = (f.length + s.length)/2 + 1;
    result = KthElementofSortedArray.secondRun(f, s, k);
    System.out.println("second run for the median (" + k + ") is " + result);
  }

}
