package net.datafaker.providers.show;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SiliconValleyTest extends ShowFakerTest {

    @Test
    void testCharacter() {
        assertThat(faker.siliconValley().character()).isNotEmpty();
    }

    @Test
    void testCompany() {
        assertThat(faker.siliconValley().company()).isNotEmpty();
    }

    @Test
    void testQuote() {
        assertThat(faker.siliconValley().quote()).isNotEmpty();
    }

    @Test
    void testApp() {
        assertThat(faker.siliconValley().app()).isNotEmpty();
    }

    @Test
    void testInvention() {
        assertThat(faker.siliconValley().invention()).isNotEmpty();
    }

    @Test
    void testMotto() {
        assertThat(faker.siliconValley().motto()).isNotEmpty();
    }

    @Test
    void testUrl() {
        assertThat(faker.siliconValley().url()).isNotEmpty();
    }

    @Test
    void testEmail() {
        assertThat(faker.siliconValley().email()).isNotEmpty();
    }

}
