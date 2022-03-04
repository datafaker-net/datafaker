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
