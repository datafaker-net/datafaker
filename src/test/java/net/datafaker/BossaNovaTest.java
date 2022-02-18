package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

public class BossaNovaTest extends AbstractFakerTest {

    @RepeatedTest(10)
    public void artists() {
        assertThat(faker.bossaNova().artist(), matchesRegularExpression("[A-Za-z .-]+"));
    }

    @RepeatedTest(10)
    public void songs() {
        assertThat(faker.bossaNova().song(), not(is(emptyOrNullString())));
    }
}