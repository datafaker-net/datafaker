package net.datafaker;

/**
 * @since 0.9.0
 */
public class Barcode extends AbstractProvider {

    public Barcode(Faker faker) {
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
        long first_part = 0;
        switch (length) {
            case 8:
            case 12:
            case 13:
            case 14:
                first_part = this.faker.number().randomNumber(length - 1, true);
                break;
        }
        int odd = 0;
        int even = 0;
        long number = first_part;
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
        int var = 0;

        if (length == 13) {
            var = odd + even + (even << 1);
        } else if (length == 8 || length == 14 || length == 12) {
            var = odd + even + (odd << 1);
        }

        int rounded = roundToHighestMultiplyOfTen((var));
        int checkDigit = rounded - var;
        int product = 10;
        while (product <= checkDigit) {
            product *= 10;
        }
        return first_part * product + checkDigit;
    }

    public String type() {
        return resolve("barcode.types");
    }
}
