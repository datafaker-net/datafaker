package net.datafaker.internal.helper;

import java.net.IDN;
import java.util.Locale;
import org.jspecify.annotations.Nullable;

/**
 * Helper for converting domain names to ASCII using Punycode (IDN).
 * <p>
 * This class wraps {@link IDN#toASCII(String)} with fallback handling for edge cases
 * (e.g., bidirectional text in Farsi and Hebrew). The output is guaranteed to be a
 * valid RFC 1123 hostname (LDH-compliant per RFC 1035 / RFC 1123).
 * <p>
 * Inline hostname sanitization ensures all output conforms to RFC 1123 LDH rules:
 * - Each label 1-63 characters, total ≤253 characters
 * - Labels contain only letters, digits, hyphens
 * - Labels cannot start/end with hyphens
 * - Falls back to "example"/"example.com" (RFC 6761) if unsanitizable
 * <p>
 * See RFC 3490 (IDNA), RFC 3491 (Punycode), RFC 1123, and RFC 6761.
 *
 * @since 1.0.0
 */
public class FakerIDN {
    private static final int MAX_LABEL_LENGTH = 63;
    private static final int MAX_HOSTNAME_LENGTH = 253;
    /**
     * Converts a string to an ASCII hostname, applying Punycode (IDN) encoding where needed.
     * Never throws; returns valid RFC 1123 hostname or "example" fallback.
     *
     * @param input the domain name to convert
     * @return valid ASCII hostname (LDH-compliant per RFC 1123, never null or empty)
     */
    public static String toASCII(@Nullable String input) {
        if (input == null || input.isEmpty()) {
            return "example";
        }

        @Nullable String asciiResult = tryFullConversion(input);
        if (asciiResult == null) {
            asciiResult = tryCharacterByCharacter(input);
        }

        if (asciiResult == null || asciiResult.isEmpty()) {
            asciiResult = "example";
        }

        // Sanitize to RFC 1123: lowercase, [a-z0-9-] only, no leading/trailing hyphens
        return sanitizeHostname(asciiResult);
    }

    private static String sanitizeHostname(String input) {
        String[] labels = input.split("\\.", -1);
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < labels.length; i++) {
            if (i > 0) result.append(".");
            result.append(sanitizeLabel(labels[i]));
        }

        String hostname = result.toString();
        if (hostname.length() > MAX_HOSTNAME_LENGTH) {
            hostname = hostname.substring(0, MAX_HOSTNAME_LENGTH).replaceAll("\\.$", "");
        }
        return hostname.isEmpty() ? "example.com" : hostname;
    }

    private static String sanitizeLabel(String input) {
        String result = input.toLowerCase(Locale.ROOT);
        StringBuilder sanitized = new StringBuilder();
        for (char c : result.toCharArray()) {
            if ((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == '-') {
                sanitized.append(c);
            }
        }
        result = sanitized.toString();
        // Preserve xn-- (Punycode prefix) but collapse other consecutive hyphens
        if (!result.startsWith("xn--")) {
            while (result.contains("--")) result = result.replace("--", "-");
        }
        result = result.replaceAll("^-+|-+$", "");
        if (result.length() > MAX_LABEL_LENGTH) {
            result = result.substring(0, MAX_LABEL_LENGTH).replaceAll("-+$", "");
        }
        return result.isEmpty() ? "example" : result;
    }

    @Nullable
    private static String tryFullConversion(String input) {
        try {
            return IDN.toASCII(input);
        } catch (IllegalArgumentException ignore) {
            return null;
        }
    }

    @Nullable
    private static String tryCharacterByCharacter(String input) {
        final StringBuilder result = new StringBuilder(input.length());
        for (int i = 0; i < input.length(); i++) {
            try {
                result.append(IDN.toASCII(input.substring(i, i + 1)));
            } catch (IllegalArgumentException ignored) {
                // Skip characters that cannot be converted
            }
        }
        return result.length() > 0 ? result.toString() : null;
    }
}
