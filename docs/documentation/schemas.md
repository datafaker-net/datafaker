# Schema and transformers

Since version 1.7.0 of Datafaker it's possible to specify transformation schema.

It also provides a set of ready to use transformers:

* CSV
* JSON
* SQL
* YAML
* XML
* Java Object

## Schema

Schema is a set of rules describing what should be done to transform data from Datafaker representation to one of the supported formats.
One of the main advantages of Schema is that the same schema could be used to transform to different formats.

Schema can be used in 2 ways: it could be used to generate data from scratch or it could be used to transform existing data.

Example of schema definition:

=== "Java"

    ``` java
        Schema<String, String> schema =
            Schema.of(
                field("first_name", () -> faker.name().firstName()),
                field("last_name", () -> faker.name().lastName()),
                field("address", () -> faker.address().streetAddress()));
    ```
It is also supported nested(composite) fields e.g.:

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
The following can be configured:

* the separator and quotes could be specified with `separator()` and `quote()`
* with or without header also could be specified with `header()`

To generate data based on a schema just call `generate` against `schema`:

=== "Java"

    ``` java
         String csv = transformer.generate(schema, limit);
    ```
Also it's possible to use schemas to transform existing data. E.g. there is a collection of `Name` objects 
and we are going to build csv of first and last names based on this collection:

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

Example of JSON generation:

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

Note: right now only `INSERT` is supported. 

It generates a number of `INSERT` statements. There are 2 modes: batch and non batch generation.

Batch generation means that one `INSERT` statement contains several rows to insert.
Since different databases could have different syntax there is initial support for different dialects. 
Dialect could be specified during `SQLTransformaer` build e.g:

=== "Java"

    ``` java
        SqlTransformer<String> transformer =
            new SqlTransformer.SqlTransformerBuilder<String>()
                .schemaName(tableSchemaName).dialect(SqlDialect.ORACLE).build();
    ```
Dialect also handle SQL quote identifiers, quotes and other SQL dialect specifics.

An example of batch mode:

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

## YAML transformation
YAML transformation is very similar to CSV.

The following is an example on how to use it:

=== "Java"

    ``` java
        final BaseFaker faker = new BaseFaker();

        YamlTransformer<Object> transformer = new YamlTransformer<>();
        Schema<Object, ?> schema = Schema.of(
            field("name", () -> faker.name().firstName()),
            field("lastname", () -> faker.name().lastName()),
            field("phones", () -> Schema.of(
                field("worknumbers", () -> ((Stream<?>) faker.<String>stream().suppliers(() -> faker.phoneNumber().phoneNumber()).maxLen(2).build().get())
                    .collect(Collectors.toList())),
                field("cellphones", () -> ((Stream<?>) faker.<String>stream().suppliers(() -> faker.phoneNumber().cellPhone()).maxLen(3).build().get())
                    .collect(Collectors.toList()))
            )),
            field("address", () -> Schema.of(
                field("city", () -> faker.address().city()),
                field("country", () -> faker.address().country()),
                field("streetAddress", () -> faker.address().streetAddress())
            ))
        );

        System.out.println(transformer.generate(schema, 1));
    ```

will generate yaml with nested fields:

```
name: Mason
lastname: Bechtelar
phones:
  worknumbers:
    - (520) 205-2587 x2139
    - (248) 225-6912 x4880
  cellphones:
    - 714-269-8609
    - 1-512-606-8850
    - 1-386-909-7996
address:
  city: Port Wan
  country: Trinidad and Tobago
  streetAddress: 6510 Duncan Landing
  ```

## XML

XML transformation could be build with help of `new XmlTransformer.XmlTransformerBuilder<>().build()`.

`XmlTransformer.XmlTransformerBuilder.pretty` allows you to specify that the document should be formatted.

### Elements and attributes

In case you want to generate XML, Datafaker provides a facility to build XML elements and
attributes using XmlTransformer and randomly generated data in the following way:

=== "Java"

    ``` java
    public static void main(String[] args) {
        final BaseFaker faker = new BaseFaker();
        FakeStream<Object> address =
            (FakeStream<Object>) faker.stream()
                .suppliers(() ->
                    compositeField("address",
                        new Field[]{
                            field("country", () -> faker.address().country()),
                            field("city", () -> faker.address().city()),
                            field("streetAddress", () -> faker.address().streetAddress())}))
                .maxLen(3).build();

        FakeStream<Object> persons =
            (FakeStream<Object>) faker.stream()
                .suppliers(() ->
                    compositeField("person",
                        new Field[]{
                            field("firstname", () -> faker.name().firstName()),
                            field("lastname", () -> faker.name().lastName()),
                            field(null, () -> Collections.singletonList(
                                field("addresses", () -> address.get().collect(Collectors.toList()))))}))
                .maxLen(3).build();

        XmlTransformer<Object> xmlTransformer = new XmlTransformer.XmlTransformerBuilder<>().pretty(true).build();
        System.out.println(xmlTransformer.generate(Schema.of(field("persons", () -> persons.get().collect(Collectors.toList()))), 1));
    }
    ```

