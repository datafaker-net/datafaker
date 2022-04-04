package net.datafaker;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @since 0.8.0
 */
public class Vehicle {
    private final Faker faker;

    static final String VIN_REGEX = "([A-HJ-NPR-Z0-9]){3}[A-HJ-NPR-Z0-9]{5}[A-HJ-NPR-Z0-9]{1}[A-HJ-NPR-Z0-9]{1}[A-HJ-NPR-Z0-0]{1}[A-HJ-NPR-Z0-9]{1}\\d{5}";

    public Vehicle(Faker faker) {
        this.faker = faker;
    }

    public String vin() {
        return faker.fakeValuesService().regexify(VIN_REGEX);
    }

    public String manufacturer() {
        return faker.resolve("vehicle.manufacture");
    }

    public String make() {
        return faker.resolve("vehicle.makes");
    }

    public String model() {
        return model(make());
    }

    public String model(String make) {
        return faker.resolve("vehicle.models_by_make." + make);
    }

    public String makeAndModel() {
        String make = make();
        return make + " " + model(make);
    }

    public String style() {
        return faker.resolve("vehicle.styles");
    }

    public String color() {
        return faker.resolve("vehicle.colors");
    }

    public String transmission() {
        return faker.resolve("vehicle.transmissions");
    }

    public String driveType() {
        return faker.resolve("vehicle.drive_types");
    }

    public String fuelType() {
        return faker.resolve("vehicle.fuel_types");
    }

    public String carType() {
        return faker.resolve("vehicle.car_types");
    }

    public String engine() {
        return faker.resolve("vehicle.engine_sizes")
            + " "
            + faker.resolve("vehicle.cylinder_engine");
    }

    public List<String> carOptions() {
        return carOptions(5, 10);
    }

    public List<String> carOptions(int min, int max) {
        int optionSize = faker.number().numberBetween(min, max);
        List<String> arr = new ArrayList<>(optionSize);
        while (optionSize > 0) {
            arr.add(faker.resolve("vehicle.car_options"));
            optionSize -= 1;
        }
        return arr;
    }

    public List<String> standardSpecs() {
        return standardSpecs(5, 10);
    }

    public List<String> standardSpecs(int min, int max) {
        int standardSpecsSize = faker.number().numberBetween(min, max);
        List<String> arr = new ArrayList<>(standardSpecsSize);
        while (standardSpecsSize > 0) {
            arr.add(faker.resolve("vehicle.standard_specs"));
            standardSpecsSize -= 1;
        }
        return arr;
    }

    public String doors() {
        return faker.resolve("vehicle.doors");
    }

    public String licensePlate() {
        return faker.regexify(faker.bothify(faker.resolve("vehicle.license_plate")));
    }

    public String licensePlate(String stateAbbreviation) {

        if ("".equals(stateAbbreviation)) {
            return null;
        }

        String licensePlatesByState = faker.fakeValuesService().resolve("vehicle.license_plate_by_state." + stateAbbreviation, this, faker);
        return licensePlatesByState == null ? null : faker.regexify(faker.bothify(licensePlatesByState)).toUpperCase(Locale.ROOT);
    }
}
