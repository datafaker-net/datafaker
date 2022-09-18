package net.datafaker.base;

/**
 * Generates aviation related strings.
 *
 * @since 0.8.0
 */
public class Aviation extends AbstractProvider<BaseProviders> {

    protected Aviation(BaseFaker faker) {
        super(faker);
    }

    /**
     * @return some aircraft name/model/make e.g. "An-2".
     */
    public String aircraft() {
        return  resolve("aviation.aircraft");
    }

    /**
     * Returns an airport ICAO code.
     * See also: <a href="https://en.wikipedia.org/wiki/List_of_airports_by_ICAO_code:_A">https://en.wikipedia.org/wiki/List_of_airports_by_ICAO_code:_A</a>
     */
    public String airport() {
        return  resolve("aviation.airport");
    }

    /**
     * Provides a METAR weather report.
     * Have a look at <a href="https://en.wikipedia.org/wiki/METAR">https://en.wikipedia.org/wiki/METAR</a>
     */
    public String METAR() {
        return  resolve("aviation.metar");
    }

    /**
     * Returns a flight number (IATA or ICAO format).
     *
     * @return A random flight number with IATA or ICAO format in a String.
     */
    public String flight(String type) {
        String airline;
        if (type.equals("ICAO")) {
            airline =  resolve("aviation.ICAO_airline");
        } else {
            airline =  resolve("aviation.IATA_airline");
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
        return  resolve("aviation.airline");
    }
}

