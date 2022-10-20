package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

class NameTest extends BaseFakerTest<BaseFaker> {

    @Spy
    private BaseFaker mockedFaker;

    @Test
    void name() {
        assertThat(faker.name().name()).matches("([\\w']+\\.?( )?){2,3}");
    }

    @Test
    void nameWithMiddle() {
        assertThat(faker.name().nameWithMiddle()).matches("([\\w']+\\.?( )?){3,4}");
    }

    @Test
    void nameWithMiddleDoesNotHaveRepeatedName() {
        int theSameNameCnt = 0;
        int total = 100;
        for (int i = 0; i < total; i++) {
            String nameWithMiddle = faker.name().nameWithMiddle();
            String[] splitNames = nameWithMiddle.split(" ");
            if (splitNames[0].equals(splitNames[1])) {
                theSameNameCnt++;
            }
        }
        assertThat(theSameNameCnt).isLessThan(total / 10);
    }

    @Test
    void fullName() {
        assertThat(faker.name().fullName()).matches("([\\w']+\\.?( )?){2,4}");
    }

    @Test
    void fullNameArabic() {
        BaseFaker localFaker = new BaseFaker(new Locale("ar"));

        for (int i = 0; i < 25; i++) {
            assertThat(localFaker.name().fullName()).matches("^[\\u0600-\\u06FF\\u0750-\\u077F ]+$");
        }
    }

    @Test
    void firstName() {
        assertThat(faker.name().firstName()).matches("\\w+");
    }

    @Test
    void lastName() {
        assertThat(faker.name().lastName()).matches("[A-Za-z']+");
    }

    @Test
    void prefix() {
        assertThat(faker.name().prefix()).matches("\\w+\\.?");
    }

    @Test
    void suffix() {
        assertThat(faker.name().suffix()).matches("\\w+\\.?");
    }

    @Test
    void title() {
        assertThat(faker.name().title()).matches("(\\w+\\.?( )?){3}");
    }

    @RepeatedTest(100)
    void username() {
        assertThat(faker.name().username()).matches("^(\\w+)\\.(\\w+)$");
    }

    @Test
    void usernameWithSpaces() {
        final Name name = Mockito.spy(new Name(mockedFaker));
        doReturn("Compound Name").when(name).firstName();
        doReturn(name).when(mockedFaker).name();
        assertThat(mockedFaker.name().username()).matches("^(\\w+)\\.(\\w+)$");
    }

}
