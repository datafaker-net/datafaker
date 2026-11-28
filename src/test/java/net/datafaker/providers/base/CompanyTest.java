package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.doReturn;

import java.net.URL;
import java.net.URI;
import java.util.List;
import java.util.Collection;
import java.util.regex.Pattern;

class CompanyTest extends BaseFakerTest {

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

    @RepeatedTest(10)
    void testUrl() {
        assertThat(company.url()).matches(URL_PATTERN);
    }

    @Test
    void testDomainNameIsValidLabel() {
        String domainName = company.domainName();
        // Should be a valid RFC 1123 label: [a-z0-9]([a-z0-9-]{0,61}[a-z0-9])?
        assertThat(domainName).matches("^[a-z0-9]([a-z0-9-]{0,61}[a-z0-9])?$");
    }

    @RepeatedTest(10)
    void testUrlCanBeUsedAsHttpsUrl() {
        String url = company.url();
        assertDoesNotThrow(() -> new URL("https://" + url + "/"));
    }

    @RepeatedTest(10)
    void testUrlCanBeUsedAsURI() {
        String url = company.url();
        assertDoesNotThrow(() -> new URI("https://" + url + "/"));
    }

    @Test
    void testDomainNameWithAmpersand() {
        // Edge case from https://github.com/datafaker-net/datafaker/pull/1757
        // Company names with & should be sanitized to valid hostnames
        // Mock the company to ensure the name contains '&'
        Company mockedCompany = spy(company);
        doReturn("Acme & Co").when(mockedCompany).name();

        String domainName = mockedCompany.domainName();
        assertThat(domainName).isNotEmpty();
        assertThat(domainName).matches("^[a-z0-9]([a-z0-9-]{0,61}[a-z0-9])?$");
        // Verify that ampersand was stripped (result should be valid)
        assertThat(domainName).doesNotContain("&");
    }
}
