package net.datafaker.providers.base;

import java.time.LocalDate;

public record PersonIdNumber(
    String idNumber,
    LocalDate birthDate,
    Gender gender
) {
    public enum Gender {
        FEMALE, MALE
    }
}
