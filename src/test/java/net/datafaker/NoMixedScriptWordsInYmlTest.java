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
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.nio.file.Files.newInputStream;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * A word that mixes Latin, Cyrillic or Greek letters is almost always a typo:
 * a lookalike letter from another keyboard layout (Latin "i" in "Мiнск" instead of Cyrillic "і").
 * Such words look right, but they don't match the real word in search, sorting or comparison.
 */
class NoMixedScriptWordsInYmlTest {

    private static final Pattern WORD = Pattern.compile("[\\p{L}\\p{M}\\p{N}.\\-]+");
    /** Latin and Cyrillic letters that look the same as a letter of the other alphabet. */
    private static final String LOOKALIKES = "AaBCcEeHIiKMOoPpTXxYy" + "АаВСсЕеНІіКМОоРрТХхУу";
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
            throw new RuntimeException("Failed to read %s: %s".formatted(file, e.getMessage()), e);
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
            map.forEach((key, value) -> {
                findMixedScriptWords(key, mixedWords);
                findMixedScriptWords(value, mixedWords);
            });
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

    /**
     * A word is a run of letters, digits, dots and dashes, so a model name such as "Зенит-E" is one word.
     * It is flagged when one run of letters mixes scripts ("Мiнск"), or when the letters from the
     * minority script all have a lookalike in the main script ("Зенит-E" with a Latin E).
     * Names that really mix scripts, such as "occam-π" or "Зенит-200-Zoom", are not flagged.
     */
    private static boolean isMixedScript(String word) {
        for (String letters : word.split("[^\\p{L}\\p{M}]+")) {
            if (scriptCounts(letters).size() > 1) {
                return true;
            }
        }
        Map<UnicodeScript, Long> counts = scriptCounts(word);
        if (counts.size() < 2) {
            return false;
        }
        UnicodeScript mainScript = Collections.max(counts.entrySet(), Map.Entry.comparingByValue()).getKey();
        return word.codePoints()
            .filter(c -> CHECKED_SCRIPTS.contains(UnicodeScript.of(c)) && UnicodeScript.of(c) != mainScript)
            .allMatch(c -> LOOKALIKES.indexOf(c) >= 0);
    }

    private static Map<UnicodeScript, Long> scriptCounts(String text) {
        return text.codePoints()
            .mapToObj(UnicodeScript::of)
            .filter(CHECKED_SCRIPTS::contains)
            .collect(Collectors.groupingBy(Function.identity(), () -> new EnumMap<>(UnicodeScript.class), Collectors.counting()));
    }
}
