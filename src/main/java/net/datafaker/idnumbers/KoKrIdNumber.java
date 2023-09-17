package net.datafaker.idnumbers;

import net.datafaker.providers.base.BaseProviders;
import net.datafaker.service.RandomService;

import java.time.LocalDate;

/**
 * Generate number of Resident Registration Number for Republic of Korea.
 * <p>
 * Implementation based on the description at
 * <a href="https://en.wikipedia.org/wiki/Resident_registration_number">Wikipedia - Resident registration number</a>
 */
public class KoKrIdNumber implements IdNumbers {

    public String getValidRrn(BaseProviders f) {
        StringBuilder patternBuilder = new StringBuilder();
        LocalDate now = LocalDate.now();
        String iso = f.nation().isoCountry();
        String gender = f.gender().shortBinaryTypes();

        // 1st to 6th digits indicate date of birth

        patternBuilder.append(generateDay(f.random(), now.getYear() - 65,
            now.getMonthValue(), now.getDayOfMonth(), now.getYear() - 18,
            now.getMonthValue(), now.getDayOfMonth()));

        // Matches RRN Pattern ( ######-####### )
        patternBuilder.append('-');

        // 7th digit indicates birth century, gender, nationality
        patternBuilder.append(get7thDigit(now.getYear(), gender, iso));

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

    private String generateDay(RandomService rand, int yearStart, int monthStart, int dayStart, int yearEnd, int monthEnd, int dayEnd) {
        final int year = rand.nextInt(yearStart, yearEnd) % 100;
        final int month = rand.nextInt(monthStart, monthEnd);
        final int day = rand.nextInt(dayStart, dayEnd);
        final char[] res = new char[6];
        res[0] = (char) ('0' + (year / 10));
        res[1] = (char) ('0' + (year % 10));
        res[2] = (char) ('0' + (month / 10));
        res[3] = (char) ('0' + (month % 10));
        res[4] = (char) ('0' + (day / 10));
        res[5] = (char) ('0' + (day % 10));
        return String.valueOf(res);
    }

}
