
Data Faker
==========

[![Maven Status](https://maven-badges.herokuapp.com/maven-central/net.datafaker/datafaker/badge.svg?style=flat)](http://mvnrepository.com/artifact/net.datafaker/datafaker)
[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![codecov](https://codecov.io/gh/datafaker-net/datafaker/branch/master/graph/badge.svg?token=FJ6EXMUTFD)](https://codecov.io/gh/datafaker-net/datafaker)

This library is a modern fork of [java-faker](https://github.com/DiUS/java-faker), built on Java 8,
with up to date libraries and several newly added Fake Generators.

This library generates fake data, similar to other fake data generators, such as:

* Ruby's [faker](https://github.com/stympy/faker) gem
* Perl's [Data::Faker](https://metacpan.org/pod/Data::Faker) library
* Python [faker](https://faker.readthedocs.io/en/master/) package
* PHP [faker](https://fakerphp.github.io/) library
* Javascript [Faker.js](https://github.com/faker-js/faker) library

It's useful when you're developing a new project and need some pretty data for showcase.

Usage
-----

In the pom.xml, add the following fragment to the `dependencies` section:

```xml
<dependency>
    <groupId>net.datafaker</groupId>
    <artifactId>datafaker</artifactId>
    <version>1.6.0</version>
</dependency>
```

For Gradle users, add the following to your build.gradle file.

```groovy
dependencies {
    implementation 'net.datafaker:datafaker:1.6.0'
}

```

You can also use the snapshot version (`1.7.0-SNAPSHOT`), which automatically gets published
after every push to the master branch of this repository. Binary repository URL for snapshots download is
https://s01.oss.sonatype.org/content/repositories/snapshots/.

### Get started
In your Java code:

```java
Faker faker = new Faker();

String name = faker.name().fullName(); // Miss Samanta Schmidt
String firstName = faker.name().firstName(); // Emory
String lastName = faker.name().lastName(); // Barton

String streetAddress = faker.address().streetAddress(); // 60018 Sawayn Brooks Suite 449
```

Or in your Kotlin code:

```kotlin
val faker = Faker()

val name = faker.name().fullName() // Miss Samanta Schmidt
val firstName = faker.name().firstName() // Emory
val lastName = faker.name().lastName() // Barton

val streetAddress = faker.address().streetAddress() // 60018 Sawayn Brooks Suite 449
```

JShell
```
# from project root folder
jshell --class-path $(ls -d target/*.jar | tr '\n' ':')
|  Welcome to JShell -- Version 17.0.4
|  For an introduction type: /help intro

jshell> import net.datafaker.Faker;

jshell> var faker = new Faker();
faker ==> net.datafaker.Faker@c4437c4

jshell> faker.address().city();
$3 ==> "Brittneymouth"

jshell> faker.name().fullName();
$5 ==> "Vernie Schmidt"
```

### Expressions

```java
Faker faker = new Faker();
faker.expression("#{letterify 'test????test'}"); // testqwastest
faker.expression("#{numerify '#test#'}"); // 3test5
faker.expression("#{templatify 'test','t','q','@'}"); // @esq
faker.expression("#{examplify 'test'}"); // ghjk
faker.expression("#{regexify '[a-z]{4,10}'}"); // wbevoa
faker.expression("#{options.option '23','2','5','$','%','*'}"); // *
faker.expression("#{date.birthday 'yy DDD hh:mm:ss'}"); // 61 327 08:11:45
faker.expression("#{csv '1','name_column','#{Name.first_name}','last_name_column','#{Name.last_name}'}");
// "name_column","last_name_column"
// "Sabrina","Kihn"
faker.expression("#{json 'person','#{json ''first_name'',''#{Name.first_name}'',''last_name'',''#{Name.last_name}''}','address','#{json ''country'',''#{Address.country}'',''city'',''#{Address.city}''}'}");
// {"person": {"first_name": "Barbie", "last_name": "Durgan"}, "address": {"country": "Albania", "city": "East Catarinahaven"}}
```
also more examples at https://www.datafaker.net/documentation/expressions/

### Collections
```java
Faker faker = new Faker();
List<String> names = faker.collection(
                              () -> faker.name().firstName(),
                              () -> faker.name().lastName())
                         .len(3, 5)
                         .generate();
System.out.println(names);
// [Skiles, O'Connell, Lorenzo, West]
```
more examples about that at https://www.datafaker.net/documentation/collections/

### File formats
#### csv
```java
String csv = Format.toCsv(faker.collection(faker::name).build())
                .headers(() -> "first_name", () -> "last_name")
                .columns(Name::firstName, Name::lastName)
                .separator(" ; ")
                .limit(2).build().get();
// "first_name" ; "last_name"
// "Kimberely" ; "Considine"
// "Mariela" ; "Krajcik"
```
JShell
```
# from project root folder
jshell --class-path $(ls -d target/*.jar | tr '\n' ':')
|  Welcome to JShell -- Version 17.0.4
|  For an introduction type: /help intro

jshell> import net.datafaker.Faker;

jshell> import net.datafaker.fileformats.Format;

jshell> import net.datafaker.service.base.Name;

jshell> var faker = new Faker();
faker ==> net.datafaker.Faker@c4437c4

jshell> System.out.println(Format.toCsv(faker.collection(faker::name).build())
   ...>                 .headers(() -> "first_name", () -> "last_name")
   ...>                 .columns(Name::firstName, Name::lastName)
   ...>                 .separator(" ; ")
   ...>                 .limit(2).build().get());
"first_name" ; "last_name"
"Lisa" ; "Crooks"
"Lakita" ; "Powlowski"
```

#### json
```java
Faker faker = new Faker();
String json = Format.toJson(
                faker.collection(faker::name)
                  .maxLen(2)
                  .build())
              .set("firstName", Name::firstName)
              .set("lastName", Name::lastName)
              .build()
              .generate();
// [{"firstName": "Oleta", "lastName": "Toy"},
// {"firstName": "Gerard", "lastName": "Windler"}]
```
More complex examples and other formats like YAML, XML could be found at https://www.datafaker.net/documentation/file-formats/

### Unique Values
```java
Faker faker = new Faker();

// The values returned in the following lines will never be the same.
String firstUniqueInstrument = faker.unique().fetchFromYaml("music.instruments"); // "Flute"
String secondUniqueInstrument = faker.unique().fetchFromYaml("music.instruments"); // "Clarinet"
```
More examples can be found in https://www.datafaker.net/documentation/unique-values

### Custom provider
Add your own custom provider in your app following steps from https://www.datafaker.net/documentation/custom-providers/

Documentation
-----
[Getting started](https://www.datafaker.net/documentation/getting-started/).


Contributions
-------------
See [CONTRIBUTING.md](https://github.com/datafaker-net/datafaker/blob/master/CONTRIBUTING.md)

Providers
-----
[Full list of providers](https://www.datafaker.net/documentation/providers/).


* Address
* Ancient
* Animal
* App
* Appliance
* Aqua Teen Hunger Force
* Artist
* Australia
* Avatar
* Aviation
* AWS
* Babylon 5
* Back To The Future
* Barcode
* Basketball
* Battlefield 1  
* Beer
* Big Bang Theory
* Blood Type
* Bojack Horseman
* Book
* Bool
* Bossa Nova
* Breaking Bad
* Brooklyn Nine-Nine
* Buffy
* Business
* CNPJ [Brazilian National Registry of Legal Entities](https://en.wikipedia.org/wiki/CNPJ)
* CPF [Brazilian individual taxpayer registry identification](https://en.wikipedia.org/wiki/CPF_number)
* Camera
* Cat
* Chuck Norris
* Clash of Clans
* Code
* Coin
* Color
* Commerce
* Community
* Company
* Computer
* Country
* Credit Card Type
* Crypto
* Currency
* Date and Time
* Demographic
* Departed
* Dessert
* Device
* Disease
* Dog
* DC Comics
* Domain
* Dragon Ball
* Driving License
* Dumb and Dumber
* Dune
* Durations
* Educator
* Elden Ring
* Elder Scrolls
* Electrical Components
* England Football
* Esports
* Fallout
* Famous Last Words
* File
* Final Space
* Finance
* Food
* Formula 1 (:racing_car:)
* Friends
* Funny Name
* Game Of Thrones
* Garment Size
* Gender
* Ghostbusters
* Grateful Dead
* Greek Philosopher
* Hacker
* Harry Potter
* Hashing
* Hearthstone
* Heroes of the Storm
* Hey Arnold
* Hipster
* Hitchhiker's Guide To The Galaxy
* Hobbit
* Hobby
* Horse
* House
* How I Met Your Mother
* IdNumber
* Industry Segments
* Internet
* Job
* Kaamelott
* K-pop (Korean popular music)
* League Of Legends
* Lebowski
* Lord Of The Rings
* Locality
* Lorem
* Marketing
* Mass Effect
* Matz
* MBTI
* Measurement
* Medical
* Military
* Minecraft
* Money
* Mood
* Mountains
* Mountaineering
* Movie
* Music
* Name
* Nation
* Nato Phonetic Alphabet
* Nigeria
* Number
* Options
* Oscar Movie
* Overwatch
* Passport
* Password
* Phone Number
* Photography
* Pokemon
* Princess Bride
* Programming Language
* Relationship Terms
* Resident Evil
* Restaurant
* Rick and Morty
* Robin
* Rock Band
* RuPaul's Drag Race
* Science
* Seinfeld
* Shakespeare
* Sip
* Size
* Simpsons
* Slack Emoji
* Soul Knight
* Space
* StarCraft
* StarTrek
* Stock
* Subscription
* Superhero
* Super Mario
* Tea
* Team
* The IT Crowd
* Time
* Touhou
* Tron
* Twin Peaks
* Twitter
* University
* Vehicle
* Verb
* Volleyball
* Weather
* Witcher
* Yoda
* Zelda

Usage with Locales
-----

```java
Faker faker = new Faker(new Locale("YOUR_LOCALE"));
```

For example:

```java
new Faker(new Locale("en", "US")).address().zipCodeByState("CA"));
```

Supported Locales
-----
* ar
* bg
* ca
* ca-CAT
* cs
* da-DK
* de
* de-AT
* de-CH
* en
* en-AU
* en-au-ocker
* en-BORK
* en-CA
* en-GB
* en-IND
* en-MS
* en-NEP
* en-NG
* en-NZ
* en-PAK
* en-SG
* en-UG
* en-US
* en-ZA
* en-PH
* es
* es-MX
* fa
* fi-FI
* fr
* he
* hu
* in-ID
* it
* ja
* ko
* nb-NO
* nl
* pl
* pt
* pt-BR
* ru
* sk
* sv
* sv-SE
* tr
* uk
* vi
* zh-CN
* zh-TW

LICENSE
-------
Copyright (c) 2022 DataFaker.net See the LICENSE file for license rights and limitations.
