package net.datafaker.providers.base;

public enum AviationCodeType {
    /**
     * 3-letter passenger-facing code, for example:
     * <ul>
     *     <li>TLL - Lennart Meri Tallinn Airport</li>
     *     <li>VNO - Vilnius Airport</li>
     *     <li>JFK - John F. Kennedy International Airport (New York)</li>
     * </ul>
     */
    IATA,

    /**
     * 4-letter operational code, for example:
     * <ul>
     *     <li>EETN - Lennart Meri Tallinn Airport</li>
     *     <li>EYVI - Vilnius Airport</li>
     *     <li>KJFK - John F. Kennedy International Airport (New York)</li>
     * </ul>
     */
    ICAO
}
