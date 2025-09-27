package net.datafaker.idnumbers;

import net.datafaker.annotations.InternalApi;
import net.datafaker.providers.base.BaseProviders;
import net.datafaker.providers.base.IdNumber;
import net.datafaker.providers.base.PersonIdNumber;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;

import static net.datafaker.idnumbers.Utils.birthday;
import static net.datafaker.idnumbers.Utils.gender;

/**
 * Implementation based on the definition at
 * <a href="https://www.skatteverket.se/privat/folkbokforing/personnummer.4.3810a01c150939e893f18c29.html">https://www.skatteverket.se/privat/folkbokforing/personnummer.4.3810a01c150939e893f18c29.html</a>
 * and the description at
 * <a href="https://en.wikipedia.org/wiki/Personal_identity_number_">https://en.wikipedia.org/wiki/Personal_identity_number_</a>(Sweden)
 */
@InternalApi
public class SwedenIdNumber implements IdNumberGenerator {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");
    private static final DateTimeFormatter FULL_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Override
    public String countryCode() {
        return "SE";
    }

    private static final String[] VALID_PATTERNS = {"######-####", "######+####"};

    @Deprecated
    public String getValidSsn(BaseProviders f) {
        return generateValid(f);
    }

    @Override
    public PersonIdNumber generateValid(BaseProviders f, IdNumber.IdNumberRequest request) {
        LocalDate birthday = birthday(f, request);
        String end = generateEndPart(f);
        String basePart = DATE_TIME_FORMATTER.format(birthday)
            + generateSymbol(birthday)
            + end;
        String idNumber = basePart + calculateChecksum(basePart);
        return new PersonIdNumber(idNumber, birthday, gender(f, request));
    }

    private String generateSymbol(LocalDate date){
        return isYearOver100YearsAgo(date.toString().substring(0, 4), LocalDate.now()) ? "+" : "-";
    }

    private String generateEndPart(BaseProviders f) {
        return "%03d".formatted(f.number().numberBetween(1, 1000));
    }

    /**
     * @deprecated Use method {@link #generateInvalid(BaseProviders)} instead
     */
    @Deprecated
    public String getInvalidSsn(BaseProviders f) {
        return generateInvalid(f);
    }

    @Override
    public String generateInvalid(BaseProviders f) {
        String candidate = "121212-1212"; // Seed with a valid number
        while (isValidSwedishSsn(candidate)) {
            String pattern = f.options().option(VALID_PATTERNS);
            candidate = f.numerify(pattern);
        }

        return candidate;
    }

    public static boolean isValidSwedishSsn(String ssn) {
        if (ssn.length() != 11) {
            return false;
        }

        try {
            if (isDateIncorrect(ssn)) {
                return false;
            }
        } catch (DateTimeParseException | NumberFormatException ignore) {
            return false;
        }

        if (ssn.startsWith("000", 7)) {
            return false;
        }

        int calculatedChecksum = calculateChecksum(ssn);
        int checksum = Integer.parseInt(ssn.substring(10, 11));
        return checksum == calculatedChecksum;
    }

    private static boolean isDateIncorrect(String ssn) {
        String dateString = ssn.substring(0, 6);

        if (!ChronoField.YEAR.range().isValidIntValue(Integer.parseInt(dateString.substring(0, 2)))) {
            return true;
        }

        if (!ChronoField.MONTH_OF_YEAR.range().isValidIntValue(Integer.parseInt(dateString.substring(2, 4)))) {
            return true;
        }

        if (!ChronoField.DAY_OF_MONTH.range().isValidIntValue(Integer.parseInt(dateString.substring(4)))) {
            return true;
        }

        dateString = findYearBeginningFromSsn(ssn) + dateString;

        LocalDate date = LocalDate.parse(dateString, FULL_DATE_FORMATTER);
        // want to check that the parsed date is equal to the supplied data, most of the attempts will fail
        String reversed = date.format(FULL_DATE_FORMATTER);
        return !reversed.equals(dateString);
    }

    @InternalApi
    static String findYearBeginningFromSsn(String ssn) {
        char symbol = ssn.charAt(6);
        String yearEnd = ssn.substring(0, 2);

        int startYear = (symbol == '+') ? LocalDate.now().minusYears(100).getYear() - 1 : LocalDate.now().getYear();

        for (int year = startYear; year >= 0; year--) {
            if (String.valueOf(year).endsWith(yearEnd)) {
                return String.valueOf(year).substring(0, 2);
            }
        }

        String errorMessage = symbol == '+'
            ? "Cannot find year that ends with %s and is before %d".formatted(yearEnd, startYear + 1)
            : "Cannot find year that ends with %s".formatted(yearEnd);

        throw new RuntimeException(errorMessage);
    }

    @InternalApi
    static boolean isYearOver100YearsAgo(String year, LocalDate currentDate) {
        LocalDate hundredYearsAgo = currentDate.minusYears(100);
        return LocalDate.of(Integer.parseInt(year), 1, 1).isBefore(hundredYearsAgo);
    }

    private static int calculateChecksum(String number) {
        String dateString = number.substring(0, 6);
        String birthNumber = number.substring(7, 10);

        String calculatedNumber = calculateDigits(dateString + birthNumber);
        int sum = calculateDigitSum(calculatedNumber);

        int lastDigit = (sum % 10);
        int difference = 10 - lastDigit;

        return (difference % 10);
    }

    private static String calculateDigits(String numbers) {
        StringBuilder calculatedNumbers = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            int res;
            int n = numbers.charAt(i) - '0';
            if (i % 2 == 0) {
                res = n << 1;
            } else {
                res = n;
            }

            calculatedNumbers.append(res);
        }
        return calculatedNumbers.toString();
    }

    private static int calculateDigitSum(String numbers) {
        int sum = 0;
        final int length = numbers.length();
        for (int i = 0; i < length; i++) {
            int n = numbers.charAt(i) - '0';
            sum += n;
        }
        return sum;
    }
}
