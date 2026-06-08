package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HttpTest extends BaseFakerTest {

    private final Http http = faker.http();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(http::requestHeader, "http.request_header"),
            TestSpec.of(http::responseHeader, "http.response_header"),
            TestSpec.of(http::contentType, "http.content_type"),
            TestSpec.of(http::httpMethod, "http.http_method"),
            TestSpec.of(http::httpVersion, "http.http_version"),
            TestSpec.of(http::encoding, "http.encoding"),
            TestSpec.of(http::responseBody, "http.response_body")
        );
    }

    // -------------------------------------------------------------------------
    // Status code range tests
    // -------------------------------------------------------------------------

    @RepeatedTest(20)
    void statusCode() {
        assertThat(http.statusCode()).isBetween(100, 599);
    }

    @RepeatedTest(10)
    void informational() {
        assertThat(http.informational()).isBetween(100, 199);
    }

    @RepeatedTest(10)
    void successful() {
        assertThat(http.successful()).isBetween(200, 299);
    }

    @RepeatedTest(10)
    void redirect() {
        assertThat(http.redirect()).isBetween(300, 399);
    }

    @RepeatedTest(10)
    void clientError() {
        assertThat(http.clientError()).isBetween(400, 499);
    }

    @RepeatedTest(10)
    void serverError() {
        assertThat(http.serverError()).isBetween(500, 599);
    }

    // -------------------------------------------------------------------------
    // Status reason phrase tests
    // -------------------------------------------------------------------------

    @RepeatedTest(10)
    void statusMessage() {
        assertThat(http.statusMessage()).isNotBlank().matches("[\\w ']+");
    }

    @RepeatedTest(10)
    void informationalMessage() {
        assertThat(http.informationalMessage()).isNotBlank();
    }

    @RepeatedTest(10)
    void successfulMessage() {
        assertThat(http.successfulMessage()).isNotBlank();
    }

    @RepeatedTest(10)
    void redirectMessage() {
        assertThat(http.redirectMessage()).isNotBlank();
    }

    @RepeatedTest(10)
    void clientErrorMessage() {
        assertThat(http.clientErrorMessage()).isNotBlank();
    }

    @RepeatedTest(10)
    void serverErrorMessage() {
        assertThat(http.serverErrorMessage()).isNotBlank();
    }

    // -------------------------------------------------------------------------
    // Combined code + reason
    // -------------------------------------------------------------------------

    @RepeatedTest(10)
    void statusCodeWithReason() {
        String entry = http.statusCodeWithReason();
        assertThat(entry).matches("\\d{3} .+");
        int code = Integer.parseInt(entry.substring(0, 3));
        assertThat(code).isBetween(100, 599);
    }

    // -------------------------------------------------------------------------
    // User agent tests
    // -------------------------------------------------------------------------

    @RepeatedTest(10)
    void userAgent() {
        assertThat(http.userAgent()).isNotBlank().startsWith("Mozilla/");
    }

    @RepeatedTest(10)
    void mobileUserAgent() {
        assertThat(http.mobileUserAgent()).isNotBlank().contains("Mobile");
    }

    @Test
    void userAgentForAllBrowsers() {
        for (Http.Browser browser : Http.Browser.values()) {
            assertThat(http.userAgent(browser))
                .as("User agent for browser %s", browser)
                .isNotBlank()
                .startsWith("Mozilla/");
        }
    }
}
