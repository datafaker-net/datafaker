package net.datafaker;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import net.datafaker.service.LocalePicker;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PhoneNumberValidityFinderTest {

    @Test
    public void testAllCellPhoneForLocale() throws NumberParseException {
        String language = "en";
        String region = "GB";
        Faker localFaker = new Faker(new Locale(language, region));
        PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        System.out.println(util.getExampleNumber(region));

        int errorCount = 0;
        for (int i = 0; i < 100; i++) {
            String phoneNumber = localFaker.phoneNumber().phoneNumber();
            Phonenumber.PhoneNumber proto = util.parse(phoneNumber, region);

            if (!util.isValidNumber(proto)) {
                System.out.println(i + ": " + phoneNumber);
                errorCount++;
            }
        }

        System.out.println("Error count: " + errorCount);

    }

    @Test
    public void testValidNumber() throws NumberParseException {
        String phoneNumber = "0140 123456";
        String region = "SE";

        PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, region);
        System.out.println(util.isValidNumber(proto));
    }

    @Test
    public void testAllPhoneNumbers()  {
        List<String> allSupportedLocales = new LocalePicker().getAllSupportedLocales();
        Map<Locale, Integer> errorCounts = new HashMap<>();

        for (String supportedLocale : allSupportedLocales) {
            String country = supportedLocale;
            if(supportedLocale.contains("-")) {
                country = supportedLocale.split("-")[1];
                supportedLocale = supportedLocale.split("-")[0];
            }

            PhoneNumberUtil util = PhoneNumberUtil.getInstance();
            Locale locale = new Locale(supportedLocale, country);
            final Faker f = new Faker(locale);
            int errorCount = 0;
            for (int i = 0; i < 100; i++) {
                String phoneNumber = f.phoneNumber().phoneNumber();

                try {
                    Phonenumber.PhoneNumber proto = util.parse(phoneNumber, country.toUpperCase());

                    if(!util.isValidNumber(proto)) {
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
                .filter(e -> e.getValue() > 50)
                .sorted(Map.Entry.comparingByValue())
                .forEach(System.out::println);
    }
}
