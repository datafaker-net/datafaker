package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BarcodeTest extends AbstractFakerTest {

    @Test
    public void type() {
        assertThat(faker.barcode().type()).matches("(Code(128|39|93))|([EJ])AN(-\\d{1,2})*|Codabar|UCC|UPC(-([AE]))*|IS([BS])N|ITF|" +
                "Ames\\sCode|NW-7|Monarch|Code\\s2\\sof\\s7|Rationalized|ANSI/AIM BC3-1995|USD-4|" +
                "GS1 Databar|MSI Plessey");
    }

    public static boolean isBarcodeValid(long barcode) {
        char[] array = String.valueOf(barcode).toCharArray();
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            int digit = Integer.parseInt(String.valueOf(array[i]));
            if ((i + 1) % 2 == 0)
                sum += digit;
            else
                sum = sum + (digit * 3);
        }
        return String.valueOf(sum).endsWith("0");
    }

    @Test
    public void testEan13() {
        assertThat(String.valueOf(faker.barcode().ean13())).matches("[0-9]{13}");
    }

    @Test
    public void testGtin13() {
        assertThat(String.valueOf(faker.barcode().gtin13())).matches("[0-9]{13}");
    }

    @Test
    public void testEan8() {
        assertThat(String.valueOf(faker.barcode().ean8())).matches("[0-9]{8}");
    }

    @Test
    public void testGtin8() {
        assertThat(String.valueOf(faker.barcode().gtin8())).matches("[0-9]{8}");
    }

    @Test
    public void testGtin14Length() {
        assertThat(String.valueOf(faker.barcode().gtin14())).matches("[0-9]{14}");
    }

    @Test
    public void testGtin12Length() {
        assertThat(String.valueOf(faker.barcode().gtin12())).matches("[0-9]{12}");
    }

    @Test
    public void testGtin12CheckSum() {
        long barcode = faker.barcode().gtin12();
        assertTrue(BarcodeTest.isBarcodeValid(barcode));
    }

    @Test
    public void testGtin14CheckSum() {
        long barcode = faker.barcode().gtin14();
        assertTrue(BarcodeTest.isBarcodeValid(barcode));
    }

    @Test
    public void testEan8CheckSum() {
        long barcode = faker.barcode().ean8();
        assertTrue(BarcodeTest.isBarcodeValid(barcode));
    }

    @Test
    public void testEan13CheckSum() {
        long barcode = faker.barcode().ean13();
        char[] array = String.valueOf(barcode).toCharArray();
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            int digit = Integer.parseInt(String.valueOf(array[i]));
            if ((i + 1) % 2 == 0)
                sum = sum + digit * 3;
            else
                sum = sum + digit;
        }

        assertTrue(String.valueOf(sum).endsWith("0"));
    }
}
