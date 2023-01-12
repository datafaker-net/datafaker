package net.datafaker.providers.base;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * These tests use System.out.printlns because the error rate is quite high.
 */
class PhoneNumberValidityFinderTest extends BaseFakerTest<BaseFaker> {

    @Test
    void testAllCellPhoneForLocale() throws NumberParseException {
        String language = "en";
        String region = "GB";
        BaseFaker localFaker = new BaseFaker(new Locale(language, region));
        PhoneNumberUtil util = PhoneNumberUtil.getInstance();

        int errorCount = 0;
        for (int i = 0; i < 100; i++) {
            String phoneNumber = localFaker.phoneNumber().phoneNumber();
            Phonenumber.PhoneNumber proto = util.parse(phoneNumber, region);

            if (!util.isValidNumber(proto)) {
                errorCount++;
            }
        }

        System.out.println("Error count: " + errorCount);
    }

    @Test
    void generateExample() {
        Phonenumber.PhoneNumber exampleNumber = PhoneNumberUtil.getInstance().getExampleNumber("AR");
        System.out.println(exampleNumber);
    }

    @Test
    void testValidNumber() throws NumberParseException {
        String phoneNumber = "0140 123456";
        String region = "SE";

        PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, region);
        assertThat(util.isValidNumber(proto)).isTrue();
    }

    @Test
    void testAllPhoneNumbers() {
        List<String> allSupportedLocales = faker.locality().allSupportedLocales();
        Map<Locale, Integer> errorCounts = new HashMap<>();

        for (String supportedLocale : allSupportedLocales) {
            String country = supportedLocale;
            if (supportedLocale.contains("-")) {
                country = supportedLocale.split("-")[1];
                supportedLocale = supportedLocale.split("-")[0];
            }

            PhoneNumberUtil util = PhoneNumberUtil.getInstance();
            Locale locale = new Locale(supportedLocale, country);
            final BaseFaker f = new BaseFaker(locale);
            int errorCount = 0;
            for (int i = 0; i < 100; i++) {
                String phoneNumber = f.phoneNumber().phoneNumber();

                try {
                    Phonenumber.PhoneNumber proto = util.parse(phoneNumber, country.toUpperCase());

                    if (!util.isValidNumber(proto)) {
                        errorCount++;
                    }
                } catch (Exception e) {
                    break;
                }
            }
            errorCounts.put(locale, errorCount);
        }

        // sort by error count
        errorCounts.entrySet().stream()
            .filter(e -> e.getValue() > 35)
            .sorted(Map.Entry.comparingByValue())
            .forEach(System.out::println);
    }
}
