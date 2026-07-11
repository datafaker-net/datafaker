package net.datafaker.script;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MkdocsReleaseNavUpdaterTest {

    private static final List<String> SAMPLE = List.of(
        "nav:",
        "  - Releases:",
        "    - 2.7.0-SNAPSHOT: releases/2.7.0-SNAPSHOT.md",
        "    - 2.6.0: releases/2.6.0.md",
        "    - 2.x:",
        "      - 2.5.4: releases/2.5.4.md",
        "  - In the news: in-the-media/links.md"
    );

    @Test
    void updateNavOnRelease() {
        List<String> updated = MkdocsReleaseNavUpdater.updateNav(SAMPLE, "2.7.0", "2.8.0-SNAPSHOT");

        assertEquals(List.of(
            "nav:",
            "  - Releases:",
            "    - 2.7.0: releases/2.7.0.md",
            "    - 2.x:",
            "      - 2.6.0: releases/2.6.0.md",
            "      - 2.5.4: releases/2.5.4.md",
            "  - In the news: in-the-media/links.md"
        ), updated);
        assertFalse(updated.contains("    - 2.7.0-SNAPSHOT: releases/2.7.0-SNAPSHOT.md"));
        assertFalse(updated.contains("    - 2.8.0-SNAPSHOT: releases/2.8.0-SNAPSHOT.md"));
    }

    @Test
    void updateNavIsIdempotentWhenEntriesExist() {
        List<String> current = MkdocsReleaseNavUpdater.updateNav(SAMPLE, "2.7.0", "2.8.0-SNAPSHOT");
        List<String> again = MkdocsReleaseNavUpdater.updateNav(current, "2.7.0", "2.8.0-SNAPSHOT");

        assertEquals(current, again);
    }

    @Test
    void updateNavWhenNoPreviousStableAtTopLevel() {
        List<String> nav = List.of(
            "  - Releases:",
            "    - 2.7.0-SNAPSHOT: releases/2.7.0-SNAPSHOT.md",
            "    - 2.x:",
            "      - 2.5.4: releases/2.5.4.md"
        );

        List<String> updated = MkdocsReleaseNavUpdater.updateNav(nav, "2.7.0", "2.8.0-SNAPSHOT");

        assertTrue(updated.contains("    - 2.7.0: releases/2.7.0.md"));
        assertFalse(updated.contains("    - 2.7.0-SNAPSHOT: releases/2.7.0-SNAPSHOT.md"));
        assertFalse(updated.contains("    - 2.8.0-SNAPSHOT: releases/2.8.0-SNAPSHOT.md"));
        assertEquals("      - 2.5.4: releases/2.5.4.md", updated.get(updated.indexOf("    - 2.x:") + 1));
    }
}
