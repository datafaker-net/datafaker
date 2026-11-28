package net.datafaker.internal.helper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.net.URI;
import java.util.Locale;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Tests for HostnameHelper RFC 1123 LDH compliance.
 */
class HostnameHelperTest {
    private static final Pattern RFC_1123_LABEL_PATTERN = Pattern.compile("^[a-z0-9]([a-z0-9-]{0,61}[a-z0-9])?$");
    private static final Pattern RFC_1123_HOSTNAME_PATTERN = Pattern.compile("^([a-z0-9]([a-z0-9-]{0,61}[a-z0-9])?\\.)*[a-z0-9]([a-z0-9-]{0,61}[a-z0-9])?$");

    @Test
    void testToAsciiHostnameLabelWithNullInput() {
        assertThat(HostnameHelper.toAsciiHostnameLabel(null, Locale.ROOT)).isEqualTo("example");
    }

    @Test
    void testToAsciiHostnameLabelWithEmptyInput() {
        assertThat(HostnameHelper.toAsciiHostnameLabel("", Locale.ROOT)).isEqualTo("example");
    }

    @ParameterizedTest
    @CsvSource({
        "simple,simple",
        "Simple,simple",
        "SIMPLE,simple",
        "Simple123,simple123",
        "example-domain,example-domain",
    })
    void testToAsciiHostnameLabelValidInputs(String input, String expected) {
        String result = HostnameHelper.toAsciiHostnameLabel(input, Locale.ROOT);
        assertThat(result).isEqualTo(expected);
        assertThat(result).matches(RFC_1123_LABEL_PATTERN);
    }

    @ParameterizedTest
    @CsvSource({
        "company&name,companyname",
        "name with spaces,namewithspaces",
    })
    void testToAsciiHostnameLabelSpecialCharacters(String input, String expected) {
        String result = HostnameHelper.toAsciiHostnameLabel(input, Locale.ROOT);
        assertThat(result).isEqualTo(expected);
        assertThat(result).matches(RFC_1123_LABEL_PATTERN);
    }

    @Test
    void testToAsciiHostnameLabelMaxLength() {
        String longInput = "a".repeat(100);
        String result = HostnameHelper.toAsciiHostnameLabel(longInput, Locale.ROOT);
        assertThat(result).hasSizeLessThanOrEqualTo(63);
        assertThat(result).matches(RFC_1123_LABEL_PATTERN);
    }

    @Test
    void testToAsciiHostnameLabelAmpersand() {
        String result = HostnameHelper.toAsciiHostnameLabel("Acme & Co", Locale.ROOT);
        assertThat(result).isNotEmpty();
        assertThat(result).matches(RFC_1123_LABEL_PATTERN);
    }

    @Test
    void testToAsciiHostnameLabelOnlySpecialChars() {
        String result = HostnameHelper.toAsciiHostnameLabel("!@#$%^&*()", Locale.ROOT);
        assertThat(result).isEqualTo("example");
    }

    @Test
    void testToAsciiHostnameLabelHyphenFallback() {
        String result = HostnameHelper.toAsciiHostnameLabel("----", Locale.ROOT);
        assertThat(result).isEqualTo("example");
    }

    @ParameterizedTest
    @CsvSource({
        "example.com,example.com",
        "EXAMPLE.COM,example.com",
        "my-domain.co.uk,my-domain.co.uk",
        "sub.domain.example.com,sub.domain.example.com",
    })
    void testToAsciiHostnameValidInputs(String input, String expected) {
        String result = HostnameHelper.toAsciiHostname(input, Locale.ROOT);
        assertThat(result).isEqualTo(expected);
        assertThat(result).matches(RFC_1123_HOSTNAME_PATTERN);
    }

    @Test
    void testToAsciiHostnameWithNullInput() {
        assertThat(HostnameHelper.toAsciiHostname(null, Locale.ROOT)).isEqualTo("example.com");
    }

    @Test
    void testToAsciiHostnameWithEmptyInput() {
        assertThat(HostnameHelper.toAsciiHostname("", Locale.ROOT)).isEqualTo("example.com");
    }

    @Test
    void testHostnameCanBeUsedInURI() {
        String hostname = HostnameHelper.toAsciiHostname("example & co.com", Locale.ROOT);
        assertDoesNotThrow(() -> {
            new URI("https://" + hostname + "/");
        });
    }
}
