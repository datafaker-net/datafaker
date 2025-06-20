package net.datafaker.internal.helper;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

class LazyEvaluatedTest {
    private final AtomicInteger executions = new AtomicInteger(0);
    private final Supplier<Integer> slowLoader = () -> {
        sleep();
        return executions.incrementAndGet();
    };

    private final LazyEvaluated<Integer> lazyEvaluated = new LazyEvaluated<>(slowLoader);

    @Test
    void notInitializedUntilCalled() {
        assertThat(executions.get()).isEqualTo(0);
    }

    @Test
    void initializedOnlyOnce() throws InterruptedException {
        int threadsCount = 5;
        ExecutorService threadPool = newFixedThreadPool(threadsCount);

        for (int i = 0; i < threadsCount; i++) {
            threadPool.submit(() -> {
                try {
                    assertThat(lazyEvaluated.get()).isEqualTo(1);
                } catch (Throwable e) {
                    System.out.printf("Loading failed...%s%n", e);
                }
            });
        }

        threadPool.shutdown();
        assertThat(threadPool.awaitTermination(10, SECONDS)).isTrue();

        assertThat(executions.get())
            .as("The slowLoader should be executed only once, even if the value was asked in multiple parallel threads.")
            .isEqualTo(1);
    }

    private static void sleep() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
