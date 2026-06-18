package net.datafaker.script;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Builds a release notes markdown file for the post-release workflow.
 *
 * <p>Invoked from {@code .github/workflows/version-updates.yml} via {@code mvn exec:java}.
 * The workflow supplies:
 * <ul>
 *   <li>{@code RELEASED_VERSION} — from the latest GitHub release tag</li>
 *   <li>{@code TODAY} — current date in {@code dd-mm-yyyy} format</li>
 *   <li>{@code docs/releases/.RELEASE-template.md} — placeholders {@code X.Y.Z} and
 *       {@code dd-mm-yyyy}, plus a {@code ## What's changed} heading where the body is inserted</li>
 *   <li>a temp file containing the GitHub release body ({@code gh release view ... --json body})</li>
 *   <li>output path {@code docs/releases/{RELEASED_VERSION}.md} (must not already exist)</li>
 * </ul>
 *
 * <p>Strips {@code #} from bullet lines like {@code * #1234 ...} so issue numbers are not
 * treated as markdown headings.
 *
 * <p>Usage: {@code ReleaseNotesGenerator <version> <date> <template> <output> <body>}
 */
public final class ReleaseNotesGenerator {

    static final String WHATS_CHANGED = "## What's changed";
    private static final Pattern ISSUE_HASH_BULLET = Pattern.compile("^(\\s*[-*]\\s*)#(\\d+)");

    private ReleaseNotesGenerator() {
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 5) {
            System.err.println("Usage: ReleaseNotesGenerator <version> <date> <template> <output> <body>");
            System.exit(1);
        }
        String template = Files.readString(Path.of(args[2]));
        String body = Files.readString(Path.of(args[4]));
        Files.writeString(Path.of(args[3]), generate(template, args[0], args[1], body));
    }

    static String generate(String template, String version, String date, String releaseBody) {
        List<String> bodyLines = releaseBody.isEmpty() ? List.of() : List.of(releaseBody.split("\n", -1));
        return generate(List.of(template.split("\n", -1)), version, date, bodyLines);
    }

    static String generate(List<String> templateLines, String version, String date, List<String> bodyLines) {
        List<String> out = new ArrayList<>(templateLines.size() + bodyLines.size());
        boolean inserted = false;
        for (String line : templateLines) {
            out.add(line.replace("X.Y.Z", version).replace("dd-mm-yyyy", date));
            if (!inserted && WHATS_CHANGED.equals(out.get(out.size() - 1))) {
                out.add("");
                for (String bodyLine : bodyLines) {
                    out.add(stripIssueHash(bodyLine));
                }
                inserted = true;
            }
        }
        return String.join("\n", out) + "\n";
    }

    static String stripIssueHash(String line) {
        Matcher matcher = ISSUE_HASH_BULLET.matcher(line);
        return matcher.find() ? matcher.replaceFirst("$1$2") : line;
    }
}
