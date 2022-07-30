package net.datafaker;

import net.datafaker.passportnumbers.AmPassportNumber;
import net.datafaker.passportnumbers.ChPassportNumber;

/**
 * @since 0.9.0
 */
public class Passport extends AbstractProvider {

    protected Passport(Faker faker) {
        super(faker);
    }

    public String chValid() {
        ChPassportNumber chPassportNumber = new ChPassportNumber();
        return chPassportNumber.getValidCh(faker);
    }

    public String chInvalid() {
        ChPassportNumber chPassportNumber = new ChPassportNumber();
        return chPassportNumber.getInvalidCh(faker);
    }

    public String amValid() {
        AmPassportNumber amPassportNumber = new AmPassportNumber();
        return amPassportNumber.getValidAm(faker);
    }

    public String amInvalid() {
        AmPassportNumber amPassportNumber = new AmPassportNumber();
        return amPassportNumber.getInvalidAm(faker);
    }
}
