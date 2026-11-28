package net.datafaker.internal.helper;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

class CopyOnWriteMapTest {
    @Test
    void concurrentUpdatesKeepAllEntriesAndInitializeOnce() throws Exception {
        int workers = 8;
        int entriesPerWorker = 50;
        var entries = new CopyOnWriteMap<Integer, Integer>(HashMap::new);
        var total = new CopyOnWriteMap<String, Integer>(HashMap::new);
        var initializations = new AtomicInteger();
        var start = new CountDownLatch(1);
        var executor = Executors.newFixedThreadPool(workers);
        var futures = new ArrayList<Future<?>>(workers);

        try {
            for (int worker = 0; worker < workers; worker++) {
                int firstKey = worker * entriesPerWorker;
                futures.add(executor.submit(() -> {
                    start.await();
                    total.computeIfAbsent("count", key -> {
                        initializations.incrementAndGet();
                        return 0;
                    });
                    for (int entry = 0; entry < entriesPerWorker; entry++) {
                        entries.put(firstKey + entry, entry);
                        total.merge("count", 1, Integer::sum);
                    }
                    return null;
                }));
            }
            start.countDown();
            for (Future<?> future : futures) {
                future.get(10, TimeUnit.SECONDS);
            }
        } finally {
            executor.shutdownNow();
        }

        assertThat(initializations).hasValue(1);
        assertThat(entries).hasSize(workers * entriesPerWorker);
        assertThat(total).containsEntry("count", workers * entriesPerWorker);
    }
}
