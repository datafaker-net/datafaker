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
 *       {@code docs/releases/{NEXT_VERSION}-SNAPSHOT.md} (repo only; excluded from the
 *       published site; {@code {RELEASED_VERSION}-SNAPSHOT.md} is removed when that version
 *       ships)</li>
 *   <li>updated {@code extra.datafaker.version} and {@code extra.datafaker.snapshot} in
 *       {@code mkdocs.yml}</li>
 * </ul>
 *
 * <p>Expects grouped Releases nav ({@code 2.x}, {@code 1.x}, …) with the latest stable release
 * at the top level. Major-version groups (e.g. {@code 3.x}) are created automatically when the
 * first release in that series is folded out of the top-level nav. Empty group headers must not
 * appear in {@code mkdocs.yml}; MkDocs rejects them as invalid nav. Snapshot release notes are
 * kept in {@code docs/releases/} for the workflow but are not linked in nav or published to the
 * site (see {@code exclude_docs} in {@code mkdocs.yml}).
 *
 * <p>Usage: {@code MkdocsReleaseNavUpdater <released> <nextSnapshot> <mkdocs.yml>}
 */
public final class MkdocsReleaseNavUpdater {

    private static final Pattern RELEASES_HEADER = Pattern.compile("^  - Releases:\\s*$");
    private static final Pattern NEXT_TOP_LEVEL_NAV = Pattern.compile("^  - \\S");
    private static final Pattern GROUP_MAJOR_X = Pattern.compile("^    - (\\d+)\\.x:\\s*$");
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
                if (!containsLine(lines, topLevelEntry(releasedVersion))) {
                    out.add(topLevelEntry(releasedVersion));
                }
                continue;
            }
            if (inReleases && NEXT_TOP_LEVEL_NAV.matcher(line).matches()) {
                inReleases = false;
            }
            if (inReleases && (line.equals(topLevelEntry(oldSnapshot)) || TOP_SNAPSHOT.matcher(line).matches())) {
                continue;
            }
            if (previousStable != null && line.equals(topLevelEntry(previousStable))) {
                continue;
            }
            out.add(line);
        }
        if (previousStable != null) {
            ensurePreviousStableGrouped(out, previousStable);
        }
        return out;
    }

    private static void ensurePreviousStableGrouped(List<String> out, String previousStable) {
        if (containsInGroup(out, previousStable)) {
            return;
        }
        int major = majorVersion(previousStable);
        String header = majorGroupHeader(major);
        if (!out.contains(header)) {
            out.add(findGroupInsertIndex(out, major), header);
        }
        int headerIndex = out.indexOf(header);
        out.add(headerIndex + 1, inGroupEntry(previousStable));
    }

    private static int findGroupInsertIndex(List<String> out, int major) {
        boolean inReleases = false;
        for (int i = 0; i < out.size(); i++) {
            String line = out.get(i);
            if (RELEASES_HEADER.matcher(line).matches()) {
                inReleases = true;
                continue;
            }
            if (inReleases && NEXT_TOP_LEVEL_NAV.matcher(line).matches()) {
                return i;
            }
            if (inReleases) {
                Matcher groupMatcher = GROUP_MAJOR_X.matcher(line);
                if (groupMatcher.matches()) {
                    int groupMajor = Integer.parseInt(groupMatcher.group(1));
                    if (groupMajor < major) {
                        return i;
                    }
                }
            }
        }
        for (int i = out.size() - 1; i >= 0; i--) {
            if (RELEASES_HEADER.matcher(out.get(i)).matches()) {
                return i + 1;
            }
        }
        return out.size();
    }

    private static String majorGroupHeader(int major) {
        return "    - " + major + ".x:";
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
            if (inReleases && GROUP_MAJOR_X.matcher(line).matches()) {
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

    private static int majorVersion(String version) {
        return Integer.parseInt(version.substring(0, version.indexOf('.')));
    }

    private static boolean containsLine(List<String> lines, String entry) {
        return lines.stream().anyMatch(entry::equals);
    }

    private static boolean containsInGroup(List<String> lines, String version) {
        return lines.stream().anyMatch(line -> line.equals(inGroupEntry(version)));
    }

    private static String topLevelEntry(String version) {
        return "    - " + version + ": releases/" + version + ".md";
    }

    private static String inGroupEntry(String version) {
        return "      - " + version + ": releases/" + version + ".md";
    }
}
