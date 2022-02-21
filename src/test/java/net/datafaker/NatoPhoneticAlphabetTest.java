package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;

public class NatoPhoneticAlphabetTest extends AbstractFakerTest {

    @Test
    public void codeWord() {
        assertThat(faker.natoPhoneticAlphabet().codeWord(), not(is(emptyOrNullString())));
    }

}
