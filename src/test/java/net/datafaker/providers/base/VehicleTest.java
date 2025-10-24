package net.datafaker.providers.base;

import net.datafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class VehicleTest {

    private static final String WORD_MATCH = "\\w+\\.?";
    private static final String WORDS_MATCH = "^[a-zA-Z\\d_/ -]*$";
    private static final String INTERNATIONAL_WORDS_MATCH = "\\P{Cc}+";
    private static final String IRISH_VEHICLE_LICENCE_PLATE_REGEX = "[0-9]{2}[1|2]-[A-Z]{1,2}-[0-9]{1,6}";
    private static final String UKRAINIAN_VEHICLE_LICENCE_PLATE_REGEX = "^(AA|KA|AB|KB|AC|KC|AE|KE|AH|KH|AI|KI|AK|KK|AM|KM|AO|KO|AP|KP|AT|KT|AX|KX|BA|HA|BB|HB|BC|HC|BE|HE|BH|HH|BI|HI|BK|HK|BM|HM|BO|HO|BT|HT|BX|HX|CA|IA|CB|IB|CE|IE|CH|IH)\\d{4}[A-Z]{2}$";
    private final Faker faker = new Faker();
    private final Faker fakerUA = new Faker(new Locale("uk", "UA"));

    @RepeatedTest(10)
    void testVin() {
        assertThat(faker.vehicle().vin()).matches(Vehicle.VIN_REGEX);
    }

    @RepeatedTest(10)
    void testManufacturer() {
        assertThat(faker.vehicle().manufacturer()).matches(INTERNATIONAL_WORDS_MATCH);
    }

    @RepeatedTest(10)
    void testMake() {
        assertThat(faker.vehicle().make()).matches(INTERNATIONAL_WORDS_MATCH);
    }

    @RepeatedTest(10)
    void testModel() {
        assertThat(faker.vehicle().model()).matches(INTERNATIONAL_WORDS_MATCH);
    }

    @RepeatedTest(10)
    void testModelWithParams() {
        assertThat(faker.vehicle().model("Toyota")).matches(INTERNATIONAL_WORDS_MATCH);
    }

    @RepeatedTest(10)
    void testMakeAndModel() {
        assertThat(faker.vehicle().makeAndModel()).matches(INTERNATIONAL_WORDS_MATCH);
    }

    @RepeatedTest(10)
    void testStyle() {
        assertThat(faker.vehicle().style()).matches(WORD_MATCH);
    }

    @RepeatedTest(10)
    void testColor() {
        assertThat(faker.vehicle().color()).matches(WORD_MATCH);
    }

    @RepeatedTest(10)
    void testUpholsteryColor() {
        assertThat(faker.vehicle().upholsteryColor()).matches(WORD_MATCH);
    }

    @RepeatedTest(10)
    void testUpholsteryFabric() {
        assertThat(faker.vehicle().upholsteryFabric()).matches(WORDS_MATCH);
    }

    @RepeatedTest(10)
    void testUpholstery() {
        assertThat(faker.vehicle().upholstery()).matches(WORDS_MATCH);
    }

    @RepeatedTest(10)
    void testTransmission() {
        assertThat(faker.vehicle().transmission()).matches(WORD_MATCH);
    }

    @RepeatedTest(10)
    void testTransmission_Ukraine() {
        assertThat(fakerUA.vehicle().transmission()).matches(INTERNATIONAL_WORDS_MATCH);
    }

    @RepeatedTest(10)
    void testDriveType() {
        assertThat(faker.vehicle().driveType()).matches(WORDS_MATCH);
    }

    @RepeatedTest(10)
    void testFuelType() {
        assertThat(faker.vehicle().fuelType()).matches(WORDS_MATCH);
    }

    @RepeatedTest(10)
    void testFuelType_Ukraine() {
        assertThat(fakerUA.vehicle().fuelType()).matches(INTERNATIONAL_WORDS_MATCH);
    }

    @RepeatedTest(10)
    void testCarType() {
        assertThat(faker.vehicle().carType()).matches(WORDS_MATCH);
    }

    @RepeatedTest(10)
    void testEngine() {
        assertThat(faker.vehicle().engine()).matches("\\d Cylinder Engine");
    }

    @RepeatedTest(10)
    void testCarOptions() {
        List<String> carOptions = faker.vehicle().carOptions();
        assertThat(carOptions)
            .hasSizeGreaterThanOrEqualTo(5)
            .hasSizeLessThanOrEqualTo(10);
    }

    @RepeatedTest(10)
    void testCarOptionsMinMax() {
        List<String> carOptions = faker.vehicle().carOptions(11, 12);

        assertThat(carOptions)
            .hasSizeGreaterThanOrEqualTo(11)
            .hasSizeLessThanOrEqualTo(12);

        assertThat(carOptions.get(0)).isNotNull();
    }

    @RepeatedTest(10)
    void testStandardSpecsMinMax() {
        List<String> standardSpecs = faker.vehicle().standardSpecs(13, 14);

        assertThat(standardSpecs)
            .hasSizeGreaterThanOrEqualTo(13)
            .hasSizeLessThanOrEqualTo(14);
    }

    @RepeatedTest(10)
    void testStandardSpecs() {
        List<String> standardSpecs = faker.vehicle().standardSpecs();

        assertThat(standardSpecs)
            .hasSizeGreaterThanOrEqualTo(5)
            .hasSizeLessThanOrEqualTo(10);
    }

    @RepeatedTest(10)
    void testDoor() {
        assertThat(faker.vehicle().doors()).matches("\\d");
    }

    @RepeatedTest(10)
    void testLicensePlate() {
        assertThat(faker.vehicle().licensePlate()).matches(WORDS_MATCH);
    }

    @RepeatedTest(10)
    void testLicensePlateWithParam() {
        assertThat(faker.vehicle().licensePlate("GA")).matches(WORDS_MATCH);
        assertThat(faker.vehicle().licensePlate("AL")).matches(WORDS_MATCH);
    }

    @RepeatedTest(10)
    void testLicensePlateWithParam_Canada() {
        BaseFaker test = new BaseFaker(Locale.CANADA);
        assertThat(test.vehicle().licensePlate("MB")).matches(WORDS_MATCH);
        assertThat(test.vehicle().licensePlate("ON")).matches(WORDS_MATCH);
    }

    @RepeatedTest(10)
    void testLicensePlate_Ireland() {
        Faker faker = new Faker(new Locale("en", "IE"));
        assertThat(faker.vehicle().licensePlate()).matches(IRISH_VEHICLE_LICENCE_PLATE_REGEX);
    }

    @RepeatedTest(10)
    void testLicensePlate_Ukraine() {
        assertThat(fakerUA.vehicle().licensePlate()).matches(UKRAINIAN_VEHICLE_LICENCE_PLATE_REGEX);
    }
}
