# GraalVM Native Image

Datafaker 3.0 packages generated reachability metadata for built-in providers and locale resources. Add Datafaker as a dependency to your application's Native Image build. Built-in provider calls and expressions work with the metadata in the JAR.

```java
Faker faker = new Faker(Locale.US);
String name = faker.name().fullName();
String city = faker.expression("#{Address.city}");
```

Datafaker tests the packaged library on GraalVM for JDK 21 and 25.

## Custom providers and YAML

Load a YAML file available on the deployment filesystem with `addPath`. Load a URL or a resource bundled in your application with `addUrl`.

```java
Faker faker = new Faker();
faker.addPath(Locale.ENGLISH, Path.of("custom-providers.yml"));
String item = faker.resolve("custom.item");
```

The file must define the requested key under its locale and `faker` sections. See [custom providers](custom-providers.md) for the file structure.

Include application-bundled YAML in your application's Native Image resource configuration. Register your application's custom provider methods when expressions call them by name, and register any application classes that a transformation accesses through reflection. [GraalVM reachability metadata](https://www.graalvm.org/latest/reference-manual/native-image/metadata/)

For a working Native Image application using Datafaker, see the [Datafaker native demo](https://github.com/datafaker-net/datafaker-native-demo).
