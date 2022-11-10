# Sequences

Supported fake sequences:

- **FakeCollection**
- **FakeStream**

## FakeSequence API

Support of fake collections has been added to Datafaker since version 1.2.0.  
Support of fake streams/fake sequence has been added to Datafaker since version 1.7.0.

For example, the following code will generate a list/stream of first and last names with number of elements in it between 3 and 5:

=== "List"

    ``` java 
    List<String> names = 
        faker.collection(
                () -> faker.name().firstName(), 
                () -> faker.name().lastName())
            .len(3, 5)
            .generate();
    ```

=== "Stream"

    ``` java 
    Stream<String> names = 
        faker.stream(
                () -> faker.name().firstName(), 
                () -> faker.name().lastName())
            .len(3, 5)
            .generate();
    ```

A list/stream can also contain different types:

=== "List"

    ``` java 
    List<Object> objects =
        faker.<Object>collection(
                () -> faker.name().firstName(),
                () -> faker.random().nextInt(100))
            .maxLen(5)
            .generate();
    ```

=== "Stream"

    ``` java 
    Stream<Object> objects =
        faker.<Object>stream(
                () -> faker.name().firstName(),
                () -> faker.random().nextInt(100))
            .maxLen(5)
            .generate();
    ```

With usage of `nullRate` it is possible to specify how often it should contain null values.
By default, it's value is 0, i.e. no null values will be generated.

=== "List"

    ``` java 
    List<Object> objects =
        faker.<Object>collection(
                () -> faker.name().firstName(),
                () -> faker.random().nextInt(100))
            .nullRate(1)
            .maxLen(5)
            .generate();
    ```

=== "Stream"

    ``` java 
    Stream<Object> objects =
        faker.<Object>stream(
                () -> faker.name().firstName(),
                () -> faker.random().nextInt(100))
            .nullRate(1)
            .maxLen(5)
            .generate();
    ```

The above will generate a collection/stream where every value is null.
To generate a collection/stream with only about 30% values of null, `nullRate(0.3)` will do it.

=== "List"

    ``` java 
    List<Object> objects =
        faker.<Object>collection(
                () -> faker.name().firstName(),
                () -> faker.random().nextInt(100))
            .nullRate(0.3)
            .maxLen(5)
            .generate();
    ```

=== "Stream"

    ``` java 
    Stream<Object> objects =
        faker.<Object>stream(
                () -> faker.name().firstName(),
                () -> faker.random().nextInt(100))
            .nullRate(0.3)
            .maxLen(5)
            .generate();
    ```

FakeSequence also supports generation of an infinite stream:

=== "Java"

    ``` java 
    Stream<Object> objects =
        faker.<Object>stream(
                () -> faker.name().firstName(),
                () -> faker.random().nextInt(100))
            .generate();
    ```

It is also possible to distinguish finite and infinite 
FakeStreams based on FakeSequence API:

=== "Java"

    ``` java 
    FakeSequence<Object> fakeSequence = faker.<Object>stream(
                () -> faker.name().firstName(),
                () -> faker.random().nextInt(100))
            .build();

    System.out.println(fakeSequence.isInfinite()); // true
    ```

For FakeCollection this function will always return false:

=== "Java"

    ``` java 
    FakeSequence<Object> fakeSequence = faker.<Object>collection(
                () -> faker.name().firstName(),
                () -> faker.random().nextInt(100))
            .build();

    System.out.println(fakeSequence.isInfinite()); // false
    ```
