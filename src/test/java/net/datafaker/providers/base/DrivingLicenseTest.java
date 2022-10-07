package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

public class DrivingLicenseTest extends BaseFakerTest<BaseFaker> {

    @RepeatedTest(100)
    void drivingLicense_AL() {
        assertThat(faker.drivingLicense().drivingLicense("AL")).matches("^\\d{6,8}$");
    }

    @RepeatedTest(100)
    void drivingLicense_AK() {
        assertThat(faker.drivingLicense().drivingLicense("AK")).matches("^\\d{6,7}$");
    }

    @RepeatedTest(100)
    void drivingLicense_AZ() {
        assertThat(faker.drivingLicense().drivingLicense("AZ")).matches("^([A-Z]?)\\d{8,9}$");
    }

    @RepeatedTest(100)
    void drivingLicense_AR() {
        assertThat(faker.drivingLicense().drivingLicense("AR")).matches("^\\d{8,9}$");
    }

    @RepeatedTest(100)
    void drivingLicense_CA() {
        assertThat(faker.drivingLicense().drivingLicense("CA")).matches("^[A-Z]\\d{7}$");
    }

    @RepeatedTest(100)
    void drivingLicense_CO() {
        assertThat(faker.drivingLicense().drivingLicense("CO")).matches("(^[0-9]{9}$)|(^[A-Z]{1}[0-9]{3,6}$)|(^[A-Z]{2}[0-9]{2,5}$)");
    }

    @RepeatedTest(100)
    void drivingLicense_CT() {
        assertThat(faker.drivingLicense().drivingLicense("CT")).matches("^\\d{9}$");
    }

    @RepeatedTest(100)
    void drivingLicense_DE() {
        assertThat(faker.drivingLicense().drivingLicense("DE")).matches("^\\d{6,7}$");
    }

    @RepeatedTest(100)
    void drivingLicense_DC() {
        assertThat(faker.drivingLicense().drivingLicense("DC")).matches("(^\\d{7}$)|(^\\d{9}$)");
    }

    @RepeatedTest(100)
    void drivingLicense_FL() {
        assertThat(faker.drivingLicense().drivingLicense("FL")).matches("^[A-Z]\\d{12}$");
    }

    @RepeatedTest(100)
    void drivingLicense_GA() {
        assertThat(faker.drivingLicense().drivingLicense("GA")).matches("^\\d{7,9}$");
    }

    @RepeatedTest(100)
    void drivingLicense_HI() {
        assertThat(faker.drivingLicense().drivingLicense("HI")).matches("(^[A-Z]\\d{8}$)|(^\\d{9}$)");
    }

    @RepeatedTest(100)
    void drivingLicense_ID() {
        assertThat(faker.drivingLicense().drivingLicense("ID")).matches("(^[A-Z]{2}\\d{6}[A-Z]$)|(^\\d{9}$)");
    }

    @RepeatedTest(100)
    void drivingLicense_IL() {
        assertThat(faker.drivingLicense().drivingLicense("IL")).matches("^[A-Z]\\d{11,12}$");
    }

    @RepeatedTest(100)
    void drivingLicense_IN() {
        assertThat(faker.drivingLicense().drivingLicense("IN")).matches("(^[A-Z]\\d{9}$)|(^\\d{9,10}$)");
    }

    @RepeatedTest(100)
    void drivingLicense_IA() {
        assertThat(faker.drivingLicense().drivingLicense("IA")).matches("^(\\d{9}|(\\d{3}[A-Z]{2}\\d{4}))$");
    }

    @RepeatedTest(100)
    void drivingLicense_KS() {
        assertThat(faker.drivingLicense().drivingLicense("KS")).matches("(^([A-Z]\\d){2}[A-Z]$)|(^[A-Z]\\d{8}$)|(^\\d{9}$)");
    }

