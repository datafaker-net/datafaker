package net.datafaker.providers.base;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class BaseFakerTest<T extends BaseFaker> {

    protected final T faker = getFaker();

    @BeforeEach
    protected void before() {
        try (AutoCloseable ignored = MockitoAnnotations.openMocks(this)) {

            Logger rootLogger = LogManager.getLogManager().getLogger("");
            Handler[] handlers = rootLogger.getHandlers();
            rootLogger.setLevel(Level.INFO);
            for (Handler h : handlers) {
                h.setLevel(Level.INFO);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected T getFaker() {
        return (T) new BaseFaker();
    }
}
