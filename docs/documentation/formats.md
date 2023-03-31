# Formats (This is DEPRECATED functionality! Please have a look at Transformation Schemas instead)

Since version 1.2.0 of Datafaker it's possible to export generated data to a file format of your choice.

The currently supported file formats are:

* XML

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
