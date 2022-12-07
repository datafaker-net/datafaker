package net.datafaker.idnumbers;

import net.datafaker.providers.base.BaseProviders;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Generate number of Resident Registration Number for Republic of Korea.
 * <p>
 * Implementation based on the description at
 * <a href="https://en.wikipedia.org/wiki/Resident_registration_number">Wikipedia - Resident registration number</a>
 */
public class KoKrIdNumber implements IdNumbers {

    public String getValidRrn(BaseProviders f) {
        StringBuilder patternBuilder = new StringBuilder();
        Timestamp birthTimeStamp = f.date().birthday();
        String iso = f.nation().isoCountry();
        String gender = f.gender().shortBinaryTypes();

        // 1st to 6th digits indicate date of birth
        patternBuilder.append(new SimpleDateFormat("yyMMdd").format(f.date().birthday()));

        // Matches RRN Pattern ( ######-####### )
        patternBuilder.append('-');

        // 7th digit indicates birth century, gender, nationality
        patternBuilder.append(get7thDigit(birthTimeStamp.toLocalDateTime().getYear(), gender, iso));

        // From Oct 2020, 8 to 13 digits are randomized
        // 8th to 13th digits are random digits
        patternBuilder.append("######");

        return f.numerify(patternBuilder.toString());
    }

    private int get7thDigit(int year, String shortBinaryGender, String isoCountry) {
        // Local starts with 1, foreigner starts with 5
        int locality = isoCountry.equalsIgnoreCase("kr") ? 1 : 5;
        if (year < 1900) {
            // Male: 9 | Female: 0
            return shortBinaryGender.equalsIgnoreCase("m") ? 9 : 0;
        } else if (year < 2000) {
            // Male: 1, 5 | Female: 2, 6
            return locality + (shortBinaryGender.equalsIgnoreCase("m") ? 0 : 1);
        } else {
            // Male: 3, 7 | Female: 4, 8
            return locality + (shortBinaryGender.equalsIgnoreCase("m") ? 2 : 3);
        }
    }

}
