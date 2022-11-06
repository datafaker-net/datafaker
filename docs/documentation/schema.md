# Schema and transformers

Since version 1.7.0 of Datafaker it's possible to specify transformation schema.
It also provides a set of ready to use transformers
* CSV
* JSON
* SQL
* YAML
* Java Object

## Schema
Schema is a set of rules describing what should be done to transform data from datafaker representation to one of the supported format.
One of the main advantages of Schema is that the same schema could be used to transform to different formats.

Schema could be used in 2 ways: it could be used to generate data from scratch or it could be used to transform existing data.
Example of schema definition:

=== "Java"

    ``` java
        Schema<String, String> schema =
            Schema.of(
                field("first_name", () -> faker.name().firstName()),
                field("last_name", () -> faker.name().lastName()),
                field("address", () -> faker.address().streetAddress()));
    ```
It is also supported nested(composite) fields e.g.
=== "Java"

    ``` java
        Schema.of(
            compositeField("key", new Field[]{field("key", () -> "value")}));
    ```
## CSV transformation
CSV transformer could be build with help of `CsvTransformer.CsvTransformerBuilder` e.g.

=== "Java"

    ``` java
         CsvTransformer<String> transformer =
            new CsvTransformer.CsvTransformerBuilder<String>().header(true).separator(separator).build();
    ```
There could be specified
* the separator and quotes could be specified with `separator()` and `quote()`
* with or without header also could be specified with `header()`

To generate data based on schema just call `generate` against `schema`.

=== "Java"

    ``` java
         String csv = transformer.generate(schema, limit);
    ```
Also it's possible to use it to transform existing data. E.g. there is a collection of `Name` objects and we are going
to build csv of first and last names based on this collection:
=== "Java"

    ``` java
         Schema<Name, String> schema =
            Schema.of(field("firstName", Name::firstName), field("lastname", Name::lastName));

        CsvTransformer<Name> transformer =
            new CsvTransformer.CsvTransformerBuilder<Name>().header(false).separator(" : ").build();
        String csv =
            transformer.generate(
                faker.<Name>collection().suppliers(faker::name).maxLen(limit).build(),
                schema);
    ```

## JSON transformation
JSON transformation is very similar to CSV. The main difference is that JSON supports nested values which could be handled with help of `compositeField`.
Simple example of json generation
=== "Java"

    ``` java
        Schema<Object, ?> schema = Schema.of(
            field("Text", () -> faker.name().firstName()),
            field("Bool", () -> faker.bool().bool())
        );

        JsonTransformer<Object> transformer = new JsonTransformer<>();
        String json = transformer.generate(schema, 2);
    ```
To use composite fields it should be defined on `Schema` level and nothing more.

## SQL Transformation
Right now only `INSERT` is supported. 
It generates a number of `INSERT` statements. There could be 2 modes: batch and non batch generation. 
Batch generation means that one `INSERT` statement contains several rows to insert.
Since different databases could have different syntax there is initial support for different dialects. 
Dialect could be specified during `SQLTransformaer` build e.g.

=== "Java"

    ``` java
        SqlTransformer<String> transformer =
            new SqlTransformer.SqlTransformerBuilder<String>()
                .schemaName(tableSchemaName).dialect(SqlDialect.ORACLE).build();
    ```
Dialect also handle SQL quote identifiers, quotes and other SQL dialect specifics.
An example of batch mode
=== "Java"

    ``` java
        Faker faker = new Faker();
        Schema<String, String> schema =
            Schema.of(field("firstName", () -> faker.name().firstName()),
                field("lastName", () -> faker.name().lastName()));
        SqlTransformer<String> transformer =
            new SqlTransformer.SqlTransformerBuilder<String>()
                .batch(5)
                .tableName("MY_TABLE")
                .dialect(SqlDialect.POSTGRES)
                .build();
        String output = transformer.generate(schema, 10);
    ```
will generate 2 `INSERT` each containing 5 rows e.g.
```
INSERT INTO MY_TABLE ("firstName", "lastName")
VALUES ('Billy', 'Wintheiser'),
       ('Fernando', 'Sanford'),
       ('Jamey', 'Torp'),
       ('Nicolette', 'Wiza'),
       ('Sherman', 'Miller');
INSERT INTO MY_TABLE ("firstName", "lastName")
VALUES ('Marcell', 'Walsh'),
       ('Kareen', 'Bode'),
       ('Jules', 'Homenick'),
       ('Lashay', 'Gaylord'),
       ('Tyler', 'Miller');
```

### Advanced SQL types
It also supports generation of `ARRAY`, `MULTISET` and `ROW` types.
Please be aware that not every database engine supports it and datafaker could do it for every dialect.
To generate `ARRAY` schema field should supply and array.
To generate `MULTISET` schema field should supply a list (SQL `MULTISET` could contain duplicates)
To generate `ROW` schema field should supply a `compositeField`

e.g.
=== "Java"

    ``` java
        Schema.of(field("ints", () -> new int[]{1, 2, 3}));
    ```
will lead to 
```
INSERT INTO "MyTable" ("ints") VALUES (ARRAY[1, 2, 3]);
```
=== "Java"

    ``` java
        Schema.of(field("names_multiset", () -> Collections.singleton("hello"));
    ```
will lead to
```
INSERT INTO "MyTable" ("names_multiset") VALUES (MULTISET['hello']);
```
=== "Java"

    ``` java
        chema.of(compositeField("row", new Field[]{field("name", () -> "2")});
    ```
will lead to
```
INSERT INTO "MyTable" ("row") VALUES (ROW('2'));
```
