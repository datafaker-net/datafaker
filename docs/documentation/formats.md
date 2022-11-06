# Formats (This is DEPRECATED functionality! Please have a look at Transformation Schemas instead)

Since version 1.2.0 of Datafaker it's possible to export generated data to a file format of your choice.

The currently supported file formats are:

* CSV
* JSON
* SQL
* YAML
* XML

## CSV

Using the CSV generation, it's possible to do the following:

* the number and names of columns could be specified
* the separator and quotes could be specified with `separator()` and `quote()`
* the number of lines could be specified via `limit()`
* with or without header also could be specified with `header()`

An example can be found below:

=== "Java"

    ``` java
        System.out.println(
            Format.toCsv(
                    Csv.Column.of("first_name", () -> faker.name().firstName()),
                    Csv.Column.of("last_name", () -> faker.name().lastName()),
                    Csv.Column.of("address", () -> faker.address().streetAddress()))
                .header(true)
                .separator(" ; ")
                .limit(5).build().get());
    ```

Executing the above will result in something similar to the below:

```csv
"first_name" ; "last_name" ; "address"
"Jonah" ; "Kovacek" ; "009 Wilkinson Summit"
"John" ; "Murphy" ; "379 McCullough Locks"
"Colby" ; "Bins" ; "93534 Stevie Gardens"
"Wade" ; "Herzog" ; "83108 Willy Road"
"Marg" ; "Effertz" ; "415 Gene Plaza"
```

Another example using a fake sequence builder:

=== "Java"

    ``` java
        System.out.println(
            Format.toCsv(faker.collection(faker::name).build())
                .headers(() -> "first_name", () -> "last_name")
                .columns(Name::firstName, Name::lastName)
                .separator(" ; ")
                .limit(2).build().get());
    ```

=== "Java"

    ``` java
        System.out.println(
            Format.toCsv(faker.stream(faker::name).build())
                .headers(() -> "first_name", () -> "last_name")
                .columns(Name::firstName, Name::lastName)
                .separator(" ; ")
                .limit(2).build().get());
    ```

The result in both cases looks something like this:

```csv
"first_name" ; "last_name"
"Jonah" ; "Kovacek"
"John" ; "Murphy"
```

Note: you can generate a CSV from an infinite FakeStream in case if you specified
limit parameter for the result CSV, otherwise you will get an exception.

## JSON

A way to generate simple JSON. However, it won't work for complex JSON generation:

=== "Java"

    ``` java
        Faker faker = new Faker();

        System.out.println(Format.toJson()
            .set("firstName", () -> faker.name().firstName())
            .set("lastName", () -> faker.name().lastName())
            .set("address", () -> faker.address().country())
            .build().generate());
    ```
This will produce JSON similar to the following:
``` json
{"firstName": "Clemencia", "lastName": "Collier", "address": "Nauru"}
```

It's also possible to generate a more complex JSON:

=== "Java"

    ``` java
    Faker faker = new Faker();
    String json = Format.toJson(
                faker.collection(faker::name)
                    .len(2)
                    .build())
            .set("firstName", Name::firstName)
            .set("lastName", Name::lastName)
            .set("address",
                Format.toJson(
                        faker.collection(faker::address)
                            .len(1)
                            .build())
                    .set("country", Address::country)
                    .set("city", Address::city)
                    .set("zipcode", Address::zipCode)
                    .set("streetAddress", Address::streetAddress)
                    .build())
            .set("phones", name -> faker.collection(() -> faker.phoneNumber().phoneNumber()).maxLen(3).build().get())
            .build()
            .generate();
        System.out.println(json);
    ```

The same could be achieved with FakeStream:

=== "Java"

    ``` java
    Faker faker = new Faker();
    String json = Format.toJson(
                faker.stream(faker::name)
                    .len(2)
                    .build())
            .set("firstName", Name::firstName)
            .set("lastName", Name::lastName)
            .set("address",
                Format.toJson(
                        faker.stream(faker::address)
                            .len(1)
                            .build())
                    .set("country", Address::country)
                    .set("city", Address::city)
                    .set("zipcode", Address::zipCode)
                    .set("streetAddress", Address::streetAddress)
                    .build())
            .set("phones", name -> faker.stream(() -> faker.phoneNumber().phoneNumber()).maxLen(3).build().get())
            .build()
            .generate();
        System.out.println(json);
    ```


