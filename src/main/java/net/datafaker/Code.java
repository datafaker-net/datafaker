package net.datafaker;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * ISBN Rules : <a href="https://en.wikipedia.org/wiki/International_Standard_Book_Number">https://en.wikipedia.org/wiki/International_Standard_Book_Number</a>
 *
 * @since 0.8.0
 */
public class Code {

    private static final int[] GTIN_8_CHECK_DIGITS = {3, 1, 3, 1, 3, 1, 3};
    private static final int[] GTIN_13_CHECK_DIGITS = {1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3};
    private static final String[] REPORTING_BODY_IDENTIFIERS
        = {"01", "10", "30", "33", "35", "44", "45", "49", "50", "51", "52", "53", "54", "86", "91", "98", "99"};

    private static final Pattern HYPHEN = Pattern.compile("-");
    private final Faker faker;

    protected Code(Faker faker) {
        this.faker = faker;
    }

    /**
     * This can be overridden by specifying
     * <code>
     * code:
     * isbn_gs1: "some expression"
     * </code>
     * in the appropriate yml file.
     *
     * @return a GS1 code for an ISBN13, currently is only 978 and 979
     */
    public String isbnGs1() {
        return faker.regexify("978|979");
    }

    /**
     * This can be overridden by specifying
     * <code>
     * code:
     * isbn_group: "some expression"
     * </code>
     * in the appropriate yml file.
     *
     * @return an ISBN group number
     */
    public String isbnGroup() {
        return faker.regexify("[0-1]");
    }

    /**
     * This can be overridden by specifying
     * <code>
     * code:
     * isbn_registrant: "some expression"
     * </code>
     * in the appropriate yml file.
     *
     * @return an ISBN registrant 'element' with separator
     */
    public String isbnRegistrant() {
        int ct = faker.random().nextInt(6) + 1;
        switch (ct) {
            case 6:
                return faker.number().numberBetween(0, 1) + faker.number().digit() + "-" + faker.number().digits(6);
            case 5:
                return faker.number().numberBetween(200, 699) + "-" + faker.number().digits(5);
            case 4:
                return faker.number().numberBetween(7000, 8499) + "-" + faker.number().digits(4);
            case 3:
                return faker.number().numberBetween(85000, 89999) + "-" + faker.number().digits(3);
            case 2:
                return faker.number().numberBetween(900000, 949999) + "-" + faker.number().digits(2);
            case 1:
                return faker.number().numberBetween(9500000, 9999999) + "-" + faker.number().digits(1);
            default:
                throw new IllegalStateException("Invalid random " + ct);
        }
    }

    /**
     * @return a valid ISBN10 number with no separators (ex. 9604250590)
     */
    public String isbn10() {
        return isbn10(false);
    }

    /**
     * @param separator true if you want separators returned, false otherwise
     * @return a valid ISBN10 number with or without separators (ex. 9604250590, 960-425-059-0)
     */
    public String isbn10(boolean separator) {
        // The registration group identifier is a 1- to 5-digit number
        final StringBuilder isbn10 = new StringBuilder()
            .append(faker.expression("#{code.isbn_group}"))
            .append('-')
            .append(faker.expression("#{code.isbn_registrant}"))
            .append('-');

        final int checkDigit = isbn10CheckDigit(isbn10);
        isbn10.append(checkDigit != 10 ? checkDigit : "X");
        return separator ? isbn10.toString() : stripIsbnSeparator(isbn10);
    }

    /**
     * @return a valid ISBN13 number with no separators (ex. 9789604250590)
     */
    public String isbn13() {
        return isbn13(false);
    }

    /**
     * @param separator true if you want separators returned, false otherwise
     * @return a valid ISBN13 number with or without separators (ex. 9789604250590, 978-960-425-059-0)
     */
    public String isbn13(boolean separator) {
        // The registration group identifier is a 1- to 5-digit number
        final StringBuilder isbn13 = new StringBuilder()
            .append(faker.expression("#{code.isbn_gs1}"))
            .append('-')
            .append(faker.expression("#{code.isbn_group}"))
            .append('-')
            .append(faker.expression("#{code.isbn_registrant}"))
            .append('-');

        final int checkDigit = isbn13CheckDigit(isbn13);
        isbn13.append(checkDigit);
        return separator ? isbn13.toString() : stripIsbnSeparator(isbn13);
    }

    private int isbn10CheckDigit(CharSequence t) {
        String value = stripIsbnSeparator(t);
        int sum = 0;
        for (int i = 0; i < value.length(); i++) {
            sum += ((i + 1) * Integer.parseInt(value.substring(i, i + 1)));
        }
        return sum % 11;
    }

    private int isbn13CheckDigit(CharSequence t) {
        String value = stripIsbnSeparator(t);
        int sum = 0;
        int multiplier;
        for (int i = 0; i < value.length(); i++) {
            multiplier = i % 2 == 0 ? 1 : 3;
            sum += multiplier * Integer.parseInt(value.subSequence(i, i + 1).toString());
        }

        return (10 - sum % 10) % 10;
    }

    private String stripIsbnSeparator(CharSequence t) {
        return HYPHEN.matcher(t.toString()).replaceAll("");
    }

    public String asin() {
        return faker.resolve("code.asin");
    }

    public String imei() {
        char[] str = new char[15];
        int len = str.length;

        // Fill in the first two values of the string based with the specified prefix.
        String arr = faker.options().option(REPORTING_BODY_IDENTIFIERS);
        str[0] = arr.charAt(0);
        str[1] = arr.charAt(1);

        // Fill all the remaining numbers except for the last one with random values.
        for (int i = 2; i < len - 1; i++) {
            str[i] = Character.forDigit(faker.number().numberBetween(0, 9), 10);
        }

        // Calculate the Luhn checksum of the values thus far
        int lenOffset = (len + 1) % 2;
        int sum = 0;
        for (int i = 0; i < len - 1; i++) {
            if ((i + lenOffset) % 2 != 0) {
                int t = Character.getNumericValue(str[i]) << 1;

                if (t > 9) {
                    t -= 9;
                }

                sum += t;
            } else {
                sum += Character.getNumericValue(str[i]);
            }
        }

        // Choose the last digit so that it causes the entire string to pass the checksum.
        str[len - 1] = Character.forDigit(((10 - (sum % 10)) % 10), 10);

        return new String(str);
    }

    public String ean8() {
        return gtin8();
    }

    public String gtin8() {
        return gtin("\\d{7}", GTIN_8_CHECK_DIGITS);
    }

    public String gtin13() {
        return gtin("\\d{12}", GTIN_13_CHECK_DIGITS);
    }

    public String ean13() {
        return gtin13();
    }

    private String gtin(String regex, int[] checkDigits) {
        List<Character> values = faker.regexify(regex)
            .chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.toList());

        int sum = 0;
        for (int i = 0; i < values.size(); i++) {
            sum += Character.getNumericValue(values.get(i)) * checkDigits[i];
        }
        int checkDigit = 10 - sum % 10;
        if (checkDigit == 10) {
            values.add(Character.forDigit(0, 10));
        } else {
            values.add(Character.forDigit(checkDigit, 10));
        }

        return values.stream()
            .map(String::valueOf)
            .collect(Collectors.joining());
    }
}
