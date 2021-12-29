Data Faker
==========

[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

This library is a modern port of [java-faker](https://github.com/DiUS/java-faker), built on Java 11, with up to date libraries
and more Fake Generators than Java Faker has. 

This library is similar to Ruby's [faker](https://github.com/stympy/faker) gem (as well as Perl's Data::Faker library) 
that generates fake data. It's useful when you're developing a new project and need some pretty data for showcase.

Usage
-----
In pom.xml, add the following xml stanza between `<dependencies> ... </dependencies>`

```xml
<dependency>
    <groupId>net.datafaker</groupId>
    <artifactId>datafaker</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

For gradle users, add the following to your build.gradle file.

```groovy
dependencies {
    implementation 'net.datafaker:datafaker:1.0.0-SNAPSHOT'
}

```

In your Java code

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

This is a [demo web application](https://java-faker.herokuapp.com/) that uses the library.

Javadoc
-----
https://www.datafaker.net/docs


Contributions
-------------
See [CONTRIBUTING.md](https://github.com/datafaker-net/datafaker/blob/master/CONTRIBUTING.md)


Fakers
-----
* Address
* Ancient
* Animal
* App
* Aqua Teen Hunger Force
* Artist
* Avatar
* Aviation
* Babylon5 (new!)
* Back To The Future
* Barcode (new!)
* Basketball
* Beer
* Bojack Horseman
* Book
* Bool
* Business
* ChuckNorris
* Cat
* Code
* Coin
* Color
* Commerce
* Company
* CPF [Brazilian individual taxpayer registry identification](https://en.wikipedia.org/wiki/CPF_number)
* Crypto
* DateAndTime
* Demographic
* Dessert (new!)
* Disease
* Dog
* Domain (new!)
* DragonBall
* Dune
* Durations (new!)
* Educator
* Esports
* EnglandFootBall (new!)
* File
* Finance
* Food
* Friends
* FunnyName
* GameOfThrones
* Gender
* Hacker
* HarryPotter
* Heartstone (new!)
* Hipster
* HitchhikersGuideToTheGalaxy
* Hobbit
* HowIMetYourMother
* IdNumber
* Internet
* Job
* Kaamelott
* LeagueOfLegends
* Lebowski
* LordOfTheRings
* Lorem
* Matz
* Mood (new!)
* Minecraft (new!)
* Music
* Name
* Nation
* Number
* Options
* Overwatch
* Passport (new!)
* PhoneNumber
* Photography
* Pokemon
* Princess Bride
* Relationship Terms
* Resident Evil (new!)
* RickAndMorty
* Robin
* RockBand
* Shakespeare
* Sip
* SlackEmoji
* Space
* StarCraft
* StarTrek
* Stock
* Superhero
* Team
* Touhou (new!)
* TwinPeaks
* Twitter (new!)
* University
* Vehicle (new!)
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
new Faker(new Locale("en-us")).address().zipCodeByState("CA"));
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
Copyright (c) 2021 DataFaker.net See the LICENSE file for license rights and limitations.
