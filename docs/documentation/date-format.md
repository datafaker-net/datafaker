# Date formats

Since 1.2.0 Datafaker supports specifying of date formats for dates and timestamps.


=== "Java"

    ``` java 
    Faker faker = new Faker();
    System.out.println(faker.date().future(1, TimeUnit.HOURS, "YYYY MM.dd mm:hh:ss");
    System.out.println(faker.date().past(1, TimeUnit.HOURS, "YYYY-MM-dd mm:hh:ss");
    System.out.println(faker.date().birthday(1, 99, "YYYY/MM/dd");

    ```

And also this feature could be used in expressions like

=== "Java"

    ``` java 
    faker.expression("#{date.past '15','SECONDS','dd/MM/yyyy hh:mm:ss'}")
    ```

List of available time units:

| Name    | Time unit   | Since |
|---------|-------------|-------|
| NANO    | NANOSECOND  | 1.2.0 |
| NANOS   | NANOSECOND  | 1.2.0 |
| MICRO   | MICROSECOND | 1.2.0 |
| MICROS  | MICROSECOND | 1.2.0 |
| MILLI   | MILLISECOND | 1.2.0 |
| MILLIS  | MILLISECOND | 1.2.0 |
| SECOND  | SECOND      | 1.2.0 |
| SECONDS | SECOND      | 1.2.0 |
| MINUTE  | MINUTE      | 1.2.0 |
| MINUTES | MINUTE      | 1.2.0 |
| HOUR    | HOUR        | 1.2.0 |
| HOURS   | HOUR        | 1.2.0 |
| DAY     | DAY         | 1.2.0 |
| DAYS    | DAY         | 1.2.0 |
