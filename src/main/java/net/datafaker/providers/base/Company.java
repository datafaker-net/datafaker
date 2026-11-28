package net.datafaker.providers.base;

import net.datafaker.internal.helper.FakerIDN;
import net.datafaker.internal.helper.HostnameHelper;
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
        return faker.selection().oneOf(allBuzzwords.get());
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

    /**
     * Returns a domain name based on the company name.
     * <p>
     * The domain name is created by sanitizing the company name according to RFC 1123
     * (Letter-Digit-Hyphen rules). The result is always a valid ASCII hostname.
     *
     * @return a valid ASCII domain name
     * @since 0.8.0
     */
    public String domainName() {
        String companyName = name();
        return HostnameHelper.toAsciiHostnameLabel(companyName, faker.getContext().getLocale());
    }

    /**
     * Returns a web URL for the company.
     * <p>
     * The domain name is created by sanitizing the company name according to RFC 1123
     * (Letter-Digit-Hyphen rules), and the full URL is constructed with a domain suffix.
     *
     * @return a valid web URL
     * @since 0.8.0
     */
    public String url() {
        return "www."
            + FakerIDN.toASCII(domainName()) + "."
            + domainSuffix();
    }

    /**
     * Returns a domain suffix (TLD).
     * <p>
     * The suffix is sanitized according to RFC 1123 to ensure it is ASCII-compatible.
     * For non-ASCII locales, the suffix is converted to ASCII via the hostname sanitizer.
     *
     * @return a domain suffix (ASCII)
     */
    private String domainSuffix() {
        String suffix = resolve("internet.domain_suffix");
        return HostnameHelper.toAsciiHostname(suffix, faker.getContext().getLocale());
    }

    private String joinSampleOfEachList(List<List<String>> listOfLists) {
        return listOfLists.stream()
            .map(list -> faker.selection().oneOf(list))
            .collect(joining(" "));
    }
}
