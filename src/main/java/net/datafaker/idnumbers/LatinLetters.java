package net.datafaker.idnumbers;

import net.datafaker.annotations.InternalApi;

import java.util.Set;

@InternalApi
public class LatinLetters {

    private static final Set<Character> VOWELS = Set.of('A', 'a', 'E', 'e', 'I', 'i', 'O', 'o', 'U', 'u');

    @InternalApi
    static boolean isConsonant(int c) {
        return isConsonant((char) c);
    }

    @InternalApi
    static boolean isConsonant(char c) {
        return !VOWELS.contains(c);
    }

    @InternalApi
    static String removeNonLatinLetters(String text) {
        return text.replaceAll("[^a-zA-Z]+", "");
    }
}
