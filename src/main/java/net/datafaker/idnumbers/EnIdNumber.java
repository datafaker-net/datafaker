package net.datafaker.idnumbers;

import java.util.regex.Pattern;

import net.datafaker.core.Faker;

public class EnIdNumber {
    private static final Pattern[] INVALID_SSN_PATTERNS = {
        Pattern.compile("0{3}-\\\\d{2}-\\\\d{4}"),
        Pattern.compile("\\d{3}-0{2}-\\d{4}"),
        Pattern.compile("\\d{3}-\\d{2}-0{4}"),
        Pattern.compile("666-\\d{2}-\\d{4}"),
        Pattern.compile("9\\d{2}-\\d{2}-\\d{4}")};

    public String getValidSsn(Faker f) {
        String ssn = f.regexify("[0-8]\\d{2}-\\d{2}-\\d{4}");

        boolean isValid = true;
        for (Pattern invalidSSNPattern : INVALID_SSN_PATTERNS) {
            if (invalidSSNPattern.matcher(ssn).matches()) {
                isValid = false;
                break;
            }
        }

        if (!isValid) {
            ssn = getValidSsn(f);
        }
        
        return ssn;
    }
}
