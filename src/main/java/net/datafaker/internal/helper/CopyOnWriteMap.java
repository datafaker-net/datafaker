package net.datafaker.internal.helper;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Collections.unmodifiableCollection;
import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElse;

/**
 * This is a Copy On Write map. The main idea behind this is that
 * there is lots of static info per provider to make the providers operate.
 * At the same time there is no need to load all the info at the start since
 * we don't know which providers will be used and loading for all takes time.
 * For that reason it is loaded on request and stored in these Copy On Write maps.
 * Since it is loaded only once per provider and after that is only read then
 * it should be ok and moreover it will allow to have non-blocking reads.
 *
 * The supplier must create empty maps whose read operations do not modify them.
 */
public class CopyOnWriteMap<K, V> implements Map<K, V> {
    private volatile Map<K, V> map;
    private final Supplier<Map<K, V>> mapSupplier;

    public CopyOnWriteMap(Supplier<Map<K, V>> mapSupplier) {
        this.mapSupplier = mapSupplier;
        this.map = mapSupplier.get();
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
    public V putIfAbsent(K key, V value) {
        throw new UnsupportedOperationException("Avoid pattern 'get+put'. Use computeIfAbsent instead.");
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        requireNonNull(mappingFunction);
        V value = map.get(key);
        if (value != null) {
            return value;
        }
        synchronized (this) {
            return Map.super.computeIfAbsent(key, mappingFunction);
        }
    }

    @Override
    public synchronized V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return Map.super.merge(key, value, remappingFunction);
    }

    @Override
    public synchronized V put(K key, V value) {
        requireNonNull(value, () -> "value is null for key " + key);
        Map<K, V> newMap = mapSupplier.get();
        newMap.putAll(map);
        final V result = newMap.put(key, value);
        map = newMap;
        return result;
    }

    @Override
    public synchronized V remove(Object key) {
        Map<K, V> newMap = mapSupplier.get();
        newMap.putAll(map);
        final V result = newMap.remove(key);
        map = newMap;
        return result;
    }

    @Override
    public synchronized void putAll(Map<? extends K, ? extends V> m) {
        Map<K, V> newMap = mapSupplier.get();
        newMap.putAll(map);
        newMap.putAll(m);
        map = newMap;
    }

    @Override
    public synchronized void clear() {
        map = mapSupplier.get();
    }

    @Override
    public Set<K> keySet() {
        return unmodifiableSet(map.keySet());
    }

    @Override
    public Collection<V> values() {
        return unmodifiableCollection(map.values());
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return unmodifiableMap(map).entrySet();
    }

    @Override
    public String toString() {
        return map.toString();
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        V v = get(key);
        return requireNonNullElse(v, defaultValue);
    }
}
