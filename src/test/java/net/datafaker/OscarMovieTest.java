package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Strings.isNullOrEmpty;
public class OscarMovieTest extends AbstractFakerTest{
    @Test
    public void actor() {
        assertThat(faker.oscarMovie().actor()).matches("[A-Za-z,\\-.() ]+");
    }
    @Test
    public void movieName() {
        assertThat(isNullOrEmpty(faker.oscarMovie().movieName())).isFalse();
    }
    @Test
    public void quote() {
        assertThat(isNullOrEmpty(faker.oscarMovie().quote())).isFalse();
    }
    @Test
    public void character() {
        assertThat(faker.oscarMovie().actor()).matches("[A-Za-z,\\- ]+");
    }
    @Test
    public void releaseDate() {
        assertThat(faker.oscarMovie().actor()).matches("[A-Za-z,0-9\\-.() ]+");
    }
}
