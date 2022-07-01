package net.sjl.fragrans.cache;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedisExample {

    private static  Config config = new Config();

    static {
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
    }

    public void trySet(String key) {
        RedissonClient client = Redisson.create(config);
        RBucket<Boolean> bucket = client.getBucket(key);
        boolean result = bucket.trySet(true);
        System.out.println("The key is " + key + " and result is " + result);
    }
}
