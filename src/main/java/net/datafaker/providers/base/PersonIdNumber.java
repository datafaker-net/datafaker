package net.datafaker.providers.base;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static java.time.LocalDate.now;

public record PersonIdNumber(
    String idNumber,
    LocalDate birthDate,
    Gender gender
) {
    public enum Gender {
        FEMALE, MALE
    }

    public int age() {
        return age(now());
    }

    int age(LocalDate atDate) {
        return (int) ChronoUnit.YEARS.between(birthDate, atDate);
    }
}
