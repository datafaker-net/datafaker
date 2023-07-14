package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Collection;

class CompanyTest extends BaseFakerTest<BaseFaker> {

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
        assertThat(company.catchPhrase()).matches("(\\w+[ /-]?){1,9}");
    }

    @Test
    void testBs() {
        assertThat(company.bs()).matches("(\\w+[ /-]?){1,9}");
    }

    @Test
    void testLogo() {
        assertThat(company.logo()).matches("https://pigment.github.io/fake-logos/logos/medium/color/\\d+\\.png");
    }

    @RepeatedTest(100)
    void testUrl() {
        String regexp = "(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])";
        assertThat(company.url()).matches(regexp);
    }
}
