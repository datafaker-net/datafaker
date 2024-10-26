package net.datafaker.idnumbers;

import net.datafaker.providers.base.BaseFaker;
import net.datafaker.providers.base.IdNumber;
import net.datafaker.providers.base.PersonIdNumber;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static net.datafaker.providers.base.IdNumber.GenderRequest.ANY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UkrainianIdNumberTest {
    private final BaseFaker faker = new BaseFaker();
    private final UkrainianIdNumber ukrainianIdNumber = new UkrainianIdNumber();

    @Test
    void valid() {
        IdNumber.IdNumberRequest request = new IdNumber.IdNumberRequest(1, 65, ANY);
        PersonIdNumber person = ukrainianIdNumber.generateValid(faker, request);
        String unzr = person.idNumber();
        LocalDate bod = person.birthDate();

        assertEquals(bod.getYear(), Integer.parseInt(unzr.substring(0, 4)));
        assertEquals(bod.getMonthValue(), Integer.parseInt(unzr.substring(4, 6)));
        assertEquals(bod.getDayOfMonth(), Integer.parseInt(unzr.substring(6, 8)));
        assertTrue(isUnzrValid(unzr));
    }

    @Test
    void invalid() {
        String number = ukrainianIdNumber.generateInvalid(faker);
        assertFalse(isUnzrValid(number));
    }

    private boolean isUnzrValid(String unzr) {
        int[] digits = unzr.replace("-", "")
            .chars()
            .map(Character::getNumericValue)
            .toArray();

        int checksum = digits[0] * 7 + digits[1] * 3 + digits[2] + digits[3] * 7 + digits[4] * 3 + digits[5] +
            digits[6] * 7 + digits[7] * 3 + digits[8] + digits[9] * 7 + digits[10] * 3 + digits[11];
        return checksum % 10 == digits[12];
    }
}
