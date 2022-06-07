# Collections

Support of fake collections has been added to Datafaker since version 1.2.0.

For example, the following code will generate a list of first and last names with number of elements in it between 3 and 5:


=== "Java"

    ``` java 
    List<String> names = 
        faker.collection(
                () -> faker.name().firstName(), 
                () -> faker.name().lastName())
            .len(3, 5)
            .generate();
    ```

A list can also contain different types:

=== "Java"

    ``` java 
    List<Object> objects =
        faker.<Object>collection(
                () -> faker.name().firstName(),
                () -> faker.random().nextInt(100))
            .maxLen(5)
            .generate();
    ```

With usage of `nullRate` it is possible to specify how often it should contain null values.
By default, it's value is 0, i.e. no null values will be generated.

=== "Java"

    ``` java 
    List<Object> objects =
        faker.<Object>collection(
                () -> faker.name().firstName(),
                () -> faker.random().nextInt(100))
            .nullRate(1)
            .maxLen(5)
            .generate();
    ```
will generate a collection where every value is null.
And to generate a collection with only about 30% values of null `nullRate(0.3)` will do it

=== "Java"

    ``` java 
    List<Object> objects =
        faker.<Object>collection(
                () -> faker.name().firstName(),
                () -> faker.random().nextInt(100))
            .nullRate(0.3)
            .maxLen(5)
            .generate();
    ```
