package net.datafaker.providers.base;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import java.util.Collection;

class DroneTest extends BaseFakerTest<BaseFaker> {

    private final Drone drone = faker.drone();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(drone::name, "drone.name"),
                TestSpec.of(drone::batteryType, "drone.battery_type"),
                TestSpec.of(drone::iso, "drone.iso"),
                TestSpec.of(drone::photoFormat, "drone.photo_format"),
                TestSpec.of(drone::videoFormat, "drone.video_format"),
                TestSpec.of(drone::maxShutterSpeed, "drone.max_shutter_speed"),
                TestSpec.of(drone::minShutterSpeed, "drone.min_shutter_speed"),
                TestSpec.of(drone::shutterSpeedUnits, "drone.shutter_speed_units"));
    }

    @Test
    void weight() {
        assertThat(drone.weight()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void maxAscentSpeed() {
        assertThat(drone.maxAscentSpeed()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void maxDescentSpeed() {
        assertThat(drone.maxDescentSpeed()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void flightTime() {
        assertThat(drone.flightTime()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void maxAltitude() {
        assertThat(drone.maxAltitude()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void maxFlightDistance() {
        assertThat(drone.maxFlightDistance()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void maxSpeed() {
        assertThat(drone.maxSpeed()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void maxWindResistance() {
        assertThat(drone.maxWindResistance()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void maxAngularVelocity() {
        assertThat(drone.maxAngularVelocity()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void maxTiltAngle() {
        assertThat(drone.maxTiltAngle()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void operatingTemperature() {
        assertThat(drone.operatingTemperature()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void batteryCapacity() {
        assertThat(drone.batteryCapacity()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void batteryVoltage() {
        assertThat(drone.batteryVoltage()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void batteryWeight() {
        assertThat(drone.batteryWeight()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void chargingTemperature() {
        assertThat(drone.chargingTemperature()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void maxChargingPower() {
        assertThat(drone.maxChargingPower()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void maxResolution() {
        assertThat(drone.maxResolution()).isNotEmpty().doesNotContain("#");
    }

}
