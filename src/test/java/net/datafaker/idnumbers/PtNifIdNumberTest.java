package net.datafaker.idnumbers;

import net.datafaker.AbstractFakerTest;
import net.datafaker.Faker;
import net.datafaker.repeating.Repeat;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;
import java.util.Random;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class PtNifIdNumberTest extends AbstractFakerTest {

    private Faker ptFaker;

    @Before
    public void before() {
        ptFaker = new Faker(new Locale("pt"));
    }

    @Test
    @Repeat(times = 100)
    public void testInValid() {
        PtNifIdNumber idNumber = new PtNifIdNumber();
        assertThat(idNumber.getInvalid(ptFaker), matchesRegularExpression("[0-9]{9,10}"));
    }

    @Test
    @Repeat(times = 100)
    public void testValid() {
        PtNifIdNumber idNumber = new PtNifIdNumber();
        assertThat(idNumber.getValid(ptFaker), matchesRegularExpression("[0-9]{9,10}"));
    }

    @Test
    @Repeat(times = 100)
    public void testValidWithFaker() {
        assertThat(ptFaker.idNumber().valid(), matchesRegularExpression("[0-9]{9,10}"));
    }

}
