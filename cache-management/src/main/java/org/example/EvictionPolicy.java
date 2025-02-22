package org.example;

public interface EvictionPolicy<K> {
    void operateOnGet(K key);
    K evictKey();
    void operateOnUpdate(K key);
    void operateOnPut(K key);
    void operateOnRemoval(K key);
    void clear();
}
