package net.datafaker.idnumbers;

import net.datafaker.providers.base.BaseProviders;

import java.util.List;
import java.util.regex.Pattern;

public class AmericanIdNumber implements IdNumberGenerator {
    @Override
    public String countryCode() {
        return "US";
    }

    private static final List<String> INVALID_SSNS = List.of(
        "0{3}-\\d{2}-\\d{4}",
        "\\d{3}-0{2}-\\d{4}",
        "\\d{3}-\\d{2}-0{4}",
        "666-\\d{2}-\\d{4}",
        "9\\d{2}-\\d{2}-\\d{4}");

    private static final List<Pattern> INVALID_SSN_PATTERNS = INVALID_SSNS.stream()
        .map(Pattern::compile)
        .toList();

    @Deprecated
    public String getValidSsn(BaseProviders f) {
        return generateValid(f);
    }

    @Override
    public String generateValid(BaseProviders f) {
        final String ssn = f.regexify("[0-8]\\d{2}-\\d{2}-\\d{4}");

        boolean isValid = INVALID_SSN_PATTERNS.stream()
            .noneMatch(invalidSSNPattern -> invalidSSNPattern.matcher(ssn).matches());
        return isValid ? ssn : generateValid(f);
    }

    @Override
    public String generateInvalid(BaseProviders faker) {
        return faker.regexify(faker.options().nextElement(INVALID_SSNS));
    }
}
