package net.datafaker.idnumbers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SwedishIdNumberTest {

    @Test
    public void valid() {
        SvSEIdNumber idNumber = new SvSEIdNumber();
        assertTrue(idNumber.validSwedishSsn("670919-9530"));
        assertTrue(idNumber.validSwedishSsn("811228-9874"));
    }

    @Test
    public void invalid() {
        SvSEIdNumber idNumber = new SvSEIdNumber();
        assertFalse(idNumber.validSwedishSsn("8112289873"));
        assertFalse(idNumber.validSwedishSsn("foo228-9873"));
        assertFalse(idNumber.validSwedishSsn("811228-9873"));
        assertFalse(idNumber.validSwedishSsn("811228-9875"));
        assertFalse(idNumber.validSwedishSsn("811200-9874"));
        assertFalse(idNumber.validSwedishSsn("810028-9874"));
    }
}
