package net.datafaker.providers.base;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class DrivingLicenseTest extends BaseFakerTest<BaseFaker> {
    private final DrivingLicense drivingLicense = getFaker().drivingLicense();

    private enum LicensePattern {
        AL("AL", "^\\d{6,8}$"),
        AK("AK", "^\\d{6,7}$"),
        AZ("AZ", "^([A-Z]?)\\d{8,9}$"),
        AR("AR", "^\\d{8,9}$"),
        CA("CA", "^[A-Z]\\d{7}$"),
        CO("CO", "(^[0-9]{9}$)|(^[A-Z]{1}[0-9]{3,6}$)|(^[A-Z]{2}[0-9]{2,5}$)"),
        CT("CT", "^\\d{9}$"),
        DE("DE", "^\\d{6,7}$"),
        DC("DC", "(^\\d{7}$)|(^\\d{9}$)"),
        FL("FL", "^[A-Z]\\d{12}$"),
        GA("GA", "^\\d{7,9}$"),
        HI("HI", "(^[A-Z]\\d{8}$)|(^\\d{9}$)"),
        ID("ID", "(^[A-Z]{2}\\d{6}[A-Z]$)|(^\\d{9}$)"),
        IL("IL", "^[A-Z]\\d{11,12}$"),
        IN("IN", "(^[A-Z]\\d{9}$)|(^\\d{9,10}$)"),
        IA("IA", "^(\\d{9}|(\\d{3}[A-Z]{2}\\d{4}))$"),
        KS("KS", "(^([A-Z]\\d){2}[A-Z]$)|(^[A-Z]\\d{8}$)|(^\\d{9}$)"),
        KY("KY", "(^[A-Z]\\d{8,9}$)|(^\\d{9}$)"),
        LA("LA", "^\\d{8,9}$"),
        MA("MA", "(^[A-Z]\\d{8}$)|(^\\d{9}$)"),
        MD("MD", "^[A-Z]\\d{12}$"),
        ME("ME", "(^\\d{7,8}$)|(^\\d{7}[A-Z]$)"),
        MI("MI", "(^[A-Z]\\d{10}$)|(^[A-Z]\\d{12}$)"),
        MN("MN", "^[A-Z]\\d{12}$"),
        MO("MO", "(^[A-Z]\\d{5,9}$)|(^[A-Z]\\d{6}R$)|(^\\d{3}[A-Z]\\d{6}$)|(^\\d{8}[A-Z]{2}$)|(^\\d{9}[A-Z]$)|(^\\d{9}$)"),
        MS("MS", "^\\d{9}$"),
        MT("MT", "(^[A-Z]\\d{8}$)|(^\\d{13}$)|(^\\d{14}$)|(^\\d{9}$)"),
        NC("NC", "^\\d{10,12}$"),
        ND("ND", "(^[A-Z]{3}\\d{6}$)|(^\\d{9}$)"),
        NE("NE", "^[A-Z]\\d{6,8}$"),
        NH("NH", "(^\\d{2}[A-Z]{3}\\d{5}$)"),
        NJ("NJ", "^[A-Z]\\d{14}$"),
        NM("NM", "^\\d{8,9}$"),
        NV("NV", "(^\\d{9,10}$)|(^\\d{12}$)|(^X\\d{8}$)"),
        NY("NY", "(^[A-Z]\\d{7}$)|(^[A-Z]\\d{18}$)|(^\\d{8}$)|(^\\d{9}$)|(^\\d{16}$)|(^[A-Z]{8}$)"),
        OH("OH", "(^[A-Z]\\d{8}$)|(^[A-Z]{2}\\d{7}$)|(^\\d{8}$)"),
        OK("OK", "(^[A-Z]\\d{9}$)|(^\\d{9}$)"),
        OR("OR", "^\\d{8,9}$"),
        PA("PA", "^\\d{8}$"),
        RI("RI", "^(\\d{7}$)|(^[A-Z]\\d{6}$)"),
        SC("SC", "^\\d{8,11}$"),
        SD("SD", "(^\\d{8,10}$)|(^\\d{12}$)"),
        TN("TN", "^\\d{7,9}$"),
        TX("TX", "^\\d{7,8}$"),
        UT("UT", "^\\d{9,10}$"),
        VA("VA", "(^[A-Z]\\d{8,11}$)|(^\\d{9}$)"),
        VT("VT", "(^\\d{8}$)|(^\\d{7}A$)"),
        WA("WA", "(^[A-Z]{7}\\d{5}$)|(^[A-Z]{8}\\d{4}$)|(^[A-Z]{9}\\d{3}$)|(^[A-Z]{10}\\d{2}$)|(^[A-Z]{11}\\d$)|(^[A-Z]{12}$)|(^[A-Z]{7}\\d[A-Z]\\d[A-Z]\\d$)|(^[A-Z]{7}\\d{2}[A-Z]{2}\\d$)"),
        WI("WI", "^[A-Z]\\d{13}$"),
        WV("WV", "(^\\d{7}$)|(^[A-Z]{1,2}\\d{5,6}$)"),
        WY("WY", "^\\d{9,10}$");
        private final String abbv;
        private final Pattern pattern;

        LicensePattern(String abbv, String regex) {
            this.abbv = abbv;
            this.pattern = Pattern.compile(regex);
        }
    }


    @ParameterizedTest
    @EnumSource(LicensePattern.class)
    void drivingLicense(LicensePattern licensePattern) {
        for (int i = 0; i < 100; i++) {
            assertThat(drivingLicense.drivingLicense(licensePattern.abbv)).matches(licensePattern.pattern);
        }
    }
}
