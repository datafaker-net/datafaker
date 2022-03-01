# Custom providers

Since version 1.2.0 of Datafaker it's possible create your own provider of data.

A [full example](https://github.com/datafaker-net/datafaker/blob/master/src/test/java/net/datafaker/CustomFakerTest.java) can be found in the source code.


## Custom hardcoded provider

To create a custom provider of data, you'll need to do the following steps:

* Create custom provider of data
* Create your own custom faker which extends `Faker` and register custom provider

In code, this would look like the following:

### Hardcoded provider

Create custom provider of data:

=== "Java"

    ``` java
    public static class SpaceForce<T extends Faker> {
        private static final String[] ROCKET_NAMES = new String[]{"Appollo", "Soyuz", "Vostok", "Voskhod", "Progress", "Falcon", "Gemini", "Mercury"};
        private final T faker;

        public SpaceForce(T faker) {
            this.faker = faker;
        }

        public String nextRocketName() {
            return ROCKET_NAMES[faker.random().nextInt(ROCKET_NAMES.length)];
        }
    }
    ```


### Register provider

Create your own custom faker which extends `Faker` and register custom provider:

=== "Java"

    ``` java
    public static class MyCustomFaker extends Faker {
        public SpaceForce spaceForce() {
            return getProvider(SpaceForce.class, () -> new SpaceForce<>(this));
        }
    }
    ```

### Usage

To use the custom faker, you can do the following:

=== "Java"

    ``` java
    MyCustomFaker myFaker = new MyCustomFaker();
    System.out.println(myFaker.spaceForce().nextRocketName());
    ```

This will print something like the following:

```
Falcon
```

## Custom provider using Yaml file

In case you have a large set of data to load, it might be easier to use a Yaml file.
This is possible too.

To create a custom provider of data fom a file, you'll need to do the following steps:

* Create custom provider of data
* Create your own custom faker which extends `Faker` and register custom provider

### Yaml provider

In code, this would look like the following:

=== "Java"

    ``` java
    public static class SpaceForceFromFile<T extends Faker> {
        private static final String KEY = "spaceforcefromfile";
        private final T faker;

        public SpaceForceFromFile(T faker) {
            this.faker = faker;
            // Multiple files can be loaded here if needed
            faker.fakeValuesService().addPath(Locale.ENGLISH, Paths.get("src/test/rockets.yml"));
        }

        public String rocketName() {
            return faker.fakeValuesService().resolve(KEY + ".rocketname", null, faker);
        }

        public String rocketName2() {
            return faker.fakeValuesService().resolve(KEY + ".rocketname2", null, faker);
        }
    }    
    ```

The `rockets.yml` would look like the following:

```yaml
en:
  faker:
    spaceforcefromfile:
      rocketname: ['Appollo', 'Soyuz', 'Vostok', 'Voskhod', 'Progress', 'Falcon', 'Gemini', 'Mercury']
```

### Register provider

Registering the provider would happen like this:

=== "Java"

    ``` java
    public static class MyCustomFaker extends Faker {
        public SpaceForceFromFile spaceForceFromFile() {
            return getProvider(SpaceForceFromFile.class, () -> new SpaceForceFromFile<>(this));
        }
    }
    ```

### Usage

To use the custom faker, you can do the following:

=== "Java"

    ``` java
    MyCustomFaker myFaker = new MyCustomFaker();
    System.out.println(myFaker.spaceForceFromFile().rocketName());
    ```

This will print something like the following:

```
Mercury
```
