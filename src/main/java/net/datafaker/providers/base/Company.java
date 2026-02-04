package net.datafaker.providers.base;

import net.datafaker.internal.helper.FakerIDN;
import net.datafaker.internal.helper.LazyEvaluated;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.joining;

/**
 * @since 0.8.0
 */
public class Company extends AbstractProvider<BaseProviders> {

    private final LazyEvaluated<List<String>> allBuzzwords = new LazyEvaluated<>(() -> loadBuzzwords());

    protected Company(BaseProviders faker) {
        super(faker);
    }

    public String name() {
        return resolve("company.name");
    }

    public String suffix() {
        return resolve("company.suffix");
    }

    public String industry() {
        return resolve("company.industry");
    }

    public String profession() {
        return resolve("company.profession");
    }

    public String buzzword() {
        return faker.options().nextElement(allBuzzwords.get());
    }

    private List<String> loadBuzzwords() {
        List<List<String>> buzzwordLists = faker.fakeValuesService().fetchObject("company.buzzwords", faker.getContext());
        return buzzwordLists.stream().flatMap(Collection::stream).toList();
    }

    /**
     * Generate a buzzword-laden catch phrase.
     */
    public String catchPhrase() {
        List<List<String>> catchPhraseLists = faker.fakeValuesService().fetchObject("company.buzzwords", faker.getContext());
        return joinSampleOfEachList(catchPhraseLists);
    }

    /**
     * When a straight answer won't do, BS to the rescue!
     */
    public String bs() {
        List<List<String>> buzzwordLists = faker.fakeValuesService().fetchObject("company.bs", faker.getContext());
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
        return "www."
            + FakerIDN.toASCII(domainName()) + "."
            + domainSuffix();
    }

    private String domainName() {
        final char[] res = name().toLowerCase(faker.getContext().getLocale()).toCharArray();
        int offset = 0;
        for (int i = 0; i < res.length; i++) {
            final char c = res[i];
            switch (c) {
                case '.', ',', '\'', ' ', ']', '&' -> offset++;
                default -> res[i - offset] = res[i];
            }
        }
        return String.valueOf(res, 0, res.length - offset);
    }

    private String domainSuffix() {
        return resolve("internet.domain_suffix");
    }

    private String joinSampleOfEachList(List<List<String>> listOfLists) {
        return listOfLists.stream()
            .map(list -> faker.options().nextElement(list))
            .collect(joining(" "));
    }
}