This will produce something similar to the following:

```json
[{"firstName": "Azucena", "lastName": "Block", "address": [{"country": "Micronesia", "city": "Ralphberg", "zipcode": "03792", "streetAddress": "522 Detra Motorway"}], "phones": ["885.387.7538 x3339", "673-179-8684 x7840", "512-510-3469 x47468"]},
  {"firstName": "Hollis", "lastName": "Conroy", "address": [{"country": "Anguilla", "city": "Murrayshire", "zipcode": "96973", "streetAddress": "84545 Carolyne Hills"}], "phones": ["133.943.3781 x16122", "797.830.4970 x310", "(599) 214-5520 x920"]}]
```

Another example with json payload

=== "Java"

    ``` java
        Faker faker = new Faker();
        String json = Format.toJson(
                faker.collection(faker::name).faker(faker)
                    .len(2)
                    .build())
            .set("firstName", Name::firstName)
            .set("lastName", Name::lastName)
            .set("payload", payload ->
                Format.toJson(
                        faker.collection(faker::address)
                            .len(1)
                            .build())
                    .set("country", Address::country)
                    .set("city", Address::city)
                    .set("zipcode", Address::zipCode)
                    .set("streetAddress", Address::streetAddress)
                    .build().generate())
            .set("phones", name -> faker.collection(() -> faker.phoneNumber().phoneNumber()).maxLen(3).build().get())
            .build()
            .generate();
        System.out.println(json);
    ```

This will produce json with escaped json payload e.g.:
```json
[{"firstName": "Rey", "lastName": "Hilpert", "payload": "[{\"country\": \"Vanuatu\", \"city\": \"Douglasborough\", \"zipcode\": \"78956\", \"streetAddress\": \"15586 DuBuque Circles\"}]", "phones": ["(739) 078-6320", "(530) 089-9967 x167", "422.892.6273 x46644"]},
{"firstName": "Timmy", "lastName": "Lakin", "payload": "[{\"country\": \"Chile\", \"city\": \"East Frederick\", \"zipcode\": \"07470\", \"streetAddress\": \"425 Hackett Tunnel\"}]", "phones": ["416.215.9044", "700.631.9476", "1-521-484-1096"]}]
```

## YAML

A lightweight YAML generator is now built into Datafaker. 

The following is an example on how to use it:

=== "Java"

    ``` java
    Faker faker = new Faker();
    Map<Supplier<String>, Supplier<Object>> map = new LinkedHashMap<>();
    Map<Supplier<String>, Supplier<Object>> address = new LinkedHashMap<>();
    Map<Supplier<String>, Supplier<Object>> phones = new LinkedHashMap<>();
    phones.put(() -> "worknumbers", () -> faker.<String>collection().suppliers(() -> faker.phoneNumber().phoneNumber()).maxLen(2).build().get());
    phones.put(() -> "cellphones", () -> faker.<String>collection().suppliers(() -> faker.phoneNumber().cellPhone()).maxLen(3).build().get());
    address.put(() -> "city", () -> faker.address().city());
    address.put(() -> "country", () -> faker.address().country());
    address.put(() -> "streetAddress", () -> faker.address().streetAddress());
    map.put(() -> "name", () -> faker.name().firstName());
    map.put(() -> "lastname", () -> faker.name().lastName());
    map.put(() -> "address", () -> address);
    map.put(() -> "phones", () -> phones);
    Yaml yaml = new Yaml(map);
    System.out.println(yaml.generate());
    ```

## XML

### Elements and attributes

In case you want to generate XML, Datafaker provides a facility to build XML elements and 
attributes using randomly generated data in the following way: 

=== "Java"

    ``` java
    public static void main(String[] args) {
        Faker faker = new Faker();

        Collection<Xml.XmlNode> address = faker.<Xml.XmlNode>collection()
                .suppliers(() -> new Xml.XmlNode("address",
                        map(entry("country", faker.address().country()),
                                entry("city", faker.address().city()), entry("streetAddress", faker.address().streetAddress())), Collections.emptyList()))
                .maxLen(3).build().get();

        Collection<Xml.XmlNode> persons = faker.<Xml.XmlNode>collection()
                .suppliers(() -> new Xml.XmlNode("person",
                        map(entry("firstname", faker.name().firstName()),
                                entry("lastname", faker.name().lastName())),
                        of(new Xml.XmlNode("addresses", address)))).maxLen(3).build().get();

        String str = new Xml(new Xml.XmlNode("persons", persons)).generate(true);
        System.out.println(str);
    }

    private static <T> Collection<T> of(T... elems) {
        return Arrays.asList(elems);
    }

    private static Map.Entry<String, String> entry(String key, String value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    private static Map<String, String> map(Map.Entry<String, String>... entries) {
        Map<String, String> map = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }    
    ```

