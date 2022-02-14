package net.datafaker.service;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FakerIDNTest {

    @Test
    public void toASCIINoError() {
        assertEquals("hello", FakerIDN.toASCII("hello"));
    }
}