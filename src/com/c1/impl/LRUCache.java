package com.c1.impl;

import com.c1.Cache;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<K,V> implements Cache<K,V> {

    private Map<K,Node<K,V>> cache = new HashMap<K,Node<K,V>>();
    // head of the list is the least recently used -- start with a dummy node
    private Node<K,V> listHead = new Node<K, V>(null, null, null, null);;
    // end of the list is the most recently used
    private Node<K,V> listEnd = listHead;
    private int capacity;

    public LRUCache(int inCap) {
        this.capacity = inCap;
    }

    @Override
    public synchronized V add(K key, V value) {
        if (key == null || value == null) throw new NullPointerException();

        V retVal = null;
        int curSize = cache.size();

        // See if the cache contains the value
        Node<K,V> node = cache.get(key);

        if (node != null) { // we found it replace the value
            retVal = node.value;
            node.value = value;
        } else {
            node = new Node<K, V>(listEnd, null, key, value);
        }

        listEnd.next = node;
        listEnd = node;

        // Delete the left-most entry and update the LRU pointer
        if (curSize == capacity){
            cache.remove(listHead.key);
            listHead = listHead.next;
            listHead.previous = null;
        } else if (curSize < capacity){
            if (curSize == 0){
                listHead = node;
            }
        }
        cache.put(key, node);

        return retVal;
    }

    @Override
    public synchronized V get(K key) {
        if (key == null) throw new NullPointerException();

        Node<K,V> node = cache.get(key);

        // Node not present
        if (node == null) {
            return null;
        } else if (node == listEnd) { // nothing to fix up
            return node.value;
        }

        // Get the next and previous nodes
        Node<K, V> nextNode = node.next;
        Node<K, V> previousNode = node.previous;

        // If at the left-most, we update LRU
        if (node == listHead) {
            nextNode.previous = null;
            listHead = nextNode;
        } else if (node != listEnd) { // We are in the middle -- need to update the nodes before and after our node
            previousNode.next = nextNode;
            nextNode.previous = previousNode;
        }

        // Finally move our node to the end
        node.previous = listEnd;
        listEnd.next = node;
        listEnd = node;
        listEnd.next = null;

        return node.value;
    }

    @Override
    public boolean exists(K key) {
        if (key == null) return false;

        return this.get(key) != null;
    }

    class Node<K, V> {
        Node<K, V> previous;
        Node<K, V> next;
        K key;
        V value;

        public Node(Node<K, V> previous, Node<K, V> next, K key, V value){
            this.previous = previous;
            this.next = next;
            this.key = key;
            this.value = value;
        }
    }
}
