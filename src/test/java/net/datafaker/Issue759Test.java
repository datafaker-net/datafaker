package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;

import java.util.Arrays;

class Issue759Test {
    static class WorkerThread extends Thread {
        final Faker _faker;
        final int _workerNum;
        final int _maxIterations;
        int _iterations;

        WorkerThread(Faker faker, int workerNum, int maxIterations) {
            _faker = faker;
            _workerNum = workerNum;
            _maxIterations = maxIterations;
        }

        public int getIterations() {
            return _iterations;
        }

        @Override
        public void run() {
            for (_iterations = 0; _iterations < _maxIterations; _iterations++) {
                fakeSomeData(_faker);
            }
        }
    }

    public static void fakeSomeData(Faker faker) {
        String state = faker.address().stateAbbr();
        String zipCode = faker.address().zipCodeByState(state);
        try {
            String county = faker.address().countyByZipCode(zipCode);
        } catch (Exception e)
    {
    }
    }

    static int[] getIterations(WorkerThread[] threads) {
        int[] iters = new int[threads.length];
        for (int i = 0; i < threads.length; i++) {
            iters[i] = threads[i].getIterations();
        }
        return iters;
    }

    static boolean allElementsEqual(int[] arr, int val) {
        for (int n : arr) {
            if (n != val) {
                return false;
            }
        }
        return true;
    }

    @RepeatedTest(10)
    void issue759Test() throws InterruptedException {
        final int numThreads = 5;
        final int iterationsPerThread = 60000;

        final Faker faker = new Faker();

        WorkerThread[] threads = new WorkerThread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new WorkerThread(faker, i, iterationsPerThread);
        }

        for (int i = 0; i < numThreads; i++) {
            threads[i].start();
        }

        int[] lastIters = getIterations(threads);
        final int delayMs = 100;
        final int maxIntervalWithoutChangesMs = 1000;
        int intervalWithoutChanges = 0;
        while (true) {
            Thread.sleep(delayMs);
            int[] iters = getIterations(threads);
            if (Arrays.equals(lastIters, iters)) {
                // Either all threads are done, or something is probably stuck
                if (allElementsEqual(iters, iterationsPerThread)) {
                    break;
                } else if (Arrays.equals(lastIters, iters)) {
                    intervalWithoutChanges += delayMs;
                    if (intervalWithoutChanges > maxIntervalWithoutChangesMs) {
                        throw new AssertionError("Not all of %s are equal to %s".formatted(Arrays.toString(iters), iterationsPerThread));
                    }
                } else {
                    intervalWithoutChanges = 0;
                }
            }
            lastIters = iters;
        }
    }
}
