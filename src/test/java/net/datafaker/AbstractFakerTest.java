package net.datafaker;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class AbstractFakerTest {

    protected static final Function<Predicate<Character>, Condition<String>> CONDITION_FUNCTION = characterPredicate -> new Condition<>(s -> {
        for (int i = 0; i < s.length(); i++) {
            if (!characterPredicate.test(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }, "condition");

    protected static Faker faker;

    @BeforeEach
    public void before() {
        MockitoAnnotations.openMocks(this);

        Logger rootLogger = LogManager.getLogManager().getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        rootLogger.setLevel(Level.INFO);
        for (Handler h : handlers) {
            h.setLevel(Level.INFO);
        }
    }

    @BeforeAll
    public static void setup() {
        faker = new Faker();
    }
}
