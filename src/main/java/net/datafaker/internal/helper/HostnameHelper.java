package net.datafaker.internal.helper;

import java.net.IDN;
import java.util.Locale;

/**
 * Helper for creating valid hostnames according to RFC 1123 (Letter-Digit-Hyphen rules).
 * <p>
 * This class provides methods to sanitize strings into valid DNS hostnames that conform to:
 * - RFC 1035: Original DNS specification
 * - RFC 1123: Relaxed hostname rules (allows digits at start of label)
 * <p>
 * Hostname rules per RFC 1123:
 * - Each label (segment between dots) is 1-63 characters
 * - Total hostname including dots must be ≤253 characters
 * - Labels contain only letters, digits, and hyphens (LDH)
 * - Labels cannot start or end with hyphens
 * - Labels cannot be purely numeric
 *
 * @since 3.0.0
 */
public class HostnameHelper {
    private static final int MAX_LABEL_LENGTH = 63;
    private static final int MAX_HOSTNAME_LENGTH = 253;

    /**
     * Converts a string to a valid ASCII hostname label, sanitizing per RFC 1123 LDH rules.
     * <p>
     * Process:
     * 1. Apply IDN.toASCII() for non-ASCII input (converts to Punycode if needed)
     * 2. Convert to lowercase
     * 3. Keep only [a-z0-9-]
     * 4. Collapse consecutive hyphens (except preserve "xn--" Punycode prefix)
     * 5. Strip leading/trailing hyphens
     * 6. Enforce length ≤ 63 characters
     * 7. Fall back to "example" if result is empty
     * <p>
     * The fallback value "example" is an IETF-reserved special-use domain name (RFC 6761)
     * intended for documentation and examples.
     *
     * @param input the string to sanitize
     * @param locale the locale for lowercase conversion (e.g., Locale.ROOT)
     * @return a valid ASCII hostname label (never null or empty)
     */
    public static String toAsciiHostnameLabel(String input, Locale locale) {
        if (input == null || input.isEmpty()) {
            return "example";
        }

        // 1. Try IDN conversion for Unicode input (converts to Punycode if needed)
        String result = input;
        try {
            result = IDN.toASCII(input);
        } catch (IllegalArgumentException ignore) {
            // Not a valid IDN label; proceed with raw input
        }

        // 2. Lowercase the input
        result = result.toLowerCase(locale);

        // 3. Keep only [a-z0-9-]
        StringBuilder sanitized = new StringBuilder();
        for (char c : result.toCharArray()) {
            if ((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == '-') {
                sanitized.append(c);
            }
        }

        result = sanitized.toString();

        // 4. Collapse consecutive hyphens (preserve "xn--" Punycode prefix)
        if (!result.startsWith("xn--")) {
            while (result.contains("--")) {
                result = result.replace("--", "-");
            }
        }

        // 5. Strip leading/trailing hyphens
        result = result.replaceAll("^-+|-+$", "");

        // 6. Enforce max label length
        if (result.length() > MAX_LABEL_LENGTH) {
            result = result.substring(0, MAX_LABEL_LENGTH);
            result = result.replaceAll("-+$", "");
        }

        // 7. Return valid result or fallback
        if (result.isEmpty()) {
            return "example";
        }
        return result;
    }

    /**
     * Sanitizes each label of a hostname independently and recombines.
     * <p>
     * If input contains dots, each segment is sanitized separately, then recombined.
     * This is useful for domain names where each label has independent rules.
     * <p>
     * Fallback behavior:
     * - Null or empty input returns "example.com"
     * - Result that becomes empty after sanitization returns "example.com"
     * <p>
     * The fallback values are IETF-reserved special-use domain names (RFC 6761)
     * intended for documentation and examples.
     *
     * @param input the hostname/domain to sanitize (may contain dots)
     * @param locale the locale for lowercase conversion
     * @return a valid ASCII hostname (never null or empty)
     */
    public static String toAsciiHostname(String input, Locale locale) {
        if (input == null || input.isEmpty()) {
            return "example.com";
        }

        String[] labels = input.split("\\.", -1);
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < labels.length; i++) {
            if (i > 0) {
                result.append(".");
            }
            result.append(toAsciiHostnameLabel(labels[i], locale));
        }

        String hostname = result.toString();

        // Enforce total length
        if (hostname.length() > MAX_HOSTNAME_LENGTH) {
            // Truncate from the end, preserving the TLD
            hostname = hostname.substring(0, MAX_HOSTNAME_LENGTH);
            hostname = hostname.replaceAll("\\.$", "");
        }

        if (hostname.isEmpty()) {
            return "example.com";
        }
        return hostname;
    }
}
