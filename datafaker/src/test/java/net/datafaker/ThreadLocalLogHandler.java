package net.datafaker;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import static java.util.logging.Level.INFO;

public class ThreadLocalLogHandler extends Handler {
    private static final ThreadLocal<List<LogRecord>> logs = new ThreadLocal<>();
    private static final ConsoleHandler consoleHandler = new ConsoleHandler();

    static {
        consoleHandler.setLevel(Level.FINE);
    }

    @Override
    public void publish(LogRecord record) {
        if (logs.get() != null) {
            logs.get().add(record);
        }
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() throws SecurityException {
        logs.remove();
    }

    static void start() {
        logs.set(new ArrayList<>());
    }

    static void finish(boolean writeLogs) {
        if (writeLogs) {
            List<LogRecord> threadLogs = logs.get();
            if (!threadLogs.isEmpty()) {
                consoleHandler.publish(summaryLog(threadLogs));
                for (LogRecord log : threadLogs) {
                    consoleHandler.publish(log);
                }
            }
        }
        logs.remove();
    }

    private static LogRecord summaryLog(List<LogRecord> logs) {
        LogRecord record = new LogRecord(INFO, "Written %d logs during the test:".formatted(logs.size()));
        record.setLoggerName(ThreadLocalLogHandler.class.getName());
        return record;
    }
}
