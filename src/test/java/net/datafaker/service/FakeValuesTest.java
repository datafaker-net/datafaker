package net.datafaker.service;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

class FakeValuesTest {

    private static final String PATH = "address";
    private final FakeValues fakeValues = FakeValues.of(FakeValuesContext.of(Locale.ENGLISH, "address.yml", PATH));

    @Test
    void getAValueReturnsAValue() {
        assertThat(fakeValues.get(PATH)).isNotNull();
    }

    @Test
    void getAValueDoesNotReturnAValue() {
        assertThat(fakeValues.get("dog")).isNull();
    }

    @Test
    void getAValueWithANonEnglishFile() {
        FakeValues frenchFakeValues = FakeValues.of(FakeValuesContext.of(Locale.FRENCH));
        assertThat(frenchFakeValues.get(PATH)).isNotNull();
    }

    @Test
    void getAValueForHebrewLocale() {
        FakeValues hebrew = FakeValues.of(FakeValuesContext.of(new Locale("iw")));
        assertThat(hebrew.get(PATH)).isNotNull();
    }

    @Test
    void correctPathForHebrewLanguage() {
        FakeValues hebrew = FakeValues.of(FakeValuesContext.of(new Locale("iw")));
        assertThat(hebrew.getPaths()).containsExactly("he");
    }

    @Test
    void incorrectPathForHebrewLanguage() {
        FakeValues hebrew = FakeValues.of(FakeValuesContext.of(new Locale("iw")));
        assertThat(hebrew.getPaths()).doesNotContain("iw");
    }

    @Test
    void correctLocale() {
        FakeValues fv = FakeValues.of(FakeValuesContext.of(new Locale("uk")));
        assertThat(fv.getLocale()).isEqualTo(new Locale("uk"));
    }

    @Test
    void getAValueFromALocaleThatCantBeLoaded() {
        FakeValues fakeValues = FakeValues.of(FakeValuesContext.of(new Locale("nothing")));
        assertThat(fakeValues.get(PATH)).isNull();
    }

    /**
     * Regression test for issue <a href="http://github.com/datafaker-net/datafaker/issues/1477">#1477</a>
     * (race condition in {@code FakeValuesService.fetchObject}).
     *
     * <p>Unqualified template references like {@code #{female_first_name}} that appear in YAML
     * lists must be rewritten to fully-qualified form like {@code #{Name.femaleFirstName}} at
     * <b>load time</b>, not lazily on first fetch. The buggy implementation rewrote them lazily,
     * after the list had already been published to the cache, which let concurrent readers see
     * un-rewritten items and fail with {@code Unable to resolve #{female_first_name}}.
     */
    @Test
    @SuppressWarnings("unchecked")
    void loadFromUrl_rewritesUnqualifiedTemplateReferencesAtLoadTime() {
        String yml = "/en/name.yml";
        URL nameYmlUrl = Objects.requireNonNull(getClass().getResource(yml), () -> "Resource not found: " + yml);
        FakeValues fakeValues = FakeValues.of(FakeValuesContext.of(Locale.ENGLISH, nameYmlUrl));

        Map<String, Object> nameMap = fakeValues.get("name");
        assertThat(nameMap).as("name provider map").isNotNull();

        List<String> firstNames = (List<String>) nameMap.get("first_name");
        assertThat(firstNames)
            .as("name.first_name entries must already be qualified after loadFromUrl()")
            .containsExactlyInAnyOrder("#{Name.femaleFirstName}", "#{Name.maleFirstName}");
    }

    @Nested
    @SuppressWarnings("unchecked")
    class PrefixUnqualifiedExpressions {
        @Test
        void rewritesSimpleUnqualifiedExpressionInList() {
            List<String> list = mutableList("#{first_name}");

            FakeValues.prefixUnqualifiedExpressions(list, "name");

            assertThat(list).containsExactly("#{Name.firstName}");
        }

        @Test
        void rewritesMultipleExpressionsInsideOneString() {
            List<String> list = mutableList("#{first_name} #{last_name}");

            FakeValues.prefixUnqualifiedExpressions(list, "name");

            assertThat(list).containsExactly("#{Name.firstName} #{Name.lastName}");
        }

        @Test
        void convertsSnakeCaseKeyToCamelCaseMethodName() {
            List<String> list = mutableList("#{some_long_yaml_key}");

            FakeValues.prefixUnqualifiedExpressions(list, "p");

            assertThat(list).containsExactly("#{P.someLongYamlKey}");
        }

        @Test
        void capitalizesProviderName() {
            List<String> list = mutableList("#{x}");

            FakeValues.prefixUnqualifiedExpressions(list, "myProvider");

            assertThat(list).containsExactly("#{MyProvider.x}");
        }

        @Test
        void leavesAlreadyQualifiedExpressionsUntouched() {
            // The dot inside the {...} disqualifies the expression from rewriting.
            List<String> list = mutableList("#{Name.firstName}");

            FakeValues.prefixUnqualifiedExpressions(list, "address");

            assertThat(list).containsExactly("#{Name.firstName}");
        }

