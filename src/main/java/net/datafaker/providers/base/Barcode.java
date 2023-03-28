package net.datafaker.providers.base;

/**
 * @since 0.9.0
 */
public class Barcode extends AbstractProvider<BaseProviders> {

    public Barcode(BaseProviders faker) {
        super(faker);
    }

    public long ean13() {
        return ean(13);
    }

    public long ean8() {
        return ean(8);
    }

    public long gtin14() {
        return ean(14);
    }

    public long gtin12() {
        return ean(12);
    }

    public long gtin13() {
        return ean13();
    }

    public long gtin8() {
        return ean8();
    }

    private static int roundToHighestMultiplyOfTen(int number) {
        if (number % 10 == 0) {
            return number;
        } else {
            int ones = number % 10;
            int add = 10 - ones;
            return number + add;
        }
    }

    private long ean(int length) {
        long firstPart = switch (length) {
            case 8, 12, 13, 14 -> this.faker.number().randomNumber(length - 1, true);
            default -> 0;
        };
        int odd = 0;
        int even = 0;
        long number = firstPart;
        int i = 0;
        while (number > 0) {
            i++;
            if (i % 2 == 1) {
                odd += number % 10;
            } else {
                even += number % 10;
            }

            number /= 10;
        }
        if (i % 2 == 0) {
            int tmp = even;
            even = odd;
            odd = tmp;
        }
        int output = calculateOutput(length, odd, even);
        int rounded = roundToHighestMultiplyOfTen((output));
        int checkDigit = rounded - output;
        int product = 10;
        while (product <= checkDigit) {
            product *= 10;
        }
        return firstPart * product + checkDigit;
    }

    // Implementation Refactoring: Extract Method- to calculate the output and return it to ean()
    private int calculateOutput(int length, int odd, int even) {
        int output = odd + even;
        switch(length) {
            case 13: output += (even << 1);
                    break;
            case 8:
            case 14:
            case 12: output += (odd << 1);
                     break;
            default: output = 0;
                     break;
        }

        return output;
    }

    public String type() {
        return resolve("barcode.types");
    }
}
