package net.datafaker.providers.base;

import static org.assertj.core.api.Assertions.assertThat;

import net.datafaker.junit.FakerSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Collection;
import java.util.List;

class HttpTest extends BaseFakerTest {

    private final Http http = faker.http();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(http::requestHeader,  "http.request_header"),
            TestSpec.of(http::responseHeader, "http.response_header"),
            TestSpec.of(http::contentType,    "http.content_type"),
            TestSpec.of(http::httpMethod,     "http.http_method"),
            TestSpec.of(http::httpVersion,    "http.http_version"),
            TestSpec.of(http::encoding,       "http.encoding"),
            TestSpec.of(() -> http.responseBody("application/json"),       "http.response_body.json"),
            TestSpec.of(() -> http.responseBody("application/xml"),        "http.response_body.xml"),
            TestSpec.of(() -> http.responseBody("text/html"),              "http.response_body.html"),
            TestSpec.of(() -> http.responseBody("text/plain"),             "http.response_body.plain"),
            TestSpec.of(() -> http.responseBody("text/csv"),               "http.response_body.csv"),
            TestSpec.of(() -> http.responseBody("application/javascript"), "http.response_body.javascript"),
            TestSpec.of(() -> http.responseBody("text/css"),               "http.response_body.css"),
            TestSpec.of(() -> http.responseBody("application/graphql"),    "http.response_body.graphql"),
            TestSpec.of(() -> http.responseBody("text/markdown"),          "http.response_body.markdown")
        );
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "http#statusCode", repeat = 20)
    void statusCode(int code) {
        assertThat(code).isBetween(100, 599);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "http#informational", repeat = 10)
    void informational(int code) {
        assertThat(code).isBetween(100, 199);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "http#successful", repeat = 10)
    void successful(int code) {
        assertThat(code).isBetween(200, 299);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "http#redirect", repeat = 10)
    void redirect(int code) {
        assertThat(code).isBetween(300, 399);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "http#clientError", repeat = 10)
    void clientError(int code) {
        assertThat(code).isBetween(400, 499);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "http#serverError", repeat = 10)
    void serverError(int code) {
        assertThat(code).isBetween(500, 599);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "http#statusMessage", repeat = 10)
    void statusMessage(String msg) {
        assertThat(msg).isNotBlank();
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "http#informationalMessage", repeat = 10)
    void informationalMessage(String msg) {
        assertThat(msg).isNotBlank();
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "http#successfulMessage", repeat = 10)
    void successfulMessage(String msg) {
        assertThat(msg).isNotBlank();
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "http#redirectMessage", repeat = 10)
    void redirectMessage(String msg) {
        assertThat(msg).isNotBlank();
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "http#clientErrorMessage", repeat = 10)
    void clientErrorMessage(String msg) {
        assertThat(msg).isNotBlank();
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "http#serverErrorMessage", repeat = 10)
    void serverErrorMessage(String msg) {
        assertThat(msg).isNotBlank();
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "http#statusCodeWithReason", repeat = 10)
    void statusCodeWithReason(String entry) {
        assertThat(entry).matches("\\d{3} .+");
        assertThat(Integer.parseInt(entry.substring(0, 3))).isBetween(100, 599);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "http#userAgent", repeat = 10)
    void userAgent(String ua) {
        assertThat(ua).isNotBlank().startsWith("Mozilla/");
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "http#mobileUserAgent", repeat = 10)
    void mobileUserAgent(String ua) {
        assertThat(ua).isNotBlank().contains("Mobile");
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "http#userAgent(Http.Browser)", repeat = 1)
    void userAgentForAllBrowsers(String ua) {
        assertThat(ua).isNotBlank().startsWith("Mozilla/");
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "http#responseBody", repeat = 20)
    void responseBodyNoArg(String body) {
        assertThat(body).isNotBlank();
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
        assertThat(http.responseBody("application/unknown")).isNotBlank().startsWith("{");
    }

}

