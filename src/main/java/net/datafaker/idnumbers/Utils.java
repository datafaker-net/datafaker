package net.datafaker.idnumbers;

import static java.util.stream.Collectors.joining;
import static java.util.stream.IntStream.concat;
import static net.datafaker.providers.base.PersonIdNumber.Gender.FEMALE;
import static net.datafaker.providers.base.PersonIdNumber.Gender.MALE;

import java.time.LocalDate;
import java.util.stream.IntStream;

import net.datafaker.annotations.InternalApi;
import net.datafaker.providers.base.BaseProviders;
import net.datafaker.providers.base.IdNumber;
import net.datafaker.providers.base.IdNumber.IdNumberRequest;
import net.datafaker.providers.base.PersonIdNumber.Gender;

@InternalApi
class Utils {

    @InternalApi
    static LocalDate birthday(BaseProviders faker, IdNumberRequest request) {
        return faker.timeAndDate().birthday(request.minAge(), request.maxAge());
    }

    @InternalApi
    static Gender gender(BaseProviders faker, IdNumberRequest request) {
        IdNumber.GenderRequest gender = request.gender();
        return switch (gender) {
            case FEMALE -> FEMALE;
            case MALE -> MALE;
            case ANY -> randomGender(faker);
        };
    }

    @InternalApi
    static Gender randomGender(BaseProviders faker) {
        return faker.bool().bool() ? FEMALE : MALE;
    }

    @InternalApi
    static int digitAt(String text, int index) {
        return digit(text.charAt(index));
    }

    @InternalApi
    static int digit(char c) {
        return c - '0';
    }

    @InternalApi
    static int multiply(String text, int[] weights) {
        int checksum = 0;
        for (int i = 0; i < text.length(); i++) {
            checksum += digitAt(text, i) * weights[i];
        }
        return checksum;
    }

    @InternalApi
    static String join(IntStream chars1, IntStream chars2, IntStream chars3, int maxLength) {
        return concat(chars1, concat(chars2, chars3))
            .limit(maxLength)
            .mapToObj(c -> (char) c)
            .map(Object::toString)
            .collect(joining());
    }
}
