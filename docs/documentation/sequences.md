# Sequences

Supported fake sequences:

- **FakeCollection**
- **FakeStream**

## FakeSequence API

Support of fake collections has been added to Datafaker since version 1.2.0.  
Support of fake streams/fake sequence has been added to Datafaker since version 1.7.0.

For example, the following code will generate a list and a stream of first and last names with number of elements in it between 3 and 5:

=== "Java"

    ```java
    List<String> names =
        faker.collection(
                () -> faker.name().firstName(),
                () -> faker.name().lastName())
            .len(3, 5)
            .generate();

    Stream<String> nameStream =
        faker.stream(
                () -> faker.name().firstName(),
                () -> faker.name().lastName())
            .len(3, 5)
            .generate();
    ```

=== "Kotlin"

    ```kotlin
    val names: List<String> =
        faker.collection(
                Supplier { faker.name().firstName() },
                Supplier { faker.name().lastName() })
            .len(3, 5)
            .generate()

    val nameStream: Stream<String> =
        faker.stream(
                Supplier { faker.name().firstName() },
                Supplier { faker.name().lastName() })
            .len(3, 5)
            .generate()
    ```

=== "Groovy"

    ```groovy
    List<String> names =
        faker.collection(
                { faker.name().firstName() },
                { faker.name().lastName() })
            .len(3, 5)
            .generate()

    Stream<String> nameStream =
        faker.stream(
                { faker.name().firstName() },
                { faker.name().lastName() })
            .len(3, 5)
            .generate()
    ```

`faker.collection(...)` and `faker.stream(...)` take the same arguments. The rest of the examples use `collection`,
but each of them works with `stream` as well.

A collection can also contain different types:

=== "Java"

    ```java
    List<Object> objects =
        faker.<Object>collection(
                () -> faker.name().firstName(),
                () -> faker.random().nextInt(100))
            .maxLen(5)
            .generate();
    ```

=== "Kotlin"

    ```kotlin
    val objects: List<Any> =
        faker.collection<Any>(
                Supplier { faker.name().firstName() },
                Supplier { faker.random().nextInt(100) })
            .maxLen(5)
            .generate()
    ```

=== "Groovy"

    ```groovy
    List<Object> objects =
        faker.collection(
                { faker.name().firstName() },
                { faker.random().nextInt(100) })
            .maxLen(5)
            .generate()
    ```

With usage of `nullRate` it is possible to specify how often it should contain null values.
By default, it's value is 0, i.e. no null values will be generated.

=== "Java"

    ```java
    List<Object> objects =
        faker.<Object>collection(
                () -> faker.name().firstName(),
                () -> faker.random().nextInt(100))
            .nullRate(1)
            .maxLen(5)
            .generate();
    ```

=== "Kotlin"

    ```kotlin
    val objects: List<Any> =
        faker.collection<Any>(
                Supplier { faker.name().firstName() },
                Supplier { faker.random().nextInt(100) })
            .nullRate(1.0)
            .maxLen(5)
            .generate()
    ```

=== "Groovy"

    ```groovy
    List<Object> objects =
        faker.collection(
                { faker.name().firstName() },
                { faker.random().nextInt(100) })
            .nullRate(1)
            .maxLen(5)
            .generate()
    ```

The above will generate a collection where every value is null.
To generate a collection with only about 30% values of null, `nullRate(0.3)` will do it.

=== "Java"

    ```java
    List<Object> objects =
        faker.<Object>collection(
                () -> faker.name().firstName(),
                () -> faker.random().nextInt(100))
            .nullRate(0.3)
            .maxLen(5)
            .generate();
    ```

=== "Kotlin"

    ```kotlin
    val objects: List<Any> =
        faker.collection<Any>(
                Supplier { faker.name().firstName() },
                Supplier { faker.random().nextInt(100) })
            .nullRate(0.3)
            .maxLen(5)
            .generate()
    ```

=== "Groovy"

    ```groovy
    List<Object> objects =
        faker.collection(
                { faker.name().firstName() },
                { faker.random().nextInt(100) })
            .nullRate(0.3)
            .maxLen(5)
            .generate()
    ```

FakeSequence also supports generation of an infinite stream:

=== "Java"

    ```java
    Stream<Object> objects =
        faker.<Object>stream(
                () -> faker.name().firstName(),
                () -> faker.random().nextInt(100))
            .generate();
    ```

=== "Kotlin"

    ```kotlin
    val objects: Stream<Any> =
        faker.stream<Any>(
                Supplier { faker.name().firstName() },
                Supplier { faker.random().nextInt(100) })
            .generate()
    ```

=== "Groovy"

    ```groovy
    Stream<Object> objects =
        faker.stream(
                { faker.name().firstName() },
                { faker.random().nextInt(100) })
            .generate()
    ```

It is also possible to distinguish finite and infinite 
FakeStreams based on FakeSequence API:

=== "Java"

    ```java
    FakeSequence<Object> fakeSequence = faker.<Object>stream(
                () -> faker.name().firstName(),
                () -> faker.random().nextInt(100))
            .build();

    System.out.println(fakeSequence.isInfinite()); // true
    ```

=== "Kotlin"

    ```kotlin
    val fakeSequence: FakeSequence<Any> = faker.stream<Any>(
                Supplier { faker.name().firstName() },
                Supplier { faker.random().nextInt(100) })
            .build()

    println(fakeSequence.isInfinite()) // true
    ```

=== "Groovy"

    ```groovy
    FakeSequence<Object> fakeSequence = faker.stream(
                { faker.name().firstName() },
                { faker.random().nextInt(100) })
            .build()

    println fakeSequence.isInfinite() // true
    ```

For FakeCollection this function will always return false:

=== "Java"

    ```java
    FakeSequence<Object> fakeSequence = faker.<Object>collection(
                () -> faker.name().firstName(),
                () -> faker.random().nextInt(100))
            .build();

    System.out.println(fakeSequence.isInfinite()); // false
    ```

=== "Kotlin"

    ```kotlin
    val fakeSequence: FakeSequence<Any> = faker.collection<Any>(
                Supplier { faker.name().firstName() },
                Supplier { faker.random().nextInt(100) })
            .build()

    println(fakeSequence.isInfinite()) // false
    ```

=== "Groovy"

    ```groovy
    FakeSequence<Object> fakeSequence = faker.collection(
                { faker.name().firstName() },
                { faker.random().nextInt(100) })
            .build()

    println fakeSequence.isInfinite() // false
    ```
