# Getting started

Datafaker is a library for Java and Kotlin to generate fake data. This can be
very helpful when generating test data to fill a database, to generate data
for a stress test, or anonymize data from production services.

Datafaker 2.x and 3.x require Java 17 or later.

## Installation 

The latest version of Datafaker is {{ datafaker.version }} and is hosted on Maven Central. 

Datafaker can be included in your project using most dependency management tools: 

=== "Maven"

    ```xml
    <dependency>
        <groupId>net.datafaker</groupId>
        <artifactId>datafaker</artifactId>
        <version>{{ datafaker.version }}</version>
    </dependency>
    ```

=== "Gradle (Groovy)"

    ```groovy
    dependencies {
        implementation 'net.datafaker:datafaker:{{ datafaker.version }}'
    }
    ```

=== "Gradle (Kotlin)"

    ```kotlin
    dependencies {
        implementation("net.datafaker:datafaker:{{ datafaker.version }}")
    }
    ```

=== "Ivy"

    ```xml
    <dependency org="net.datafaker" name="datafaker" rev="{{ datafaker.version }}"/>
    ```

### Snapshot versions

To try the {{ datafaker.snapshot }} development build, use that dependency version and add the Central Portal snapshot repository.

=== "Maven"

    ```xml
    <repositories>
        <repository>
            <id>central-portal-snapshots</id>
            <url>https://central.sonatype.com/repository/maven-snapshots/</url>
            <releases><enabled>false</enabled></releases>
            <snapshots><enabled>true</enabled></snapshots>
        </repository>
    </repositories>
    ```

=== "Gradle (Kotlin)"

    ```kotlin
    repositories {
        mavenCentral()
        maven("https://central.sonatype.com/repository/maven-snapshots/") {
            mavenContent { snapshotsOnly() }
        }
    }

    dependencies {
        implementation("net.datafaker:datafaker:{{ datafaker.snapshot }}")
    }
    ```

See [Upgrading to Datafaker 3.0](upgrade-3.0.md) for API changes and new providers.

## Usage

To use Datafaker to generate fake data, you can use the following code as an example:

=== "Java"

    ```java
    import net.datafaker.Faker;
    
    Faker faker = new Faker();
    
    String name = faker.name().fullName(); // Miss Samanta Schmidt
    String firstName = faker.name().firstName(); // Emory
    String lastName = faker.name().lastName(); // Barton
    
    String streetAddress = faker.address().streetAddress(); // 60018 Sawayn Brooks Suite 449
    ```

=== "Kotlin"

    ```kotlin
    import net.datafaker.Faker
    
    val faker = Faker()
    
    val name = faker.name().fullName() // Miss Samanta Schmidt
    val firstName = faker.name().firstName() // Emory
    val lastName = faker.name().lastName() // Barton
    
    val streetAddress = faker.address().streetAddress() // 60018 Sawayn Brooks Suite 449
    ```

See [all providers](providers.md) for available generators and [basic usage](usage.md) for locales and seeded generation.
