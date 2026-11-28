# Date formats

Use `faker.timeAndDate()` for dates and timestamps. `birthday()` returns a `LocalDate`, and `past()` and `future()` return an `Instant`. The overloads that accept a format pattern return a string.

=== "Java"

    ```java
    import java.time.temporal.ChronoUnit;
    import net.datafaker.Faker;

    Faker faker = new Faker();
    String future = faker.timeAndDate().future(1, ChronoUnit.HOURS, "uuuu-MM-dd HH:mm:ss");
    String birthday = faker.timeAndDate().birthday(18, 65, "uuuu/MM/dd");
    ```

=== "Kotlin"

    ```kotlin
    import java.time.temporal.ChronoUnit
    import net.datafaker.Faker

    val faker = Faker()
    val future = faker.timeAndDate().future(1, ChronoUnit.HOURS, "uuuu-MM-dd HH:mm:ss")
    val birthday = faker.timeAndDate().birthday(18, 65, "uuuu/MM/dd")
    ```

=== "Groovy"

    ```groovy
    import java.time.temporal.ChronoUnit
    import net.datafaker.Faker

    def faker = new Faker()
    def future = faker.timeAndDate().future(1, ChronoUnit.HOURS, "uuuu-MM-dd HH:mm:ss")
    def birthday = faker.timeAndDate().birthday(18, 65, "uuuu/MM/dd")
    ```

In Datafaker 2.7, these methods take a `java.util.concurrent.TimeUnit` instead of a `ChronoUnit`:

```java
String future = faker.timeAndDate().future(1, TimeUnit.HOURS, "uuuu-MM-dd HH:mm:ss");
```

Use a date-only pattern for a birthday. Timestamp methods also support time-of-day fields such as `HH:mm:ss`.

For repeatable timestamps, use a seeded Faker and pass a fixed `Instant` to an overload that takes a reference time.

The same provider is available in [expressions](expressions.md).

=== "Java"

    ```java
    faker.expression("#{timeAndDate.birthday 'uuuu-MM-dd'}");
    faker.expression("#{timeAndDate.past '15','SECONDS','uuuu-MM-dd HH:mm:ss'}");
    ```

=== "Kotlin"

    ```kotlin
    faker.expression("#{timeAndDate.birthday 'uuuu-MM-dd'}")
    faker.expression("#{timeAndDate.past '15','SECONDS','uuuu-MM-dd HH:mm:ss'}")
    ```

=== "Groovy"

    ```groovy
    faker.expression("#{timeAndDate.birthday 'uuuu-MM-dd'}")
    faker.expression("#{timeAndDate.past '15','SECONDS','uuuu-MM-dd HH:mm:ss'}")
    ```

Three common units for relative timestamps are `SECONDS`, `MINUTES`, and `HOURS`. Use `ChronoUnit` in Java code targeting Datafaker 3.0.
