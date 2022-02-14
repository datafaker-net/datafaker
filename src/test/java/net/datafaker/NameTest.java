package net.datafaker;

import net.datafaker.repeating.Repeat;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.util.Locale;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Mockito.doReturn;


public class NameTest extends AbstractFakerTest {

    @Spy
    private Faker mockedFaker;

    @Test
    public void testName() {
        assertThat(faker.name().name(), matchesRegularExpression("([\\w']+\\.?( )?){2,3}"));
    }

    @Test
    public void testNameWithMiddle() {
        assertThat(faker.name().nameWithMiddle(), matchesRegularExpression("([\\w']+\\.?( )?){3,4}"));
    }

    @Test
    @Repeat(times = 10)
    public void testNameWithMiddleDoesNotHaveRepeatedName() {
        String nameWithMiddle = faker.name().nameWithMiddle();
        String[] splitNames = nameWithMiddle.split(" ");
        String firstName = splitNames[0];
        String middleName = splitNames[1];
        assertThat(firstName, not(equalTo(middleName)));
    }

    @Test
    public void testFullName() {
        assertThat(faker.name().fullName(), matchesRegularExpression("([\\w']+\\.?( )?){2,4}"));
    }

    @Test
    public void testFullNameArabic() {
        Faker localFaker = new Faker(new Locale("ar"));

        for (int i = 0; i < 25; i++) {
            assertThat(localFaker.name().fullName(), matchesRegularExpression("^[\\u0600-\\u06FF\\u0750-\\u077F ]+$"));
        }
    }

    @Test
    public void testFirstName() {
        assertThat(faker.name().firstName(), matchesRegularExpression("\\w+"));
    }

    @Test
    public void testLastName() {
        assertThat(faker.name().lastName(), matchesRegularExpression("[A-Za-z']+"));
    }

    @Test
    public void testPrefix() {
        assertThat(faker.name().prefix(), matchesRegularExpression("\\w+\\.?"));
    }

    @Test
    public void testSuffix() {
        assertThat(faker.name().suffix(), matchesRegularExpression("\\w+\\.?"));
    }

    @Test
    public void testTitle() {
        assertThat(faker.name().title(), matchesRegularExpression("(\\w+\\.?( )?){3}"));
    }

    @Test
    @Repeat(times = 100)
    public void testUsername() {
        assertThat(faker.name().username(), matchesRegularExpression("^(\\w+)\\.(\\w+)$"));
    }

    @Test
    public void testUsernameWithSpaces() {
        final Name name = Mockito.spy(new Name(mockedFaker));
        doReturn("Compound Name").when(name).firstName();
        doReturn(name).when(mockedFaker).name();
        assertThat(mockedFaker.name().username(), matchesRegularExpression("^(\\w+)\\.(\\w+)$"));
    }

    @Test
    public void testBloodGroup() {
        assertThat(faker.name().bloodGroup(), matchesRegularExpression("(A|B|AB|O)[+-]"));
    }

}
