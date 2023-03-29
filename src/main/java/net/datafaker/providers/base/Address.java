package net.datafaker.providers.base;

import net.datafaker.annotations.Deterministic;

/**
 * @since 0.8.0
 */
public class Address extends AbstractProvider<BaseProviders> {
    protected Address(BaseProviders faker) {
        super(faker);
    }

    public String streetName() {
        return resolve("address.street_name");
    }

    public String streetAddressNumber() {
        return String.valueOf(faker.random().nextInt(1000));
    }

    public String streetAddress() {
        return resolve("address.street_address");
    }

    public String streetAddress(boolean includeSecondary) {
        String streetAddress = resolve("address.street_address");
        if (includeSecondary) {
            streetAddress = streetAddress + " " + secondaryAddress();
        }
        return streetAddress;
    }

    public String secondaryAddress() {
        return faker.numerify(resolve("address.secondary_address"));
    }

    /**
     * Returns a String representing a standard 5-digit zip code.
     *
     * @return a String representing a standard zip code
     */
    public String zipCode() {
        return faker.bothify(resolve("address.postcode"));
    }

    public String postcode() {
        return faker.bothify(resolve("address.postcode"));
    }

    /**
     * Returns a String representing a ZIP+4 code for greater geographic specificity.
     * This is a 9-digit zip code, but the String itself is 10 characters including the hyphen.
     *
     * @return a String representing a ZIP+4 code
     */
    public String zipCodePlus4() {
        return faker.bothify(resolve("address.postcode_plus_four"));
    }

    public String zipCodeByState(String stateAbbr) {
        return resolve("address.postcode_by_state." + stateAbbr);
    }

    public String countyByZipCode(String postCode) {
        return resolve("address.county_by_postcode." + postCode, () -> "County are not configured for postcode " + postCode);
    }

    public String streetSuffix() {
        return resolve("address.street_suffix");
    }

    @Deterministic
    public String streetPrefix() {
        return resolve("address.street_prefix");
    }

    public String citySuffix() {
        return resolve("address.city_suffix");
    }

    public String cityPrefix() {
        return resolve("address.city_prefix");
    }

    public String city() {
        return resolve("address.city");
    }

    public String cityName() {
        return resolve("address.city_name");
    }

    public String state() {
        return resolve("address.state");
    }

    public String stateAbbr() {
        return resolve("address.state_abbr");
    }

    /**
     * @return Returns the latitude, a number between -90 to 90.
     */
    public String latitude() {
        return String.format(faker.getContext().getLocale(), "%.8f", (faker.random().nextDouble() * 180) - 90);
    }

    /**
     * @return Returns the longitude, a number between -180 and 180
     */
    public String longitude() {
        return String.format(faker.getContext().getLocale(), "%.8f", (faker.random().nextDouble() * 360) - 180);
    }

    /**
     * @return Returns the lat/lon coordinates formatted as lat,lon.
     */
    public String latLon() {
        return latLon(",");
    }

    /**
     * @return Returns the lat/lon coordinates formatted as lat delimiter lon.
     */
    public String latLon(String delimiter) {
        return latitude() + delimiter + longitude();
    }

    /**
     * @return Returns the lat/lon coordinates formatted as lon,lat.
     */
    public String lonLat() {
        return lonLat(",");
    }

    /**
     * @return Returns the lat/lon coordinates formatted as lon delimiter lat.
     */
    public String lonLat(String delimiter) {
        return longitude() + delimiter + latitude();
    }

    public String timeZone() {
        return resolve("address.time_zone");
    }

    public String country() {
        return resolve("address.country");
    }

    public String countryCode() {
        return resolve("address.country_code");
    }

    public String buildingNumber() {
        return faker.numerify(resolve("address.building_number"));
    }

    public String fullAddress() {
        return resolve("address.full_address");
    }

    public String mailBox() {
        return faker.numerify(resolve("address.mail_box"));
    }
}
