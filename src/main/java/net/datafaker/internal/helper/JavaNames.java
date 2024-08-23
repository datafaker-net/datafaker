package net.datafaker.internal.helper;

public class JavaNames {
    public static String toJavaNames(String string, boolean isMethod) {
        final int length;
        if (string == null || (length = string.length()) == 0) {
            return string;
        }
        int cnt = 0;
        for (int i = 0; i < length; i++) {
            if (string.charAt(i) == '_') {
                cnt++;
            }
        }
        if (cnt == 0 && (Character.isUpperCase(string.charAt(0)) && !isMethod || isMethod && Character.isLowerCase(string.charAt(0)))) return string;
        final char[] res = new char[length - cnt];
        int pos = 0;
        for (int i = 0; i < length; i++) {
            char c = string.charAt(i);
            if (i == 0 && Character.isLetter(c)) {
                res[pos++] = isMethod ? Character.toLowerCase(c) : Character.toUpperCase(c);
            } else if (c == '_') {
                final char next = string.charAt(i + 1);
                if (i < length - 1 && Character.isLetter(next)) {
                    res[pos++] = Character.toUpperCase(next);
                    i++;
                }
            } else {
                res[pos++] = c;
            }
        }
        return new String(res);
    }
}
