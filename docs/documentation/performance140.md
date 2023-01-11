# Performance benchmarks

This page is trying to go through some performance metrics and see how better/worse Datafaker is in compare with Java
Faker and other similar projects.

## Hardware & Software

All the tests are done with help of JMH at Fedora 36 for different JDKs (mentioned in tables below).
The laptop has 32Gb of RAM, AMD Ryzen 7 PRO 5850U with Radeon Graphics.

## Datafaker (1.4.0) vs Java Faker (1.0.2) vs Kotlin-faker (1.11.0) vs JFairy (0.6.5)

Originally Datafaker started as a fork of java-faker. For java-faker there have been mentions of the poor performance
of the library, and this document will compare the performance of the respective libraries against each other.

Recently, the 1.4.0 version of Datafaker has been released, and we'll look into what this says about performance improvements.

To demonstrate the performance, we're going to use JMH benchmarks. All the [code](https://github.com/snuyanzin/datafaker-benchmark) 
is available as a separate project because of JMH license reasons.

Since it's hardly possible and reasonable to compare each method's performance, we'll focus on a subset of features 
in this article. In the below, you can see that Datafaker is about 10x-100x times faster for several cases.

### JDK effects

Also, there is an interesting fact: in case of Datafaker moving from jdk8 to jdk18 performance is improving up to 25%,
while for Java Faker it is not happening. It seems in Java Faker there are some time-consuming operations blocking 
such improvements from updating java version which partially were fixed in Datafaker.

