package com.c1;

import com.c1.impl.LRUCache;
import com.c1.impl.RandomEvictCache;

public class CacheBuilder<K,V> {
    private int capacity = 1000;
    private Cache.CacheEvictionStrategy cacheType = Cache.CacheEvictionStrategy.LRU;


    public CacheBuilder withCapacity(int capacity) {
        if (capacity == 0) throw new IllegalArgumentException("capacity must be greater than 0");
        this.capacity = capacity;
        return this;
    }

    public CacheBuilder withEvictionStrategy(Cache.CacheEvictionStrategy strat) {
        cacheType = strat;
        return this;
    }

    public Cache<K,V> build() {

        Cache<K,V> returnCache = null;

        switch (cacheType) {
            case LRU:
                returnCache = new LRUCache<K,V>(capacity);
                break;
            case RANDOM:
                returnCache = new RandomEvictCache<K,V>(capacity);
                break;
        }

        return returnCache;
    }
}
