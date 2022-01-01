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

## Repeatable random results

To generate a more predictable random result, it's possible to provide a seed value to the Faker. 

When providing a seed, the instantiatiton of Fake objects will always happen in a predictable way,
which can be handy for generating results multiple times.

=== "Java"

    ``` java
    Faker faker = new Faker(new Random(0));
    ```

=== "Kotlin"

    ``` kotlin
    val faker = new Faker(Random(0))
    ```