    @RepeatedTest(100)
    void drivingLicense_KY() {
        assertThat(faker.drivingLicense().drivingLicense("KY")).matches("(^[A-Z]\\d{8,9}$)|(^\\d{9}$)");
    }

    @RepeatedTest(100)
    void drivingLicense_LA() {
        assertThat(faker.drivingLicense().drivingLicense("LA")).matches("^\\d{8,9}$");
    }

    @RepeatedTest(100)
    void drivingLicense_ME() {
        assertThat(faker.drivingLicense().drivingLicense("ME")).matches("(^\\d{7,8}$)|(^\\d{7}[A-Z]$)");
    }

    @RepeatedTest(100)
    void drivingLicense_MD() {
        assertThat(faker.drivingLicense().drivingLicense("MD")).matches("^[A-Z]\\d{12}$");
    }

    @RepeatedTest(100)
    void drivingLicense_MA() {
        assertThat(faker.drivingLicense().drivingLicense("MA")).matches("(^[A-Z]\\d{8}$)|(^\\d{9}$)");
    }

    @RepeatedTest(100)
    void drivingLicense_MI() {
        assertThat(faker.drivingLicense().drivingLicense("MI")).matches("(^[A-Z]\\d{10}$)|(^[A-Z]\\d{12}$)");
    }

    @RepeatedTest(100)
    void drivingLicense_MN() {
        assertThat(faker.drivingLicense().drivingLicense("MN")).matches("^[A-Z]\\d{12}$");
    }

    @RepeatedTest(100)
    void drivingLicense_MS() {
        assertThat(faker.drivingLicense().drivingLicense("MS")).matches("^\\d{9}$");
    }

    @RepeatedTest(100)
    void drivingLicense_MO() {
        assertThat(faker.drivingLicense().drivingLicense("MO")).matches("(^[A-Z]\\d{5,9}$)|(^[A-Z]\\d{6}R$)|(^\\d{3}[A-Z]\\d{6}$)|(^\\d{8}[A-Z]{2}$)|(^\\d{9}[A-Z]$)|(^\\d{9}$)");
    }

    @RepeatedTest(100)
    void drivingLicense_MT() {
        assertThat(faker.drivingLicense().drivingLicense("MT")).matches("(^[A-Z]\\d{8}$)|(^\\d{13}$)|(^\\d{14}$)|(^\\d{9}$)");
    }

    @RepeatedTest(100)
    void drivingLicense_NE() {
        assertThat(faker.drivingLicense().drivingLicense("NE")).matches("^[A-Z]\\d{6,8}$");
    }

    @RepeatedTest(100)
    void drivingLicense_NV() {
        assertThat(faker.drivingLicense().drivingLicense("NV")).matches("(^\\d{9,10}$)|(^\\d{12}$)|(^X\\d{8}$)");
    }

    @RepeatedTest(100)
    void drivingLicense_NH() {
        assertThat(faker.drivingLicense().drivingLicense("NH")).matches("(^\\d{2}[A-Z]{3}\\d{5}$)");
    }

    @RepeatedTest(100)
    void drivingLicense_NJ() {
        assertThat(faker.drivingLicense().drivingLicense("NJ")).matches("^[A-Z]\\d{14}$");
    }

    @RepeatedTest(100)
    void drivingLicense_NM() {
        assertThat(faker.drivingLicense().drivingLicense("NM")).matches("^\\d{8,9}$");
    }

    @RepeatedTest(100)
    void drivingLicense_NY() {
        assertThat(faker.drivingLicense().drivingLicense("NY")).matches("(^[A-Z]\\d{7}$)|(^[A-Z]\\d{18}$)|(^\\d{8}$)|(^\\d{9}$)|(^\\d{16}$)|(^[A-Z]{8}$)");
    }

    @RepeatedTest(100)
    void drivingLicense_NC() {
        assertThat(faker.drivingLicense().drivingLicense("NC")).matches("^\\d{10,12}$");
    }

