package net.datafaker;

import net.datafaker.service.FakeValuesService;
import net.datafaker.service.FakerContext;
import net.datafaker.service.RandomService;
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

class SharedFakeValuesServiceTest {

    @Test
    void concurrentFakersProduceNoErrors() throws Exception {
        int threads = 16;
        int iterations = 10_000;
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        CyclicBarrier barrier = new CyclicBarrier(threads);
        List<Future<Void>> futures = new ArrayList<>();
        for (int i = 0; i < threads; i++) {
            final long seed = i;
            futures.add(pool.submit(() -> {
                barrier.await();
                Faker faker = new Faker(Locale.ENGLISH, new Random(seed));
                for (int j = 0; j < iterations; j++) {
                    assertThat(faker.name().fullName()).isNotNull();
                    assertThat(faker.address().city()).isNotNull();
                    assertThat(faker.internet().emailAddress()).isNotNull();
                }
                return null;
            }));
        }
        pool.shutdown();
        if (!pool.awaitTermination(120, TimeUnit.SECONDS)) {
            pool.shutdownNow();
            throw new AssertionError("Thread pool did not terminate within timeout");
        }
        for (Future<Void> f : futures) {
            f.get();
        }
    }

    @Test
    void multipleLocalesConcurrentlyProduceNoErrors() throws Exception {
        Locale[] locales = {Locale.ENGLISH, Locale.FRENCH, Locale.GERMAN, Locale.forLanguageTag("es")};
        int threads = locales.length * 4;
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        CyclicBarrier barrier = new CyclicBarrier(threads);
        List<Future<Void>> futures = new ArrayList<>();
        for (int i = 0; i < threads; i++) {
            final Locale locale = locales[i % locales.length];
            futures.add(pool.submit(() -> {
                barrier.await();
                Faker faker = new Faker(locale, new Random());
                for (int j = 0; j < 1_000; j++) {
                    assertThat(faker.name().fullName()).isNotNull();
                    assertThat(faker.address().city()).isNotNull();
                }
                return null;
            }));
        }
        pool.shutdown();
        if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
            pool.shutdownNow();
            throw new AssertionError("Thread pool did not terminate within timeout");
        }
        for (Future<Void> f : futures) {
            f.get();
        }
    }

    @Test
    void sharedServiceAcrossRootsKeepsPerRootDeterminism() {
        // One FakeValuesService driven by two different Faker roots with different contexts (seeds).
        // The L2 cache is keyed by expression only, so it must verify root/context identity on a hit;
        // otherwise root B would reuse a resolver bound to root A's providers and read A's random stream.
        FakeValuesService shared = new FakeValuesService();
        Faker a = new Faker(shared, new FakerContext(Locale.ENGLISH, new RandomService(new Random(42))));
        Faker b = new Faker(shared, new FakerContext(Locale.ENGLISH, new RandomService(new Random(99))));

        // References: independent Fakers (own service) with the same seeds and the same call sequence.
        Faker refA = new Faker(Locale.ENGLISH, new Random(42));
        Faker refB = new Faker(Locale.ENGLISH, new Random(99));

        String expr = "#{Name.first_name} #{Name.last_name}";
        for (int i = 0; i < 200; i++) {
            // Interleave A and B on the shared service; each root must stay on its own random stream.
            assertThat(a.expression(expr)).isEqualTo(refA.expression(expr));
            assertThat(b.expression(expr)).isEqualTo(refB.expression(expr));
        }
    }

    @Test
    void deterministicOutputUnaffectedByCaching() {
        long seed = 12345L;
        Faker first = new Faker(Locale.ENGLISH, new Random(seed));
        String firstName = first.name().firstName();
        String city = first.address().city();
        String email = first.internet().emailAddress();

        // A second Faker with the same seed must produce the same output regardless of cache state
        Faker second = new Faker(Locale.ENGLISH, new Random(seed));
        assertThat(second.name().firstName()).isEqualTo(firstName);
        assertThat(second.address().city()).isEqualTo(city);
        assertThat(second.internet().emailAddress()).isEqualTo(email);
    }
}
