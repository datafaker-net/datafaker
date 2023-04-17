package net.datafaker.providers.base;

import java.util.List;

/**
 * Generates aviation related strings.
 *
 * @since 0.8.0
 */
public class Aviation extends AbstractProvider<BaseProviders> {

    protected Aviation(BaseProviders faker) {
        super(faker);
    }

    /**
     * @return one of the 6 types of aircraft:
     * airplane, warplane, general, cargo, army helicopter, civil helicopter.
     */
    public String aircraft() {
        return resolve(List.of("aviation.aircraft.airplane", "aviation.aircraft.warplane",
            "aviation.aircraft.army_helicopter", "aviation.aircraft.civil_helicopter",
            "aviation.aircraft.general", "aviation.aircraft.cargo")
            .get(faker.number().numberBetween(0, 6)));
    }

    public String airplane() {
        return resolve("aviation.aircraft.airplane");
    }

    public String warplane() {
        return resolve("aviation.aircraft.warplane");
    }

    /**
     * @return general aviation aircraft.
     * See also: <a href="https://www.iaopa.eu/what-is-general-aviation">...ICAO defines general aviation operation by exception: those flight activities not involving commercial air transportation or aerial work.</a>
     */
    public String general() {
        return resolve("aviation.aircraft.general");
    }

    /**
     * @return a cargo aircraft which is dedicated to transport freight.
     */
    public String cargo() {
        return resolve("aviation.aircraft.cargo");
    }

    public String armyHelicopter() {
        return resolve("aviation.aircraft.army_helicopter");
    }

    public String civilHelicopter() {
        return resolve("aviation.aircraft.civil_helicopter");
    }

    /**
     * Returns an airport ICAO code.
     * See also: <a href="https://en.wikipedia.org/wiki/List_of_airports_by_ICAO_code:_A">https://en.wikipedia.org/wiki/List_of_airports_by_ICAO_code:_A</a>
     */
    public String airport() {
        return resolve("aviation.airport");
    }

    /**
     * @return an airport name. Source: <a href="http://www.flugzeuginfo.net/table_airportcodes_country-location_en.php">http://www.flugzeuginfo.net/table_airportcodes_country-location_en.php</a>
     */
    public String airportName() {
        return resolve("aviation.airport_name");
    }

    /**
     * Provides a METAR weather report.
     * Have a look at <a href="https://en.wikipedia.org/wiki/METAR">https://en.wikipedia.org/wiki/METAR</a>
     */
    public String METAR() {
        return resolve("aviation.metar");
    }

    /**
     * Returns a flight number (IATA or ICAO format).
     *
     * @return A random flight number with IATA or ICAO format in a String.
     */
    public String flight(String type) {
        String airline;
        if (type.equals("ICAO")) {
            airline = resolve("aviation.ICAO_airline");
        } else {
            airline = resolve("aviation.IATA_airline");
        }
        int number = faker.number().numberBetween(0, 9999);
        return airline + number;
    }

    /**
     * Returns a flight number without specifying flight number format.
     *
     * @return A random flight number with IATA format in a String.
     */
    public String flight() {
        return flight("IATA");
    }

    /**
     * Returns an airline name.
     *
     * @return A randomly selected airline name in a String.
     */
    public String airline() {
        return resolve("aviation.airline");
    }
}

