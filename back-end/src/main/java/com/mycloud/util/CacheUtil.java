package com.mycloud.util;

import java.time.LocalDateTime;
import java.util.concurrent.*;

/**
 * 缓存
 */
public class CacheUtil {

    // 线程清理过时?

    private static boolean hasStartCleanCacheThread = false;

    static class CacheKey implements Delayed {
        LocalDateTime expireTime;

        String cacheKey;

        @Override
        public long getDelay(TimeUnit unit) {
            //TODO 还得检查
            return unit.convert(this.expireTime.getNano(), unit);
        }

        @Override
        public int compareTo(Delayed o) {
            return 0;
        }
    }

    // 并发?
    private static final ConcurrentHashMap<String, Object> cacheMap = new ConcurrentHashMap<>();
    private static final DelayQueue<CacheKey> delayQueue = new DelayQueue<CacheKey>();

    private static void startCleanCacheThread() {
        Executor exec = Executors.newSingleThreadExecutor();
        exec.execute(() -> {
            //noinspection InfiniteLoopStatement
            while (true) {
                try {
                    CacheKey take = delayQueue.take();
                    cacheMap.remove(take.cacheKey);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        hasStartCleanCacheThread = true;
    }

    // 并发?
    // 先 synchronized 约束下
    public synchronized static void put(String key, Object obj, long expireInTime) {
        //
        cacheMap.put(key, obj);
        // delayQueue
        CacheKey cache = new CacheKey();
        cache.cacheKey = key;
        cache.expireTime = null;
        delayQueue.put(cache);

        if (!hasStartCleanCacheThread) {
            startCleanCacheThread();
        }
    }


}
