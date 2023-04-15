package net.datafaker.internal.helper;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.StampedLock;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class StampedLockMap<K, V> implements Map<K, V> {
    private final StampedLock lock = new StampedLock();
    private final Map<K, V> map;

    public StampedLockMap(Supplier<Map<K, V>> mapSupplier) {
        this.map = mapSupplier.get();
    }

    public V get(Object key) {
        long stamp = lock.tryOptimisticRead();
        V value = map.get(key);
        if (!lock.validate(stamp)) {
            stamp = lock.readLock();
            try {
                value = map.get(key);
            } finally {
                lock.unlockRead(stamp);
            }
        }
        return value;
    }

    public boolean containsKey(Object key) {
        long stamp = lock.tryOptimisticRead();
        boolean value = map.containsKey(key);
        if (!lock.validate(stamp)) {
            stamp = lock.readLock();
            try {
                return map.containsKey(key);
            } finally {
                lock.unlockRead(stamp);
            }
        }
        return value;
    }

    public V putIfAbsent(K key, V value) {
        long stamp = lock.tryOptimisticRead();
        V curValue = map.get(key);
        if (!lock.validate(stamp)) {
            stamp = lock.readLock();
            try {
                curValue = map.get(key);
            } finally {
                lock.unlockRead(stamp);
            }
        }
        if (curValue != null) {
            return curValue;
        }
        stamp = lock.writeLock();
        try {
            return map.putIfAbsent(key, value);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        long stamp = lock.tryOptimisticRead();
        V value = map.get(key);
        if (!lock.validate(stamp)) {
            stamp = lock.readLock();
            try {
                if ((value = map.get(key)) != null) {
                    return value;
                }
            } finally {
                lock.unlockRead(stamp);
            }
        } else if (value != null) {
            return value;
        }
        stamp = lock.writeLock();
        try {
            return map.computeIfAbsent(key, mappingFunction);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    public void updateValue(K key, V value, Consumer<V> action) {
        long stamp = lock.writeLock();
        try {
            action.accept(map.computeIfAbsent(key, v -> value));
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        long stamp = lock.writeLock();
        try {
            return map.merge(key, value, remappingFunction);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsValue(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }
}
