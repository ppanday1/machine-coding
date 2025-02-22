package org.example;

public interface DataStore<K,V> {
    V get(K key);
    void put(K key, V value);
    void remove(K key);
    Integer getStoreSize();
    boolean keyExists(K key);
}
