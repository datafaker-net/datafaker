package net.datafaker.idnumbers;

import net.datafaker.providers.base.BaseProviders;
import net.datafaker.providers.base.IdNumber;
import net.datafaker.providers.base.IdNumber.IdNumberRequest;
import net.datafaker.providers.base.PersonIdNumber.Gender;

import java.time.LocalDate;

import static net.datafaker.providers.base.PersonIdNumber.Gender.FEMALE;
import static net.datafaker.providers.base.PersonIdNumber.Gender.MALE;

public class Utils {

    static LocalDate birthday(BaseProviders faker, IdNumberRequest request) {
        return faker.timeAndDate().birthday(request.minAge(), request.maxAge());
    }

    static Gender gender(BaseProviders faker, IdNumberRequest request) {
        IdNumber.GenderRequest gender = request.gender();
        return switch (gender) {
            case FEMALE -> FEMALE;
            case MALE -> MALE;
            case ANY -> randomGender(faker);
        };
    }

    static Gender randomGender(BaseProviders faker) {
        return faker.bool().bool() ? FEMALE : MALE;
    }

    static int digitAt(String text, int index) {
        return digit(text.charAt(index));
    }

    static int digit(char c) {
        return c - '0';
    }

    static int multiply(String text, int[] weights) {
        int checksum = 0;
        for (int i = 0; i < text.length(); i++) {
            checksum += digitAt(text, i) * weights[i];
        }
        return checksum;
    }
}
