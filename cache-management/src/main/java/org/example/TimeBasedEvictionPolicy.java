package org.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TimeBasedEvictionPolicy<K> implements EvictionPolicy<K> {
    private final ConcurrentHashMap<K, Long> timeMap;
    private final long expiryDuration;

    public TimeBasedEvictionPolicy(@Value("30000")long expiryDuration) {
        this.timeMap = new ConcurrentHashMap<>();
        this.expiryDuration = expiryDuration;
    }

    @Override
    public void operateOnGet(K key) {
        if (timeMap.containsKey(key) && !isExpired(key)) {
            timeMap.put(key, System.currentTimeMillis()); // Update last accessed time
        }
    }

    @Override
    public K evictKey() {
        long currentTime = System.currentTimeMillis();
        Iterator<ConcurrentHashMap.Entry<K, Long>> iterator = timeMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<K, Long> entry = iterator.next();
            if (currentTime - entry.getValue() > expiryDuration) {
                iterator.remove();
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public void operateOnUpdate(K key) {
        timeMap.put(key, System.currentTimeMillis());
    }

    @Override
    public void operateOnPut(K key) {
        timeMap.put(key, System.currentTimeMillis());
    }

    @Override
    public void operateOnRemoval(K key) {
        timeMap.remove(key);
    }

    @Override
    public void clear() {
        timeMap.clear();
    }

    private boolean isExpired(K key) {
        return System.currentTimeMillis() - timeMap.get(key) > expiryDuration;
    }
}
