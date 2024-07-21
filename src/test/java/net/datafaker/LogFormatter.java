package net.datafaker;

import java.time.format.DateTimeFormatter;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

import static java.lang.Thread.currentThread;
import static java.time.ZoneId.systemDefault;

public class LogFormatter extends SimpleFormatter {
    private static final DateTimeFormatter dt = DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSS");

    @Override
    public String format(LogRecord record) {
        return "[%s] %s %s".formatted(currentThread().getName(), time(record), super.format(record));
    }

    private static String time(LogRecord record) {
        return dt.format(record.getInstant().atZone(systemDefault()));
    }
}
