# Custom providers

Since version 1.2.0 of Datafaker it's possible to create your own provider of data.

A [full example](https://github.com/datafaker-net/datafaker/blob/main/src/test/java/net/datafaker/providers/base/CustomFakerTest.java) can be found in the source code.

## Custom hardcoded provider

To create a custom provider of data, you'll need to do the following steps:

* Create custom provider of data.
* Create your own custom faker which extends `Faker` and register custom provider.

In code, this would look like the following:

### Hardcoded provider

Create a custom provider of data:

=== "Java"

    ```java
    public static class Insect extends AbstractProvider<BaseProviders> {
        private static final String[] INSECT_NAMES = { "Ant", "Beetle", "Butterfly", "Wasp" };

        public Insect(BaseProviders faker) {
            super(faker);
        }

        public String nextInsectName() {
            return INSECT_NAMES[faker.random().nextInt(INSECT_NAMES.length)];
        }
    }
    ```
 
### Register provider

Create your own custom faker, which extends `Faker`, and register the custom provider:

=== "Java"

    ```java
    public static class MyCustomFaker extends Faker {
        public Insect insect() {
            return getProvider(Insect.class, Insect::new);
        }
    }
    ```

### Usage

To use the custom faker, you can do the following:

=== "Java"

    ```java
    MyCustomFaker myFaker = new MyCustomFaker();
    System.out.println(myFaker.insect().nextInsectName());
    ```

This will print something like the following:
`Wasp`

### Weighted random selection

> **Note**: Usage of weighted random selector is in the POC stage. Custom YAML providers also support the percentage syntax described below.

Since version 2.4.2 of Datafaker it's possible to use a random selector based on weights, add a `WeightedRandomSelector` and weighted values to your provider:

=== "Java"

    ```java
    public static class Insect extends AbstractProvider<BaseProviders> {
        private static final WeightedRandomSelector selector = new WeightedRandomSelector(new Random());

        private static final List<Map<String, Object>> WEIGHTED_INSECTS = List.of(
            Map.of("value", "Driver ant", "weight", 6.0),
            Map.of("value", "Fire ant", "weight", 3.0),
            Map.of("value", "Harvester ant", "weight", 1.0)
        );

        public Insect(BaseProviders faker) {
            super(faker);
        }

        public String weightedInsectName() {
            return selector.select(WEIGHTED_INSECTS);
        }
    }
    ```

Then call the weighted method on your custom faker:

=== "Java"

    ```java
    MyCustomFaker myFaker = new MyCustomFaker();
    System.out.println(myFaker.insect().weightedInsectName());
    ```

This will return a random insect name but based on the provided weights.
`Driver ant`

## Custom provider using YAML file

In case you have a large set of data to load, it might be better to use a YAML file.

To create a custom provider of data from a file, you'll need to do the following steps:

* Create a custom provider of data.
* Create your own custom faker which extends `Faker` and register custom provider.

### YAML provider

First, create the custom provider which loads the data from a file:

=== "Java"

    ```java
    public class InsectFromFile extends AbstractProvider<BaseProviders> {
        private static final String KEY = "insectsfromfile";
        
        public InsectFromFile(BaseProviders faker) {
            super(faker);
            faker.addPath(Locale.ENGLISH, Paths.get("src/test/ants.yml"));
            faker.addPath(Locale.ENGLISH, Paths.get("src/test/bees.yml"));
        }

        public String ant() {
            return resolve(KEY + ".ants");
        }

        public String bee() {
            return resolve(KEY + ".bees");
        }
    }
    ```

The `ants.yml` would look like the following:

```yaml
en:
  faker:
    insectsfromfile:
      ants: ['Driver ant', 'Fire ant', 'Harvester ant', 'Honey ant', 'Leafcutter ant', 'Sahara desert ant']
```

And if you want to use multiple YAML files, the `bees.yml` would look like this:

```yaml
en:
  faker:
    insectsfromfile:
      bees: ['Bumblebee', 'Euglossine bee', 'Honeybee', 'Carpenter bee', 'Leaf-cutter bee', 'Mining bee']
```

### Weighted YAML choices

Datafaker 3.0 supports weighted choices in custom YAML loaded with `addPath` or `addUrl`. Save the following file as `weighted-insects.yml`.

```yaml
en:
  faker:
    insectsfromfile:
      ants:
        - 60%: "Driver ant"
        - 40%: "#{other_ant}"
      other_ant: ["Fire ant", "Harvester ant"]
      domains:
        - 90%: ".it"
        - 10%: [".ee", ".lv", ".lt"]
```

Load the file and resolve its keys on the same Faker instance.

```java
import java.nio.file.Path;
import java.util.Locale;
import java.util.Random;

import net.datafaker.Faker;

Faker faker = new Faker(Locale.ENGLISH, new Random(42));
faker.addPath(Locale.ENGLISH, Path.of("weighted-insects.yml"));

String ant = faker.resolve("insectsfromfile.ants");
String domain = faker.resolve("insectsfromfile.domains");
```

Each call samples a branch with the configured probability. If that branch contains a list, the call then picks one entry uniformly. In this example, `.it` has a 90% chance and each of the three domains in the second branch has a 10% ÷ 3 chance. `resolve` evaluates the selected template, including the local reference `#{other_ant}`. Provider references and slash-delimited regexes also work.

Use one positive decimal percentage per entry, and make the percentages total exactly 100. A branch can contain a string template or a non-empty list of string templates. Numeric percentage keys such as `33.3%` start weighted syntax. A descriptive map key such as `margin%` is ordinary YAML. If a weighted list is invalid, the error identifies its key path and file.

Use `resolve` for a sampled, evaluated string. `fetch` and `safeFetch` can also sample weighted choices. Use ordinary YAML lists with `Unique.fetchFromYaml`.

### Register provider

Registering the provider would happen like this:

=== "Java"

    ```java
    public static class MyCustomFaker extends Faker {
        public InsectFromFile insectFromFile() {
            return getProvider(InsectFromFile.class, InsectFromFile::new);
        }
    }
    ```

### Usage

To use the custom faker, you can do the following:

=== "Java"

    ```java
    MyCustomFaker myFaker = new MyCustomFaker();
    System.out.println(myFaker.insectFromFile().ant());
    ```

This will print something like the following: 
`Honey ant`
