package net.datafaker.internal.helper;

import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.WeakHashMap;

import static java.lang.Character.isLetter;
import static java.lang.Character.toLowerCase;
import static java.lang.Character.toUpperCase;
import static net.datafaker.internal.helper.JavaNames.Transform.SAME;
import static net.datafaker.internal.helper.JavaNames.Transform.TO_LOWER;
import static net.datafaker.internal.helper.JavaNames.Transform.TO_UPPER;

public class JavaNames {
    private static final Map<String, String> NAME_2_YAML = new CopyOnWriteMap<>(WeakHashMap::new);

    @Nullable
    public static String toJavaNames(@Nullable String string, boolean isMethod) {
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

    /**
     * @return a yaml style name like 'phone_number' from a java style name like 'PhoneNumber'
     */
    public static String javaNameToYamlName(String expression) {
        return NAME_2_YAML.computeIfAbsent(expression, (__) -> {

            final int length = expression.length();
            final boolean firstLetterUpperCase = length > 0 && Character.isUpperCase(expression.charAt(0));
            int cnt = firstLetterUpperCase ? 1 : 0;
            for (int i = 1; i < length; i++) {
                if (Character.isUpperCase(expression.charAt(i))) {
                    cnt++;
                }
            }
            if (cnt == 0) {
                return expression;
            }
            final char[] res = new char[length + (firstLetterUpperCase ? cnt - 1 : cnt)];
            int pos = 0;
            for (int i = 0; i < length; i++) {
                final char c = expression.charAt(i);
                if (cnt > 0) {
                    if (Character.isUpperCase(c)) {
                        if (pos > 0) {
                            res[pos++] = '_';
                        }
                        res[pos++] = Character.toLowerCase(c);
                        cnt--;
                    } else {
                        res[pos++] = c;
                    }
                } else {
                    res[pos++] = c;
                }
            }
            return new String(res);
        });
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
