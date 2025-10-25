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
    private final Faker faker = new Faker();

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
    void testDriveType() {
        assertThat(faker.vehicle().driveType()).matches(WORDS_MATCH);
    }

    @RepeatedTest(10)
    void testFuelType() {
        assertThat(faker.vehicle().fuelType()).matches(WORDS_MATCH);
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
}
