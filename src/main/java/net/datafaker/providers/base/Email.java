package net.datafaker.providers.base;


import net.datafaker.service.FakerIDN;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class Email  extends AbstractProvider<BaseProviders>{

    protected Email(BaseProviders faker) {
        super(faker);
    }

    public String emailAddress() {
        return emailAddress(faker.name().username());
    }
    public String emailAddress(String localPart) {
        return emailAddress(localPart, FakerIDN.toASCII(faker.resolve("internet.free_email")));
    }
    public String safeEmailAddress() {
        return safeEmailAddress(faker.name().username());
    }

    public String safeEmailAddress(String localPart) {
        return emailAddress(localPart, FakerIDN.toASCII(faker.resolve("internet.safe_email")));
    }

    private String emailAddress(String localPart, String domain) {
        return String.join("", stripAccents(localPart), "@", domain);
    }

    public static final Pattern DIACRITICS_AND_FRIENDS
        = Pattern.compile("[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+");

    private String stripAccents(String input) {
        // strip accents from input
        String str = Normalizer.normalize(input, Normalizer.Form.NFD);
        str = DIACRITICS_AND_FRIENDS.matcher(str).replaceAll("");
        return str;
    }


}
