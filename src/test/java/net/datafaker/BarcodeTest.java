package net.datafaker;

import org.junit.jupiter.api.Test;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class BarcodeTest extends AbstractFakerTest {

    @Test
    public void type() {
        assertThat(faker.barcode().type(),
            matchesRegularExpression("(Code(128|39|93))|(E|J)AN(-\\d{1,2})*|Codabar|UCC|UPC(-(A|E))*|IS(B|S)N|ITF|" +
                "Ames\\sCode|NW-7|Monarch|Code\\s2\\sof\\s7|Rationalized|ANSI\\/AIM BC3-1995|USD-4|" +
                "GS1 Databar|MSI Plessey"));
    }

    @Test
    public void data() {
        assertThat(faker.barcode().data(), matchesRegularExpression("\\d+"));
    }

    @Test
    public void typeAndData() {
        assertThat(faker.barcode().typeAndData(), matchesRegularExpression("(\\w|\\W)+\\s\\d+$"));
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
        assertThat(String.valueOf(faker.barcode().ean13()), matchesRegularExpression("[0-9]{13}"));
    }

    @Test
    public void testGtin13() {
        assertThat(String.valueOf(faker.barcode().gtin13()), matchesRegularExpression("[0-9]{13}"));
    }

    @Test
    public void testEan8() {
        assertThat(String.valueOf(faker.barcode().ean8()), matchesRegularExpression("[0-9]{8}"));
    }

    @Test
    public void testGtin8() {
        assertThat(String.valueOf(faker.barcode().gtin8()), matchesRegularExpression("[0-9]{8}"));
    }

    @Test
    public void testGtin14Length() {
        assertThat(String.valueOf(faker.barcode().gtin14()), matchesRegularExpression("[0-9]{14}"));
    }

    @Test
    public void testGtin12Length() {
        assertThat(String.valueOf(faker.barcode().gtin12()), matchesRegularExpression("[0-9]{12}"));
    }

    @Test
    public void testGtin12CheckSum() {
        long barcode = faker.barcode().gtin12();
        assertThat(BarcodeTest.isBarcodeValid(barcode), equalTo(true));
    }

    @Test
    public void testGtin14CheckSum() {
        long barcode = faker.barcode().gtin14();
        assertThat(BarcodeTest.isBarcodeValid(barcode), equalTo(true));
    }

    @Test
    public void testEan8CheckSum() {
        long barcode = faker.barcode().ean8();
        assertThat(BarcodeTest.isBarcodeValid(barcode), equalTo(true));
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

        assertThat(String.valueOf(sum).endsWith("0"), equalTo(true));
    }
}
