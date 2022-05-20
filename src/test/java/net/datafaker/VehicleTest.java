package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class VehicleTest extends AbstractFakerTest {

    private static final String WORD_MATCH = "\\w+\\.?";
    private static final String WORDS_MATCH = "^[a-zA-Z\\d_/ -]*$";
    private static final String INTERNATIONAL_WORDS_MATCH = "\\P{Cc}+";

    @RepeatedTest(10)
    void testVin() {
        assertThat(faker.vehicle().vin()).matches(Vehicle.VIN_REGEX);
    }

    @Test
    void testManufacturer() {
        assertThat(faker.vehicle().manufacturer()).matches(INTERNATIONAL_WORDS_MATCH);
    }

    @Test
    void testMake() {
        assertThat(faker.vehicle().make()).matches(WORD_MATCH);
    }

    @Test
    void testModel() {
        assertThat(faker.vehicle().model()).matches(WORDS_MATCH);
    }

    @Test
    void testModelWithParams() {
        assertThat(faker.vehicle().model("Toyota")).matches(WORDS_MATCH);
    }

    @Test
    void testMakeAndModel() {
        assertThat(faker.vehicle().makeAndModel()).matches(WORDS_MATCH);
    }

    @Test
    void testStyle() {
        assertThat(faker.vehicle().style()).matches(WORD_MATCH);
    }

    @Test
    void testColor() {
        assertThat(faker.vehicle().color()).matches(WORD_MATCH);
    }

    @Test
    void testTransmission() {
        assertThat(faker.vehicle().transmission()).matches(WORD_MATCH);
    }

    @Test
    void testDriveType() {
        assertThat(faker.vehicle().driveType()).matches(WORDS_MATCH);
    }

    @Test
    void testFuelType() {
        assertThat(faker.vehicle().fuelType()).matches(WORDS_MATCH);
    }

    @Test
    void testCarType() {
        assertThat(faker.vehicle().carType()).matches(WORDS_MATCH);
    }

    @Test
    void testEngine() {
        assertThat(faker.vehicle().engine()).matches("\\d Cylinder Engine");
    }

    @Test
    void testCarOptions() {
        List<String> carOptions = faker.vehicle().carOptions();
        assertThat(carOptions)
            .hasSizeGreaterThanOrEqualTo(5)
            .hasSizeLessThanOrEqualTo(10);
    }

    @Test
    void testCarOptionsMinMax() {
        List<String> carOptions = faker.vehicle().carOptions(11, 12);

        assertThat(carOptions)
            .hasSizeGreaterThanOrEqualTo(11)
            .hasSizeLessThanOrEqualTo(12);

        assertThat(carOptions.get(0)).isNotNull();
    }

    @Test
    void testStandardSpecsMinMax() {
        List<String> standardSpecs = faker.vehicle().standardSpecs(13, 14);

        assertThat(standardSpecs)
            .hasSizeGreaterThanOrEqualTo(13)
            .hasSizeLessThanOrEqualTo(14);
    }

    @Test
    void testStandardSpecs() {
        List<String> standardSpecs = faker.vehicle().standardSpecs();

        assertThat(standardSpecs)
            .hasSizeGreaterThanOrEqualTo(5)
            .hasSizeLessThanOrEqualTo(10);
    }

    @Test
    void testDoor() {
        assertThat(faker.vehicle().doors()).matches("\\d");
    }

    @Test
    void testLicensePlate() {
        assertThat(faker.vehicle().licensePlate()).matches(WORDS_MATCH);
    }

    @Test
    void testLicensePlateWithParam() {
        assertThat(faker.vehicle().licensePlate("GA")).matches(WORDS_MATCH);
        assertThat(faker.vehicle().licensePlate("AL")).matches(WORDS_MATCH);
    }

    @RepeatedTest(100)
    void testLicensePlateWithParam_Canada() {
        Faker test = new Faker(Locale.CANADA);
        assertThat(test.vehicle().licensePlate("MB")).matches(WORDS_MATCH);
        assertThat(test.vehicle().licensePlate("ON")).matches(WORDS_MATCH);
    }
}
