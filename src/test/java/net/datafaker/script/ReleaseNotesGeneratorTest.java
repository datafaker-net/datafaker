package net.datafaker.script;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReleaseNotesGeneratorTest {

    private static final List<String> TEMPLATE = List.of(
        "# Datafaker X.Y.Z (dd-mm-yyyy)",
        "",
        ReleaseNotesGenerator.WHATS_CHANGED,
        "",
        "## New contributors",
        "",
        "* TBD"
    );

    @Test
    void generateReplacesPlaceholdersAndInsertsBody() {
        String result = ReleaseNotesGenerator.generate(
            TEMPLATE,
            "2.7.0",
            "18-06-2026",
            List.of("* Fix bug", "* Add feature")
        );

        assertTrue(result.startsWith("# Datafaker 2.7.0 (18-06-2026)\n"));
        assertTrue(result.contains("""
            ## What's changed

            * Fix bug
            * Add feature

            ## New contributors
            """));
    }

    @Test
    void generateStripsIssueHashFromBullets() {
        String result = ReleaseNotesGenerator.generate(
            TEMPLATE,
            "2.7.0",
            "18-06-2026",
            List.of("* #1756 generate airport codes", "- #42 answer")
        );

        assertTrue(result.contains("* 1756 generate airport codes"));
        assertTrue(result.contains("- 42 answer"));
    }

    @Test
    void generateLeavesNonMatchingLinesUntouched() {
        String result = ReleaseNotesGenerator.generate(
            TEMPLATE,
            "2.7.0",
            "18-06-2026",
            List.of("## Not a bullet", "* no hash here")
        );

        assertTrue(result.contains("## Not a bullet"));
        assertTrue(result.contains("* no hash here"));
    }

    @Test
    void generateStripsDuplicateWhatsChangedHeading() {
        String result = ReleaseNotesGenerator.generate(
            TEMPLATE,
            "2.7.0",
            "24-06-2026",
            List.of(
                "## What's Changed",
                "",
                "* Fix bug",
                "* Add feature"
            )
        );

        assertTrue(result.contains("""
            ## What's changed

            * Fix bug
            * Add feature
            """));
        assertFalse(result.contains("What's Changed"));
    }

    @Test
    void generateStripsLeadingAndTrailingBlankLinesFromBody() {
        String result = ReleaseNotesGenerator.generate(
            TEMPLATE,
            "2.7.0",
            "24-06-2026",
            List.of(
                "",
                "## What's Changed",
                "",
                "",
                "* Fix bug",
                ""
            )
        );

        assertTrue(result.contains("""
            ## What's changed

            * Fix bug

            ## New contributors
            """));
    }

    @Test
    void generateFromStringBody() {
        String result = ReleaseNotesGenerator.generate(
            String.join("\n", TEMPLATE),
            "2.7.0",
            "18-06-2026",
            "* one\n* two"
        );

        assertEquals("""
            # Datafaker 2.7.0 (18-06-2026)

            ## What's changed

            * one
            * two

            ## New contributors

            * TBD
            """, result);
    }
}
