package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class VehicleTest extends BaseFakerTest<BaseFaker> {

    private static final String WORD_MATCH = "\\w+\\.?";
    private static final String WORDS_MATCH = "^[a-zA-Z\\d_/ -]*$";
    private static final String INTERNATIONAL_WORDS_MATCH = "\\P{Cc}+";

    @RepeatedTest(10)
    void vin() {
        assertThat(faker.vehicle().vin()).matches(Vehicle.VIN_REGEX);
    }

    @RepeatedTest(10)
    void manufacturer() {
        assertThat(faker.vehicle().manufacturer()).matches(INTERNATIONAL_WORDS_MATCH);
    }

    @RepeatedTest(10)
    void make() {
        assertThat(faker.vehicle().make()).matches(INTERNATIONAL_WORDS_MATCH);
    }

    @RepeatedTest(10)
    void model() {
        assertThat(faker.vehicle().model()).matches(INTERNATIONAL_WORDS_MATCH);
    }

    @RepeatedTest(10)
    void modelWithParams() {
        assertThat(faker.vehicle().model("Toyota")).matches(INTERNATIONAL_WORDS_MATCH);
    }

    @RepeatedTest(10)
    void makeAndModel() {
        assertThat(faker.vehicle().makeAndModel()).matches(INTERNATIONAL_WORDS_MATCH);
    }

    @RepeatedTest(10)
    void style() {
        assertThat(faker.vehicle().style()).matches(WORD_MATCH);
    }

    @RepeatedTest(10)
    void color() {
        assertThat(faker.vehicle().color()).matches(WORD_MATCH);
    }

    @RepeatedTest(10)
    void upholsteryColor() {
        assertThat(faker.vehicle().upholsteryColor()).matches(WORD_MATCH);
    }

    @RepeatedTest(10)
    void upholsteryFabric() {
        assertThat(faker.vehicle().upholsteryFabric()).matches(WORDS_MATCH);
    }

    @RepeatedTest(10)
    void upholstery() {
        assertThat(faker.vehicle().upholstery()).matches(WORDS_MATCH);
    }

    @RepeatedTest(10)
    void transmission() {
        assertThat(faker.vehicle().transmission()).matches(WORD_MATCH);
    }

    @RepeatedTest(10)
    void driveType() {
        assertThat(faker.vehicle().driveType()).matches(WORDS_MATCH);
    }

    @RepeatedTest(10)
    void fuelType() {
        assertThat(faker.vehicle().fuelType()).matches(WORDS_MATCH);
    }

    @RepeatedTest(10)
    void carType() {
        assertThat(faker.vehicle().carType()).matches(WORDS_MATCH);
    }

    @RepeatedTest(10)
    void engine() {
        assertThat(faker.vehicle().engine()).matches("\\d Cylinder Engine");
    }

    @RepeatedTest(10)
    void carOptions() {
        List<String> carOptions = faker.vehicle().carOptions();
        assertThat(carOptions)
            .hasSizeGreaterThanOrEqualTo(5)
            .hasSizeLessThanOrEqualTo(10);
    }

    @RepeatedTest(10)
    void carOptionsMinMax() {
        List<String> carOptions = faker.vehicle().carOptions(11, 12);

        assertThat(carOptions)
            .hasSizeGreaterThanOrEqualTo(11)
            .hasSizeLessThanOrEqualTo(12);

        assertThat(carOptions.get(0)).isNotNull();
    }

    @RepeatedTest(10)
    void standardSpecsMinMax() {
        List<String> standardSpecs = faker.vehicle().standardSpecs(13, 14);

        assertThat(standardSpecs)
            .hasSizeGreaterThanOrEqualTo(13)
            .hasSizeLessThanOrEqualTo(14);
    }

    @RepeatedTest(10)
    void standardSpecs() {
        List<String> standardSpecs = faker.vehicle().standardSpecs();

        assertThat(standardSpecs)
            .hasSizeGreaterThanOrEqualTo(5)
            .hasSizeLessThanOrEqualTo(10);
    }

    @RepeatedTest(10)
    void door() {
        assertThat(faker.vehicle().doors()).matches("\\d");
    }

    @RepeatedTest(10)
    void licensePlate() {
        assertThat(faker.vehicle().licensePlate()).matches(WORDS_MATCH);
    }

    @RepeatedTest(10)
    void licensePlateWithParam() {
        assertThat(faker.vehicle().licensePlate("GA")).matches(WORDS_MATCH);
        assertThat(faker.vehicle().licensePlate("AL")).matches(WORDS_MATCH);
    }

    @RepeatedTest(100)
    void licensePlateWithParam_Canada() {
        BaseFaker test = new BaseFaker(Locale.CANADA);
        assertThat(test.vehicle().licensePlate("MB")).matches(WORDS_MATCH);
        assertThat(test.vehicle().licensePlate("ON")).matches(WORDS_MATCH);
    }
}
