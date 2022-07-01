package net.sjl.fragrans.test.stream;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Stream;

import org.junit.*;

import static org.junit.Assert.*;

public class StreamTestCase {

    private final static int DATA_SIZE = 5000 * 1000 * 1000;

    @Test
    public void testParallelFilterMap() {

        Arrays.asList("a1", "a2", "b1", "c2", "c1")
                .parallelStream()
                .filter(s -> {
                    System.out.format("filter: %s [%s]\n",
                            s, Thread.currentThread().getName());
                    return true;
                })
                .map(s -> {
                    System.out.format("map: %s [%s]\n",
                            s, Thread.currentThread().getName());
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.format("forEach: %s [%s]\n",
                        s, Thread.currentThread().getName()));
    }

    @Test
    public void testReduceSumInSequence() {
        int totalSum = Stream.of(1, 2, 3, 4, 5, 6)
                .reduce(0,
                        (sum, p) -> {
                            System.out.format("accumulator: sum=%s, p=%s [%s]\n",
                                    sum, p, Thread.currentThread().getName());
                            return sum += p;
                        },
                        (sum1, sum2) -> {
                            System.out.format("combiner: sum1=%s, sum2=%s [%s]\n",
                                    sum1, sum2, Thread.currentThread().getName());
                            return sum1 + sum2;
                        });
        assertEquals(21, totalSum);
    }

    @Test
    public void testReduceSumInParallel() {
        int totalSum = Stream.of(1, 2, 3, 4, 5, 6).parallel()
                .reduce(0,
                        (sum, p) -> {
                            System.out.format("accumulator: sum=%s, p=%s [%s]\n",
                                    sum, p, Thread.currentThread().getName());
                            return sum += p;
                        },
                        (sum1, sum2) -> {
                            System.out.format("combiner: sum1=%s, sum2=%s [%s]\n",
                                    sum1, sum2, Thread.currentThread().getName());
                            return sum1 + sum2;
                        });
        assertEquals(21, totalSum);
    }

    @Test
    public void testBigMRInSeries() {
        int[] dataInput = new int[DATA_SIZE];
        for (int i = 0; i < dataInput.length; i++) {
            dataInput[i] = i;
        }

        long start = System.currentTimeMillis();
        int totalSum = Arrays.stream(dataInput).filter(e -> {
            if (e % 2 == 0) {
                System.out.format("filter: %s [%s]\n", e, Thread.currentThread().getName());
                return true;
            }
            return false;
        }).map(e -> {
            System.out.format("map: %s [%s]\n", e, Thread.currentThread().getName());
            return e / 2;
        }).reduce(0, (sum, op) -> {
            System.out.format("accumulator: sum=%s, op=%s [%s]\n", sum, op, Thread.currentThread().getName());
            return sum += op;
        });

        System.out.format("The total sum in series is %s and time cost is %s milliseconds\n", totalSum, (System.currentTimeMillis() - start));


    }

    @Test
    public void testBigMRInParallel() {

        int[] dataInput = new int[DATA_SIZE];
        for (int i = 0; i < dataInput.length; i++) {
            dataInput[i] = i;
        }

        long start = System.currentTimeMillis();
        long totalSum = Arrays.stream(dataInput).parallel().filter(e -> {
            if (e % 2 == 0) {
                // System.out.format("filter: %s [%s]\n", e, Thread.currentThread().getName());
                return true;
            }
            return false;
        }).map(e -> {
            // System.out.format("map: %s [%s]\n", e, Thread.currentThread().getName());
            return e / 2;
        }).reduce(0, (sum, op) -> {
            // System.out.format("accumulator: sum=%s, op=%s [%s]\n", sum, op, Thread.currentThread().getName());
            return sum += op;
        });

        System.out.format("The total sum in parallel is %s and time cost is %s milliseconds\n", totalSum, (System.currentTimeMillis() - start));
    }

    @Test
    public void testBigMRInParallel2() {

        int[] dataInput = new int[DATA_SIZE];
        for (int i = 0; i < dataInput.length; i++) {
            dataInput[i] = i;
        }

        long start = System.currentTimeMillis();
        Callable<Long> task = () -> {
            long totalSum = Arrays.stream(dataInput).parallel().filter(e -> {
                if (e % 2 == 0) {
                    // System.out.format("filter: %s [%s]\n", e, Thread.currentThread().getName());
                    return true;
                }
                return false;
            }).map(e -> {
                // System.out.format("map: %s [%s]\n", e, Thread.currentThread().getName());
                return e / 2;
            }).reduce(0, (sum, op) -> {
                // System.out.format("accumulator: sum=%s, op=%s [%s]\n", sum, op, Thread.currentThread().getName());
                return sum += op;
            });
            return totalSum;
        };

        ForkJoinPool pool = new ForkJoinPool(10);
        try {
            long result = pool.submit(task).get();
            System.out.format("The total sum in parallel 2 is %s and time cost is %s milliseconds\n", result, (System.currentTimeMillis() - start));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
