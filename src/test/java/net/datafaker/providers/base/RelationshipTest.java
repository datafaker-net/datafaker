package net.datafaker.providers.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

class RelationshipTest extends BaseFakerTest<BaseFaker> {

    private BaseFaker mockFaker;
    private final Relationship relationship = faker.relationships();

    @BeforeEach
    protected void before() {
        super.before();
        mockFaker = Mockito.mock(BaseFaker.class);
    }

    @RepeatedTest(100)
    void anyTest() {
        assertThat(relationship.any()).isNotEmpty();
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(relationship::direct, "relationship.familial.direct"),
                TestSpec.of(relationship::extended, "relationship.familial.extended"),
                TestSpec.of(relationship::inLaw, "relationship.in_law"),
                TestSpec.of(relationship::spouse, "relationship.spouse"),
                TestSpec.of(relationship::parent, "relationship.parent"),
                TestSpec.of(relationship::sibling, "relationship.sibling"));
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
