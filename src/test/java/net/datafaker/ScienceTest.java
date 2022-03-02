package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

public class ScienceTest extends AbstractFakerTest {

    @RepeatedTest(10)
    public void element() {
        assertThat(faker.science().element(), matchesRegularExpression("[A-Za-z ]+"));
    }

    @RepeatedTest(10)
    public void elementSymbol() {
        assertThat(faker.science().elementSymbol(), matchesRegularExpression("[A-Za-z]{1,2}"));
    }

    @RepeatedTest(10)
    public void scientist() {
        assertThat(faker.science().scientist(), matchesRegularExpression("[A-Za-z. -]+"));
    }

    @RepeatedTest(10)
    public void tool() {
        assertThat(faker.science().tool(), matchesRegularExpression("[0-9A-Za-z. -]+"));
    }

    @RepeatedTest(10)
    public void quark() {
        assertThat(faker.science().quark(), matchesRegularExpression("[A-Za-z]+"));
    }

    @RepeatedTest(10)
    public void leptons() {
        assertThat(faker.science().leptons(), matchesRegularExpression("[A-Za-z ]+"));
    }

    @RepeatedTest(10)
    public void bosons() {
        assertThat(faker.science().bosons(), matchesRegularExpression("[A-Za-z ]+"));
    }
}
