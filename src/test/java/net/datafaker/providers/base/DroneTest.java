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
        assertThat(faker.drone().weight()).isNotEmpty();
    }

    @Test
    void maxAscentSpeed() {
        assertThat(faker.drone().maxAscentSpeed()).isNotEmpty();
    }

    @Test
    void maxDescentSpeed() {
        assertThat(faker.drone().maxDescentSpeed()).isNotEmpty();
    }

    @Test
    void flightTime() {
        assertThat(faker.drone().flightTime()).isNotEmpty();
    }

    @Test
    void maxAltitude() {
        assertThat(faker.drone().maxAltitude()).isNotEmpty();
    }

    @Test
    void maxFlightDistance() {
        assertThat(faker.drone().maxFlightDistance()).isNotEmpty();
    }

    @Test
    void maxSpeed() {
        assertThat(faker.drone().maxSpeed()).isNotEmpty();
    }

    @Test
    void maxWindResistance() {
        assertThat(faker.drone().maxWindResistance()).isNotEmpty();
    }

    @Test
    void maxAngularVelocity() {
        assertThat(faker.drone().maxAngularVelocity()).isNotEmpty();
    }

    @Test
    void maxTiltAngle() {
        assertThat(faker.drone().maxTiltAngle()).isNotEmpty();
    }

    @Test
    void operatingTemperature() {
        assertThat(faker.drone().operatingTemperature()).isNotEmpty();
    }

    @Test
    void batteryCapacity() {
        assertThat(faker.drone().batteryCapacity()).isNotEmpty();
    }

    @Test
    void batteryVoltage() {
        assertThat(faker.drone().batteryVoltage()).isNotEmpty();
    }

    @Test
    void batteryType() {
        assertThat(faker.drone().batteryType()).isNotEmpty();
    }

    @Test
    void batteryWeight() {
        assertThat(faker.drone().batteryWeight()).isNotEmpty();
    }

    @Test
    void chargingTemperature() {
        assertThat(faker.drone().chargingTemperature()).isNotEmpty();
    }

    @Test
    void maxChargingPower() {
        assertThat(faker.drone().maxChargingPower()).isNotEmpty();
    }

    @Test
    void iso() {
        assertThat(faker.drone().iso()).isNotEmpty();
    }

    @Test
    void maxResolution() {
        assertThat(faker.drone().maxResolution()).isNotEmpty();
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
