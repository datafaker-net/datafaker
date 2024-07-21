package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;

import java.util.concurrent.CountDownLatch;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

class Issue759Test {
    private static class WorkerThread extends Thread {
        private final Faker _faker;
        private final int _maxIterations;
        private final CountDownLatch _countDownLatch;

        private WorkerThread(Faker faker, int maxIterations, CountDownLatch countDownLatch) {
            _faker = faker;
            _maxIterations = maxIterations;
            _countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            for (int i = 0; i < _maxIterations; i++) {
                fakeSomeData(_faker);
                _countDownLatch.countDown();
            }
        }
    }

    public static void fakeSomeData(Faker faker) {
        String state = faker.address().stateAbbr();
        String zipCode = faker.address().zipCodeByState(state);
        try {
            String county = faker.address().countyByZipCode(zipCode);
            assertThat(county).isNotEqualTo(zipCode);
        } catch (RuntimeException expected) {
            assertThat(expected).hasMessageStartingWith("County is not configured for postcode " + zipCode);
        }
    }

    @RepeatedTest(10)
    void issue759Test() throws InterruptedException {
        int numThreads = 5;
        int iterationsPerThread = 20000;
        CountDownLatch countDownLatch = new CountDownLatch(numThreads * iterationsPerThread);

        Faker faker = new Faker();

        WorkerThread[] threads = new WorkerThread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new WorkerThread(faker, iterationsPerThread, countDownLatch);
        }

        for (int i = 0; i < numThreads; i++) {
            threads[i].start();
        }

        assertThat(countDownLatch.await(12, SECONDS))
            .overridingErrorMessage("Test did not complete within 12 second")
            .isTrue();
    }
}
