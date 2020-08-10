package com.c1.impl;

import com.c1.Cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandomEvictCache<K,V> implements Cache<K,V> {

    private Random random = new Random(System.currentTimeMillis());
    private int capacity;
    // A map for efficient lookup
    private Map<K, Entry<K,V>> cache = new HashMap<K, Entry<K,V>>();
    // An array holding the entries for efficient replacement
    private Entry<K,V>[] entries;

    public RandomEvictCache(int inCap) {
        this.capacity = inCap;
        this.entries = new Entry[this.capacity];
    }

    @Override
    public synchronized V add(K key, V value) {
        if (key == null || value == null) throw new NullPointerException();

        V prevValue = null;
        Entry<K,V> entry = this.cache.get(key);

        if (entry != null) { // already in cache
            prevValue = entry.value;
            entry.value = value;
        } else { // not in cache
            // If we aren't full, index will be the place to put the next entry in entries
            int index = cache.size();

            if (index < capacity) { // not yet full -- just add it to the end
                entries[index] = new Entry();

            } else { // replace a random entry
                index = random.nextInt(capacity);
                cache.remove(entries[index].key);
            }

            entries[index].key = key;
            entries[index].value = value;

            cache.put(key, entries[index]);
        }

        return prevValue;
    }

    @Override
    public synchronized V get(K key) {
        if (key == null) throw new NullPointerException();

        V retVal = null;
        Entry<K,V> entry = cache.get(key);

        if (entry != null) {
            retVal = entry.value;
        }

        return retVal;
    }

    @Override
    public boolean exists(K key) {
        if (key == null) return false;

        return this.get(key) != null;
    }

    class Entry<K,V> {
        V value;
        K key;
    }
}
