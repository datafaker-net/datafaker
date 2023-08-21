package net.datafaker.idnumbers;

import java.time.format.DateTimeFormatter;

public interface IdNumbers {
    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");
}
