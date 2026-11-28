# Usage

The simplest way to use Datafaker to generate fake data is by instantiating the Faker using the default
constructor.

## Default usage

=== "Java"

    ```java
    import net.datafaker.Faker;
    
    Faker faker = new Faker();

    String name = faker.name().fullName(); // Miss Samanta Schmidt
    ```

=== "Kotlin"

    ```kotlin
    import net.datafaker.Faker
    
    val faker = Faker()
    
    val name = faker.name().fullName() // Miss Samanta Schmidt
    ```

This will instantiate a Faker using the English locale. 

## Different locale

To use Datafaker with a different locale, you can supply one in the constructor as such:

=== "Java"

    ```java
    Faker faker = new Faker(Locale.forLanguageTag("nl"));

    String name = faker.name().fullName(); // Chelan Klijnsma
    ```

=== "Kotlin"

    ```kotlin
    val faker = Faker(Locale.forLanguageTag("nl"))

    val name = faker.name().fullName() // Chelan Klijnsma
    ```

## Multiple locales

In case you want to mix locales, the easiest way to do so is to create a Faker per locale, 
and mix between those fakers. For an example, see below, which produce something like the following:

```
8708 شارع قطر, مدينة خولة
جناح 385 127 شارع العشرين, معبر عبدالله
Schlangenlaan 461a, Oost Jessamyingen, WV 8234 ZX
1 hoog Gritlaan 52, Margiesmeer, OK 1083 VE
```

=== "Java"

    ```java 
    List<Faker> fakers = List.of(
        new Faker(Locale.forLanguageTag("nl")),
        new Faker(Locale.forLanguageTag("ar"))
    );
    Random chooser = new Random();

    for (int i = 0; i < 10; i++) {
        Faker randomFaker = fakers.get(chooser.nextInt(fakers.size()));
        System.out.println(randomFaker.address().fullAddress());
    }
    ```

=== "Kotlin"

    ```kotlin
    val fakers = listOf(
        Faker(Locale.forLanguageTag("nl")),
        Faker(Locale.forLanguageTag("ar"))
    )

    repeat(10) {
        val randomFaker = fakers.random()
        println(randomFaker.address().fullAddress())
    }
    ```

## Repeatable random results

Use the same seed, locale, Datafaker version, and sequence of calls to reproduce the random choices. For time-based generators, also supply a fixed reference time where the API provides one. Upgrades can change provider data and therefore the generated values.

=== "Java"

    ```java
    Faker faker = new Faker(new Random(0));
    ```

=== "Kotlin"

    ```kotlin
    val faker = Faker(Random(0))
    ```

To supply a Java `RandomGenerator`, wrap it in a `RandomService`.

```java
import java.util.Locale;
import java.util.SplittableRandom;

import net.datafaker.Faker;
import net.datafaker.service.RandomService;

Faker faker = new Faker(Locale.ENGLISH, new RandomService(new SplittableRandom(42)));
```

## Temporary locale and seed

In Datafaker 3.0, `doWith` applies temporary settings on the calling thread and restores the enclosing settings when the callback exits, even if it throws. Overlapping calls on a shared Faker each get their own settings.

```java
Faker faker = new Faker(Locale.US, new Random(42));

String frenchCity = faker.doWith(faker.address()::city, Locale.FRANCE);
String repeatableName = faker.doWith(faker.name()::firstName, 7L);
String repeatableFrenchCity = faker.doWith(faker.address()::city, Locale.FRANCE, 7L);
```

A locale-only call uses the current random stream. A seed-only call uses the current locale. Nested calls restore the outer callback's settings when they return.

## Random selection

In Datafaker 3.0, use `selection().oneOf()` to choose from an array, collection, or Java enum. Use `setOf()` to choose distinct elements from an array or varargs.

```java
DayOfWeek day = faker.selection().oneOf(DayOfWeek.class);
String color = faker.selection().oneOf(List.of("red", "green", "blue"));
Set<String> pair = faker.selection().setOf(2, "red", "green", "blue");
```

If the requested set size exceeds the number of distinct options, `setOf()` returns all distinct options.

In Datafaker 2.7, use `faker.options().option(DayOfWeek.class)` for enums and `faker.options().subset(2, ...)` for distinct elements.
