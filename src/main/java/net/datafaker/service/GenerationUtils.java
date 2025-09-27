package net.datafaker.service;

import net.datafaker.annotations.InternalApi;

public class GenerationUtils {
    private static final char[] DIGITS = "0123456789".toCharArray();

    // Based on Integer.MAX_VALUE the max value is 1_000_000_000
    // assuming that every digit should have equal chance.
    private static final int INT_LIMIT_DIGITS = 9;
    private static final int[] TENS = initTens();

    private static int generateNumber(RandomService randomService, int amountOfDigits) {
        if (amountOfDigits > INT_LIMIT_DIGITS || amountOfDigits < 1) {
            // should never happen
            // in case it happened most probably there is a bug in a method invoking this
            throw new IllegalArgumentException("Invalid amount of digits: " + amountOfDigits);
        }
        return randomService.nextInt(TENS[amountOfDigits + 1]);
    }

    @InternalApi
    static int generateAndSetNumber(int position, char[] target, char symbol, RandomService randomService) {
        int symbolCounter = 0;
        int generated = 0;
        do {
            symbolCounter++;

            if (symbolCounter - generated == INT_LIMIT_DIGITS) {
                final int r = generateNumber(randomService, INT_LIMIT_DIGITS);
                insertNumber(r, INT_LIMIT_DIGITS, target, position + generated);
                generated += INT_LIMIT_DIGITS;
            }
        } while (position + symbolCounter < target.length && target[position + symbolCounter] == symbol);

        final int diff = symbolCounter - generated;
        if (diff > 0) {
            final int r = generateNumber(randomService, diff);
            insertNumber(r, diff, target, position + generated);
        }
        return symbolCounter;
    }

    private static int[] initTens() {
        int[] tens  = new int[INT_LIMIT_DIGITS + 2];
        tens[0] = 1;
        for (int i = 1; i < tens.length; i++) {
            tens[i] = tens[i-1] * 10;
        }
        return tens;
    }

    private static void insertNumber(int number, int amountOfDigits, char[] target, int offset) {
        for (int k = 0; k < amountOfDigits; k++) {
            target[offset + k] = DIGITS[(number / TENS[k] % 10)];
        }
    }
}
