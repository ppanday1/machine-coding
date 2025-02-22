package org.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
@Slf4j
public class Cache<K, V> {
    private DataStore<K, V> dataStore;
    private EvictionPolicy<K> evictionPolicy;
    private final Integer boundedSize;
    private final Lock readLock;
    private final Lock writeLock;

    public Cache(DataStore<K, V> dataStore, EvictionPolicy<K> evictionPolicy,@Value("3") Integer boundedSize) {
        this.dataStore = dataStore;
        this.evictionPolicy = evictionPolicy;
        this.boundedSize = boundedSize;
        ReentrantReadWriteLock lockObject = new ReentrantReadWriteLock();
        readLock = lockObject.readLock();
        writeLock = lockObject.writeLock();
    }

    public void setDataStore(DataStore<K, V> dataStore) {
        this.dataStore = dataStore;
    }

    public void setEvictionPolicy(EvictionPolicy<K> evictionPolicy) {
        this.evictionPolicy = evictionPolicy;
    }

    public V get(K key) {
        readLock.lock();
        try {
            V value = dataStore.get(key);
            this.evictionPolicy.operateOnGet(key);
            return value;
        } finally {
            readLock.unlock();
        }
    }

    public void put(K key, V value) {
        writeLock.lock();
        try {
            if (this.dataStore.keyExists(key)) {
                this.evictionPolicy.operateOnUpdate(key);
                this.dataStore.put(key, value);
                return;
            }
            if (Objects.equals(this.dataStore.getStoreSize(), boundedSize)) {
                K evictKey = this.evictionPolicy.evictKey();
                this.dataStore.remove(evictKey);
                System.out.println("[evict] Evicting key: " + evictKey);
            }
            this.dataStore.put(key, value);
            this.evictionPolicy.operateOnPut(key);
        } finally {
            writeLock.unlock();
        }
    }
}
