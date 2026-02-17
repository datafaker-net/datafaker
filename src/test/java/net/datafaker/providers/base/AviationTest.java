package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import static net.datafaker.providers.base.AviationCodeType.IATA;
import static net.datafaker.providers.base.AviationCodeType.ICAO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AviationTest extends BaseFakerTest {
    private static final Pattern FLIGHT_IATA_CODE = Pattern.compile("[A-Z0-9]{2}\\d{1,4}");
    private static final Pattern FLIGHT_ICAO_CODE = Pattern.compile("[A-Z]{3}[0-9]+");
    private static final String AIRPORT_IATA_CODE = "[A-Z]{3}";
    private static final String AIRPORT_ICAO_CODE = "[A-Z]{4}";

    private final Aviation aviation = faker.aviation();

    @Test
    @SuppressWarnings("deprecation")
    void flight_ICAO() {
        assertThat(aviation.flight(ICAO)).matches(FLIGHT_ICAO_CODE);
        assertThat(aviation.flight("ICAO")).matches(FLIGHT_ICAO_CODE);
        assertThat(aviation.flight("icao")).matches(FLIGHT_ICAO_CODE);
        assertThat(aviation.flight("Icao")).matches(FLIGHT_ICAO_CODE);
        assertThat(aviation.flight("IcaO")).matches(FLIGHT_ICAO_CODE);
    }

    @Test
    @SuppressWarnings({"deprecation", "DataFlowIssue"})
    void flight_IATA() {
        assertThat(aviation.flight(IATA)).matches(FLIGHT_IATA_CODE);
        assertThat(aviation.flight("IATA")).matches(FLIGHT_IATA_CODE);
        assertThat(aviation.flight("iata")).matches(FLIGHT_IATA_CODE);
        assertThat(aviation.flight("test")).matches(FLIGHT_IATA_CODE);
        assertThat(aviation.flight((String) null)).matches(FLIGHT_IATA_CODE);
    }

    @Test
    @SuppressWarnings("DataFlowIssue")
    void flight_nullCodeNotAllowed() {
        assertThatThrownBy(() -> aviation.flight((AviationCodeType) null))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    void flight_default() {
        String flight = aviation.flight();
        assertThat(flight).matches(FLIGHT_IATA_CODE);
    }

    @Test
    void aircraft() {
        assertThat(aviation.aircraft()).isNotEmpty();
    }

    @Test
    void gate() {
        assertThat(aviation.gate()).isNotEmpty();
    }

    @Test
    void airportCode() {
        assertThat(aviation.airport())
            .as("Looks like ICAO 4-letter code, but sometimes is non-official local code containing digits")
            .hasSize(4).matches("[A-Z0-9]{4}");
    }

    @Test
    void airportICAOCode() {
        assertThat(aviation.airport(ICAO))
            .as("ICAO is 4-letter code consisting of Latin letters")
            .hasSize(4).matches(AIRPORT_ICAO_CODE);
    }

    @Test
    void airportIATACode() {
        assertThat(aviation.airport(IATA))
            .as("IATA is 3-letter code consisting of Latin letters")
            .hasSize(3).matches(AIRPORT_IATA_CODE);
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(aviation::airport, "aviation.airport", "\\w{4}"),
            TestSpec.of(() -> aviation.airport(IATA), "aviation.airport_iata", AIRPORT_IATA_CODE),
            TestSpec.of(() -> aviation.airport(ICAO), "aviation.airport_icao", AIRPORT_ICAO_CODE),
            TestSpec.of(aviation::airportName, "aviation.airport_name"),
            TestSpec.of(aviation::airplane, "aviation.aircraft.airplane"),
            TestSpec.of(aviation::warplane, "aviation.aircraft.warplane"),
            TestSpec.of(aviation::general, "aviation.aircraft.general"),
            TestSpec.of(aviation::cargo, "aviation.aircraft.cargo"),
            TestSpec.of(aviation::civilHelicopter, "aviation.aircraft.civil_helicopter"),
            TestSpec.of(aviation::armyHelicopter, "aviation.aircraft.army_helicopter"),
            TestSpec.of(aviation::METAR, "aviation.metar"),
            TestSpec.of(aviation::manufacturer, "aviation.manufacturer"),
            TestSpec.of(aviation::specialTypeDesignator, "aviation.aircraft_type_special_designator"),
            TestSpec.of(aviation::engineType, "aviation.engine_type"),
            TestSpec.of(aviation::flightStatus, "aviation.flight_status"),
            TestSpec.of(aviation::airline, "aviation.airline"));
    }
}
