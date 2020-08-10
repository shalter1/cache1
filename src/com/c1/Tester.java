package com.c1;

public class Tester {
    public static void main(String[] args) {
        CacheBuilder<String, String> b1 = new CacheBuilder<String, String>();
        Cache lru = b1.withCapacity(3).withEvictionStrategy(Cache.CacheEvictionStrategy.LRU).build();

        lru.add("A", "B");

        if (!lru.exists("A")) {
            System.out.println("Fail -- A should exist");
        } else {
            System.out.println("Success -- A exists");
        }

        if (lru.exists("B")) {
            System.out.println("Fail -- B should not exist");
        }

        if (lru.get("A") == null) {
            System.out.println("Fail -- A should exist");
        }

        if (!lru.get("A").equals("B")) {
            System.out.println("Fail -- B should be value");
        }

        lru.add("C", "D");
        lru.add("C", "F");

        if (!lru.get("C").equals("F")) {
            System.out.println("Fail -- F should be value");
        }

        lru.add("D", "E");
        lru.add("E", "F");

        if (lru.exists("A")) {
            System.out.println("Fail -- A should not exist");
        }

        CacheBuilder<String, String> b2 = new CacheBuilder<String, String>();
        Cache random = b2.withCapacity(3).withEvictionStrategy(Cache.CacheEvictionStrategy.RANDOM).build();

        random.add("A", "B");

        if (!random.exists("A")) {
            System.out.println("Fail -- A should exist");
        }

        if (random.exists("B")) {
            System.out.println("Fail -- B should not exist");
        }

        if (random.get("A") == null) {
            System.out.println("Fail -- A should exist");
        }

        if (!random.get("A").equals("B")) {
            System.out.println("Fail -- B should be value");
        }

        random.add("C", "D");
        random.add("C", "F");

        if (!random.get("C").equals("F")) {
            System.out.println("Fail -- F should be value");
        }

        random.add("D", "E");
        random.add("E", "F");
        random.add("V", "F");

        if (!random.exists("A")) {
            System.out.println("A evicted");
        }
        if (!random.exists("C")) {
            System.out.println("C evicted");
        }
        if (!random.exists("D")) {
            System.out.println("D evicted");
        }
        if (!random.exists("E")) {
            System.out.println("E evicted");
        }
        if (!random.exists("V")) {
            System.out.println(" Fail -- V evicted");
        }

    }
}
