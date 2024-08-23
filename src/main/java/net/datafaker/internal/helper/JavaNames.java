package net.datafaker.internal.helper;

import static java.lang.Character.isLetter;
import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;
import static java.lang.Character.toLowerCase;
import static java.lang.Character.toUpperCase;

public class JavaNames {
    public static String toJavaNames(String string, boolean isMethod) {
        if (string == null || string.isEmpty()) return string;

        int length = string.length();
        int cnt = count(string, length, '_');
        if (cnt == 0 && !isMethod && isUpperCase(string.charAt(0))) return string;
        if (cnt == 0 && isMethod && isLowerCase(string.charAt(0))) return string;

        char[] res = new char[length - cnt];
        int pos = 0;
        for (int i = 0; i < length; i++) {
            char c = string.charAt(i);
            if (i == 0 && isLetter(c)) {
                res[pos++] = isMethod ? toLowerCase(c) : toUpperCase(c);
            } else if (c == '_') {
                if (i < length - 1) {
                    char next = string.charAt(i + 1);
                    if (isLetter(next)) {
                        res[pos++] = toUpperCase(next);
                        i++;
                    }
                }
            } else {
                res[pos++] = c;
            }
        }
        return new String(res);
    }

    private static int count(String text, int length, char character) {
        int cnt = 0;
        for (int i = 0; i < length; i++) {
            if (text.charAt(i) == character) {
                cnt++;
            }
        }
        return cnt;
    }
}
