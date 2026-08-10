# Related projects

Datafaker generates high-quality fake data. Other JVM libraries can complement it for tasks outside generation itself.

## Address formatting

[address-formatter-java](https://github.com/placemarkt/address-formatter-java) formats OpenStreetMap / Nominatim-style address fields into locale-aware postal addresses (based on [OpenCageData/address-formatting](https://github.com/OpenCageData/address-formatting)).

Use Datafaker's `Address` provider for realistic components, then pass Nominatim-like input into the formatter.

### Dependency

```xml
<dependency>
  <groupId>net.placemarkt</groupId>
  <artifactId>address-formatter-java</artifactId>
  <version>0.0.12</version>
</dependency>
```

### Example

Pin a locale so generated components stay coherent (for example `en-US`). The formatter accepts a YAML-like map string (as in its own README), not strict JSON. `format` throws `IOException`.

=== "Java"

    ```java
    import java.util.Locale;
    import net.datafaker.Faker;
    import net.placemarkt.AddressFormatter;

    Faker faker = new Faker(Locale.US);
    AddressFormatter formatter = new AddressFormatter(false, true);

    String houseNumber = faker.address().buildingNumber();
    String road = faker.address().streetName();
    String city = faker.address().city();
    String state = faker.address().state();
    String postcode = faker.address().zipCode();
    String country = faker.address().country();
    String countryCode = faker.address().countryCode();

    String addressMap = """
        {
          country_code: '%s',
          house_number: '%s',
          road: '%s',
          city: '%s',
          state: '%s',
          postcode: '%s',
          country: '%s'
        }
        """.formatted(countryCode, houseNumber, road, city, state, postcode, country);

    String formatted = formatter.format(addressMap);
    System.out.println(formatted);
    ```

=== "Kotlin"

    ```kotlin
    import java.util.Locale
    import net.datafaker.Faker
    import net.placemarkt.AddressFormatter

    val faker = Faker(Locale.US)
    val formatter = AddressFormatter(false, true)

    val houseNumber = faker.address().buildingNumber()
    val road = faker.address().streetName()
    val city = faker.address().city()
    val state = faker.address().state()
    val postcode = faker.address().zipCode()
    val country = faker.address().country()
    val countryCode = faker.address().countryCode()

    val addressMap = """
        {
          country_code: '$countryCode',
          house_number: '$houseNumber',
          road: '$road',
          city: '$city',
          state: '$state',
          postcode: '$postcode',
          country: '$country'
        }
        """.trimIndent()

    println(formatter.format(addressMap))
    ```

Sample output (values vary):

```text
1234 Elm Street
Springfield, IL 62701
United States of America
```

Constructor flags: `abbreviate` shortens road names where templates allow; `appendCountry` adds a country line when missing. See the [address-formatter-java README](https://github.com/placemarkt/address-formatter-java) for details.

## Other Datafaker projects

* [datafaker-gen](https://github.com/datafaker-net/datafaker-gen) — CLI generator driven by config (CSV, JSON, SQL, and more), no app rebuild per run
* [datafaker-native-demo](https://github.com/datafaker-net/datafaker-native-demo) — GraalVM native-image demo using Datafaker

Contributions that document other complementary tools are welcome.
