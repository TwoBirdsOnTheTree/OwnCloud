package com.mycloud.util;

import java.time.LocalDateTime;
import java.util.concurrent.*;

/**
 * 缓存
 */
public class CacheUtil {

    // 线程清理过时?

    private static boolean hasStartCleanCacheThread = false;

    private static class CacheKey implements Delayed {
        LocalDateTime expireTime;

        String cacheKey;

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(this.expireTime.getNano(), unit);
        }

        @Override
        public int compareTo(Delayed o) {
            return 0;
        }
    }

    private static final ConcurrentHashMap<String, Object> cacheMap = new ConcurrentHashMap<>();
    private static final DelayQueue<CacheKey> delayQueue = new DelayQueue<>();

    /**
     * 开启清除线程
     */
    private static void startCleanCacheThread() {
        Executor exec = Executors.newSingleThreadExecutor();
        exec.execute(() -> {
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
    //TODO 先 synchronized 约束下? 需要吗
    public synchronized static void put(String key, Object obj, long expireInTime) {
        //
        cacheMap.put(key, obj);
        // 清除线程
        CacheKey cache = new CacheKey();
        cache.cacheKey = key;
        cache.expireTime = null;
        delayQueue.put(cache);

        if (!hasStartCleanCacheThread) {
            startCleanCacheThread();
        }
    }

    /**
     * get
     * @param key
     * @param resultClass
     * @param <T>
     * @return
     */
    public static <T> T get(String key, Class<T> resultClass) {
        Object get = cacheMap.get(key);
        if (null == get) {
            return null;
        }
        if (resultClass.isAssignableFrom(get.getClass())) {
            return resultClass.cast(get);
        }
        return null;
    }
}
