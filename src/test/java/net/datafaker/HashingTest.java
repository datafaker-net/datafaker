package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HashingTest extends AbstractFakerTest {

    @Test
    void testMd2() {
        assertThat(faker.hashing().md2()).matches("[a-z\\d]+");
    }

    @Test
    void testMd5() {
        assertThat(faker.hashing().md5()).matches("[a-z\\d]+");
    }

    @Test
    void testSha1() {
        assertThat(faker.hashing().sha1()).matches("[a-z\\d]+");
    }

    @Test
    void testSha256() {
        assertThat(faker.hashing().sha256()).matches("[a-z\\d]+");
    }

    @Test
    void testSha384() {
        assertThat(faker.hashing().sha384()).matches("[a-z\\d]+");
    }

    @Test
    void testSha512() {
        assertThat(faker.hashing().sha512()).matches("[a-z\\d]+");
    }
}
