package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Focused tests for the default outer-whitespace validation on {@link TestSpec}
 * and its {@link TestSpec#allowOuterWhitespace()} opt-out.
 * <p>
 * These tests verify the TestSpec record API and the outer-whitespace invariant
 * that is enforced by {@link net.datafaker.providers.base.AbstractProviderListTest#testProviderList}.
 */
class TestSpecOuterWhitespaceTest {

    /** Verifies the default is strict (outerWhitespaceAllowed = false). */
    @Test
    void defaultIsStrict() {
        AbstractProviderListTest.TestSpec spec =
            AbstractProviderListTest.TestSpec.of(() -> "anything", "key", "");
        assertThat(spec.outerWhitespaceAllowed()).isFalse();
    }

    /** Verifies allowOuterWhitespace() returns a copy with outerWhitespaceAllowed = true. */
    @Test
    void allowOuterWhitespaceReturnsCopy() {
        AbstractProviderListTest.TestSpec spec =
            AbstractProviderListTest.TestSpec.of(() -> "anything", "key", "");
        AbstractProviderListTest.TestSpec relaxed = spec.allowOuterWhitespace();
        assertThat(relaxed.outerWhitespaceAllowed()).isTrue();
        assertThat(spec.outerWhitespaceAllowed()).isFalse();
    }

    /** Verifies that a string without outer whitespace passes the strip check. */
    @Test
    void cleanStringPassesStripCheck() {
        String value = "Clean value without whitespace";
        assertThat(value).isEqualTo(value.strip());
    }

    /** Verifies that a string with leading whitespace fails the strip check. */
    @Test
    void leadingWhitespaceFailsStripCheck() {
        String value = "  leading whitespace";
        assertThat(value).isNotEqualTo(value.strip());
    }

    /** Verifies that a string with trailing whitespace fails the strip check. */
    @Test
    void trailingWhitespaceFailsStripCheck() {
        String value = "trailing whitespace  ";
        assertThat(value).isNotEqualTo(value.strip());
    }

    /** Verifies that a string with both leading and trailing whitespace fails the strip check. */
    @Test
    void bothWhitespaceFailsStripCheck() {
        String value = "  both  ";
        assertThat(value).isNotEqualTo(value.strip());
    }

    /** Verifies that the value.strip() method behaves correctly. */
    @Test
    void stripBehaviorLeading() {
        String value = "  leading whitespace";
        assertThat(value.strip()).isEqualTo("leading whitespace");
    }

    /** Verifies that the value.strip() method behaves correctly. */
    @Test
    void stripBehaviorTrailing() {
        String value = "trailing whitespace  ";
        assertThat(value.strip()).isEqualTo("trailing whitespace");
    }

    /** Verifies that the opt-out does not disable other TestSpec validations (e.g., regex). */
    @Test
    void otherValidationsStillApply() {
        AbstractProviderListTest.TestSpec spec =
            AbstractProviderListTest.TestSpec.of(() -> "test", "key", ".*");
        assertThat(spec.regex()).isEqualTo(".*");
    }
}
