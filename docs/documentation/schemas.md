# Schema and transformers

Since version 1.7.0 of Datafaker it's possible to specify transformation schema.

It also provides a set of ready to use transformers:

* CSV
* JSON
* SQL
* YAML
* XML
* Java Object
* TOML

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

=== "Kotlin"

    ``` kotlin
        val faker = BaseFaker()

        val schema = Schema.of(
            field("first_name",
                Supplier { faker.name().firstName() }),
            field("last_name",
                Supplier { faker.name().lastName() }),
            field<String, String>("address",
                Supplier { faker.address().streetAddress() })
        )
    ```

It is also supported nested(composite) fields e.g.:

=== "Java"

    ``` java
        Schema.of(
            compositeField("key", field("key", () -> "value")));
    ```

=== "Kotlin"

    ``` kotlin
        Schema.of(compositeField("key", arrayOf(field("key", Supplier { "value" }))))
    ```

## CSV transformation

CSV transformer could be built with help of `CsvTransformer.CsvTransformerBuilder` e.g.

=== "Java"

    ``` java
         CsvTransformer<String> transformer =
            CsvTransformer.<String>builder().header(true).separator(separator).build();
    ```

=== "Kotlin"

    ``` kotlin
        val transformer = CsvTransformer.builder<String>().header(true).separator(separator).build()
    ```

The following can be configured:

* the separator and quotes could be specified with `separator()` and `quote()`
* with or without header also could be specified with `header()`

To generate data based on a schema just call `generate` against `schema`:

=== "Java"

    ``` java
         String csv = transformer.generate(schema, limit);
    ```

=== "Kotlin"

    ```kotlin
        val csv = transformer.generate(schema, limit)
    ```

Also it's possible to use schemas to transform existing data. E.g. there is a collection of `Name` objects 
and we are going to build csv of first and last names based on this collection:

=== "Java"

    ``` java
         Schema<Name, String> schema =
            Schema.of(field("firstName", Name::firstName), field("lastname", Name::lastName));

        CsvTransformer<Name> transformer =
            CsvTransformer.<Name>builder().header(false).separator(" : ").build();
        String csv =
            transformer.generate(
                faker.<Name>collection().suppliers(faker::name).maxLen(limit).build(),
                schema);
    ```

=== "Kotlin"

    ```kotlin
        val faker = BaseFaker()

        val schema = Schema.of(field("firstName", Name::firstName), field("lastname", Name::lastName))

        val transformer = CsvTransformer.builder<Name>().header(false).separator(" : ").build()
        val csv = transformer.generate(
            faker.collection<Name>().suppliers(Supplier { faker.name() }).maxLen(limit).build(), schema
        )
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

        JsonTransformer<Object> transformer = JsonTransformer.builder().build();
        String json = transformer.generate(schema, 2);
    ```

=== "Kotlin"

    ```kotlin
        val faker = BaseFaker()
    
        val schema: Schema<String, *> = Schema.of(
            field("Text", Supplier { faker.name().firstName() }),
            field("Bool", Supplier { faker.bool().bool() })
        )

        val transformer = JsonTransformer.builder<String>().build();
        val json = transformer.generate(schema, 2)
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

=== "Kotlin"

    ```kotlin
        val transformer = SqlTransformer.SqlTransformerBuilder<String>()
            .schemaName(tableSchemaName).dialect(SqlDialect.ORACLE).build()
    ```

Dialect also handles SQL quote identifiers, quotes and other SQL dialect specifics.

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
=== "Kotlin"

    ``` kotlin
        val faker = Faker()
        val schema: Schema<String, String> = Schema.of(
            field("firstName", Supplier { faker.name().firstName() }),
            field("lastName", Supplier { faker.name().lastName() })
        )
        val transformer = SqlTransformer.SqlTransformerBuilder<String>()
            .batch(5)
            .tableName("MY_TABLE")
            .dialect(SqlDialect.POSTGRES)
            .build()
        val output = transformer.generate(schema, 10)
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

To generate `ARRAY` schema field supply an array.  
To generate `MULTISET` schema field supply a list (SQL `MULTISET` could contain duplicates).  
To generate `ROW` schema field should supply a `compositeField`.

e.g.

=== "Java"

    ``` java
        Schema.of(field("ints", () -> new int[]{1, 2, 3}));
    ```

=== "Kotlin"

    ```kotlin
        val schema: Schema<String, IntArray> = Schema.of(field("ints", Supplier { intArrayOf(1, 2, 3) }))
    ```

will lead to

```
INSERT INTO "MyTable" ("ints") VALUES (ARRAY[1, 2, 3]);
```
=== "Java"

    ``` java
        Schema.of(field("names_multiset", () -> Collections.singleton("hello"));
    ```

=== "Kotlin"

    ```kotlin
        val schema: Schema<String, Set<String>> = Schema.of(field("names_multiset", Supplier { Collections.singleton("hello") } ))
    ```

will lead to

```
INSERT INTO "MyTable" ("names_multiset") VALUES (MULTISET['hello']);
```
=== "Java"

    ``` java
        schema.of(compositeField("row", field("name", () -> "2"));
    ```

=== "Kotlin"

    ```kotlin
        Schema.of(compositeField("row", arrayOf(field("name", Supplier { "2" }))))
    ```

will lead to

```
INSERT INTO "MyTable" ("row") VALUES (ROW('2'));
```

#### Spark SQL

Some engines like Spark stand out with support for complex types like `STRUCT` and `MAP`.  
Spark dialect doesn't support batch inserts. The dialect will throw an exception if you attempt to generate batch inserts.

