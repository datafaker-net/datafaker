# Date formats

Use `faker.timeAndDate()` for dates and timestamps. `birthday()` returns a `LocalDate`, and `past()` and `future()` return an `Instant`. The overloads that accept a format pattern return a string.

=== "Datafaker 3.0"

    ```java
    import java.time.temporal.ChronoUnit;
    import net.datafaker.Faker;

    Faker faker = new Faker();
    String future = faker.timeAndDate().future(1, ChronoUnit.HOURS, "uuuu-MM-dd HH:mm:ss");
    String birthday = faker.timeAndDate().birthday(18, 65, "uuuu/MM/dd");
    ```

=== "Datafaker 2.7"

    ```java
    import java.util.concurrent.TimeUnit;
    import net.datafaker.Faker;

    Faker faker = new Faker();
    String future = faker.timeAndDate().future(1, TimeUnit.HOURS, "uuuu-MM-dd HH:mm:ss");
    String birthday = faker.timeAndDate().birthday(18, 65, "uuuu/MM/dd");
    ```

Use a date-only pattern for a birthday. Timestamp methods also support time-of-day fields such as `HH:mm:ss`.

For repeatable timestamps, use a seeded Faker and pass a fixed `Instant` to an overload that takes a reference time.

The same provider is available in [expressions](expressions.md).

```java
faker.expression("#{timeAndDate.birthday 'uuuu-MM-dd'}");
faker.expression("#{timeAndDate.past '15','SECONDS','uuuu-MM-dd HH:mm:ss'}");
```

Three common units for relative timestamps are `SECONDS`, `MINUTES`, and `HOURS`. Use `ChronoUnit` in Java code targeting Datafaker 3.0.
