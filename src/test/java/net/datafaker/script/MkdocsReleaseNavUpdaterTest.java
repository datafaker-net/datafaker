package net.datafaker.script;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Behaviour tests for {@link MkdocsReleaseNavUpdater}. Version strings here are fictional
 * fixtures — they do not track the project's current release line and should not need updating
 * when the repo moves to a new major or snapshot.
 */
class MkdocsReleaseNavUpdaterTest {

    @Test
    void addsReleasedVersionAtTopAndRemovesSnapshots() {
        List<String> nav = releasesNav(
            "    - 1.3.0-SNAPSHOT: releases/1.3.0-SNAPSHOT.md",
            "    - 1.2.0: releases/1.2.0.md",
            "    - 2.x:",
            "    - 1.x:",
            "      - 1.1.0: releases/1.1.0.md"
        );

        List<String> updated = MkdocsReleaseNavUpdater.updateNav(nav, "1.3.0", "1.4.0-SNAPSHOT");

        assertFirstReleaseEntry(updated, "1.3.0");
        assertNoSnapshots(updated);
        assertGroupedUnder(updated, "1.2.0", "1.x");
        assertGroupedUnder(updated, "1.1.0", "1.x");
        assertFalse(hasTopLevelEntry(updated, "1.2.0"));
    }

    @Test
    void updateNavIsIdempotentWhenEntriesExist() {
        List<String> nav = releasesNav(
            "    - 1.3.0-SNAPSHOT: releases/1.3.0-SNAPSHOT.md",
            "    - 1.2.0: releases/1.2.0.md",
            "    - 2.x:",
            "    - 1.x:",
            "      - 1.1.0: releases/1.1.0.md"
        );

        List<String> current = MkdocsReleaseNavUpdater.updateNav(nav, "1.3.0", "1.4.0-SNAPSHOT");
        List<String> again = MkdocsReleaseNavUpdater.updateNav(current, "1.3.0", "1.4.0-SNAPSHOT");

        assertEquals(current, again);
    }

    @Test
    void handlesReleaseWhenOnlyGroupedStablesExist() {
        List<String> nav = List.of(
            "  - Releases:",
            "    - 1.3.0-SNAPSHOT: releases/1.3.0-SNAPSHOT.md",
            "    - 2.x:",
            "    - 1.x:",
            "      - 1.1.0: releases/1.1.0.md"
        );

        List<String> updated = MkdocsReleaseNavUpdater.updateNav(nav, "1.3.0", "1.4.0-SNAPSHOT");

        assertFirstReleaseEntry(updated, "1.3.0");
        assertNoSnapshots(updated);
        assertGroupedUnder(updated, "1.1.0", "1.x");
    }

    @Test
    void movesPreviousStableIntoMatchingMajorGroupOnNewMajorRelease() {
        List<String> nav = releasesNav(
            "    - 1.4.0: releases/1.4.0.md",
            "    - 2.x:",
            "    - 1.x:",
            "      - 1.3.0: releases/1.3.0.md"
        );

        List<String> updated = MkdocsReleaseNavUpdater.updateNav(nav, "2.0.0", "2.1.0-SNAPSHOT");

        assertFirstReleaseEntry(updated, "2.0.0");
        assertGroupedUnder(updated, "1.4.0", "1.x");
        assertGroupedUnder(updated, "1.3.0", "1.x");
        assertFalse(hasTopLevelEntry(updated, "1.4.0"));
    }

    @Test
    void movesPreviousStableIntoSameMajorGroupOnMinorRelease() {
        List<String> nav = releasesNav(
            "    - 2.0.0: releases/2.0.0.md",
            "    - 2.x:",
            "    - 1.x:",
            "      - 1.4.0: releases/1.4.0.md"
        );

        List<String> updated = MkdocsReleaseNavUpdater.updateNav(nav, "2.1.0", "2.2.0-SNAPSHOT");

        assertFirstReleaseEntry(updated, "2.1.0");
        assertGroupedUnder(updated, "2.0.0", "2.x");
        assertFalse(hasTopLevelEntry(updated, "2.0.0"));
    }

    @Test
    void createsMajorGroupHeaderWhenMissing() {
        List<String> nav = releasesNav(
            "    - 3.0.0: releases/3.0.0.md",
            "    - 2.x:",
            "      - 2.7.0: releases/2.7.0.md"
        );

        List<String> updated = MkdocsReleaseNavUpdater.updateNav(nav, "3.1.0", "3.2.0-SNAPSHOT");

        assertFirstReleaseEntry(updated, "3.1.0");
        assertTrue(updated.contains("    - 3.x:"));
        assertGroupedUnder(updated, "3.0.0", "3.x");
    }

    private static List<String> releasesNav(String... releaseLines) {
        List<String> nav = new ArrayList<>();
        nav.add("nav:");
        nav.add("  - Releases:");
        nav.addAll(List.of(releaseLines));
        nav.add("  - In the news: in-the-media/links.md");
        return nav;
    }

    private static void assertFirstReleaseEntry(List<String> nav, String version) {
        int releasesIndex = nav.indexOf("  - Releases:");
        assertTrue(releasesIndex >= 0);
        assertEquals("    - " + version + ": releases/" + version + ".md", nav.get(releasesIndex + 1));
    }

    private static void assertNoSnapshots(List<String> nav) {
        assertFalse(nav.stream().anyMatch(line -> line.contains("-SNAPSHOT")));
    }

    private static void assertGroupedUnder(List<String> nav, String version, String group) {
        int groupIndex = nav.indexOf("    - " + group + ":");
        assertTrue(groupIndex >= 0, () -> "missing group " + group);
        assertTrue(
            nav.subList(groupIndex + 1, nav.size()).stream()
                .anyMatch(line -> line.equals("      - " + version + ": releases/" + version + ".md")),
            () -> version + " not grouped under " + group
        );
    }

    private static boolean hasTopLevelEntry(List<String> nav, String version) {
        return nav.contains("    - " + version + ": releases/" + version + ".md");
    }
}
