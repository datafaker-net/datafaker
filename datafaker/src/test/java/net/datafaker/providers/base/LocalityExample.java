package net.datafaker.providers.base;


import java.util.Arrays;
import java.util.List;
import java.util.Locale;

class LocalityExample {

    /**
     * Example to illustrate use of Locality to randomly select
     * locales (language and geographical/political/cultural region) when using Faker
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        BaseFaker defaultFaker = new BaseFaker();

        // EXAMPLE: GET LIST OF ALL SUPPORTED LOCALES
        List<String> allLocales = defaultFaker.locality().allSupportedLocales();
        System.out.println("All supported locales: " + Arrays.toString(allLocales.toArray()));

        // EXAMPLE: GET A FAKER OBJECT WITH A RANDOM LOCALE (SELECTED WITH REPLACEMENT)
        // Instantiate a Faker object with a randomized locale
        Locale pickedLocale = Locale.forLanguageTag(defaultFaker.locality().localeString());
        BaseFaker faker = new BaseFaker(pickedLocale);

        // Use Faker object to generate data in the randomly selected locale
        String fullName = faker.name().fullName();
        String streetAddress = faker.address().streetAddress();
        String phoneNumber = faker.phoneNumber().phoneNumber();

        System.out.println("EXAMPLE: SELECT A RANDOM LOCALE (WITH REPLACEMENT)");
        System.out.println("Random Locale: " + pickedLocale.toString());
        System.out.println("  Full Name: " + fullName);
        System.out.println("  Street Address: " + streetAddress);
        System.out.println("  Phone Number: " + phoneNumber);

        // EXAMPLE: ROTATE THROUGH ALL SUPPORTED LOCALES TO GENERATE USER DATA
        //   LOCALES PICKED AT RANDOM (SELECTED WITHOUT REPLACEMENT)
        System.out.println("EXAMPLE: ROTATE THROUGH ALL LOCALES AT RANDOM (WITHOUT REPLACEMENT)");
        BaseFaker currentFaker;

        int numSupportedLocales = allLocales.size();
        for (int i = 0; i < numSupportedLocales; i++) {
            Locale currentLocale = Locale.forLanguageTag(defaultFaker.locality().localeStringWithoutReplacement());
            System.out.println("Random Locale: " + currentLocale.toString());
            currentFaker = new BaseFaker(currentLocale);
            System.out.println("  First Name: " + currentFaker.name().firstName());
            System.out.println("  Last Name: " + currentFaker.name().lastName());
            System.out.println("  Street Address: " + currentFaker.address().streetAddress());
            System.out.println("  Phone Number: " + currentFaker.phoneNumber().phoneNumber());
        }

    }

}
