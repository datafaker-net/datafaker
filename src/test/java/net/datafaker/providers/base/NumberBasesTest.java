package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NumberBasesTest extends BaseFakerTest<BaseFaker>{
	
	@Test
	void testBinary() {
		assertThat(faker.numberBases().binary(6).matches("[01]+"));
	}
	@Test
	void testOctal() {
		assertThat(faker.numberBases().octal(6).matches("[01234567]+"));
	}
	@Test
	void testHexadecimal() {
		assertThat(faker.numberBases().hexadecimal(6).matches("0x[0-9a-f]+"));
	}

}
