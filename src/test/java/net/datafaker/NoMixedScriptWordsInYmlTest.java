package net.datafaker;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.lang.Character.UnicodeScript;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.Files.newInputStream;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * A word that mixes Latin, Cyrillic or Greek letters is almost always a typo:
 * a lookalike letter from another keyboard layout (Latin "i" in "Мiнск" instead of Cyrillic "і").
 * Such words look right, but they don't match the real word in search, sorting or comparison.
 */
class NoMixedScriptWordsInYmlTest {

    private static final Pattern WORD = Pattern.compile("[\\p{L}\\p{M}]+");
    private static final Set<UnicodeScript> CHECKED_SCRIPTS = EnumSet.of(UnicodeScript.LATIN, UnicodeScript.CYRILLIC, UnicodeScript.GREEK);

    @ParameterizedTest
    @MethodSource("ymlFiles")
    void noWordsMixingLatinCyrillicAndGreek(Path file) {
        try (var in = newInputStream(file)) {
            Object ymlContent = new Yaml().load(in);
            Set<String> mixedWords = new TreeSet<>();
            findMixedScriptWords(ymlContent, mixedWords);
            assertThat(mixedWords)
                .overridingErrorMessage(() -> "Words mixing Latin, Cyrillic or Greek letters in file %s: %s".formatted(file, mixedWords))
                .isEmpty();
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse %s: %s".formatted(file, e.getMessage()), e);
        }
    }

    @SuppressWarnings("unused")
    private static Collection<Path> ymlFiles() throws IOException {
        try (var stream = Files.walk(Paths.get("src"))) {
            List<Path> ymlFiles = stream.filter(p -> p.toString().endsWith(".yml")).toList();
            assertThat(ymlFiles).as("No *.yml files found").isNotEmpty();
            return ymlFiles;
        }
    }

    private static void findMixedScriptWords(Object node, Set<String> mixedWords) {
        if (node instanceof Map<?, ?> map) {
            map.values().forEach(value -> findMixedScriptWords(value, mixedWords));
        } else if (node instanceof List<?> list) {
            list.forEach(value -> findMixedScriptWords(value, mixedWords));
        } else if (node instanceof String text) {
            Matcher matcher = WORD.matcher(text);
            while (matcher.find()) {
                if (isMixedScript(matcher.group())) {
                    mixedWords.add(matcher.group());
                }
            }
        }
    }

    private static boolean isMixedScript(String word) {
        Set<UnicodeScript> scripts = EnumSet.noneOf(UnicodeScript.class);
        word.codePoints()
            .mapToObj(UnicodeScript::of)
            .filter(CHECKED_SCRIPTS::contains)
            .forEach(scripts::add);
        return scripts.size() > 1;
    }
}
