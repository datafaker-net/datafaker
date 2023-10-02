package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Collection;
import java.util.regex.Pattern;

class CompanyTest extends BaseFakerTest<BaseFaker> {

    public static final Pattern URL_PATTERN = Pattern.compile("(([a-z0-9]|[a-z0-9][a-z0-9\\-]*[a-z0-9])\\.)*([a-z0-9]|[a-z0-9][a-z0-9\\-]*[a-z0-9])");
    public static final Pattern PHRASE_PATTERN = Pattern.compile("(\\w+[ /-]?){1,9}");
    private final Company company = faker.company();

    @Test
    void testName() {
        assertThat(company.name()).matches("[A-Za-z\\-&',. ]+");
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(company::suffix, "company.suffix", "[A-Za-z ]+"),
            TestSpec.of(company::industry, "company.industry", "(\\w+([ ,&/-]{1,3})?){1,4}+"),
            TestSpec.of(company::profession, "company.profession"));
    }

    @Test
    void testBuzzword() {
        assertThat(company.buzzword()).matches("(\\w+[ /-]?){1,3}");
    }

    @Test
    void testCatchPhrase() {
        assertThat(company.catchPhrase()).matches(PHRASE_PATTERN);
    }

    @Test
    void testBs() {
        assertThat(company.bs()).matches(PHRASE_PATTERN);
    }

    @Test
    void testLogo() {
        assertThat(company.logo()).matches("https://pigment.github.io/fake-logos/logos/medium/color/\\d+\\.png");
    }

    @RepeatedTest(100)
    void testUrl() {
        assertThat(company.url()).matches(URL_PATTERN);
    }
}
