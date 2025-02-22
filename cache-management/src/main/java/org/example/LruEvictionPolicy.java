package org.example;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;

@Component
@Primary
public class LruEvictionPolicy<K> implements EvictionPolicy<K> {
    private final LinkedHashSet<K> keys;

    public LruEvictionPolicy() {
        keys = new LinkedHashSet<>();
    }

    @Override
    public void operateOnGet(K key) {
        if (keys.contains(key)) {
            keys.remove(key);
            keys.add(key);
        }
    }

    @Override
    public K evictKey() {
        if (keys.isEmpty()) {
            return null;
        }
        K lruKey = keys.iterator().next(); // Least recently used key
        keys.remove(lruKey);
        return lruKey;
    }

    @Override
    public void operateOnUpdate(K key) {
        if (keys.contains(key)) {
            keys.remove(key);
            keys.add(key);
        }
    }

    @Override
    public void operateOnPut(K key) {
        keys.add(key);
    }

    @Override
    public void operateOnRemoval(K key) {
        keys.remove(key);
    }

    @Override
    public void clear() {
        keys.clear();
    }
}
