package net.datafaker;

import net.datafaker.service.FakerIDN;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @since 0.8.0
 */
public class Company {

    private static final Pattern UNWANTED_CHARACTERS = Pattern.compile("[.,' ]");
    private final Faker faker;

    protected Company(Faker faker) {
        this.faker = faker;
    }

    public String name() {
        return faker.fakeValuesService().resolve("company.name", this, faker);
    }

    public String suffix() {
        return faker.fakeValuesService().resolve("company.suffix", this, faker);
    }

    public String industry() {
        return faker.fakeValuesService().resolve("company.industry", this, faker);
    }

    public String profession() {
        return faker.fakeValuesService().resolve("company.profession", this, faker);
    }

    public String buzzword() {
        @SuppressWarnings("unchecked")
        List<List<String>> buzzwordLists = (List<List<String>>) faker.fakeValuesService().fetchObject("company.buzzwords");
        List<String> buzzwords = new ArrayList<>();
        for (List<String> buzzwordList : buzzwordLists) {
            buzzwords.addAll(buzzwordList);
        }
        return buzzwords.get(faker.random().nextInt(buzzwords.size()));
    }

    /**
     * Generate a buzzword-laden catch phrase.
     */
    public String catchPhrase() {
        @SuppressWarnings("unchecked")
        List<List<String>> catchPhraseLists = (List<List<String>>) faker.fakeValuesService().fetchObject("company.buzzwords");
        return joinSampleOfEachList(catchPhraseLists);
    }

    /**
     * When a straight answer won't do, BS to the rescue!
     */
    public String bs() {
        @SuppressWarnings("unchecked")
        List<List<String>> buzzwordLists = (List<List<String>>) faker.fakeValuesService().fetchObject("company.bs");
        return joinSampleOfEachList(buzzwordLists);
    }

    /**
     * Generate a random company logo url in PNG format.
     */
    public String logo() {
        int number = faker.random().nextInt(13) + 1;
        return "https://pigment.github.io/fake-logos/logos/medium/color/" + number + ".png";
    }

    public String url() {
        return String.join(".",
            "www",
            FakerIDN.toASCII(domainName()),
            domainSuffix()
        );
    }

    private String domainName() {
        return UNWANTED_CHARACTERS.matcher(name().toLowerCase(faker.getLocale())).replaceAll("");
    }

    private String domainSuffix() {
        return faker.fakeValuesService().resolve("internet.domain_suffix", this, faker);
    }

    private String joinSampleOfEachList(List<List<String>> listOfLists) {
        List<String> words = new ArrayList<>();
        for (List<String> list : listOfLists) {
            words.add(list.get(faker.random().nextInt(list.size())));
        }
        return String.join(" ", words);
    }
}
