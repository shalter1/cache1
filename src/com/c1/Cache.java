package com.c1;

public interface Cache<K,V> {

    /**
     *
     * Adds an entry to the cache, if an object already exists with the same key, replace the value with the new value,
     * if the key does not already exist in the cache, then add the entry specified by key/value into the cache,
     * evicting an existing entry, if necessary
     *
     * @param key Key to use in the cache
     * @param value the value to cache
     * @return the previous value associated with key, or null if there was no mapping for key.
     * @throws NullPointerException if key or value is null
     */
    V add(K key, V value);

    /**
     *
     * Retrieves the value of the entry specified by key or null if no entry exists
     *
     * @param key Key to use in the cache
     * @return the value of the entry specified by key or null if no entry exists
     * @throws NullPointerException if key is null
     */
    V get(K key);

    /**
     *
     * Returns true if an entry with key exists in the cache, false otherwise
     *
     * @param key
     * @return true if an entry with key exists in the cache, false otherwise
     */
    boolean exists(K key);

    enum CacheEvictionStrategy {
        LRU,
        RANDOM
    }
}
