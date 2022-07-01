package net.sjl.alg;

import java.lang.Math;

public class ZeroOneKnapsack {
  
  private int[] weights;
  private int[] values;
  private int capacity;
  private int itemsNum;
  private boolean[][] solution;
  private boolean[] taken;
  private int[][] maxValues;
  private int maxWeight;
  private int maxValue;

  public ZeroOneKnapsack(int[] weights, int[] values, int capacity) {
    assert(weights != null && weights.length > 0);
    assert(values != null && values.length > 0);
    assert(weights.length == values.length);
    assert(capacity >= 0);

    this.weights = weights;
    this.values = values;
    this.capacity = capacity;
    this.itemsNum = weights.length;
    this.solution = new boolean[itemsNum + 1][capacity + 1];
    this.taken = new boolean[itemsNum];
    
    for(int i = 0; i <= itemsNum; i++) {
      for(int w = 0; w <= capacity; w++) {
        solution[i][w] = false;
      }
    }
    maxValues = new int[itemsNum + 1][capacity + 1];
  }

  public int resolve() {
    for(int w = 0; w <= capacity; w++) {
      maxValues[0][w] = 0;
    }

    for(int i = 0; i <= itemsNum; i++) {
      maxValues[i][0] = 0;
    }

    for(int i = 1; i <= itemsNum; i++) {
      for(int w = 0; w <= capacity; w++) {
        if(weights[i - 1] > w) {
          maxValues[i][w] = maxValues[i - 1][w];
        } else {
          if(maxValues[i-1][w] > maxValues[i-1][w-weights[i - 1]] + values[i - 1]) {
            maxValues[i][w] = maxValues[i-1][w];
          } else {
            maxValues[i][w] = maxValues[i-1][w-weights[i - 1]] + values[i - 1];
            solution[i][w] = true;
          }
        } 
      }
    }

    maxWeight = 0;
    for(int i = itemsNum, w = capacity; i > 0; i--) {
      if(solution[i][w]) {
        taken[i - 1] = true;
        w -= weights[i - 1];
        maxWeight += weights[i - 1];
      } else {
        taken[i - 1] = false;
      }
    }

    maxValue = maxValues[itemsNum][capacity];
    return maxValue;

  } 

  public void printSolution() {
    System.out.println("The capacity is " + capacity + " and max weight is " + maxWeight + " and max value is " + maxValue);
    for(int i = 0; i < itemsNum; i++) {
      if(taken[i]) {
        System.out.println("    The item " + i + " (" + weights[i] + ", " + values[i] + ") is choosen in solution");
      }
    }
  }
}
