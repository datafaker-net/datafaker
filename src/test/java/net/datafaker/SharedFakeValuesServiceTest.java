package net.datafaker;

import net.datafaker.service.FakeValuesService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SharedFakeValuesServiceTest {

    @Test
    void getSharedReturnsSameInstanceUnderConcurrency() throws Exception {
        int threads = 8;
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        CyclicBarrier barrier = new CyclicBarrier(threads);
        List<Future<FakeValuesService>> futures = new ArrayList<>();
        for (int i = 0; i < threads; i++) {
            futures.add(pool.submit(() -> {
                barrier.await();
                return FakeValuesService.getShared(Locale.ENGLISH);
            }));
        }
        pool.shutdown();
        assertThat(pool.awaitTermination(10, TimeUnit.SECONDS)).isTrue();
        FakeValuesService expected = futures.get(0).get();
        for (Future<FakeValuesService> f : futures) {
            assertThat(f.get()).isSameAs(expected);
        }
    }

    @Test
    void concurrentFakersWithSharedServiceProduceNoErrors() throws Exception {
        FakeValuesService shared = FakeValuesService.getShared(Locale.ENGLISH);
        int threads = 16;
        int iterations = 10_000;
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        CyclicBarrier barrier = new CyclicBarrier(threads);
        List<Future<Void>> futures = new ArrayList<>();
        for (int i = 0; i < threads; i++) {
            final long seed = i;
            futures.add(pool.submit(() -> {
                barrier.await();
                Faker faker = Faker.withSharedService(shared, Locale.ENGLISH, new Random(seed));
                for (int j = 0; j < iterations; j++) {
                    assertThat(faker.name().fullName()).isNotNull();
                    assertThat(faker.address().city()).isNotNull();
                    assertThat(faker.internet().emailAddress()).isNotNull();
                }
                return null;
            }));
        }
        pool.shutdown();
        assertThat(pool.awaitTermination(120, TimeUnit.SECONDS)).isTrue();
        for (Future<Void> f : futures) {
            f.get();
        }
    }

    @Test
    void sharedInstanceRejectsAddPathAndAddUrl() throws Exception {
        FakeValuesService shared = FakeValuesService.getShared(Locale.GERMAN);
        assertThatThrownBy(() -> shared.addPath(Locale.GERMAN, java.nio.file.Path.of("nonexistent.yml")))
                .isInstanceOf(UnsupportedOperationException.class);
        java.net.URL url = new java.net.URI("file:///nonexistent.yml").toURL();
        assertThatThrownBy(() -> shared.addUrl(Locale.GERMAN, url))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void withSharedServiceOutputMatchesNormalFaker() {
        long seed = 12345L;
        Faker normal = new Faker(Locale.ENGLISH, new Random(seed));
        Faker shared = Faker.withSharedService(
                FakeValuesService.getShared(Locale.ENGLISH), Locale.ENGLISH, new Random(seed));
        assertThat(shared.name().firstName()).isEqualTo(normal.name().firstName());
        assertThat(shared.address().city()).isEqualTo(normal.address().city());
        assertThat(shared.internet().emailAddress()).isEqualTo(normal.internet().emailAddress());
    }
}
