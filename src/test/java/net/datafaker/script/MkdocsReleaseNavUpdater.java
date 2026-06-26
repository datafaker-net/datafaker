package net.datafaker.script;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Updates the Releases navigation in {@code mkdocs.yml} as part of the post-release workflow.
 *
 * <p>Invoked from {@code .github/workflows/version-updates.yml} via {@code mvn exec:java} after
 * the workflow has already:
 * <ul>
 *   <li>derived {@code RELEASED_VERSION} from the latest GitHub release tag</li>
 *   <li>bumped {@code pom.xml} and derived {@code NEXT_VERSION} for the next snapshot</li>
 *   <li>created {@code docs/releases/{RELEASED_VERSION}.md} and
 *       {@code docs/releases/{NEXT_VERSION}-SNAPSHOT.md}</li>
 *   <li>updated {@code extra.datafaker.version} and {@code extra.datafaker.snapshot} in
 *       {@code mkdocs.yml}</li>
 * </ul>
 *
 * <p>Expects {@code mkdocs.yml} to use the grouped Releases nav layout ({@code 2.x} section,
 * top-level snapshot + latest stable entries). Nav links point at {@code releases/{version}.md}
 * files that the workflow creates in the same run.
 *
 * <p>Usage: {@code MkdocsReleaseNavUpdater <released> <nextSnapshot> <mkdocs.yml>}
 */
public final class MkdocsReleaseNavUpdater {

    private static final Pattern RELEASES_HEADER = Pattern.compile("^  - Releases:\\s*$");
    private static final Pattern NEXT_TOP_LEVEL_NAV = Pattern.compile("^  - \\S");
    private static final Pattern GROUP_2_X = Pattern.compile("^    - 2\\.x:\\s*$");
    private static final Pattern TOP_SNAPSHOT = Pattern.compile("^    - \\d+\\.\\d+\\.\\d+-SNAPSHOT:");
    private static final Pattern TOP_STABLE = Pattern.compile("^    - \\d+\\.\\d+\\.\\d+:");
    private static final Pattern STABLE_VERSION = Pattern.compile("^    - (\\d+\\.\\d+\\.\\d+):");

    private MkdocsReleaseNavUpdater() {
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.err.println("Usage: MkdocsReleaseNavUpdater <released> <nextSnapshot> <mkdocs.yml>");
            System.exit(1);
        }
        Path path = Path.of(args[2]);
        List<String> updated = updateNav(Files.readAllLines(path), args[0], args[1]);
        Files.writeString(path, String.join("\n", updated) + "\n");
    }

    static List<String> updateNav(List<String> lines, String releasedVersion, String nextSnapshot) {
        String oldSnapshot = releasedVersion + "-SNAPSHOT";
        String previousStable = findPreviousStable(lines, releasedVersion);

        List<String> out = new ArrayList<>(lines.size() + 2);
        boolean inReleases = false;
        for (String line : lines) {
            if (RELEASES_HEADER.matcher(line).matches()) {
                out.add(line);
                inReleases = true;
                if (!containsLine(lines, topLevelEntry(nextSnapshot))) {
                    out.add(topLevelEntry(nextSnapshot));
                }
                if (!containsLine(lines, topLevelEntry(releasedVersion))) {
                    out.add(topLevelEntry(releasedVersion));
                }
                continue;
            }
            if (inReleases && NEXT_TOP_LEVEL_NAV.matcher(line).matches()) {
                inReleases = false;
            }
            if (inReleases && line.equals(topLevelEntry(oldSnapshot))) {
                continue;
            }
            if (previousStable != null && line.equals(topLevelEntry(previousStable))) {
                continue;
            }
            out.add(line);
            if (previousStable != null
                && GROUP_2_X.matcher(line).matches()
                && !containsLine(lines, in2xEntry(previousStable))) {
                out.add(in2xEntry(previousStable));
            }
        }
        return out;
    }

    private static String findPreviousStable(List<String> lines, String releasedVersion) {
        boolean inReleases = false;
        for (String line : lines) {
            if (RELEASES_HEADER.matcher(line).matches()) {
                inReleases = true;
                continue;
            }
            if (inReleases && NEXT_TOP_LEVEL_NAV.matcher(line).matches()) {
                break;
            }
            if (inReleases && GROUP_2_X.matcher(line).matches()) {
                break;
            }
            if (inReleases && TOP_SNAPSHOT.matcher(line).matches()) {
                continue;
            }
            if (inReleases && TOP_STABLE.matcher(line).lookingAt() && !line.contains("-SNAPSHOT")) {
                Matcher matcher = STABLE_VERSION.matcher(line);
                if (matcher.find()) {
                    String version = matcher.group(1);
                    if (!version.equals(releasedVersion)) {
                        return version;
                    }
                }
            }
        }
        return null;
    }

    private static boolean containsLine(List<String> lines, String entry) {
        return lines.stream().anyMatch(entry::equals);
    }

    private static String topLevelEntry(String version) {
        return "    - " + version + ": releases/" + version + ".md";
    }

    private static String in2xEntry(String version) {
        return "      - " + version + ": releases/" + version + ".md";
    }
}