This will produce the following output:

```xml
<persons>
    <person firstname="Chuck" lastname="Rice">
        <addresses>
            <address country="Croatia" city="South Stacimouth" streetAddress="8958 Ervin Stravenue"/>
            <address country="Uruguay" city="South Cariefort" streetAddress="59014 Howell Pike"/>
            <address country="Bahrain" city="New Ian" streetAddress="446 Wuckert Brooks"/>
        </addresses>
    </person>
    <person firstname="Brent" lastname="Walter">
        <addresses>
            <address country="Croatia" city="South Stacimouth" streetAddress="8958 Ervin Stravenue"/>
            <address country="Uruguay" city="South Cariefort" streetAddress="59014 Howell Pike"/>
            <address country="Bahrain" city="New Ian" streetAddress="446 Wuckert Brooks"/>
        </addresses>
    </person>
    <person firstname="Amy" lastname="Parisian">
        <addresses>
            <address country="Croatia" city="South Stacimouth" streetAddress="8958 Ervin Stravenue"/>
            <address country="Uruguay" city="South Cariefort" streetAddress="59014 Howell Pike"/>
            <address country="Bahrain" city="New Ian" streetAddress="446 Wuckert Brooks"/>
        </addresses>
    </person>
</persons>
```

### Elements only

In case you only want to generate XML elements, without any attributes, that possible too:


=== "Java"

    ``` java
    Faker faker = new Faker();
    Collection<Xml.XmlNode> address = faker.<Xml.XmlNode>collection()
            .suppliers(() -> new Xml.XmlNode("address",
                    of(new Xml.XmlNode("country", faker.address().country()),
                            new Xml.XmlNode("city", faker.address().city()),
                            new Xml.XmlNode("streetAddress", faker.address().streetAddress()))))
            .maxLen(4).build().get();
    Collection<Xml.XmlNode> persons = faker.<Xml.XmlNode>collection()
            .suppliers(() -> new Xml.XmlNode("person",
                    of(new Xml.XmlNode("firstname", faker.name().firstName()),
                            new Xml.XmlNode("lastname", faker.name().lastName()),
                            new Xml.XmlNode("addresses", address)))).maxLen(2).build().get();

    String str = new Xml(new Xml.XmlNode("persons", persons)).generate(true);
    System.out.println(str);    
    ```

Executing the above will result in:

```xml
<persons>
    <person>
        <firstname>Shiloh</firstname>
        <lastname>Witting</lastname>
        <addresses>
            <address>
                <country>Azerbaijan</country>
                <city>Port Alta</city>
                <streetAddress>50808 Rickey Plains</streetAddress>
            </address>
            <address>
                <country>Congo</country>
                <city>North Eleonoraton</city>
                <streetAddress>956 Omer Mountain</streetAddress>
            </address>
            <address>
                <country>Niue</country>
                <city>Port Darleneshire</city>
                <streetAddress>363 Rocco Square</streetAddress>
            </address>
            <address>
                <country>Argentina</country>
                <city>Maritzamouth</city>
                <streetAddress>369 Mosciski Knolls</streetAddress>
            </address>
        </addresses>
    </person>
    <person>
        <firstname>Louisa</firstname>
        <lastname>Howell</lastname>
        <addresses>
            <address>
                <country>Azerbaijan</country>
                <city>Port Alta</city>
                <streetAddress>50808 Rickey Plains</streetAddress>
            </address>
            <address>
                <country>Congo</country>
                <city>North Eleonoraton</city>
                <streetAddress>956 Omer Mountain</streetAddress>
            </address>
            <address>
                <country>Niue</country>
                <city>Port Darleneshire</city>
                <streetAddress>363 Rocco Square</streetAddress>
            </address>
            <address>
                <country>Argentina</country>
                <city>Maritzamouth</city>
                <streetAddress>369 Mosciski Knolls</streetAddress>
            </address>
        </addresses>
    </person>
</persons>
```
