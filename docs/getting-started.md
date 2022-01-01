# Getting started

Datafaker is a library for Java and Kotlin to generate fake data. This can be
very helpful when generating test data to fill a database, to generate data
for a stress test, or anonymize data from production services.

## Installation 

The latest version of Datafaker is {{ datafaker.version }} and is hosted on Maven Central. 

Datafaker can be included in your project using most dependency management tools: 

=== "Maven"

    ``` sh
    <dependency>
        <groupId>net.datafaker</groupId>
        <artifactId>datafaker</artifactId>
        <version>{{ datafaker.version }}</version>
    </dependency>
    ```

=== "Gradle (Groovy)"

    ``` sh
    dependencies {
        implementation 'net.datafaker:datafaker:{{ datafaker.version }}'
    }
    ```

=== "Gradle (Kotlin)"

    ``` sh
    dependencies {
        implementation("net.datafaker:datafaker:{{ datafaker.version }}")
    }
    ```

=== "Ivy"

    ``` sh
    <dependency org="net.datafaker" name="datafaker" rev="{{ datafaker.version }}"/>
    ```


### Snapshot versions

It's also possible to use the latest snapshot version (currently {{ datafaker.snapshot }})
by including the Sonatype snapshot repository in your configuration. 

A Gradle example can be found below:

``` sh
repositories {
    mavenCentral()
    maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    implementation("net.datafaker:datafaker:{{ datafaker.snapshot }}")
}
```

## Usage

To use Datafaker to generate fake data, you can use the following code as an example:

=== "Java"

    ``` java
    import net.datafaker.Faker;
    
    Faker faker = new Faker();
    
    String name = faker.name().fullName(); // Miss Samanta Schmidt
    String firstName = faker.name().firstName(); // Emory
    String lastName = faker.name().lastName(); // Barton
    
    String streetAddress = faker.address().streetAddress(); // 60018 Sawayn Brooks Suite 449
    ```

=== "Kotlin"

    ```
    import net.datafaker.Faker
    
    val faker = Faker()
    
    val name = faker.name().fullName() // Miss Samanta Schmidt
    val firstName = faker.name().firstName() // Emory
    val lastName = faker.name().lastName() // Barton
    
    val streetAddress = faker.address().streetAddress() // 60018 Sawayn Brooks Suite 449
    ```

For a full list of all the fake data providers, have a look at the reference documentation.
