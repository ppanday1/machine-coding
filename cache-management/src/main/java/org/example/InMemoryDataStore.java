package org.example;

import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryDataStore<K, V> implements DataStore<K, V> {
    private ConcurrentHashMap<K, V> hmp;

    public InMemoryDataStore() {
        hmp = new ConcurrentHashMap<>();
    }

    @Override
    public V get(K key) {
        return hmp.get(key);
    }

    @Override
    public void put(K key, V value) {
        hmp.put(key, value);
    }

    @Override
    public void remove(K key) {
        hmp.remove(key);
    }

    @Override
    public Integer getStoreSize() {
        return hmp.size();
    }

    @Override
    public boolean keyExists(K key) {
        return hmp.containsKey(key);
    }
}