        @Test
        void leavesExpressionsWithArgumentsUntouched() {
            // The space and quotes inside the {...} disqualify the expression from rewriting.
            List<String> list = mutableList("#{numerify '#-####'}");

            FakeValues.prefixUnqualifiedExpressions(list, "code");

            assertThat(list).containsExactly("#{numerify '#-####'}");
        }

        @Test
        void leavesPlainStringsUntouched() {
            List<String> list = mutableList("Aaron", "Bob", "Charlie");

            FakeValues.prefixUnqualifiedExpressions(list, "name");

            assertThat(list).containsExactly("Aaron", "Bob", "Charlie");
        }

        @Test
        void rewritesUnqualifiedAndPreservesQualifiedInTheSameString() {
            // Mixed expression: "#{city_prefix}" gets prefixed, "#{Name.firstName}" stays as is.
            List<String> list = mutableList("#{city_prefix} #{Name.firstName}#{city_suffix}");

            FakeValues.prefixUnqualifiedExpressions(list, "address");

            assertThat(list).containsExactly("#{Address.cityPrefix} #{Name.firstName}#{Address.citySuffix}");
        }

        @Test
        void recursesIntoNestedMapsAndRewritesListsAtAnyDepth() {
            Map<String, Object> inner = new LinkedHashMap<>();
            inner.put("title", mutableList("#{first_name}"));
            Map<String, Object> middle = new LinkedHashMap<>();
            middle.put("nested", inner);
            Map<String, Object> root = new LinkedHashMap<>();
            root.put("deeply", middle);

            FakeValues.prefixUnqualifiedExpressions(root, "name");

            List<Object> rewritten = (List<Object>) inner.get("title");
            assertThat(rewritten).containsExactly("#{Name.firstName}");
        }

        @Test
        void doesNotRewriteScalarStringsOutsideLists() {
            // Only items inside Lists are rewritten. A bare String value in a Map is left alone.
            Map<String, Object> map = new HashMap<>();
            map.put("scalar", "#{first_name}");
            map.put("list", mutableList("#{first_name}"));

            FakeValues.prefixUnqualifiedExpressions(map, "name");

            assertThat(map.get("scalar")).isEqualTo("#{first_name}");
            @SuppressWarnings("unchecked")
            List<Object> rewrittenList = (List<Object>) map.get("list");
            assertThat(rewrittenList).containsExactly("#{Name.firstName}");
        }

        @Test
        void rewritesNameFirstNameRedirectList_issue1477() {
            // The exact scenario from issue #1477 — name.first_name is a list of two
            // unqualified delegations to sibling YAML keys.
            List<String> list = mutableList("#{female_first_name}", "#{male_first_name}");

            FakeValues.prefixUnqualifiedExpressions(list, "name");

            assertThat(list).containsExactly("#{Name.femaleFirstName}", "#{Name.maleFirstName}");
        }

        private static List<String> mutableList(String... items) {
            return new ArrayList<>(Arrays.asList(items));
        }
    }

    @ParameterizedTest
    @MethodSource("fakeValuesProvider")
    void checkEquals(FakeValues fv1, FakeValues fv2, boolean equals) {
        if (equals) {
            assertThat(fv1).isEqualTo(fv2);
        } else {
            assertThat(fv1).isNotEqualTo(fv2);
        }
    }

    static Stream<Arguments> fakeValuesProvider() throws MalformedURLException {
        Path tmp = Paths.get("tmp");
        return Stream.of(
            of(FakeValues.of(FakeValuesContext.of(Locale.CANADA)), FakeValues.of(FakeValuesContext.of(Locale.CANADA)), true),
            of(null, FakeValues.of(FakeValuesContext.of(Locale.CANADA)), false),
            of(FakeValues.of(FakeValuesContext.of(Locale.CANADA)), null, false),
            of(FakeValues.of(FakeValuesContext.of(Locale.CANADA)), null, false),
            of(FakeValues.of(FakeValuesContext.of(Locale.ENGLISH)), FakeValues.of(FakeValuesContext.of(Locale.ENGLISH, "filepath", "path")), false),
            of(FakeValues.of(FakeValuesContext.of(Locale.ENGLISH, "filepath", null)), FakeValues.of(FakeValuesContext.of(Locale.ENGLISH, "filepath", "path")), false),
            of(FakeValues.of(FakeValuesContext.of(Locale.ENGLISH, "filepath", "path")), FakeValues.of(FakeValuesContext.of(Locale.ENGLISH, "filepath", "path")), true),
            of(FakeValues.of(FakeValuesContext.of(Locale.ENGLISH, "filepath", "path", tmp.toUri().toURL())), FakeValues.of(FakeValuesContext.of(Locale.ENGLISH, "filepath", "path", tmp.toUri().toURL())), true),
            of(FakeValues.of(FakeValuesContext.of(Locale.ENGLISH, "filepath", "path", Paths.get("tmp2").toUri().toURL())), FakeValues.of(FakeValuesContext.of(Locale.ENGLISH, "filepath", "path", tmp.toUri().toURL())), false)
        );
    }
}
