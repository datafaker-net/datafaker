package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HashingTest extends BaseFakerTest<BaseFaker> {

    @Test
    void testMd2() {
        assertThat(faker.hashing().md2()).matches("\\b[a-fA-F\\d]{32}\\b");
    }

    @Test
    void testMd5() {
        assertThat(faker.hashing().md5()).matches("\\b[a-fA-F\\d]{32}\\b");
    }

    @Test
    void testSha1() {
        assertThat(faker.hashing().sha1()).matches("\\b[a-fA-F\\d]{40}\\b");
    }

    @Test
    void testSha256() {
        assertThat(faker.hashing().sha256()).matches("\\b[a-fA-F\\d]{64}\\b");
    }

    @Test
    void testSha384() {
        assertThat(faker.hashing().sha384()).matches("\\b[a-fA-F\\d]{96}\\b");
    }

    @Test
    void testSha512() {
        assertThat(faker.hashing().sha512()).matches("\\b[a-fA-F\\d]{128}\\b");
    }
}