This will produce the following output:

```xml
<persons>
    <person firstname="Dianna" lastname="Langworth">
        <addresses>
            <address country="Burundi" city="Robbimouth" streetAddress="731 Haley Valleys"/>
            <address country="Guam" city="McKenziechester" streetAddress="7653 Sonny Crossing"/>
            <address country="Bangladesh" city="New Rodrigoland" streetAddress="9653 Lester Highway"/>
        </addresses>
    </person>
    <person firstname="Emilie" lastname="Bednar">
        <addresses>
            <address country="Iceland" city="Port Garret" streetAddress="4013 Luciano Terrace"/>
            <address country="Micronesia" city="West Murrayshire" streetAddress="109 Weber Streets"/>
            <address country="United States Minor Outlying Islands" city="Port Cortney" streetAddress="085 Laci Expressway"/>
        </addresses>
    </person>
    <person firstname="Lina" lastname="Purdy">
        <addresses>
            <address country="Liechtenstein" city="New Andree" streetAddress="08276 Treutel Street"/>
            <address country="Somalia" city="Shirelytown" streetAddress="8482 Hansen Valleys"/>
            <address country="Indonesia" city="New Earlie" streetAddress="814 Londa Flat"/>
        </addresses>
    </person>
</persons>
```

### Elements only

In case you only want to generate XML elements, without any attributes, that possible too:


=== "Java"

    ``` java
    final BaseFaker faker = new BaseFaker();
    FakeStream<?> address = (FakeStream<SimpleField<String, List<Object>>>)
            faker.<SimpleField<String, List<Object>>>stream()
                .suppliers(() ->
                    field("address",
                        () -> Arrays.asList(
                            field("country", () -> faker.address().country()),
                            field("city", () -> faker.address().city()),
                            field("streetAddress", () -> faker.address().streetAddress()))))
                .maxLen(3).build();

        FakeStream<?> persons = (FakeStream<SimpleField<Object, List<Object>>>)
            faker.<SimpleField<Object, List<Object>>>stream()
                .suppliers(() ->
                    field("person",
                        () -> Arrays.asList(
                            field("firstname", () -> faker.name().firstName()),
                            field("lastname", () -> faker.name().lastName()),
                            field("addresses", () -> address.get().collect(Collectors.toList())))))
                .maxLen(3).build();


        XmlTransformer<Object> xmlTransformer = new XmlTransformer.XmlTransformerBuilder<>().pretty(true).build();
        System.out.println(xmlTransformer.generate(Schema.of(field("persons", () -> persons.get().collect(Collectors.toList()))), 1));  
    ```

Executing the above will result in:

```xml
<persons>
    <person>
        <firstname>Rosario</firstname>
        <lastname>Mayert</lastname>
        <addresses>
            <address>
                <country>Marshall Islands</country>
                <city>Stiedemannside</city>
                <streetAddress>435 Doyle Harbors</streetAddress>
            </address>
            <address>
                <country>Democratic People&apos;s Republic of Korea</country>
                <city>Dominiquefort</city>
                <streetAddress>25135 Hansen Terrace</streetAddress>
            </address>
            <address>
                <country>Greenland</country>
                <city>Carrollstad</city>
                <streetAddress>8802 Lueilwitz Tunnel</streetAddress>
            </address>
        </addresses>
    </person>
    <person>
        <firstname>Cyrstal</firstname>
        <lastname>Spinka</lastname>
        <addresses>
            <address>
                <country>Andorra</country>
                <city>East Lanny</city>
                <streetAddress>3488 Alejandro Crossroad</streetAddress>
            </address>
            <address>
                <country>Cameroon</country>
                <city>Lake Kira</city>
                <streetAddress>30136 Watsica Squares</streetAddress>
            </address>
            <address>
                <country>Ukraine</country>
                <city>Cassandrabury</city>
                <streetAddress>5764 Koepp Throughway</streetAddress>
            </address>
        </addresses>
    </person>
    <person>
        <firstname>Leigh</firstname>
        <lastname>Satterfield</lastname>
        <addresses>
            <address>
                <country>Mali</country>
                <city>South Simonne</city>
                <streetAddress>0870 Corkery Green</streetAddress>
            </address>
            <address>
                <country>Mongolia</country>
                <city>Margotland</city>
                <streetAddress>4280 Lonnie Haven</streetAddress>
            </address>
            <address>
                <country>Zimbabwe</country>
                <city>Boyleland</city>
                <streetAddress>4972 Medhurst Extensions</streetAddress>
            </address>
        </addresses>
    </person>
</persons>
```