There is also [Kotlin-faker](https://github.com/serpro69/kotlin-faker), which a [performance comparison table](https://github.com/serpro69/kotlin-faker) in README page. 

Unfortunately there is not so much information what kind of benchmarks have been executed. After some search across the
project I was able to find only [this page](https://github.com/serpro69/kotlin-faker/blob/master/docs/src/orchid/resources/pages/faker-comparisons.md)
So, it looks like the only test which were done is checking `Faker.name().name()` performance. 

Ok, let's start with the similar test here. Similar, because we are going to use JMH which was not used in their test.
If applicable we try to execute same tests we did for previous section. So let's start with the original test from
Kotlin Faker

### Original Kotlin Faker Test

(for different libs there should be different classes, for more details look in the code):
`net.datafaker.benchmark.kotlinfakerbenchmark`

| Project      | Java Version                              | Mode | Cnt | Score               | Units |
|:-------------|:------------------------------------------|:-----|:----|:--------------------|:------|
| Datafaker    | openjdk-1.8.0.332.b09-1.fc36.x86_64       | avgt | 10  | 2352.789 ± 61.214   | ms/op |
| Datafaker    | openjdk-11.0.15.0.10-1.fc36.x86_64        | avgt | 10  | 2428.885 ± 19.153   | ms/op |
| Datafaker    | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | avgt | 10  | 1985.712 ± 27.692   | ms/op |
| Java Faker   | openjdk-1.8.0.332.b09-1.fc36.x86_64       | avgt | 10  | 12026.367 ± 103.005 | ms/op |
| Java Faker   | openjdk-11.0.15.0.10-1.fc36.x86_64        | avgt | 10  | 11985.717 ± 202.768 | ms/op |
| Java Faker   | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | avgt | 10  | 9910.120 ± 334.016  | ms/op |
| JFairy       | openjdk-1.8.0.332.b09-1.fc36.x86_64       | avgt | 10  | 10429.579 ± 79.899  | ms/op |
| JFairy       | openjdk-11.0.15.0.10-1.fc36.x86_64        | avgt | 10  | 8921.440 ± 88.582   | ms/op |
| JFairy       | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | avgt | 10  | 8371.050 ± 47.380   | ms/op |
| Kotlin Faker | openjdk-1.8.0.332.b09-1.fc36.x86_64       | avgt | 10  | 2530.238 ± 86.146   | ms/op |
| Kotlin Faker | openjdk-11.0.15.0.10-1.fc36.x86_64        | avgt | 10  | 2522.862 ± 19.028   | ms/op |
| Kotlin Faker | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | avgt | 10  | 2376.537 ± 28.235   | ms/op |

**ATTENTION!** In this test we measure not throughput but the average time of one operation => lower is better.

In general, we could say that for jdk8 and jdk11 timings for Datafaker and Kotlin Faker are more or less similar and for
jdk18 Datafaker is about 20% faster. JFairy and Java Faker are far behind.

### Initialization

It's worth to measure since initially during initialization of Faker object it requires to
initialise all the providers objects and read all the yaml files for providers.

Tests for initialization could be found at `net.datafaker.benchmark.initialization`
Initialization:

| Project      | Java Version                              | Mode  | Cnt | Score              | Units  |
|:-------------|:------------------------------------------|:------|:----|:-------------------|:-------|
| Datafaker    | openjdk-1.8.0.332.b09-1.fc36.x86_64       | thrpt | 10  | 2446.670 ± 15.040  | ops/ms |
| Datafaker    | openjdk-11.0.15.0.10-1.fc36.x86_64        | thrpt | 10  | 2633.235 ± 143.345 | ops/ms |
| Datafaker    | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | thrpt | 10  | 3840.849 ± 143.408 | ops/ms |
| Java Faker   | openjdk-1.8.0.332.b09-1.fc36.x86_64       | thrpt | 10  | 23.834 ± 0.217     | ops/ms |
| Java Faker   | openjdk-11.0.15.0.10-1.fc36.x86_64        | thrpt | 10  | 23.316 ± 0.417     | ops/ms |
| Java Faker   | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | thrpt | 10  | 27.717 ± 0.575     | ops/ms |
| JFairy       | openjdk-1.8.0.332.b09-1.fc36.x86_64       | thrpt | 10  | 0.266 ± 0.008      | ops/ms |
| JFairy       | openjdk-11.0.15.0.10-1.fc36.x86_64        | thrpt | 10  | 0.253 ± 0.015      | ops/ms |
| JFairy       | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | thrpt | 10  | 0.276 ± 0.006      | ops/ms |
| Kotlin Faker | openjdk-1.8.0.332.b09-1.fc36.x86_64       | thrpt | 10  | 0.017 ± 0.001      | ops/ms |
| Kotlin Faker | openjdk-11.0.15.0.10-1.fc36.x86_64        | thrpt | 10  | 0.017 ± 0.001      | ops/ms |
| Kotlin Faker | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | thrpt | 10  | 0.018 ± 0.001      | ops/ms |

### Simple methods `fullname`, `firstname`, `address`

Performance of simple method calls like `fullname`, `firstname`, `address`.

Tests could be found at `net.datafaker.benchmark.simplemethods`

#### Firstname:

| Project       | Java Version                              | Mode  | Cnt | Score             | Units  |
|---------------|:------------------------------------------|:------|:----|:------------------|:-------|
| Datafaker     | openjdk-1.8.0.332.b09-1.fc36.x86_64       | thrpt | 10  | 1381.132 ± 9.388  | ops/ms |
| Datafaker     | openjdk-11.0.15.0.10-1.fc36.x86_64        | thrpt | 10  | 1523.378 ± 10.667 | ops/ms |
| Datafaker     | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | thrpt | 10  | 1640.834 ± 59.306 | ops/ms |
| Java Faker    | openjdk-1.8.0.332.b09-1.fc36.x86_64       | thrpt | 10  | 232.055 ± 2.842   | ops/ms |
| Java Faker    | openjdk-11.0.15.0.10-1.fc36.x86_64        | thrpt | 10  | 257.667 ± 1.610   | ops/ms |
| Java Faker    | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | thrpt | 10  | 301.318 ± 2.774   | ops/ms |
| JFairy        | openjdk-1.8.0.332.b09-1.fc36.x86_64       | thrpt | 10  | 114.971 ± 0.881   | ops/ms |
| JFairy        | openjdk-11.0.15.0.10-1.fc36.x86_64        | thrpt | 10  | 137.098 ± 2.846   | ops/ms |
| JFairy        | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | thrpt | 10  | 148.091 ± 2.048   | ops/ms |
| Kotlin Faker  | openjdk-1.8.0.332.b09-1.fc36.x86_64       | thrpt | 10  | 904.868 ± 6.883   | ops/ms |
| Kotlin Faker  | openjdk-11.0.15.0.10-1.fc36.x86_64        | thrpt | 10  | 965.916 ± 27.270  | ops/ms |
| Kotlin Faker  | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | thrpt | 10  | 1074.216 ± 98.063 | ops/ms |

#### Fullname:

| Project      | Java Version                              | Mode  | Cnt | Score            | Units  |
|--------------|:------------------------------------------|:------|:----|:-----------------|:-------|
| Datafaker    | openjdk-1.8.0.332.b09-1.fc36.x86_64       | thrpt | 10  | 433.824 ± 21.696 | ops/ms |
| Datafaker    | openjdk-11.0.15.0.10-1.fc36.x86_64        | thrpt | 10  | 517.713 ± 24.944 | ops/ms |
| Datafaker    | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | thrpt | 10  | 535.289 ± 5.651  | ops/ms |
| Java Faker   | openjdk-1.8.0.332.b09-1.fc36.x86_64       | thrpt | 10  | 81.282 ± 1.199   | ops/ms |
| Java Faker   | openjdk-11.0.15.0.10-1.fc36.x86_64        | thrpt | 10  | 85.278 ± 2.770   | ops/ms |
| Java Faker   | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | thrpt | 10  | 106.813 ± 1.710  | ops/ms |
| JFairy       | openjdk-1.8.0.332.b09-1.fc36.x86_64       | thrpt | 10  | 114.874 ± 0.564  | ops/ms |
| JFairy       | openjdk-11.0.15.0.10-1.fc36.x86_64        | thrpt | 10  | 136.762 ± 1.933  | ops/ms |
| JFairy       | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | thrpt | 10  | 143.378 ± 1.582  | ops/ms |
| Kotlin Faker | openjdk-1.8.0.332.b09-1.fc36.x86_64       | thrpt | 10  | 407.972 ± 6.095  | ops/ms |
| Kotlin Faker | openjdk-11.0.15.0.10-1.fc36.x86_64        | thrpt | 10  | 447.162 ± 5.546  | ops/ms |
| Kotlin Faker | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | thrpt | 10  | 448.645 ± 28.789 | ops/ms |

#### StreetAddress:

| Project      | Java Version                              | Mode  | Cnt | Score           | Units  |
|--------------|:------------------------------------------|:------|:----|:----------------|:-------|
| Datafaker    | openjdk-1.8.0.332.b09-1.fc36.x86_64       | thrpt | 10  | 291.779 ± 3.699 | ops/ms |
| Datafaker    | openjdk-11.0.15.0.10-1.fc36.x86_64        | thrpt | 10  | 319.959 ± 7.984 | ops/ms |
| Datafaker    | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | thrpt | 10  | 339.846 ± 9.068 | ops/ms |
| Java Faker   | openjdk-1.8.0.332.b09-1.fc36.x86_64       | thrpt | 10  | 42.421 ± 0.497  | ops/ms |
| Java Faker   | openjdk-11.0.15.0.10-1.fc36.x86_64        | thrpt | 10  | 43.939 ± 0.571  | ops/ms |
| Java Faker   | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | thrpt | 10  | 66.658 ± 0.811  | ops/ms |
| JFairy       | openjdk-1.8.0.332.b09-1.fc36.x86_64       | thrpt | 10  | 113.572 ± 1.644 | ops/ms |
| JFairy       | openjdk-11.0.15.0.10-1.fc36.x86_64        | thrpt | 10  | 136.216 ± 0.465 | ops/ms |
| JFairy       | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | thrpt | 10  | 145.778 ± 2.186 | ops/ms |
| Kotlin Faker | openjdk-1.8.0.332.b09-1.fc36.x86_64       | thrpt | 10  | 76.850 ± 6.447  | ops/ms |
| Kotlin Faker | openjdk-11.0.15.0.10-1.fc36.x86_64        | thrpt | 10  | 100.451 ± 0.480 | ops/ms |
| Kotlin Faker | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | thrpt | 10  | 99.861 ± 1.799  | ops/ms |

### String template operations

From one side Kotlin Faker and JFairy do not support expressions, from the other side Kotlin Faker supports
numerify/bothify/letterify and regexify operations.

So, the tests are done based on [Kotlin Faker's doc page](https://serpro69.github.io/kotlin-faker/wiki/extras/#random-strings-from-templates)

#### Numerify:

| Project      | Java Version                              | Mode  | Cnt | Score                | Units  |
|:-------------|:------------------------------------------|:------|:----|:---------------------|:-------|
| Datafaker    | openjdk-1.8.0.332.b09-1.fc36.x86_64       | thrpt | 10  | 46027.055 ± 4323.326 | ops/ms |
| Datafaker    | openjdk-11.0.15.0.10-1.fc36.x86_64        | thrpt | 10  | 45048.868 ± 1976.526 | ops/ms |
| Datafaker    | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | thrpt | 10  | 47427.468 ± 423.228  | ops/ms |
| Java Faker   | openjdk-1.8.0.332.b09-1.fc36.x86_64       | thrpt | 10  | 27089.972 ± 1244.279 | ops/ms |
| Java Faker   | openjdk-11.0.15.0.10-1.fc36.x86_64        | thrpt | 10  | 23325.344 ± 265.684  | ops/ms |
| Java Faker   | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | thrpt | 10  | 30599.747 ± 548.377  | ops/ms |
| Kotlin Faker | openjdk-1.8.0.332.b09-1.fc36.x86_64       | thrpt | 10  | 4503.070 ± 60.716    | ops/ms |
| Kotlin Faker | openjdk-11.0.15.0.10-1.fc36.x86_64        | thrpt | 10  | 4886.276 ± 51.213    | ops/ms |
| Kotlin Faker | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | thrpt | 10  | 5504.774 ± 57.402    | ops/ms |

#### Letterify:

| Subject      | Java Version                              | Mode  | Cnt | Score                | Units  |
|:-------------|:------------------------------------------|:------|:----|:---------------------|:-------|
| Datafaker    | openjdk-1.8.0.332.b09-1.fc36.x86_64       | thrpt | 10  | 41273.044 ± 1247.091 | ops/ms |
| Datafaker    | openjdk-11.0.15.0.10-1.fc36.x86_64        | thrpt | 10  | 47005.203 ± 2799.484 | ops/ms |
| Datafaker    | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | thrpt | 10  | 48441.249 ± 1040.172 | ops/ms |
| Java Faker   | openjdk-1.8.0.332.b09-1.fc36.x86_64       | thrpt | 10  | 30510.549 ± 589.002  | ops/ms |
| Java Faker   | openjdk-11.0.15.0.10-1.fc36.x86_64        | thrpt | 10  | 30869.658 ± 2137.318 | ops/ms |
| Java Faker   | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | thrpt | 10  | 21462.296 ± 530.481  | ops/ms |
| Kotlin Faker | openjdk-1.8.0.332.b09-1.fc36.x86_64       | thrpt | 10  | 4861.635 ± 69.498    | ops/ms |
| Kotlin Faker | openjdk-11.0.15.0.10-1.fc36.x86_64        | thrpt | 10  | 4746.286 ± 71.018    | ops/ms |
| Kotlin Faker | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | thrpt | 10  | 5223.486 ± 46.612    | ops/ms |

#### Bothify:

| Subject      | Java Version                              | Mode  | Cnt | Score               | Units  |
|:-------------|:------------------------------------------|:------|:----|:--------------------|:-------|
| Datafaker    | openjdk-1.8.0.332.b09-1.fc36.x86_64       | thrpt | 10  | 21785.504 ± 596.322 | ops/ms |
| Datafaker    | openjdk-11.0.15.0.10-1.fc36.x86_64        | thrpt | 10  | 22618.331 ± 825.949 | ops/ms |
| Datafaker    | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | thrpt | 10  | 23757.533 ± 172.493 | ops/ms |
| Java Faker   | openjdk-1.8.0.332.b09-1.fc36.x86_64       | thrpt | 10  | 11096.829 ± 574.783 | ops/ms |
| Java Faker   | openjdk-11.0.15.0.10-1.fc36.x86_64        | thrpt | 10  | 9118.489 ± 336.647  | ops/ms |
| Java Faker   | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | thrpt | 10  | 8296.013 ± 76.493   | ops/ms |
| Kotlin Faker | openjdk-1.8.0.332.b09-1.fc36.x86_64       | thrpt | 10  | 1693.124 ± 62.691   | ops/ms |
| Kotlin Faker | openjdk-11.0.15.0.10-1.fc36.x86_64        | thrpt | 10  | 1762.054 ± 78.296   | ops/ms |
| Kotlin Faker | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | thrpt | 10  | 1908.346 ± 41.831   | ops/ms |

#### Regexify:

| Subject       | Java Version                              | Mode  | Cnt | Score             | Units  |
|:--------------|:------------------------------------------|:------|:----|:------------------|:-------|
| Datafaker     | openjdk-1.8.0.332.b09-1.fc36.x86_64       | thrpt | 10  | 2206.095 ± 33.280 | ops/ms |
| Datafaker     | openjdk-11.0.15.0.10-1.fc36.x86_64        | thrpt | 10  | 2616.809 ± 34.568 | ops/ms |
| Datafaker     | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | thrpt | 10  | 3454.312 ± 11.464 | ops/ms |
| Java Faker    | openjdk-1.8.0.332.b09-1.fc36.x86_64       | thrpt | 10  | 267.981 ± 3.333   | ops/ms |
| Java Faker    | openjdk-11.0.15.0.10-1.fc36.x86_64        | thrpt | 10  | 261.420 ± 0.822   | ops/ms |
| Java Faker    | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | thrpt | 10  | 325.523 ± 2.777   | ops/ms |
| Kotlin Faker  | openjdk-1.8.0.332.b09-1.fc36.x86_64       | thrpt | 10  | 366.627 ± 23.346  | ops/ms |
| Kotlin Faker  | openjdk-11.0.15.0.10-1.fc36.x86_64        | thrpt | 10  | 347.008 ± 3.380   | ops/ms |
| Kotlin Faker  | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | thrpt | 10  | 434.123 ± 1.089   | ops/ms |

### Expressions

Since both Java Faker and Datafaker provide expression functionality like letterify, bothify, regexify, it would make
sense to see the difference.
Here, it is also worth mentioning that for different patterns score could be different, however it is impossible to
check all.

`net.datafaker.benchmark.templatestrings.DatafakerTemplateStrings`

#### Bothify expression:

| Project    | Java Version                              | Mode  | Cnt | Score             | Units  |
|------------|:------------------------------------------|:------|:----|:------------------|:-------|
| Datafaker  | openjdk-1.8.0.332.b09-1.fc36.x86_64       | thrpt | 10  | 1918.381 ± 91.410 | ops/ms |
| Datafaker  | openjdk-11.0.15.0.10-1.fc36.x86_64        | thrpt | 10  | 1868.298 ± 27.953 | ops/ms |
| Datafaker  | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | thrpt | 10  | 2169.941 ± 45.504 | ops/ms |
| Java Faker | openjdk-1.8.0.332.b09-1.fc36.x86_64       | thrpt | 10  | 207.428 ± 2.923   | ops/ms |
| Java Faker | openjdk-11.0.15.0.10-1.fc36.x86_64        | thrpt | 10  | 208.868 ± 2.023   | ops/ms |
| Java Faker | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64 | thrpt | 10  | 298.704 ± 4.315   | ops/ms |

#### Letterify expression:

| Project    | Java Version                               | Mode  | Cnt | Score             | Units  |
|:-----------|:-------------------------------------------|:------|:----|:------------------|:-------|
| Datafaker  | openjdk-1.8.0.332.b09-1.fc36.x86_64        | thrpt | 10  | 1821.056 ± 70.109 | ops/ms |
| Datafaker  | openjdk-11.0.15.0.10-1.fc36.x86_64         | thrpt | 10  | 1922.130 ± 16.922 | ops/ms |
| Datafaker  | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64  | thrpt | 10  | 2104.585 ± 81.704 | ops/ms |
| Java Faker | openjdk-1.8.0.332.b09-1.fc36.x86_64        | thrpt | 10  | 205.280 ± 3.724   | ops/ms |
| Java Faker | openjdk-11.0.15.0.10-1.fc36.x86_64         | thrpt | 10  | 208.239 ± 1.535   | ops/ms |
| Java Faker | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64  | thrpt | 10  | 316.426 ± 5.495   | ops/ms |

#### Regexify expression:

| Project     | Java Version                               | Mode  | Cnt | Score             | Units  |
|:------------|:-------------------------------------------|:------|:----|:------------------|:-------|
| Datafaker   | openjdk-1.8.0.332.b09-1.fc36.x86_64        | thrpt | 10  | 1273.177 ± 8.818  | ops/ms |
| Datafaker   | openjdk-11.0.15.0.10-1.fc36.x86_64         | thrpt | 10  | 1235.418 ± 11.646 | ops/ms |
| Datafaker   | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64  | thrpt | 10  | 1607.923 ± 34.328 | ops/ms |
| Java Faker  | openjdk-1.8.0.332.b09-1.fc36.x86_64        | thrpt | 10  | 120.409 ±  0.993  | ops/ms |
| Java Faker  | openjdk-11.0.15.0.10-1.fc36.x86_64         | thrpt | 10  | 121.226 ± 0.670   | ops/ms |
| Java Faker  | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64  | thrpt | 10  | 158.919 ± 1.609   | ops/ms |

#### Method invocations

Also, both Java Faker and Datafaker allow invocation of methods from expression. Let's consider an example where in
expression it is required to parse and process only one method. In Datafaker, a was added cache for the parsing of methods.
Since only Datafaker and Java Faker support method invocations, there are only these 2 projects tested:

| Project    | Java Version                               | Mode  | Cnt | Score             | Units  |
|:-----------|:-------------------------------------------|:------|:----|:------------------|:-------|
| Datafaker  | openjdk-1.8.0.332.b09-1.fc36.x86_64        | thrpt | 10  | 1020.388 ± 12.270 | ops/ms |
| Datafaker  | openjdk-11.0.15.0.10-1.fc36.x86_64         | thrpt | 10  | 1057.132 ± 27.611 | ops/ms |
| Datafaker  | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64  | thrpt | 10  | 1113.307 ± 21.496 | ops/ms |
| Java Faker | openjdk-1.8.0.332.b09-1.fc36.x86_64        | thrpt | 10  | 145.144 ± 2.523   | ops/ms |
| Java Faker | openjdk-11.0.15.0.10-1.fc36.x86_64         | thrpt | 10  | 147.812 ± 2.206   | ops/ms |
| Java Faker | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64  | thrpt | 10  | 207.275 ± 3.522   | ops/ms |

Similar example as previous, however there are 3 methods. Besides, cache of parsed methods there was also added cache
for parsed args.

| Project    | Java Version                               | Mode  | Cnt | Score            | Units  |
|:-----------|:-------------------------------------------|:------|:----|:-----------------|:-------|
| Datafaker  | openjdk-1.8.0.332.b09-1.fc36.x86_64        | thrpt | 10  | 276.175 ± 6.503  | ops/ms |
| Datafaker  | openjdk-11.0.15.0.10-1.fc36.x86_64         | thrpt | 10  | 321.559 ± 12.098 | ops/ms |
| Datafaker  | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64  | thrpt | 10  | 334.248 ± 12.733 | ops/ms |
| Java Faker | openjdk-1.8.0.332.b09-1.fc36.x86_64        | thrpt | 10  | 33.567 ± 0.515   | ops/ms |
| Java Faker | openjdk-11.0.15.0.10-1.fc36.x86_64         | thrpt | 10  | 36.055 ± 0.460   | ops/ms |
| Java Faker | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64  | thrpt | 10  | 49.014 ± 1.994   | ops/ms |

It makes sense to keep in mind that these tests do not cover all possible use cases and could be considered only as a
starting point for analysis.

## More Fun

There is an [issue](https://github.com/DiUS/java-faker/issues/663) in Java Faker about generation of 100M of objects.
Of course, the task could be solved with concurrent generation in multiple threads.
However, here it is interesting how much a 1 thread application can generated in 1 hour.

The code below for Datafaker generates a bit more than 170M objects for 1 hour.
The same code for Java Faker generates about 38M for 1 hour, meaning on average, Datafaker is about 4x faster than Javafaker.

Kotlin Faker does not support setting of birthday and blood. Without these 2 params it generates about 90M for 1 hour.
`net.datafaker.benchmark.generate_one_hour`
