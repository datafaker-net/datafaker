# File formats

Since version 1.2.0 of Datafaker it's possible to export generated data to a file format of your choice.

The currently support file formats are:

* CSV
* JSON
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
    System.out.println(new Csv.CsvBuilder()
            .columns(Csv.Column.of("first_name", () -> faker.name().firstName()),
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
    

## JSON

It's also possible to generate JSON output:

=== "Java"

    ``` java
    Faker faker = new Faker();
    // If order is not important other maps e.g. HashMap could be used
    Map<Supplier<String>, Supplier<Object>> person = new LinkedHashMap<>();
    Map<Supplier<String>, Supplier<Object>> address = new LinkedHashMap<>();
    address.put(() -> "country", () -> faker.address().country());
    address.put(() -> "city", () -> faker.address().city());
    address.put(() -> "zipcode", () -> faker.address().zipCode());
    address.put(() -> "streetAddress", () -> faker.address().streetAddress());

    person.put(() -> "firstName", () -> faker.name().firstName());
    person.put(() -> "lastName", () -> faker.name().lastName());
    person.put(() -> "phones", () -> new FakeCollection.Builder<String>().suppliers(() -> faker.phoneNumber().phoneNumber()).maxLen(3).build().get());
    person.put(() -> "address", () -> address);
    Json json = new Json(person);
    System.out.println(json.generate());    
    ```

This will produce something similar to the following:

```json
{
  "firstName": "Beau",
  "lastName": "Lueilwitz",
  "phones": [
    "(088) 217-5229 x0106",
    "(343) 379-9190 x7682",
    "(115) 661-2988 x6965"
  ],
  "address": {
    "country": "Iceland",
    "city": "East Everettefurt",
    "zipcode": "65196",
    "streetAddress": "929 Littel Curve"
  }
}
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
    phones.put(() -> "worknumbers", () -> new FakeCollection.Builder<String>().suppliers(() -> faker.phoneNumber().phoneNumber()).maxLen(2).build().get());
    phones.put(() -> "cellphones", () -> new FakeCollection.Builder<String>().suppliers(() -> faker.phoneNumber().cellPhone()).maxLen(3).build().get());
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

        Collection<Xml.XmlNode> address = new FakeCollection.Builder<Xml.XmlNode>()
                .suppliers(() -> new Xml.XmlNode("address",
                        map(entry("country", faker.address().country()),
                                entry("city", faker.address().city()), entry("streetAddress", faker.address().streetAddress())), Collections.emptyList()))
                .maxLen(3).build().get();

        Collection<Xml.XmlNode> persons = new FakeCollection.Builder<Xml.XmlNode>()
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
    Collection<Xml.XmlNode> address = new FakeCollection.Builder<Xml.XmlNode>()
            .suppliers(() -> new Xml.XmlNode("address",
                    of(new Xml.XmlNode("country", faker.address().country()),
                            new Xml.XmlNode("city", faker.address().city()),
                            new Xml.XmlNode("streetAddress", faker.address().streetAddress()))))
            .maxLen(4).build().get();
    Collection<Xml.XmlNode> persons = new FakeCollection.Builder<Xml.XmlNode>()
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
