package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class VehicleTest extends AbstractFakerTest {

    private static final String WORD_MATCH = "\\w+\\.?";
    private static final String WORDS_MATCH = "^[a-zA-Z0-9_/ -]*$";
    private static final String INTERNATIONAL_WORDS_MATCH = "\\P{Cc}+";

    @RepeatedTest(10)
    public void testVin() {
        assertThat(faker.vehicle().vin()).matches(Vehicle.VIN_REGEX);
    }

    @Test
    public void testManufacturer() {
        assertThat(faker.vehicle().manufacturer()).matches(INTERNATIONAL_WORDS_MATCH);
    }

    @Test
    public void testMake() {
        assertThat(faker.vehicle().make()).matches(WORD_MATCH);
    }

    @Test
    public void testModel() {
        assertThat(faker.vehicle().model()).matches(WORDS_MATCH);
    }

    @Test
    public void testModelWithParams() {
        assertThat(faker.vehicle().model("Toyota")).matches(WORDS_MATCH);
    }

    @Test
    public void testMakeAndModel() {
        assertThat(faker.vehicle().makeAndModel()).matches(WORDS_MATCH);
    }

    @Test
    public void testStyle() {
        assertThat(faker.vehicle().style()).matches(WORD_MATCH);
    }

    @Test
    public void testColor() {
        assertThat(faker.vehicle().color()).matches(WORD_MATCH);
    }

    @Test
    public void testTransmission() {
        assertThat(faker.vehicle().transmission()).matches(WORD_MATCH);
    }

    @Test
    public void testDriveType() {
        assertThat(faker.vehicle().driveType()).matches(WORDS_MATCH);
    }

    @Test
    public void testFuelType() {
        assertThat(faker.vehicle().fuelType()).matches(WORDS_MATCH);
    }

    @Test
    public void testCarType() {
        assertThat(faker.vehicle().carType()).matches(WORDS_MATCH);
    }

    @Test
    public void testEngine() {
        assertThat(faker.vehicle().engine()).matches("\\d Cylinder Engine");
    }

    @Test
    public void testCarOptions() {
        List<String> carOptions = faker.vehicle().carOptions();
        assertThat(carOptions.size()).isGreaterThanOrEqualTo(5);
        assertThat(carOptions.size()).isLessThanOrEqualTo(10);
    }

    @Test
    public void testCarOptionsMinMax() {
        List<String> carOptions = faker.vehicle().carOptions(11, 12);
        assertThat(carOptions.size()).isGreaterThanOrEqualTo(11);
        assertThat(carOptions.size()).isLessThanOrEqualTo(12);
        assertThat(carOptions.get(0)).isNotNull();
    }

    @Test
    public void testStandardSpecsMinMax() {
        List<String> standardSpecs = faker.vehicle().standardSpecs(13, 14);
        assertThat(standardSpecs.size()).isGreaterThanOrEqualTo(13);
        assertThat(standardSpecs.size()).isLessThanOrEqualTo(14);
    }

    @Test
    public void testStandardSpecs() {
        List<String> standardSpecs = faker.vehicle().standardSpecs();
        assertThat(standardSpecs.size()).isGreaterThanOrEqualTo(5);
        assertThat(standardSpecs.size()).isLessThanOrEqualTo(10);
    }

    @Test
    public void testDoor() {
        assertThat(faker.vehicle().doors()).matches("\\d");
    }


    @Test
    public void testLicensePlate() {
        assertThat(faker.vehicle().licensePlate()).matches(WORDS_MATCH);
    }

    @Test
    public void testLicensePlateWithParam() {
        assertThat(faker.vehicle().licensePlate("GA")).matches(WORDS_MATCH);
        assertThat(faker.vehicle().licensePlate("AL")).matches(WORDS_MATCH);
    }

    @RepeatedTest(100)
    public void testLicensePlateWithParam_Canada() {
        Faker test = new Faker(Locale.CANADA);
        assertThat(test.vehicle().licensePlate("MB")).matches(WORDS_MATCH);
        assertThat(test.vehicle().licensePlate("ON")).matches(WORDS_MATCH);
    }
}
