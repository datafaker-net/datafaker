package net.datafaker.internal.helper;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import static java.util.Collections.synchronizedMap;

public class COWMap<K, V> implements Map<K, V> {
    private final Map<K, V> map;

    public COWMap(Supplier<Map<K, V>> mapSupplier) {
        this.map = synchronizedMap(mapSupplier.get());
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    public <K2, V2> void updateNestedValue(K key, Supplier<V> valueSupplier, K2 key2, V2 value) {
        // It is assumed that nested could be only Map
        Map<K2, V2> nestedMap = (Map<K2, V2>) map.computeIfAbsent(key, k -> {
            Map<K2, V2> v = synchronizedMap((Map<K2, V2>) valueSupplier.get());
            return (V) v;
        });
        nestedMap.put(key2, value);
    }
}
