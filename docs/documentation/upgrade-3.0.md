# Upgrading to Datafaker 3.0

Update your dependency to `3.0.0` using the [installation instructions](getting-started.md#installation). Datafaker 3.0 requires Java 17 or later.

## Replace removed APIs

| Datafaker 2.x call | Datafaker 3.0 replacement |
| --- | --- |
| `faker.date().birthday()` | `faker.timeAndDate().birthday()`, which returns a `LocalDate` |
| `faker.date().past(...)` or `faker.date().future(...)` | `faker.timeAndDate().past(...)` or `faker.timeAndDate().future(...)`, which return an `Instant` without a format pattern |
| `faker.internet().username()` or `faker.name().username()` | `faker.credentials().username()` |
| `faker.internet().password(...)` | `faker.credentials().password(...)` |
| `faker.currency().code()` or `faker.currency().name()` | `faker.money().currencyCode()` or `faker.money().currency()` |
| `faker.finance().nyseTicker()` or `faker.finance().nasdaqTicker()` | `faker.stock().nyseSymbol()` or `faker.stock().nsdqSymbol()` |
| `faker.finance().stockMarket()` | `faker.stock().exchanges()` |
| `getProvider(Insect.class, Insect::new, this)` | `getProvider(Insect.class, Insect::new)` |

Use `timeAndDate` in date expressions as well. The timestamp overloads now accept `TemporalUnit` values such as `ChronoUnit.HOURS`. See [date formats](date-format.md).

### Move from Options to Selection

The Options provider is deprecated in 3.0. In Java or Kotlin, use `faker.selection().oneOf(...)` in place of `faker.options().option(...)`, or `faker.selection().setOf(...)` in place of `faker.options().subset(...)`. For string choices inside a template, use the documented [option expression](expressions.md#options).

## New providers and generators

=== "Java"

    ```java
    Faker faker = new Faker();

    String positive = faker.adjective().positive();
    String negative = faker.adjective().negative();
    String either = faker.adjective().any();

    String vendor = "PostgreSQL";
    int port = faker.database().port(vendor);
    String jdbcUrl = faker.database().jdbcUrl(vendor, "localhost", "example");
    String sqlType = faker.database().sqlType();

    String isin = faker.stock().isin();
    String spanishId = new Faker(Locale.forLanguageTag("es-ES")).idNumber().valid();
    ```

=== "Kotlin"

    ```kotlin
    val faker = Faker()

    val positive = faker.adjective().positive()
    val negative = faker.adjective().negative()
    val either = faker.adjective().any()

    val vendor = "PostgreSQL"
    val port = faker.database().port(vendor)
    val jdbcUrl = faker.database().jdbcUrl(vendor, "localhost", "example")
    val sqlType = faker.database().sqlType()

    val isin = faker.stock().isin()
    val spanishId = Faker(Locale.forLanguageTag("es-ES")).idNumber().valid()
    ```

=== "Groovy"

    ```groovy
    def faker = new Faker()

    def positive = faker.adjective().positive()
    def negative = faker.adjective().negative()
    def either = faker.adjective().any()

    def vendor = "PostgreSQL"
    def port = faker.database().port(vendor)
    def jdbcUrl = faker.database().jdbcUrl(vendor, "localhost", "example")
    def sqlType = faker.database().sqlType()

    def isin = faker.stock().isin()
    def spanishId = new Faker(Locale.forLanguageTag("es-ES")).idNumber().valid()
    ```

- `adjective()` samples positive or negative adjectives. `any()` chooses either category with equal probability. Use `word().adjective()` for the general vocabulary.
- `database()` supplies sample database metadata such as vendor names, ports, driver classes, JDBC URLs, SQL types, and constraints. Reuse the same vendor for related values.
- `stock().isin()` generates a synthetic security identifier with a check digit. With the `es-ES` locale, `idNumber().valid()` generates a Spanish DNI/NIF with its check letter.

## Behavior changes

- Use the same Datafaker version, locale, call sequence, and inputs for seeded fixtures. Provider data changes can produce different results after an upgrade. The unseeded default uses a per-thread random source.
- `doWith` scopes temporary locale and seed settings to the calling thread. See [temporary locale and seed](usage.md#temporary-locale-and-seed).
- `text().text()` and `credentials().password()` can require lowercase letters. See [character rules](password.md#require-lowercase-letters).
- SQL generation handles quoting for raw schema, table, and field names. Java object generation can transform a batch of existing records. See [SQL identifiers](schemas.md#sql-transformation) and [Java records](schemas.md#java-records).
- Vietnamese full names put the family name first. `publicIpV4Address()` excludes special-purpose ranges, and `privateIpV4Address()` uses the RFC 1918 ranges.
- Datafaker ships generated [GraalVM Native Image metadata](native-image.md) for its built-in providers and resources.
