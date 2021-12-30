package com.github.javafaker.idnumbers;

import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class PtNifIdNumberTest {

	private static final int CONSTANT_SEED_VALUE = 10;
	private Faker faker;

	@Before
	public void before() {
		final Random random = new Random(CONSTANT_SEED_VALUE);
		faker = new Faker(new Locale("pt"), random);
	}

	@Test
	public void testInValid() {
		PtNifIdNumber idNumber = new PtNifIdNumber();
		assertEquals("7202838711", idNumber.getInvalid(faker));
	}

	@Test
	public void testValid() {
	    PtNifIdNumber idNumber = new PtNifIdNumber();
		assertEquals("346336848", idNumber.getValid(faker));
	}

	@Test
	public void testValidWithFaker() {
		assertEquals("346336848", faker.idNumber().valid());
	}

}
