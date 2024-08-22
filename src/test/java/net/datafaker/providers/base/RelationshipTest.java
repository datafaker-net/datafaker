package net.datafaker.providers.base;

import net.datafaker.service.FakeValuesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class RelationshipTest extends BaseFakerTest<BaseFaker> {

    private final BaseFaker mockFaker = spy(new BaseFaker());
    private final FakeValuesService fakeValuesService = mock();
    private final Relationship relationship = new Relationship(mockFaker);

    @BeforeEach
    final void beforeEach() {
        reset(mockFaker, fakeValuesService);
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
        when(mockFaker.fakeValuesService()).thenReturn(fakeValuesService);
        when(fakeValuesService.resolve(any(), any(), any())).thenThrow(new IllegalArgumentException("Oops"));

        assertThatThrownBy(() -> relationship.any())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Oops");
    }

    @Test
    void anyWithInvocationTargetExceptionThrown() {
        when(mockFaker.fakeValuesService()).thenReturn(fakeValuesService);
        when(fakeValuesService.resolve(any(), any(), any())).thenThrow(new NullPointerException("Oops"));

        assertThatThrownBy(() -> relationship.any())
            .isInstanceOf(NullPointerException.class)
            .hasMessage("Oops");
    }
}
