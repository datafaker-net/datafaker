package net.datafaker.internal.helper;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

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
 * In case for whatever reason there is a need to change this class,
 * please double-check jmh report before and after e.g.
 * {@code mvn clean package exec:exec -Dbenchmarks="DatafakerSimpleMethods" -Ddatafaker.version=2.2.3-SNAPSHOT}
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
    public V put(K key, V value) {
        requireNonNull(value, () -> "value is null for key " + key);
        Map<K, V> newMap = mapSupplier.get();
        newMap.putAll(map);
        final V result = newMap.put(key, value);
        map = newMap;
        return result;
    }

    @Override
    public V remove(Object key) {
        Map<K, V> newMap = mapSupplier.get();
        newMap.putAll(map);
        final V result = newMap.remove(key);
        map = newMap;
        return result;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        Map<K, V> newMap = mapSupplier.get();
        newMap.putAll(map);
        newMap.putAll(m);
        map = newMap;
    }

    @Override
    public void clear() {
        map = mapSupplier.get();
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
