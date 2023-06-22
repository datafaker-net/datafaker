package net.datafaker.idnumbers;

import net.datafaker.providers.base.BaseProviders;

import java.util.Arrays;
import java.util.List;

/**
 * Implementation based on the definition at
 * <a href="https://en.wikipedia.org/wiki/Unique_Population_Registry_Code">https://en.wikipedia.org/wiki/Unique_Population_Registry_Code</a>
 */
public class EsMXIdNumber implements IdNumbers {

    private static final String[] CHA = {
        "HEFA560427MVZRRL04",
        "DKEM193827HDQWEF05",
        "KAKS142444HNSSFAW6",
        "KSDF414424HNSDFAW6",
        "AKDF414424MSDSFAW6",
        "ADKF144424MNSDFCD6",
        "MYDF144424MDNFAW37",
        "AKKS414424MDAFDFW6",
        "WKDF144244HSDCNFA2",
        "AKSK414244HSDATT56",
        "QWDF414424HNSDVAW4",
        "AKDF144424MDEFVFA1"
    };
    private static final String[] CONSONANT = {"B", "C", "C", "D", "F",
        "G", "H", "J", "K", "L", "L", "M", "N", "N",
        "P", "Q", "R", "S", "T", "V", "W", "X", "Y", "Z"
    };

    private static final String[] VOWEL = {"A", "E", "I", "O", "U"};

    private static final String[] STATES = {"AG", "BC", "BS", "CM", "CS",
        "CH", "CO", "CL", "DF", "DG", "GT", "GR",
        "HG", "JA", "EM", "MI", "MO", "NA", "NL",
        "OA", "PU", "QT", "QR", "SL", "SI", "SO",
        "TB", "TM", "TL", "VE", "YU", "ZA", "NE",
    };

    /**
     * Get A valid MEX CURP.
     *
     * @param faker faker
     * @return A valid MEX CURP.
     */
    public String get(BaseProviders faker) {

        String sex = new String[]{"H", "M"}[faker.random().nextInt(2)];
        String birthDay = getBirthday(faker);
        String state = faker.options().option(STATES);

        String c1 = faker.options().option(CONSONANT);
        String c2 = faker.options().option(CONSONANT);
        String c3 = faker.options().option(CONSONANT);

        String v1 = faker.options().option(VOWEL);
        String v2 = faker.options().option(VOWEL);
        String v3 = faker.options().option(VOWEL);
        String v4 = faker.options().option(VOWEL);

        String ranNum = (Integer.parseInt(birthDay.substring(0, 4)) <= 1999) ?
            "0" : faker.options().option(CONSONANT);

        String ssn = c1 + v1 + c2 + c3 + birthDay.substring(2, 8) + sex + state + v2 + v3 + v4 + ranNum;
        ssn = ssn + getChecksum(ssn);

        return ssn;
    }

    /**
     * Get A invalid MEX CURP.
     *
     * @param faker faker
     * @return A invalid MEX CURP.
     */
    public String getWrong(BaseProviders faker) {
        return faker.options().option(CHA);
    }

    /**
     * Randomly gets a birthday.
     *
     * @param f A specific instance of Faker.
     * @return A valid date.
     */
    private String getBirthday(BaseProviders f) {
        int year = f.random().nextInt(1900, 2021);
        int month = f.random().nextInt(1, 12);
        int day = validDay(year, month, f);
        return String.valueOf(year * 10000 + month * 100 + day);
    }


    /**
     * Gets a valid day according to year and month.
     *
     * @param year  A specific year.
     * @param month A specific month.
     * @param f     A specific instance of Faker.
     * @return A valid day.
     */
    private int validDay(int year, int month, BaseProviders f) {

        List<Integer> bigMonths = Arrays.asList(1, 3, 5, 7, 8, 10, 12);

        if (month == 2) {
            if (year % 4 == 0) {
                return f.random().nextInt(1, 29);
            } else return f.random().nextInt(1, 28);
        } else if (bigMonths.contains(month)) {
            return f.random().nextInt(1, 31);
        } else return f.random().nextInt(1, 30);

    }

    /**
     * Gets the Checksum.
     *
     * @param str input string
     * @return Checksum.
     */
    private int getChecksum(String str) {
        int sum = 0;
        int v = str.length() + 1;
        for (int i = 0; i < str.length(); i++) {
            int number;
            if (str.charAt(i) < '9')
                number = str.charAt(i) - '0';
            else number = str.charAt(i) - 'A' + 10;
            sum += number * v--;
        }
        sum = Math.abs((sum % 10) - 10);
        return (sum == 10) ? 0 : sum;
    }

}
