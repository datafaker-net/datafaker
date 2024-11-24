package net.datafaker.idnumbers;

import java.util.Set;

public class LatinLetters {

    private static final Set<Character> VOWELS = Set.of('A', 'a', 'E', 'e', 'I', 'i', 'O', 'o', 'U', 'u');

    static boolean isConsonant(int c) {
        return isConsonant((char) c);
    }

    static boolean isConsonant(char c) {
        return !VOWELS.contains(c);
    }

    static String removeNonLatinLetters(String text) {
        return text.replaceAll("[^a-zA-Z]+", "");
    }

}
