package net.datafaker.internal.helper;

import net.datafaker.Faker;
import net.datafaker.providers.base.BaseFaker;
import net.datafaker.providers.base.IdNumber;
import net.datafaker.service.FakeValuesService;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.TimeUnit.MINUTES;
import static org.assertj.core.api.Assertions.assertThat;

class CopyOnWriteMapTest {
    private static final Logger log = Logger.getLogger(CopyOnWriteMapTest.class.getName());
    private static final Class<?>[] classes = {Faker.class, BaseFaker.class, Boolean.class, Base64.class, IdNumber.class, FakerIDNTest.class, FakeValuesService.class};
    private static final String[] methods = {"a", "b", "c", "d", "e", "f", "g", "h"};

    @Test
    void concurrentPutAndGet() throws InterruptedException {
        int count = 10_000;
        Map<Class<?>, Map<String, Map<String[], Integer>>> MAP_OF_METHOD_AND_COERCED_ARGS = new CopyOnWriteMap<>(IdentityHashMap::new);

        SoftAssertions softly = new SoftAssertions();
        Random random = new Random();
        ExecutorService pool = newFixedThreadPool(20);
        CountDownLatch countDown = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            Class<?> klass = classes[random.nextInt(classes.length)];
            String methodName = methods[random.nextInt(methods.length)];

            pool.submit(() -> {
                final Map<String, Map<String[], Integer>> stringMapMap =
                    MAP_OF_METHOD_AND_COERCED_ARGS.computeIfAbsent(klass, t -> new CopyOnWriteMap<>(WeakHashMap::new));

                try {
                    Thread.sleep(random.nextInt(10));
                    stringMapMap.putIfAbsent(methodName, new CopyOnWriteMap<>(WeakHashMap::new));
                    if (random.nextInt(10) < 4) System.gc();

                    // here `stringMapMap.get(methodName)` sometimes returns NULL
                    stringMapMap.get(methodName).putIfAbsent(new String[0], 42);
                } catch (Throwable e) {
                    log.log(Level.SEVERE, e, () -> "Fail to put and get (%s, %s)".formatted(klass, methodName));
                    softly.fail(e);
                }
                countDown.countDown();
            });
        }
        pool.shutdown();
        assertThat(countDown.await(1, MINUTES)).isTrue();
        softly.assertAll();
    }
}
