package net.datafaker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FakeCollectionTest extends AbstractFakerTest {
    @Test
    public void generateCollection() {
        List<String> names = new FakeCollection.Builder<String>()
                .suppliers(() -> faker.name().firstName(), () -> faker.name().lastName())
                .minLen(3)
                .maxLen(5).build().get();
        assertThat(names.size(), is(lessThanOrEqualTo(5)));
        assertThat(names.size(), is(greaterThanOrEqualTo(3)));
        for (String name : names) {
            assertThat(name, matchesRegularExpression("[a-zA-Z']+"));
        }
    }

    @Test
    public void generateCollectionWithDifferentObjects() {
        List<Object> objects = new FakeCollection.Builder<>()
                .suppliers(() -> faker.name().firstName(), () -> faker.random().nextInt(100))
                .maxLen(5).build().get();
        assertEquals(5, objects.size());
        for (Object object : objects) {
            assertTrue(object instanceof Integer || object instanceof String);
        }
    }

    @Test
    public void checkWrongArguments() {
        IllegalArgumentException iae = Assertions.assertThrows(IllegalArgumentException.class, () ->
                new FakeCollection.Builder<String>()
                        .suppliers(() -> faker.name().firstName())
                        .minLen(10)
                        .maxLen(5).build().get());
        assertEquals("Max length must be not less than min length and not negative", iae.getMessage());
    }
}
