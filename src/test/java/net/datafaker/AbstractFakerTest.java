package net.datafaker;

import net.datafaker.repeating.RepeatRule;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class AbstractFakerTest {

    @Rule
    public RepeatRule repeatRule = new RepeatRule();

    protected static Faker faker;

    @Before
    public void before() {
        MockitoAnnotations.openMocks(this);

        Logger rootLogger = LogManager.getLogManager().getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        rootLogger.setLevel(Level.INFO);
        for (Handler h : handlers) {
            h.setLevel(Level.INFO);
        }
    }

    @BeforeClass
    public static void setup() {
        faker = new Faker();
    }
}
