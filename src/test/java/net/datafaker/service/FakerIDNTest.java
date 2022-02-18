package net.datafaker.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FakerIDNTest {

    @Test
    public void toASCIINoError() {
        assertEquals("hello", FakerIDN.toASCII("hello"));
    }
}