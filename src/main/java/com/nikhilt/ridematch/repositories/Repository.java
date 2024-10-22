package com.nikhilt.ridematch.repositories;

import java.util.HashMap;
import java.util.Map;

public abstract class Repository<K, V> {
    Map<K, V> values;

    Repository() {
        values = new HashMap<>();
    }

    public void addValue(K key, V value) {
        values.put(key, value);
    }

    public V getValue(K key) {
        return values.get(key);
    }

    public boolean contains(K key){
        return values.containsKey(key);
    }

    public boolean removeValue(K key) {
        return values.remove(key) != null;
    }
}
