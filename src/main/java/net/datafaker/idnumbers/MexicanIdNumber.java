package net.datafaker.idnumbers;

import net.datafaker.providers.base.BaseProviders;
import net.datafaker.providers.base.Options;

import java.time.LocalDate;

/**
 * Implementation based on the definition at
 * <a href="https://en.wikipedia.org/wiki/Unique_Population_Registry_Code">https://en.wikipedia.org/wiki/Unique_Population_Registry_Code</a>
 */
public class MexicanIdNumber implements IdNumbers {

    @Override
    public String country() {
        return "MX";
    }

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
    private static final char[] CONSONANT = {'B', 'C', 'C', 'D', 'F',
        'G', 'H', 'J', 'K', 'L', 'L', 'M', 'N', 'N',
        'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z'
    };

    private static final char[] VOWEL = {'A', 'E', 'I', 'O', 'U'};

    private static final String[] STATES = {"AG", "BC", "BS", "CM", "CS",
        "CH", "CO", "CL", "DF", "DG", "GT", "GR",
        "HG", "JA", "EM", "MI", "MO", "NA", "NL",
        "OA", "PU", "QT", "QR", "SL", "SI", "SO",
        "TB", "TM", "TL", "VE", "YU", "ZA", "NE",
    };
    private static final char[] SEX = {'H', 'M'};

    @Deprecated
    public String get(BaseProviders faker) {
        return generateValid(faker);
    }

    /**
     * Get A valid MEX CURP.
     *
     * @param faker faker
     * @return A valid MEX CURP.
     */
    @Override
    public String generateValid(BaseProviders faker) {
        final Options options = faker.options();
        char[] birthDay = getBirthday(faker).toCharArray();
        final char[] ssn = new char[18];

        ssn[0] = options.option(CONSONANT);
        ssn[1] = options.option(VOWEL);
        ssn[2] = options.option(CONSONANT);
        ssn[3] = options.option(CONSONANT);
        System.arraycopy(birthDay, 0, ssn, 4, 6);
        ssn[10] = options.option(SEX);
        System.arraycopy(options.option(STATES).toCharArray(), 0, ssn, 11, 2);
        ssn[13] = options.option(VOWEL);
        ssn[14] = options.option(VOWEL);
        ssn[15] = options.option(VOWEL);
        ssn[16] = (birthDay[0] == '1' ? '0' : options.option(CONSONANT));
        ssn[17] = String.valueOf(getChecksum(ssn)).charAt(0);
        return String.valueOf(ssn);
    }

    @Deprecated
    public String getWrong(BaseProviders faker) {
        return generateInvalid(faker);
    }

    /**
     * Get A invalid MEX CURP.
     *
     * @param faker faker
     * @return A invalid MEX CURP.
     */
    @Override
    public String generateInvalid(BaseProviders faker) {
        return faker.options().option(CHA);
    }

    /**
     * Randomly gets a birthday.
     *
     * @param f A specific instance of Faker.
     * @return A valid date.
     */
    private String getBirthday(BaseProviders f) {
        LocalDate birthday = f.timeAndDate().birthday(0, 120);
        return String.valueOf(birthday.getYear() * 10000 + birthday.getMonthValue() * 100 + birthday.getDayOfMonth());
    }

    /**
     * Gets the Checksum.
     *
     * @param str input string
     * @return Checksum.
     */
    private int getChecksum(char[] str) {
        int sum = 0;
        int v = str.length;
        for (int i = 0; i < str.length - 1; i++) {
            int number;
            if (str[i] < '9')
                number = str[i] - '0';
            else number = str[i] - 'A' + 10;
            sum += number * v--;
        }
        sum = Math.abs((sum % 10) - 10);
        return (sum == 10) ? 0 : sum;
    }

}
