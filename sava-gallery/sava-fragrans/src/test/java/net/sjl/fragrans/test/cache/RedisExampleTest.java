package net.sjl.fragrans.test.cache;

import net.sjl.fragrans.cache.RedisExample;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

public class RedisExampleTest {

    @Test
    public void testTrySet() {
        RedisExample example = new RedisExample();
        example.trySet("object");
    }

    @Test
    public void testConcurrentTrySet1() throws InterruptedException {
        int numberOfThreads = 10;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        String key = "newObject1";
        for (int i = 0; i < numberOfThreads; i++) {
            service.execute(() -> {
                RedisExample example = new RedisExample();
                example.trySet(key);
                latch.countDown();
            });
        }
        latch.await();
    }

    @Test
    public void testConcurrentTrySet2() throws InterruptedException {
        int numberOfThreads = 10;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        String key = "newObject2";
        RedisExample example = new RedisExample();
        for (int i = 0; i < numberOfThreads; i++) {
            service.execute(() -> {
                example.trySet(key);
                latch.countDown();
            });
        }
        latch.await();
    }

}
