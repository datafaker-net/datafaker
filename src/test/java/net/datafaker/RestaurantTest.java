package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;

public class RestaurantTest extends AbstractFakerTest {

    @Test
    public void namePrefix() {
        assertThat(faker.restaurant().namePrefix(), not(is(emptyOrNullString())));
    }

    @Test
    public void nameSuffix() {
        assertThat(faker.restaurant().nameSuffix(), not(is(emptyOrNullString())));
    }

    @Test
    public void name() {
        assertThat(faker.restaurant().name(), not(is(emptyOrNullString())));
    }

    @Test
    public void type() {
        assertThat(faker.restaurant().type(), not(is(emptyOrNullString())));
    }

    @Test
    public void description() {
        assertThat(faker.restaurant().description(), not(is(emptyOrNullString())));
    }

    @Test
    public void review() {
        assertThat(faker.restaurant().review(), not(is(emptyOrNullString())));
    }

}
