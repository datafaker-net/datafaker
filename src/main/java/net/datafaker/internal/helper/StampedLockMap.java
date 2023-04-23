package net.datafaker.internal.helper;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.StampedLock;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class StampedLockMap<K, V> implements Map<K, V> {
    private final StampedLock lock = new StampedLock();
    private final Map<K, V> map;

    public StampedLockMap(Map<K, V> map) {
        this.map = map;
    }

    @Override
    public V get(Object key) {
        // Yes, a bit code duplication
        // the reasoning could be found at https://github.com/datafaker-net/datafaker/pull/770#discussion_r1174527507
        final long stamp = lock.tryOptimisticRead();
        final V result = map.get(key);
        if (lock.validate(stamp)) {
            return result;
        }
        final long stamp2 = lock.readLock();
        try {
            return map.get(key);
        } finally {
            lock.unlockRead(stamp2);
        }
    }

    @Override
    public boolean containsKey(Object key) {
        // Yes, a bit code duplication
        // the reasoning could be found at https://github.com/datafaker-net/datafaker/pull/770#discussion_r1174527507
        final long stamp = lock.tryOptimisticRead();
        final boolean result = map.containsKey(key);
        if (lock.validate(stamp)) {
            return result;
        }
        final long stamp2 = lock.readLock();
        try {
            return map.containsKey(key);
        } finally {
            lock.unlockRead(stamp2);
        }
    }

    @Override
    public int size() {
        // Yes, a bit code duplication
        // the reasoning could be found at https://github.com/datafaker-net/datafaker/pull/770#discussion_r1174527507
        final long stamp = lock.tryOptimisticRead();
        final int result = map.size();
        if (lock.validate(stamp)) {
            return result;
        }
        final long stamp2 = lock.readLock();
        try {
            return map.size();
        } finally {
            lock.unlockRead(stamp2);
        }
    }

    @Override
    public boolean isEmpty() {
        // Yes, a bit code duplication
        // the reasoning could be found at https://github.com/datafaker-net/datafaker/pull/770#discussion_r1174527507
        final long stamp = lock.tryOptimisticRead();
        final boolean result = map.isEmpty();
        if (lock.validate(stamp)) {
            return result;
        }
        final long stamp2 = lock.readLock();
        try {
            return map.isEmpty();
        } finally {
            lock.unlockRead(stamp2);
        }
    }

    @Override
    public boolean containsValue(Object value) {
        // Yes, a bit code duplication
        // the reasoning could be found at https://github.com/datafaker-net/datafaker/pull/770#discussion_r1174527507
        final long stamp = lock.tryOptimisticRead();
        final boolean result = map.containsValue(value);
        if (lock.validate(stamp)) {
            return result;
        }
        final long stamp2 = lock.readLock();
        try {
            return map.containsValue(value);
        } finally {
            lock.unlockRead(stamp2);
        }
    }

    @Override
    public Set<K> keySet() {
        // Yes, a bit code duplication
        // the reasoning could be found at https://github.com/datafaker-net/datafaker/pull/770#discussion_r1174527507
        final long stamp = lock.tryOptimisticRead();
        final Set<K> result = map.keySet();
        if (lock.validate(stamp)) {
            return result;
        }
        final long stamp2 = lock.readLock();
        try {
            return map.keySet();
        } finally {
            lock.unlockRead(stamp2);
        }
    }

    @Override
    public Collection<V> values() {
        // Yes, a bit code duplication
        // the reasoning could be found at https://github.com/datafaker-net/datafaker/pull/770#discussion_r1174527507
        final long stamp = lock.tryOptimisticRead();
        final Collection<V> result = map.values();
        if (lock.validate(stamp)) {
            return result;
        }
        final long stamp2 = lock.readLock();
        try {
            return map.values();
        } finally {
            lock.unlockRead(stamp2);
        }
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        // Yes, a bit code duplication
        // the reasoning could be found at https://github.com/datafaker-net/datafaker/pull/770#discussion_r1174527507
        final long stamp = lock.tryOptimisticRead();
        final Set<Entry<K, V>> result = map.entrySet();
        if (lock.validate(stamp)) {
            return result;
        }
        final long stamp2 = lock.readLock();
        try {
            return map.entrySet();
        } finally {
            lock.unlockRead(stamp2);
        }
    }

    @Override
    public V putIfAbsent(K key, V value) {
        // Yes, a bit code duplication
        // the reasoning could be found at https://github.com/datafaker-net/datafaker/pull/770#discussion_r1174527507
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
        try {
            stamp = lock.tryConvertToWriteLock(stamp);
            if (stamp == 0) {
                stamp = lock.writeLock();
            }
            return map.putIfAbsent(key, value);
        } finally {
            lock.unlock(stamp);
        }
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        // Yes, a bit code duplication
        // the reasoning could be found at https://github.com/datafaker-net/datafaker/pull/770#discussion_r1174527507
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
        try {
            stamp = lock.tryConvertToWriteLock(stamp);
            if (stamp == 0) {
                stamp = lock.writeLock();
            }
            return map.computeIfAbsent(key, mappingFunction);
        } finally {
            lock.unlock(stamp);
        }
    }

    public <K2, V2> void updateNestedValue(K key, Supplier<V> valueSupplier, K2 key2, V2 value) {
        long stamp = lock.writeLock();
        try {
            map.putIfAbsent(key, valueSupplier.get());
            // It is assumed that nested could be only StampedLockMap
            // otherwise there is no guarantee for thread safe
            ((StampedLockMap<K2, V2>)map.get(key)).putWithoutLock(key2, value);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    /**
     * This one is private and has no any synchronized inside.
     * For that reason it is assumed that it is invoked only in already synchronized/locked sections
     */
    private V putWithoutLock(K k, V v) {
        return map.put(k, v);
    }

    @Override
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
        final long stamp = lock.writeLock();
        try {
            return map.put(k, v);
        } finally {
            lock.unlockWrite(stamp);
        }
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
