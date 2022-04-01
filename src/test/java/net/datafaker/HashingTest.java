package net.datafaker;

import org.junit.jupiter.api.Test;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;


public class HashingTest extends AbstractFakerTest {

    @Test
    public void testMd2() {
        assertThat(faker.hashing().md2(), matchesRegularExpression("[a-z\\d]+"));
    }

    @Test
    public void testMd5() {
        assertThat(faker.hashing().md5(), matchesRegularExpression("[a-z\\d]+"));
    }

    @Test
    public void testSha1() {
        assertThat(faker.hashing().sha1(), matchesRegularExpression("[a-z\\d]+"));
    }

    @Test
    public void testSha256() {
        assertThat(faker.hashing().sha256(), matchesRegularExpression("[a-z\\d]+"));
    }

    @Test
    public void testSha384() {
        assertThat(faker.hashing().sha384(), matchesRegularExpression("[a-z\\d]+"));
    }

    @Test
    public void testSha512() {
        assertThat(faker.hashing().sha512(), matchesRegularExpression("[a-z\\d]+"));
    }
}
