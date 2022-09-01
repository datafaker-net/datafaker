# Unique Values

## Values from YAML files

Unique values can be retrieved from the YAML files by key, if the key references a hard-coded list of values.

=== "Java"

    ``` java 
    Faker faker = new Faker();

    // The values returned in the following lines will never be the same.
    String firstUniqueInstrument = faker.unique().fetchFromYaml("music.instruments"); // "Flute"
    String secondUniqueInstrument = faker.unique().fetchFromYaml("music.instruments"); // "Clarinet"
    ```

Note: Unique values are based on key and locale, so it's possible to get the same value if the locale is manually changed or if two different keys contain the same value.

If all possible values have been returned, an exception will be thrown.

=== "Java"

    ``` java 
    Faker faker = new Faker();
    String firstUniqueInstrument = faker.unique().fetchFromYaml("music.instruments"); // "Ukelele"
    ...
    String nthUniqueInstrument = faker.unique().fetchFromYaml("music.instruments"); // throws NoSuchElementException
    ```

Any non-string values will be converted.

=== "Java"

    ``` java 
    Faker faker = new Faker();
    String successCode = faker.unique().fetchFromYaml("sip.response.codes.success")); // "200"
    ```
