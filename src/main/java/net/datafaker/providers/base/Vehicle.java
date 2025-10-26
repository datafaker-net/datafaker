package net.datafaker.providers.base;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @since 0.8.0
 */
public class Vehicle extends AbstractProvider<BaseProviders> {

    static final String VIN_REGEX = "([A-HJ-NPR-Z0-9]){3}[A-HJ-NPR-Z0-9]{5}[A-HJ-NPR-Z0-9]{1}[A-HJ-NPR-Z0-9]{1}[A-HJ-NPR-Z0-0]{1}[A-HJ-NPR-Z0-9]{1}\\d{5}";

    public Vehicle(BaseProviders faker) {
        super(faker);
    }

    public String vin() {
        return faker.regexify(VIN_REGEX);
    }

    public String manufacturer() {
        return resolve("vehicle.manufacture");
    }

    public String make() {
        return resolve("vehicle.makes");
    }

    public String model() {
        return model(make());
    }

    public String model(String make) {
        return resolve("vehicle.models_by_make." + make);
    }

    public String makeAndModel() {
        String make = make();
        return make + " " + model(make);
    }

    public String style() {
        return resolve("vehicle.styles");
    }

    public String color() {
        return resolve("vehicle.colors");
    }

    public String upholsteryColor() {
        return resolve("vehicle.upholstery_colors");
    }

    public String upholsteryFabric() {
        return resolve("vehicle.upholstery_fabrics");
    }

    public String upholstery() {
        return resolve("vehicle.upholsteries");
    }

    public String transmission() {
        return resolve("vehicle.transmissions");
    }

    public String driveType() {
        return resolve("vehicle.drive_types");
    }

    public String fuelType() {
        return resolve("vehicle.fuel_types");
    }

    public String carType() {
        return resolve("vehicle.car_types");
    }

    public String engine() {
        return resolve("vehicle.engine_sizes")
            + " "
            + resolve("vehicle.cylinder_engine");
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
        return resolve("vehicle.doors");
    }

    public String licensePlate() {
        return faker.bothify(faker.resolve("vehicle.license_plate")).toUpperCase(Locale.ROOT);
    }

    public String licensePlate(String stateAbbreviation) {

        if ("".equals(stateAbbreviation)) {
            return null;
        }

        String licensePlatesByState = resolve("vehicle.license_plate_by_state." + stateAbbreviation);
        return licensePlatesByState == null ? null : faker.bothify(licensePlatesByState).toUpperCase(Locale.ROOT);
    }
}
