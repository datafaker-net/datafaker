package net.datafaker.providers.base;

import net.datafaker.annotations.Deterministic;

/**
 * An unmanned aerial vehicle (UAV), commonly known as a drone, is an aircraft without any human pilot, crew, or passengers on board.
 *
 * @since 1.7.0
 */
public class Drone extends AbstractProvider<BaseProviders> {

    protected Drone(BaseProviders faker) {
        super(faker);
    }

    public String name() {
        return resolve("drone.name");
    }

    public String weight() {
        return faker.numerify(resolve("drone.weight"));
    }

    public String maxAscentSpeed() {
        return faker.numerify(resolve("drone.max_ascent_speed"));
    }

    public String maxDescentSpeed() {
        return faker.numerify(resolve("drone.max_descent_speed"));
    }

    public String flightTime() {
        return faker.numerify(resolve("drone.flight_time"));
    }

    public String maxAltitude() {
        return faker.numerify(resolve("drone.max_altitude"));
    }

    public String maxFlightDistance() {
        return faker.numerify(resolve("drone.max_flight_distance"));
    }

    public String maxSpeed() {
        return faker.numerify(resolve("drone.max_speed"));
    }

    public String maxWindResistance() {
        return faker.numerify(resolve("drone.max_wind_resistance"));
    }

    public String maxAngularVelocity() {
        return faker.numerify(resolve("drone.max_angular_velocity"));
    }

    public String maxTiltAngle() {
        return faker.numerify(resolve("drone.max_tilt_angle"));
    }

    public String operatingTemperature() {
        return faker.numerify(resolve("drone.operating_temperature"));
    }

    public String batteryCapacity() {
        return faker.numerify(resolve("drone.battery_capacity"));
    }

    public String batteryVoltage() {
        return faker.numerify(resolve("drone.battery_voltage"));
    }

    public String batteryType() {
        return resolve("drone.battery_type");
    }

    public String batteryWeight() {
        return faker.numerify(resolve("drone.battery_weight"));
    }

    public String chargingTemperature() {
        return faker.numerify(resolve("drone.charging_temperature"));
    }

    public String maxChargingPower() {
        return faker.numerify(resolve("drone.max_charging_power"));
    }

    public String iso() {
        return resolve("drone.iso");
    }

    public String maxResolution() {
        return faker.numerify(resolve("drone.max_resolution"));
    }

    public String photoFormat() {
        return resolve("drone.photo_format");
    }

    public String videoFormat() {
        return resolve("drone.video_format");
    }

    public String maxShutterSpeed() {
        return resolve("drone.max_shutter_speed");
    }

    public String minShutterSpeed() {
        return resolve("drone.min_shutter_speed");
    }

    @Deterministic
    public String shutterSpeedUnits() {
        return resolve("drone.shutter_speed_units");
    }

}