    @RepeatedTest(100)
    void drivingLicense_ND() {
        assertThat(faker.drivingLicense().drivingLicense("ND")).matches("(^[A-Z]{3}\\d{6}$)|(^\\d{9}$)");
    }

    @RepeatedTest(100)
    void drivingLicense_OH() {
        assertThat(faker.drivingLicense().drivingLicense("OH")).matches("(^[A-Z]\\d{8}$)|(^[A-Z]{2}\\d{7}$)|(^\\d{8}$)");
    }

    @RepeatedTest(100)
    void drivingLicense_OK() {
        assertThat(faker.drivingLicense().drivingLicense("OK")).matches("(^[A-Z]\\d{9}$)|(^\\d{9}$)");
    }

    @RepeatedTest(100)
    void drivingLicense_OR() {
        assertThat(faker.drivingLicense().drivingLicense("OR")).matches("^\\d{8,9}$");
    }

    @RepeatedTest(100)
    void drivingLicense_PA() {
        assertThat(faker.drivingLicense().drivingLicense("PA")).matches("^\\d{8}$");
    }

    @RepeatedTest(100)
    void drivingLicense_RI() {
        assertThat(faker.drivingLicense().drivingLicense("RI")).matches("^(\\d{7}$)|(^[A-Z]\\d{6}$)");
    }

    @RepeatedTest(100)
    void drivingLicense_SC() {
        assertThat(faker.drivingLicense().drivingLicense("SC")).matches("^\\d{8,11}$");
    }

    @RepeatedTest(100)
    void drivingLicense_SD() {
        assertThat(faker.drivingLicense().drivingLicense("SD")).matches("(^\\d{8,10}$)|(^\\d{12}$)");
    }

    @RepeatedTest(100)
    void drivingLicense_TN() {
        assertThat(faker.drivingLicense().drivingLicense("TN")).matches("^\\d{7,9}$");
    }

    @RepeatedTest(100)
    void drivingLicense_TX() {
        assertThat(faker.drivingLicense().drivingLicense("TX")).matches("^\\d{7,8}$");
    }

    @RepeatedTest(100)
    void drivingLicense_UT() {
        assertThat(faker.drivingLicense().drivingLicense("UT")).matches("^\\d{9,10}$");
    }

    @RepeatedTest(100)
    void drivingLicense_VT() {
        assertThat(faker.drivingLicense().drivingLicense("VT")).matches("(^\\d{8}$)|(^\\d{7}A$)");
    }

    @RepeatedTest(100)
    void drivingLicense_VA() {
        assertThat(faker.drivingLicense().drivingLicense("VA")).matches("(^[A-Z]\\d{8,11}$)|(^\\d{9}$)");
    }

    @RepeatedTest(100)
    void drivingLicense_WA() {
        assertThat(faker.drivingLicense().drivingLicense("WA")).matches("(^[A-Z]{7}\\d{5}$)|(^[A-Z]{8}\\d{4}$)|(^[A-Z]{9}\\d{3}$)|(^[A-Z]{10}\\d{2}$)|(^[A-Z]{11}\\d$)|(^[A-Z]{12}$)|(^[A-Z]{7}\\d[A-Z]\\d[A-Z]\\d$)|(^[A-Z]{7}\\d{2}[A-Z]{2}\\d$)");
    }

    @RepeatedTest(100)
    void drivingLicense_WV() {
        assertThat(faker.drivingLicense().drivingLicense("WV")).matches("(^\\d{7}$)|(^[A-Z]{1,2}\\d{5,6}$)");
    }

    @RepeatedTest(100)
    void drivingLicense_WI() {
        assertThat(faker.drivingLicense().drivingLicense("WI")).matches("^[A-Z]\\d{13}$");
    }

    @RepeatedTest(100)
    void drivingLicense_WY() {
        assertThat(faker.drivingLicense().drivingLicense("WY")).matches("^\\d{9,10}$");
    }
}
