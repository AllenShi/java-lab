package net.sjl.alg; 

import org.junit.*;
import static org.junit.Assert.*;

public class ZeroOneKnapsackTestCase {

  @Test
  public void testZeroOneKnapsack1() {
    int[] w = {1, 3, 4, 8, 10, 20};
    int[] v = {2, 9, 11, 13, 14, 15};
    int c = 10;

    ZeroOneKnapsack knapsack = new ZeroOneKnapsack(w, v, c);
    int max = knapsack.resolve();
    knapsack.printSolution(); 
  }

  @Test
  public void testZeroOneKnapsack2() {
    int[] w = {1, 3, 4, 8, 10, 20, 10000000};
    int[] v = {2, 9, 11, 13, 14, 15, 10000};
    int c = 10000000;

    ZeroOneKnapsack knapsack = new ZeroOneKnapsack(w, v, c);
    int max = knapsack.resolve();
    knapsack.printSolution(); 
  }

  @Test
  public void testZeroOneKnapsack3() {
    int[] w = {1, 3, 4, 8, 10, 20};
    int[] v = {2, 9, 11, 13, 14, 15};
    int c = 20;

    ZeroOneKnapsack knapsack = new ZeroOneKnapsack(w, v, c);
    int max = knapsack.resolve();
    knapsack.printSolution(); 
  }

}
