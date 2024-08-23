package net.datafaker.internal.helper;

import static java.lang.Character.isLetter;
import static java.lang.Character.toLowerCase;
import static java.lang.Character.toUpperCase;
import static net.datafaker.internal.helper.JavaNames.Transform.SAME;
import static net.datafaker.internal.helper.JavaNames.Transform.TO_LOWER;
import static net.datafaker.internal.helper.JavaNames.Transform.TO_UPPER;

public class JavaNames {
    public static String toJavaNames(String string, boolean isMethod) {
        if (string == null || string.isEmpty()) return string;

        int length = string.length();
        char[] res = new char[length];
        int pos = 0;
        Transform next = isMethod ? TO_LOWER : TO_UPPER;

        for (int i = 0; i < length; i++) {
            char c = string.charAt(i);
            if (isLetter(c)) {
                res[pos++] = next.transform(c);
                next = SAME;
            } else if (c == '_') {
                next = TO_UPPER;
            } else {
                res[pos++] = c;
                next = SAME;
            }
        }
        return new String(res, 0, pos);
    }

    enum Transform {
        SAME, TO_LOWER, TO_UPPER;

        public char transform(char c) {
            return switch (this) {
                case SAME -> c;
                case TO_LOWER -> toLowerCase(c);
                case TO_UPPER -> toUpperCase(c);
            };
        }
    }
}
