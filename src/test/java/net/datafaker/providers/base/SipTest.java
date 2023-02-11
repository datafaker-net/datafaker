package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SipTest extends BaseFakerTest<BaseFaker> {

    Sip sip = faker.sip();

    @Test
    void method_returnUpperCaseWithMinimum3Chars() {
        assertThat(sip.method()).matches("^[A-Z]{3,}$");
    }

    @Test
    void contentType_returnLowerCaseTwoWordsSepereatedBySlashMinimum3And4Chars() {
        assertThat(sip.contentType()).matches("^[a-z]{4,}/+[a-z\\d-]{3,}$");
    }

    @Test
    void messagingPort_return4DigitIntBetween1000And9999() {
        assertThat(sip.messagingPort()).isBetween(1000, 10000);
    }

    @Test
    void rtpPort_returnPositiveEvenInt() {
        int sut = sip.rtpPort();
        assertThat(sut).isGreaterThanOrEqualTo(2);
        assertThat(sut % 2).isZero();
    }

    @Test
    void provisionalResponseCode_return3DigitIntBetween100And199() {
        assertThat(sip.provisionalResponseCode()).isBetween(100, 200);
    }

    @Test
    void successResponse_Codereturn3DigitIntBetween200And299() {
        assertThat(sip.successResponseCode()).isBetween(200, 300);
    }

    @Test
    void redirectResponseCode_Codereturn3DigitIntBetween300And399() {
        assertThat(sip.redirectResponseCode()).isBetween(300, 400);
    }

    @Test
    void clientErrorResponseCode_Codereturn3DigitIntBetween400And499() {
        assertThat(sip.clientErrorResponseCode()).isBetween(400, 500);
    }

    @Test
    void serverErrorResponseCode_Codereturn3DigitIntBetween500And599() {
        assertThat(sip.serverErrorResponseCode()).isBetween(500, 600);
    }

    @Test
    void globalErrorResponseCode_Codereturn3DigitIntBetween600And699() {
        assertThat(sip.globalErrorResponseCode()).isBetween(600, 700);
    }

    @Test
    void provisionalResponsePhrase_returnAnyNonDigitString() {
        assertThat(sip.provisionalResponsePhrase()).matches("\\D+");
    }

    @Test
    void successResponsePhrase_returnAnyNonDigitString() {
        assertThat(sip.successResponsePhrase()).matches("\\D+");
    }

    @Test
    void redirectResponsePhrase_returnAnyNonDigitString() {
        assertThat(sip.redirectResponsePhrase()).matches("\\D+");
    }

    @Test
    void clientErrorResponsePhrase_returnAnyNonDigitString() {
        assertThat(sip.clientErrorResponsePhrase()).matches("\\D+");
    }

    @Test
    void serverErrorResponsePhrase_returnAnyNonDigitString() {
        assertThat(sip.serverErrorResponsePhrase()).matches("\\D+");
    }

    @Test
    void globalErrorResponsePhrase_returnAnyNonDigitString() {
        assertThat(sip.globalErrorResponsePhrase()).matches("\\D+");
    }

    @Test
    void bodyString_returnAValidSdpBodyString() {
        String[] sut = sip.bodyString().split("\n");

        assertThat(sut).hasSize(7);

        assertThat(sut[0]).isEqualTo("v=0");

        String[] secondLine = sut[1].split(" ");
        assertThat(secondLine[0]).startsWith("o=");
        assertThat(secondLine[1]).matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$");
        assertThat(secondLine[secondLine.length - 1]).matches("[a-z]+\\.\\w{2,4}");

        assertThat(sut[2]).isEqualTo("s=-");

        String[] fourthLine = sut[3].split(" ");
        assertThat(fourthLine[0]).isEqualTo("c=IN");
        assertThat(fourthLine[fourthLine.length - 1]).matches("^\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}$");

        assertThat(sut[4]).isEqualTo("t=0 0");

        String[] sixthLine = sut[5].split(" ");
        assertThat(sixthLine[0]).isEqualTo("m=audio");
        assertThat(Integer.parseInt(sixthLine[1])).isGreaterThanOrEqualTo(2);
        assertThat(Integer.parseInt(sixthLine[1]) % 2).isZero();

        assertThat(sut[6]).isEqualTo("a=rtpmap:0 PCMU/8000");
    }

    @Test
    void bodyBytes_isNotNull() {
        byte[] sut = sip.bodyBytes();

        assertThat(sut.length).isNotNull();
    }

    @Test
    void nameAddress_returnValidNameAddressString() {
        String[] sut = sip.nameAddress().split("@");

        assertThat(sut[0].split(":")[1]).matches("\\w+");
        assertThat(sut[1].split(":")[0]).matches("^\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}$");
    }
}
