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

    private <T> T doROOperation(Supplier<T> supplier) {
        long stamp = lock.tryOptimisticRead();
        T value = supplier.get();
        if (lock.validate(stamp)) {
            return value;
        }
        stamp = lock.readLock();
        try {
            return supplier.get();
        } finally {
            lock.unlockRead(stamp);
        }
    }

    public V get(Object key) {
        return doROOperation(() -> map.get(key));
    }

    public boolean containsKey(Object key) {
        return doROOperation(() -> map.containsKey(key));
    }


    @Override
    public int size() {
        return doROOperation(map::size);
    }

    @Override
    public boolean isEmpty() {
        return doROOperation(map::isEmpty);
    }

    @Override
    public boolean containsValue(Object value) {
        return doROOperation(() -> map.containsValue(value));
    }

    @Override
    public Set<K> keySet() {
        return doROOperation(map::keySet);
    }

    @Override
    public Collection<V> values() {
        return doROOperation(map::values);
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return doROOperation(map::entrySet);
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

}
