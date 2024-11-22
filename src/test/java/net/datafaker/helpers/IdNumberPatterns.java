package net.datafaker.helpers;

import java.util.regex.Pattern;

public class IdNumberPatterns {

    public static final Pattern SWEDISH = Pattern.compile("\\d{6}[-+]\\d{4}");
    public static final Pattern UKRAINIAN = Pattern.compile("\\d{8}-\\d{5}");
    public static final Pattern SOUTH_AFRICAN = Pattern.compile("[0-9]{10}([01])8[0-9]");
    public static final Pattern FRENCH = Pattern.compile("[12]\\d{2}(1[0-2]|0[1-9])\\d[0-9a-zA-Z]\\d{8}");
    public static final Pattern ITALIAN = Pattern.compile("[A-Z]{6}\\d{2}[ABCDEHLMPRST]\\d{2}[\\dA-Z]{5}");

}
