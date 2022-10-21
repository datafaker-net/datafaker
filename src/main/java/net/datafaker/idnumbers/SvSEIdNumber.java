package net.datafaker.idnumbers;

import net.datafaker.providers.base.BaseProviders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Implementation based on the definition at
 * <a href="https://www.skatteverket.se/privat/folkbokforing/personnummer.4.3810a01c150939e893f18c29.html">https://www.skatteverket.se/privat/folkbokforing/personnummer.4.3810a01c150939e893f18c29.html</a>
 * and the description at
 * <a href="https://en.wikipedia.org/wiki/Personal_identity_number_">https://en.wikipedia.org/wiki/Personal_identity_number_</a>(Sweden)
 */
public class SvSEIdNumber implements IdNumbers {
    private static final String[] VALID_PATTERNS = {"######-####", "######+####"};

    public String getValidSsn(BaseProviders f) {
        String candidate = "";
        while (!validSwedishSsn(candidate)) {
            String pattern = getPattern(f);
            candidate = f.numerify(pattern);
        }

        return candidate;
    }

    public String getInvalidSsn(BaseProviders f) {
        String candidate = "121212-1212"; // Seed with a valid number
        while (validSwedishSsn(candidate)) {
            String pattern = getPattern(f);
            candidate = f.numerify(pattern);
        }

        return candidate;
    }

    private String getPattern(BaseProviders faker) {
        return faker.options().option(VALID_PATTERNS);
    }

    boolean validSwedishSsn(String ssn) {
        if (ssn.length() != 11) {
            return false;
        }

        try {
            if (parseDate(ssn)) {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }

        int calculatedChecksum = calculateChecksum(ssn);
        int checksum = Integer.parseInt(ssn.substring(10, 11));
        return checksum == calculatedChecksum;
    }

    private boolean parseDate(String ssn) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        String dateString = ssn.substring(0, 6);
        Date date = sdf.parse(dateString);

        // want to check that the parsed date is equal to the supplied data, most of the attempts will fail
        String reversed = sdf.format(date);
        return !reversed.equals(dateString);
    }

    private int calculateChecksum(String number) {
        String dateString = number.substring(0, 6);
        String birthNumber = number.substring(7, 10);

        String calculatedNumber = calculateDigits(dateString + birthNumber);
        int sum = calculateDigitSum(calculatedNumber);

        int lastDigit = (sum % 10);
        int difference = 10 - lastDigit;

        return (difference % 10);
    }

    private String calculateDigits(String numbers) {
        StringBuilder calculatedNumbers = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            int res;
            int n = Integer.parseInt(numbers.substring(i, i + 1));
            if (i % 2 == 0) {
                res = n << 1;
            } else {
                res = n;
            }

            calculatedNumbers.append(res);
        }
        return calculatedNumbers.toString();
    }

    private int calculateDigitSum(String numbers) {
        int sum = 0;
        for (int i = 0; i < numbers.length(); i++) {
            int n = Integer.parseInt(numbers.substring(i, i + 1));
            sum += n;
        }
        return sum;
    }

}
