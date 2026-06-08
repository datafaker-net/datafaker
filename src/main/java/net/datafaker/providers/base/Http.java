package net.datafaker.providers.base;

import java.util.List;

/**
 * Faker for HTTP-related data: status codes and reason phrases, request/response headers,
 * content types, user agents, HTTP methods and versions, transfer encodings, and response bodies.
 *
 * @since 2.4.0
 */
public class Http extends AbstractProvider<BaseProviders> {

    private static final List<String> ALL_STATUS_CATEGORIES = List.of(
        "informational", "successful", "redirect", "client_error", "server_error"
    );

    protected Http(BaseProviders faker) {
        super(faker);
    }

    // -------------------------------------------------------------------------
    // Status codes — return int
    // -------------------------------------------------------------------------

    /** Returns a random valid HTTP status code from any category (1xx–5xx). */
    public int statusCode() {
        return parseCode(randomStatusEntry());
    }

    /** Returns a random 1xx informational status code. */
    public int informational() {
        return parseCode(resolve("http.status_code.informational"));
    }

    /** Returns a random 2xx successful status code. */
    public int successful() {
        return parseCode(resolve("http.status_code.successful"));
    }

    /** Returns a random 3xx redirect status code. */
    public int redirect() {
        return parseCode(resolve("http.status_code.redirect"));
    }

    /** Returns a random 4xx client-error status code. */
    public int clientError() {
        return parseCode(resolve("http.status_code.client_error"));
    }

    /** Returns a random 5xx server-error status code. */
    public int serverError() {
        return parseCode(resolve("http.status_code.server_error"));
    }

    // -------------------------------------------------------------------------
    // Status reason phrases — return String
    // -------------------------------------------------------------------------

    /** Returns the reason phrase for a random HTTP status code (e.g. {@code "OK"}, {@code "Not Found"}). */
    public String statusMessage() {
        return parseReason(randomStatusEntry());
    }

    /** Returns the reason phrase for a random 1xx status code. */
    public String informationalMessage() {
        return parseReason(resolve("http.status_code.informational"));
    }

    /** Returns the reason phrase for a random 2xx status code. */
    public String successfulMessage() {
        return parseReason(resolve("http.status_code.successful"));
    }

    /** Returns the reason phrase for a random 3xx status code. */
    public String redirectMessage() {
        return parseReason(resolve("http.status_code.redirect"));
    }

    /** Returns the reason phrase for a random 4xx status code. */
    public String clientErrorMessage() {
        return parseReason(resolve("http.status_code.client_error"));
    }

    /** Returns the reason phrase for a random 5xx status code. */
    public String serverErrorMessage() {
        return parseReason(resolve("http.status_code.server_error"));
    }

    // -------------------------------------------------------------------------
    // Combined code + reason — return String like "200 OK"
    // -------------------------------------------------------------------------

    /** Returns a random status code and reason phrase, e.g. {@code "200 OK"} or {@code "404 Not Found"}. */
    public String statusCodeWithReason() {
        return randomStatusEntry();
    }

    // -------------------------------------------------------------------------
    // Headers
    // -------------------------------------------------------------------------

    /** Returns a random HTTP request header name (e.g. {@code "Content-Type"}, {@code "Authorization"}). */
    public String requestHeader() {
        return resolve("http.request_header");
    }

    /** Returns a random HTTP response header name (e.g. {@code "ETag"}, {@code "Cache-Control"}). */
    public String responseHeader() {
        return resolve("http.response_header");
    }

    // -------------------------------------------------------------------------
    // Content type / MIME
    // -------------------------------------------------------------------------

    /** Returns a random MIME content type (e.g. {@code "application/json"}, {@code "text/html; charset=utf-8"}). */
    public String contentType() {
        return resolve("http.content_type");
    }

    // -------------------------------------------------------------------------
    // User agents
    // -------------------------------------------------------------------------

    /** Returns a random browser user agent string from any supported browser. */
    public String userAgent() {
        Browser browser = faker.options().nextElement(Browser.values());
        return resolve("http.user_agent." + browser.key);
    }

    /**
     * Returns a user agent string for the specified browser.
     *
     * @param browser the browser whose user agent to return
     */
    public String userAgent(Browser browser) {
        return resolve("http.user_agent." + browser.key);
    }

    /** Returns a random mobile device user agent string. */
    public String mobileUserAgent() {
        return resolve("http.user_agent.mobile");
    }

    // -------------------------------------------------------------------------
    // Methods, versions, encoding
    // -------------------------------------------------------------------------

    /** Returns a random HTTP method (e.g. {@code "GET"}, {@code "POST"}, {@code "DELETE"}). */
    public String httpMethod() {
        return resolve("http.http_method");
    }

    /** Returns a random HTTP version string (e.g. {@code "HTTP/1.1"}, {@code "HTTP/2"}). */
    public String httpVersion() {
        return resolve("http.http_version");
    }

    /** Returns a random transfer encoding value (e.g. {@code "gzip"}, {@code "br"}). */
    public String encoding() {
        return resolve("http.encoding");
    }

    // -------------------------------------------------------------------------
    // Response body
    // -------------------------------------------------------------------------

    /** Returns a random JSON response body string, covering both success and error scenarios. */
    public String responseBody() {
        return resolve("http.response_body");
    }

    // -------------------------------------------------------------------------
    // Internal helpers
    // -------------------------------------------------------------------------

    private String randomStatusEntry() {
        String category = faker.options().nextElement(ALL_STATUS_CATEGORIES);
        return resolve("http.status_code." + category);
    }

    private static int parseCode(String statusEntry) {
        return Integer.parseInt(statusEntry.substring(0, statusEntry.indexOf(' ')));
    }

    private static String parseReason(String statusEntry) {
        return statusEntry.substring(statusEntry.indexOf(' ') + 1);
    }

    // -------------------------------------------------------------------------
    // Enums
    // -------------------------------------------------------------------------

    public enum Browser {
        CHROME("chrome"),
        FIREFOX("firefox"),
        SAFARI("safari"),
        EDGE("edge"),
        MOBILE("mobile");

        private final String key;

        Browser(String key) {
            this.key = key;
        }
    }
}
