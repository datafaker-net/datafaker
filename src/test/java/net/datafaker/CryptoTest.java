package net.datafaker;

import org.junit.jupiter.api.Test;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;


public class CryptoTest extends AbstractFakerTest {

    @Test
    public void testMd2() {
        assertThat(faker.crypto().md2(), matchesRegularExpression("[a-z\\d]+"));
    }

    @Test
    public void testMd5() {
        assertThat(faker.crypto().md5(), matchesRegularExpression("[a-z\\d]+"));
    }

    @Test
    public void testSha1() {
        assertThat(faker.crypto().sha1(), matchesRegularExpression("[a-z\\d]+"));
    }

    @Test
    public void testSha256() {
        assertThat(faker.crypto().sha256(), matchesRegularExpression("[a-z\\d]+"));
    }

    @Test
    public void testSha384() {
        assertThat(faker.crypto().sha384(), matchesRegularExpression("[a-z\\d]+"));
    }

    @Test
    public void testSha512() {
        assertThat(faker.crypto().sha512(), matchesRegularExpression("[a-z\\d]+"));
    }
}
