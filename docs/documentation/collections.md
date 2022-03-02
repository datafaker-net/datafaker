# Collections

Support of fake collections has been added to Datafaker since version 1.2.0.

For example, the following code will generate a list of first and last names with size `[3, 5]`:


=== "Java"

    ``` java 
    List<String> names = new FakeCollection.Builder<String>()
                    .suppliers(() -> faker.name().firstName(), () -> faker.name().lastName())
                    .minLen(3)
                    .maxLen(5).build().get();
    ```

A list can also contain different types:

=== "Java"

    ``` java 
    List<Object> objects = new FakeCollection.Builder<>()
                        .suppliers(() -> faker.name().firstName(), () -> faker.random().nextInt(100))
                        .maxLen(5).build().get();
    ```

