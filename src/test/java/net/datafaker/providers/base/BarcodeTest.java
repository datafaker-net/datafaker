package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BarcodeTest extends BaseFakerTest<BaseFaker> {

    @Test
    void type() {
        assertThat(faker.barcode().type()).matches("(Code(128|39|93))|([EJ])AN(-\\d{1,2})*|Codabar|UCC|UPC(-([AE]))*|IS([BS])N|ITF|" +
            "Ames\\sCode|NW-7|Monarch|Code\\s2\\sof\\s7|Rationalized|ANSI/AIM BC3-1995|USD-4|" +
            "GS1 Databar|MSI Plessey");
    }

    private static boolean isBarcodeValid(long barcode) {
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
    void ean13() {
        assertThat(String.valueOf(faker.barcode().ean13())).matches("[0-9]{13}");
    }

    @Test
    void gtin13() {
        assertThat(String.valueOf(faker.barcode().gtin13())).matches("[0-9]{13}");
    }

    @Test
    void ean8() {
        assertThat(String.valueOf(faker.barcode().ean8())).matches("[0-9]{8}");
    }

    @Test
    void gtin8() {
        assertThat(String.valueOf(faker.barcode().gtin8())).matches("[0-9]{8}");
    }

    @Test
    void gtin14Length() {
        assertThat(String.valueOf(faker.barcode().gtin14())).matches("[0-9]{14}");
    }

    @Test
    void gtin12Length() {
        assertThat(String.valueOf(faker.barcode().gtin12())).matches("[0-9]{12}");
    }

    @Test
    void gtin12CheckSum() {
        long barcode = faker.barcode().gtin12();
        assertThat(BarcodeTest.isBarcodeValid(barcode)).isTrue();
    }

    @Test
    void gtin14CheckSum() {
        long barcode = faker.barcode().gtin14();
        assertThat(BarcodeTest.isBarcodeValid(barcode)).isTrue();
    }

    @Test
    void ean8CheckSum() {
        long barcode = faker.barcode().ean8();
        assertThat(BarcodeTest.isBarcodeValid(barcode)).isTrue();
    }

    @Test
    void ean13CheckSum() {
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

        assertThat(String.valueOf(sum)).endsWith("0");
    }
}
