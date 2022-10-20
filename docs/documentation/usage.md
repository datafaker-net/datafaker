# Usage

The simplest way to use Datafaker to generate fake data is by instantiating the Faker using the default
constructor.

## Default usage

=== "Java"

    ``` java
    import net.datafaker.Faker;
    
    Faker faker = new Faker();

    String name = faker.name().fullName(); // Miss Samanta Schmidt
    ```

=== "Kotlin"

    ```
    import net.datafaker.Faker
    
    val faker = Faker()
    
    val name = faker.name().fullName() // Miss Samanta Schmidt
    ```

This will instantiate a Faker using the English locale. 

## Different locale

To use Datafaker with a different locale, you can supply on in the constructor as such:

=== "Java"

    ``` java
    Faker faker = new Faker(new Locale("nl"));

    String name = faker.name().fullName(); // Chelan Klijnsma
    ```

=== "Kotlin"

    ``` kotlin
    val faker = Faker(Locale("nl"))

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

    ``` java 
    Faker faker1 = new Faker(new Locale("nl"));
    Faker faker2 = new Faker(new Locale("ar"));

    List<Faker> fakers = Arrays.asList(faker1, faker2);

    for (int i = 0; i < 10; i++) {
        Faker randomFaker = new Faker().options().nextElement(fakers);
        System.out.println(randomFaker.address().fullAddress());
    }
    ```

=== "Kotlin"

    ``` kotlin
    val faker1 = Faker(Locale("nl"))
    val faker2 = Faker(Locale("ar"))

    val fakers = listOf(faker1, faker2)

    repeat(10) {
        val randomFaker = Faker().options().nextElement(fakers)
        println(randomFaker.address().fullAddress())
    }
    ```

## Repeatable random results

To generate a more predictable random result, it's possible to provide a seed value to the Faker. 

When providing a seed, the instantiation of Fake objects will always happen in a predictable way,
which can be handy for generating results multiple times.

=== "Java"

    ``` java
    Faker faker = new Faker(new Random(0));
    ```

=== "Kotlin"

    ``` kotlin
    val faker = Faker(Random(0))
    ```