The following schema:

=== "Java"
    
    ``` java
        Schema.of(
            field("string", () -> "string"),
            field("array", () -> new int[]{1, 2, 3}),
            field("map", () -> Map.of("key", "value")),
            compositeField("struct", field("name", () -> "2"))
        );
    ```
=== "Kotlin"

    ```kotlin
        Schema.of(
            field("string", Supplier { "string" }),
            field("array", Supplier { intArrayOf(1, 2, 3) }),
            field("map", Supplier { mapOf("key" to "value") }),
            compositeField("struct", arrayOf(field("name", Supplier { "2" })))
        )
    ```
will lead to:

```
INSERT INTO `MyTable` (`string`, `array`, `map`, `struct`) 
VALUES ('string', ARRAY(1, 2, 3), MAP('key', 'value'), NAMED_STRUCT('name', '2'));
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

## Java Object transformation

Java Object transformer could be built with help of JavaObjectTransformer. 

When building JavaObjectTransformer you should provide a class to be used as a template for generated objects.
=== "Java"

    ``` java

        public static class Person {
           private String firstName;
           private String lastName;
           private Date birthDate;
           private int id;
        }
    ```
=== "Kotlin"

    ``` kotlin

        data class Person(
            var firstName: String,
            var lastName: String,
            var birthDate: Date,
            var id: Int
        )
    ```

Then you should provide a schema for the class.

=== "Java"

    ``` java

        JavaObjectTransformer jTransformer = new JavaObjectTransformer();
        Schema<Object, ?> schema = Schema.of(
            field("firstName", () -> faker.name().firstName()),
            field("lastName", () -> faker.name().lastName()),
            field("birthDate", () -> faker.date().birthday()),
            field("id", () -> faker.number().positive()));

        System.out.println(jTransformer.apply(Person.class, schema));
    ```

=== "Kotlin"

    ``` kotlin

        val jTransformer = JavaObjectTransformer()
        val schema: Schema<Any, Any> = Schema.of(
            field("firstName", Supplier { faker.name().firstName() }),
            field("lastName", Supplier { faker.name().lastName() }),
            field("birthDate", Supplier { faker.date().birthday() }),
            field("id", Supplier { faker.number().positive() }))

        println(jTransformer.apply(Person::class.java, schema))
    ```

will generate object with fields populated with random values based on specified suppliers.

### Populating Java Object with predefined Schema

You can use predefined schema to populate Java Object or default schema for the class.
Schema should be declared as a static method with return type `Schema<Object, ?>`.

=== "Java"

    ```java
          public static Schema<Object, ?> defaultSchema() {
            var faker = new Faker(Locale.forLanguageTag("fr-en"), new RandomService(new Random(1)));
            return Schema.of(field("name", () -> faker.name().fullName()));
          }
    ```

=== "Kotlin"

    ```kotlin
        fun defaultSchema(): Schema<Any, Any> {
            val faker = Faker(Locale.forLanguageTag("fr-en"), RandomService(Random(1)))
            return Schema.of(field("name", Supplier { faker.name().fullName() }))
        }
    ```

Then you should provide a class to be used as a template for generated objects. Class should be annotated with `@FakeForSchema` annotation with path to the schema method as a value.

> Note: If default schema and class template are in the same class, you can omit full path to the method and use only method name.
 
=== "Java"

    ```java
        @FakeForSchema("net.datafaker.annotations.FakeAnnotationTest#defaultSchema")
        public class Person {
            private String fullName;
        
            public String getFullName() {
                return fullName;
            }
        
            public void setFullName(String fullName) {
                this.fullName = fullName;
            }
        }
    ```

=== "Kotlin"

    ```kotlin
        @FakeForSchema("net.datafaker.annotations.FakeAnnotationTest#defaultSchema")
        data class Person(
            var fullName: String
        )
    ```

Then you can use `net.datafaker.providers.base.BaseFaker.populate(java.lang.Class<T>)` to populate object with default predefined schema.

=== "Java"

    ```java
        BaseFaker faker = new BaseFaker();
        Person person = faker.populate(Person.class);
    ```

=== "Kotlin"

    ```kotlin
        val faker = BaseFaker()
        val person = faker.populate(Person::class.java)
    ```

Or you can use `net.datafaker.providers.base.BaseFaker.populate(java.lang.Class<T>, net.datafaker.schema.Schema<java.lang.Object, ?>)` to populate object with custom schema.

=== "Java"

    ```java
        BaseFaker faker = new BaseFaker();
        Person person = faker.populate(Person.class, Schema.of(field("name", () -> faker.superhero().name())));
    ```

=== "Kotlin"

    ```kotlin
        val faker = BaseFaker()
        val person = faker.populate(Person::class.java, Schema.of(field("name", Supplier { faker.superhero().name() })))
    ```


## TOML transformation
TOML transformation is similar to YAML and CSV.

The following is an example on how to use it:

=== "Java"

    ``` java
        final BaseFaker faker = new BaseFaker();

        TomlTransformer<Object> transformer = new TomlTransformer<>();
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

will generate toml with nested fields:

```
name = "Elaine"
lastname = "King"
[phones]
worknumbers = [ "(806) 207-5920", "(505) 640-6195" ]
cellphones = [ "(214) 287-6337", "(872) 940-4806", "(813) 294-1719" ]
[address]
city = "Lake Caitlin"
country = "Mongolia"
streetAddress = "5111 D'Amore Fall"
```
