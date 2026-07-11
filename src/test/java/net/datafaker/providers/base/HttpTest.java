package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
            TestSpec.of(() -> http.responseBody("application/json"), "http.response_body.json"),
            TestSpec.of(() -> http.responseBody("application/xml"), "http.response_body.xml"),
            TestSpec.of(() -> http.responseBody("text/html"), "http.response_body.html"),
            TestSpec.of(() -> http.responseBody("text/plain"), "http.response_body.plain"),
            TestSpec.of(() -> http.responseBody("text/csv"), "http.response_body.csv"),
            TestSpec.of(() -> http.responseBody("application/javascript"), "http.response_body.javascript"),
            TestSpec.of(() -> http.responseBody("text/css"), "http.response_body.css"),
            TestSpec.of(() -> http.responseBody("application/graphql"), "http.response_body.graphql"),
            TestSpec.of(() -> http.responseBody("text/markdown"), "http.response_body.markdown")
        );
    }

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

    @RepeatedTest(10)
    void statusMessage() {
        assertThat(http.statusMessage()).isNotBlank();
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

    @RepeatedTest(10)
    void statusCodeWithReason() {
        String entry = http.statusCodeWithReason();
        assertThat(entry).matches("\\d{3} .+");
        int code = Integer.parseInt(entry.substring(0, 3));
        assertThat(code).isBetween(100, 599);
    }

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
        for (var browser : Http.Browser.values()) {
            assertThat(http.userAgent(browser))
                .as("User agent for browser %s", browser)
                .isNotBlank()
                .startsWith("Mozilla/");
        }
    }

    @RepeatedTest(20)
    void responseBodyNoArg() {
        assertThat(http.responseBody()).isNotBlank();
    }

    @ParameterizedTest
    @CsvSource({
        "application/json,          {",
        "application/ld+json,       {",
        "application/vnd.api+json,  {",
        "application/problem+json,  {",
        "application/xml,           <?xml",
        "text/xml,                  <?xml",
        "text/html,                 <!DOCTYPE html>",
        "text/html; charset=utf-8,  <!DOCTYPE html>",
        "text/plain,                ''",
        "text/csv,                  ''",
        "application/javascript,    ''",
        "text/javascript,           ''",
        "text/css,                  ''",
        "application/graphql,       {",
        "text/markdown,             ''"
    })
    void responseBodyForContentType(String contentType, String expectedPrefix) {
        String body = http.responseBody(contentType.trim());
        assertThat(body).isNotBlank();
        if (!expectedPrefix.isBlank()) {
            assertThat(body).startsWith(expectedPrefix.trim());
        }
    }

    @Test
    void responseBodyUnknownContentTypeFallsBackToJson() {
        String body = http.responseBody("application/unknown");
        assertThat(body).isNotBlank().startsWith("{");
    }
}
