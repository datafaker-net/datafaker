
Data Faker
==========

[![Maven Status](https://maven-badges.herokuapp.com/maven-central/net.datafaker/datafaker/badge.svg?style=flat)](http://mvnrepository.com/artifact/net.datafaker/datafaker)
[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![codecov](https://codecov.io/gh/datafaker-net/datafaker/branch/master/graph/badge.svg?token=FJ6EXMUTFD)](https://codecov.io/gh/datafaker-net/datafaker)

This library is a modern port of [java-faker](https://github.com/DiUS/java-faker), built on Java 8, 
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
    <version>1.1.0</version>
</dependency>
```

For Gradle users, add the following to your build.gradle file.

```groovy
dependencies {
    implementation 'net.datafaker:datafaker:1.1.0'
}

```

You can also use the snapshot version (1.2.0-SNAPSHOT), which automatically gets published 
after every push to the master branch of this repository.

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

Documentation
-----
https://www.datafaker.net/documentation/providers/


Contributions
-------------
See [CONTRIBUTING.md](https://github.com/datafaker-net/datafaker/blob/master/CONTRIBUTING.md)

Providers
-----

* Address
* Ancient
* Animal
* App
* Appliance
* Aqua Teen Hunger Force
* Artist
* Avatar
* Aviation
* Babylon5
* Back To The Future
* Barcode
* Basketball
* Beer
* Bojack Horseman
* Book
* Bool
* Bossa Nova
* Breaking Bad
* Business
* CNPJ [Brazilian National Registry of Legal Entities](https://en.wikipedia.org/wiki/CNPJ)
* CPF [Brazilian individual taxpayer registry identification](https://en.wikipedia.org/wiki/CPF_number)
* Cat
* ChuckNorris
* Code
* Coin
* Color
* Commerce
* Company
* Crypto
* DateAndTime
* Demographic
* Dessert
* Disease
* Dog
* Domain
* DragonBall
* Dune
* Durations
* Educator
* EnglandFootBall
* Esports
* File
* Finance
* Food
* Formula 1 (:racing_car:)
* Friends
* FunnyName
* GameOfThrones
* Gender
* Hacker
* HarryPotter
* Heartstone
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
* Minecraft
* Mood
* Mountains
* Music
* Name
* Nation
* Nigeria
* Number
* Options
* Overwatch
* Passport
* PhoneNumber
* Photography
* Pokemon
* Princess Bride
* Relationship Terms
* Resident Evil
* RickAndMorty
* Robin
* RockBand
* RuPaul's Drag Race
* Shakespeare
* Sip
* SlackEmoji
* Space
* StarCraft
* StarTrek
* Stock
* Superhero
* Team
* Touhou
* TwinPeaks
* Twitter
* University
* Vehicle
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
Copyright (c) 2022 DataFaker.net See the LICENSE file for license rights and limitations.
