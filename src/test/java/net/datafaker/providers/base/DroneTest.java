package net.datafaker.providers.base;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DroneTest extends net.datafaker.AbstractFakerTest {

    @Test
    void name() {
        assertThat(faker.drone().name()).isNotEmpty();
    }

    @Test
    void weight() {
        assertThat(faker.drone().weight()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void maxAscentSpeed() {
        assertThat(faker.drone().maxAscentSpeed()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void maxDescentSpeed() {
        assertThat(faker.drone().maxDescentSpeed()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void flightTime() {
        assertThat(faker.drone().flightTime()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void maxAltitude() {
        assertThat(faker.drone().maxAltitude()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void maxFlightDistance() {
        assertThat(faker.drone().maxFlightDistance()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void maxSpeed() {
        assertThat(faker.drone().maxSpeed()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void maxWindResistance() {
        assertThat(faker.drone().maxWindResistance()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void maxAngularVelocity() {
        assertThat(faker.drone().maxAngularVelocity()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void maxTiltAngle() {
        assertThat(faker.drone().maxTiltAngle()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void operatingTemperature() {
        assertThat(faker.drone().operatingTemperature()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void batteryCapacity() {
        assertThat(faker.drone().batteryCapacity()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void batteryVoltage() {
        assertThat(faker.drone().batteryVoltage()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void batteryType() {
        assertThat(faker.drone().batteryType()).isNotEmpty();
    }

    @Test
    void batteryWeight() {
        assertThat(faker.drone().batteryWeight()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void chargingTemperature() {
        assertThat(faker.drone().chargingTemperature()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void maxChargingPower() {
        assertThat(faker.drone().maxChargingPower()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void iso() {
        assertThat(faker.drone().iso()).isNotEmpty();
    }

    @Test
    void maxResolution() {
        assertThat(faker.drone().maxResolution()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void photoFormat() {
        assertThat(faker.drone().photoFormat()).isNotEmpty();
    }

    @Test
    void videoFormat() {
        assertThat(faker.drone().videoFormat()).isNotEmpty();
    }

    @Test
    void maxShutterSpeed() {
        assertThat(faker.drone().maxShutterSpeed()).isNotEmpty();
    }

    @Test
    void minShutterSpeed() {
        assertThat(faker.drone().minShutterSpeed()).isNotEmpty();
    }

    @Test
    void shutterSpeedUnits() {
        assertThat(faker.drone().shutterSpeedUnits()).isNotEmpty();
    }

}
