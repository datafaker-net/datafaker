package net.datafaker;

import net.datafaker.repeating.Repeat;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.InvocationTargetException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Mockito.when;

public class RelationshipTest extends AbstractFakerTest {

    private Faker mockFaker;

    @Before
    public void before() {
        super.before();
        mockFaker = Mockito.mock(Faker.class);
    }

    @Test
    @Repeat(times = 100)
    public void anyTest() {
        assertThat(faker.relationships().any(), not(is(emptyOrNullString())));
    }

    @Test
    public void directTest() {
        assertThat(faker.relationships().direct(), not(is(emptyOrNullString())));
    }

    @Test
    public void extendedTest() {
        assertThat(faker.relationships().extended(), not(is(emptyOrNullString())));
    }

    @Test
    public void inLawTest() {
        assertThat(faker.relationships().inLaw(), not(is(emptyOrNullString())));
    }

    @Test
    public void spouseTest() {
        assertThat(faker.relationships().spouse(), not(is(emptyOrNullString())));
    }

    @Test
    public void parentTest() {
        assertThat(faker.relationships().parent(), not(is(emptyOrNullString())));
    }

    @Test
    public void siblingTest() {
        assertThat(faker.relationships().sibling(), not(is(emptyOrNullString())));
    }

    @Test(expected = RuntimeException.class)
    public void anyWithIllegalArgumentExceptionThrown() {
        when(mockFaker.random()).thenThrow(new IllegalArgumentException());
        new Relationship(mockFaker).any();
    }

    @Test(expected = RuntimeException.class)
    public void anyWithSecurityExceptionThrown() {
        when(mockFaker.random()).thenThrow(new SecurityException());
        new Relationship(mockFaker).any();
    }

    @Test(expected = RuntimeException.class)
    public void anyWithIllegalAccessExceptionThrown() {
        when(mockFaker.random()).thenThrow(new IllegalAccessException());
        new Relationship(mockFaker).any();
    }

    @Test(expected = RuntimeException.class)
    public void anyWithInvocationTargetExceptionThrown() {
        when(mockFaker.random()).thenThrow(new InvocationTargetException(new Exception()));
        new Relationship(mockFaker).any();
    }

}
