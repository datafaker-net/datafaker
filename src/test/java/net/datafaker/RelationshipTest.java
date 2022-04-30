package net.datafaker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

class RelationshipTest extends AbstractFakerTest {

    private Faker mockFaker;

    @BeforeEach
    public void before() {
        super.before();
        mockFaker = Mockito.mock(Faker.class);
    }

    @RepeatedTest(100)
    void anyTest() {
        assertThat(faker.relationships().any()).isNotEmpty();
    }

    @Test
    void directTest() {
        assertThat(faker.relationships().direct()).isNotEmpty();
    }

    @Test
    void extendedTest() {
        assertThat(faker.relationships().extended()).isNotEmpty();
    }

    @Test
    void inLawTest() {
        assertThat(faker.relationships().inLaw()).isNotEmpty();
    }

    @Test
    void spouseTest() {
        assertThat(faker.relationships().spouse()).isNotEmpty();
    }

    @Test
    void parentTest() {
        assertThat(faker.relationships().parent()).isNotEmpty();
    }

    @Test
    void siblingTest() {
        assertThat(faker.relationships().sibling()).isNotEmpty();
    }

    @Test
    void anyWithIllegalArgumentExceptionThrown() {
        when(mockFaker.random()).thenThrow(new IllegalArgumentException());
        assertThatThrownBy(() -> new Relationship(mockFaker).any())
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void anyWithSecurityExceptionThrown() {
        when(mockFaker.random()).thenThrow(new SecurityException());
        assertThatThrownBy(() -> new Relationship(mockFaker).any())
            .isInstanceOf(SecurityException.class);
    }

    @Test
    void anyWithIllegalAccessExceptionThrown() {
        when(mockFaker.random()).then(invocationOnMock -> {
            throw new IllegalAccessException();
        });
        assertThatThrownBy(() -> new Relationship(mockFaker).any())
            .isInstanceOf(RuntimeException.class)
            .hasMessageStartingWith("IllegalAccessException: ");
    }

    @Test
    void anyWithInvocationTargetExceptionThrown() {
        when(mockFaker.random()).then(invocationOnMock -> {
            throw new InvocationTargetException(new Exception());
        });
        assertThatThrownBy(() -> new Relationship(mockFaker).any())
            .isInstanceOf(RuntimeException.class)
            .hasMessageStartingWith("InvocationTargetException: ");
    }

}
