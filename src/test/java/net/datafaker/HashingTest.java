package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HashingTest extends AbstractFakerTest {

    @Test
    public void testMd2() {
        assertThat(faker.hashing().md2()).matches("[a-z\\d]+");
    }

    @Test
    public void testMd5() {
        assertThat(faker.hashing().md5()).matches("[a-z\\d]+");
    }

    @Test
    public void testSha1() {
        assertThat(faker.hashing().sha1()).matches("[a-z\\d]+");
    }

    @Test
    public void testSha256() {
        assertThat(faker.hashing().sha256()).matches("[a-z\\d]+");
    }

    @Test
    public void testSha384() {
        assertThat(faker.hashing().sha384()).matches("[a-z\\d]+");
    }

    @Test
    public void testSha512() {
        assertThat(faker.hashing().sha512()).matches("[a-z\\d]+");
    }
}
