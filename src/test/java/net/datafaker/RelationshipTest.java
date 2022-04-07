package net.datafaker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class RelationshipTest extends AbstractFakerTest {

    private Faker mockFaker;

    @BeforeEach
    public void before() {
        super.before();
        mockFaker = Mockito.mock(Faker.class);
    }

    @RepeatedTest(100)
    public void anyTest() {
        assertThat(faker.relationships().any()).isNotEmpty();
    }

    @Test
    public void directTest() {
        assertThat(faker.relationships().direct()).isNotEmpty();
    }

    @Test
    public void extendedTest() {
        assertThat(faker.relationships().extended()).isNotEmpty();
    }

    @Test
    public void inLawTest() {
        assertThat(faker.relationships().inLaw()).isNotEmpty();
    }

    @Test
    public void spouseTest() {
        assertThat(faker.relationships().spouse()).isNotEmpty();
    }

    @Test
    public void parentTest() {
        assertThat(faker.relationships().parent()).isNotEmpty();
    }

    @Test
    public void siblingTest() {
        assertThat(faker.relationships().sibling()).isNotEmpty();
    }

    @Test
    public void anyWithIllegalArgumentExceptionThrown() {
        when(mockFaker.random()).thenThrow(new IllegalArgumentException());
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Relationship(mockFaker).any());
    }

    @Test
    public void anyWithSecurityExceptionThrown() {
        when(mockFaker.random()).thenThrow(new SecurityException());
        Assertions.assertThrows(SecurityException.class, () -> new Relationship(mockFaker).any());
    }

    @Test
    public void anyWithIllegalAccessExceptionThrown() {
        when(mockFaker.random()).then(invocationOnMock -> {
            throw new IllegalAccessException();
        });
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> new Relationship(mockFaker).any());
        assertTrue(exception.getMessage().startsWith("IllegalAccessException: "));
    }

    @Test
    public void anyWithInvocationTargetExceptionThrown() {
        when(mockFaker.random()).then(invocationOnMock -> {
            throw new InvocationTargetException(new Exception());
        });
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> new Relationship(mockFaker).any());
        assertTrue(exception.getMessage().startsWith("InvocationTargetException: "));
    }

}
