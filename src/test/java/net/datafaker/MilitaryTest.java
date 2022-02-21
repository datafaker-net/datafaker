package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;

public class MilitaryTest extends AbstractFakerTest {

    @Test
    public void armyRank() {
        assertThat(faker.military().armyRank(), not(is(emptyOrNullString())));
    }

    @Test
    public void marinesRank() {
        assertThat(faker.military().marinesRank(), not(is(emptyOrNullString())));
    }

    @Test
    public void navyRank() {
        assertThat(faker.military().navyRank(), not(is(emptyOrNullString())));
    }

    @Test
    public void airForceRank() {
        assertThat(faker.military().airForceRank(), not(is(emptyOrNullString())));
    }

    @Test
    public void dodPaygrade() {
        assertThat(faker.military().dodPaygrade(), not(is(emptyOrNullString())));
    }

}
