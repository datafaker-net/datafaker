# Performance benchmarks

This page is trying to go through some performance metrics and see how better/worse Datafaker is in compare with Java
Faker and other similar projects.

## Hardware & Software

All the tests are done with help of JMH at Fedora 37 for different JDKs (mentioned in tables below).
The laptop has 32Gb of RAM, AMD Ryzen 7 PRO 5850U with Radeon Graphics.

## Datafaker (1.7.0) vs Datafaker (1.4.0) vs Java Faker (1.0.2) vs Kotlin-faker (1.13.0) vs JFairy (0.6.5)

Originally Datafaker started as a fork of java-faker. For java-faker there have been mentions of the poor performance
of the library, and this document will compare the performance of the respective libraries against each other.

Recently, the 1.7.0 version of Datafaker has been released, and we'll look into what this says about performance improvements.

To demonstrate the performance, we're going to use JMH benchmarks. All the [code](https://github.com/snuyanzin/datafaker-benchmark)
is available as a separate project because of JMH license reasons.

Since it's hardly possible and reasonable to compare each method's performance, we'll focus on a subset of features
in this article. In the below, you can see that Datafaker is about 10x-100x times faster for several cases.

### JDK effects

Also, there is an interesting fact: in case of Datafaker moving from jdk8 to jdk19 performance is improving up to 25%,
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

| Project      | Java Version                                        | Mode | Cnt | Score               | Units |
|:-------------|:----------------------------------------------------|:-----|:----|:--------------------|:------|
| Datafaker    | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | avgt | 10  | 659.362 ± 65.740    | ms/op |
| Datafaker    | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | avgt | 10  | 523.569 ± 14.033    | ms/op |
| Datafaker    | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | avgt | 10  | 472.270 ± 45.679    | ms/op |
| Datafaker    | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | avgt | 10  | 486.267 ± 14.501    | ms/op |
| Java Faker   | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | avgt | 10  | 12146.879 ± 106.738 | ms/op |
| Java Faker   | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | avgt | 10  | 11879.473 ± 281.136 | ms/op |
| Java Faker   | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | avgt | 10  | 9847.805 ± 292.088  | ms/op |
| Java Faker   | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | avgt | 10  | 9411.708 ± 149.752  | ms/op |
| JFairy       | openjdk-1.8.0.332.b09-1.fc36.x86_64                 | avgt | 10  | 10429.579 ± 79.899  | ms/op |
| JFairy       | openjdk-11.0.15.0.10-1.fc36.x86_64                  | avgt | 10  | 8921.440 ± 88.582   | ms/op |
| JFairy       | openjdk-18.0.1.0.10-1.rolling.fc36.x86_64           | avgt | 10  | 8371.050 ± 47.380   | ms/op |
| Kotlin Faker | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | avgt | 10  | 2588.774 ± 160.853  | ms/op |
| Kotlin Faker | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | avgt | 10  | 1909.895 ± 53.884   | ms/op |
| Kotlin Faker | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | avgt | 10  | 1332.293 ± 36.930   | ms/op |
| Kotlin Faker | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | avgt | 10  | 1803.990 ± 26.429   | ms/op |

**ATTENTION!** In this test we measure not throughput but the average time of one operation => lower is better.

1.7.0 Datafaker looks much faster than others in this test.

### Initialization

It's worth to measure since initially during initialization of Faker object it requires to
initialise all the providers objects and read all the yaml files for providers.

Tests for initialization could be found at `net.datafaker.benchmark.initialization`
Initialization:

| Project      | Java Version                                        | Mode  | Cnt | Score              | Units  |
|:-------------|:----------------------------------------------------|:------|:----|:-------------------|:-------|
| Datafaker    | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 1770.056 ± 23.905  | ops/ms |
| Datafaker    | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 2189.480 ± 93.875  | ops/ms |
| Datafaker    | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 2825.415 ± 119.968 | ops/ms |
| Datafaker    | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 2498.148 ± 21.015  | ops/ms |
| Java Faker   | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 24.847 ± 0.520     | ops/ms |
| Java Faker   | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 25.630 ± 1.178     | ops/ms |
| Java Faker   | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 34.029 ± 1.227     | ops/ms |
| Java Faker   | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 30.559 ± 0.179     | ops/ms |
| JFairy       | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 0.260 ± 0.010      | ops/ms |
| JFairy       | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 0.253 ± 0.012      | ops/ms |
| JFairy       | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 0.279 ± 0.004      | ops/ms |
| JFairy       | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 0.273 ± 0.010      | ops/ms |
| Kotlin Faker | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 290.887 ± 0.799    | ops/ms |
| Kotlin Faker | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 271.947 ± 1.289    | ops/ms |
| Kotlin Faker | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 265.584 ± 5.756    | ops/ms |
| Kotlin Faker | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 266.037 ± 2.999    | ops/ms |

### Simple methods `fullname`, `firstname`, `address`

Performance of simple method calls like `fullname`, `firstname`, `address`.

Tests could be found at `net.datafaker.benchmark.simplemethods`

#### Firstname:

| Project      | Java Version                                        | Mode  | Cnt | Score              | Units  |
|--------------|:----------------------------------------------------|:------|:----|:-------------------|:-------|
| Datafaker    | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 3087.829 ± 21.698  | ops/ms |
| Datafaker    | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 4876.539 ± 262.490 | ops/ms |
| Datafaker    | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 4982.802 ± 28.234  | ops/ms |
| Datafaker    | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 4953.212 ± 177.312 | ops/ms |
| Java Faker   | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 231.796 ± 8.644    | ops/ms |
| Java Faker   | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 235.770 ± 1.619    | ops/ms |
| Java Faker   | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 294.121 ± 15.599   | ops/ms |
| Java Faker   | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 288.382 ± 2.770    | ops/ms |
| JFairy       | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 114.964 ± 0.986    | ops/ms |
| JFairy       | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 139.915 ± 1.446    | ops/ms |
| JFairy       | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 149.559 ± 1.306    | ops/ms |
| JFairy       | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 148.669 ± 0.986    | ops/ms |
| Kotlin Faker | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 1327.995 ± 298.392 | ops/ms |
| Kotlin Faker | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 1269.240 ± 143.267 | ops/ms |
| Kotlin Faker | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 1746.555 ± 104.489 | ops/ms |
| Kotlin Faker | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 1421.184 ± 20.329  | ops/ms |

#### Fullname:

| Project      | Java Version                                        | Mode  | Cnt | Score              | Units  |
|--------------|:----------------------------------------------------|:------|:----|:-------------------|:-------|
| Datafaker    | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 1681.933 ± 25.017  | ops/ms |
| Datafaker    | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 2144.822 ± 90.151  | ops/ms |
| Datafaker    | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 2178.651 ± 27.219  | ops/ms |
| Datafaker    | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 2166.699 ± 106.132 | ops/ms |
| Java Faker   | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 81.140 ± 0.570     | ops/ms |
| Java Faker   | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 84.408 ± 3.909     | ops/ms |
| Java Faker   | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 99.199 ± 2.024     | ops/ms |
| Java Faker   | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 104.506 ± 2.861    | ops/ms |
| JFairy       | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 111.744 ± 2.459    | ops/ms |
| JFairy       | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 137.069 ± 1.346    | ops/ms |
| JFairy       | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 147.787 ± 1.270    | ops/ms |
| JFairy       | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 146.856 ± 2.407    | ops/ms |
| Kotlin Faker | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 396.399 ± 3.653    | ops/ms |
| Kotlin Faker | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 522.469 ± 15.708   | ops/ms |
| Kotlin Faker | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 746.832 ± 21.802   | ops/ms |
| Kotlin Faker | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 545.365 ± 23.483   | ops/ms |

#### StreetAddress:

| Project      | Java Version                                        | Mode  | Cnt | Score             | Units  |
|--------------|:----------------------------------------------------|:------|:----|:------------------|:-------|
| Datafaker    | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 1124.835 ± 22.068 | ops/ms |
| Datafaker    | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 1260.726 ± 11.418 | ops/ms |
| Datafaker    | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 1363.289 ± 37.035 | ops/ms |
| Datafaker    | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 1219.488 ± 9.408  | ops/ms |
| Java Faker   | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 42.751 ± 0.440    | ops/ms |
| Java Faker   | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 43.101 ± 0.559    | ops/ms |
| Java Faker   | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 59.785 ± 3.270    | ops/ms |
| Java Faker   | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 57.944 ± 0.415    | ops/ms |
| JFairy       | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 113.553 ± 1.889   | ops/ms |
| JFairy       | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 138.431 ± 1.314   | ops/ms |
| JFairy       | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 146.255 ± 1.918   | ops/ms |
| JFairy       | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 144.412 ± 0.684   | ops/ms |
| Kotlin Faker | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 78.245 ± 1.265    | ops/ms |
| Kotlin Faker | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 97.818 ± 7.926    | ops/ms |
| Kotlin Faker | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 108.132 ± 4.515   | ops/ms |
| Kotlin Faker | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 101.432 ±  1.645  | ops/ms |

### String template operations

From one side Kotlin Faker and JFairy do not support expressions, from the other side Kotlin Faker supports
numerify/bothify/letterify and regexify operations.

So, the tests are done based on [Kotlin Faker's doc page](https://serpro69.github.io/kotlin-faker/wiki/extras/#random-strings-from-templates)

#### Numerify:

| Project      | Java Version                                        | Mode  | Cnt | Score                | Units  |
|:-------------|:----------------------------------------------------|:------|:----|:---------------------|:-------|
| Datafaker    | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 43284.011 ± 2480.512 | ops/ms |
| Datafaker    | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 41784.591 ± 930.326  | ops/ms |
| Datafaker    | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 44165.964 ± 906.038  | ops/ms |
| Datafaker    | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 43090.660 ± 1431.219 | ops/ms |
| Java Faker   | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 26869.455 ± 171.590  | ops/ms |
| Java Faker   | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 24186.835 ± 452.751  | ops/ms |
| Java Faker   | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 24898.424 ± 910.303  | ops/ms |
| Java Faker   | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 25363.478 ± 995.818  | ops/ms |
| Kotlin Faker | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 4710.176 ± 63.139    | ops/ms |
| Kotlin Faker | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 6055.504 ± 306.761   | ops/ms |
| Kotlin Faker | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 7172.360 ± 68.914    | ops/ms |
| Kotlin Faker | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 6743.303 ± 88.353    | ops/ms |

#### Letterify:

| Subject      | Java Version                                        | Mode  | Cnt | Score                | Units  |
|:-------------|:----------------------------------------------------|:------|:----|:---------------------|:-------|
| Datafaker    | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 44238.161 ± 460.133  | ops/ms |
| Datafaker    | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 40382.343 ± 169.415  | ops/ms |
| Datafaker    | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 44153.720 ± 720.553  | ops/ms |
| Datafaker    | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 44967.669 ± 1853.888 | ops/ms |
| Java Faker   | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 32327.400 ± 267.620  | ops/ms |
| Java Faker   | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 30619.871 ± 534.152  | ops/ms |
| Java Faker   | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 21062.038 ± 534.198  | ops/ms |
| Java Faker   | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 21367.003 ± 120.525  | ops/ms |
| Kotlin Faker | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 5219.411 ± 73.725    | ops/ms |
| Kotlin Faker | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 5716.061 ± 210.304   | ops/ms |
| Kotlin Faker | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 6906.832 ± 136.554   | ops/ms |
| Kotlin Faker | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 6410.062 ± 195.229   | ops/ms |

#### Bothify:

| Subject      | Java Version                                        | Mode  | Cnt | Score                | Units  |
|:-------------|:----------------------------------------------------|:------|:----|:---------------------|:-------|
| Datafaker    | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 28442.736 ± 1427.555 | ops/ms |
| Datafaker    | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 28309.247 ± 408.859  | ops/ms |
| Datafaker    | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 25577.048 ± 496.402  | ops/ms |
| Datafaker    | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 28418.711 ± 486.121  | ops/ms |
| Java Faker   | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 11220.600 ± 255.977  | ops/ms |
| Java Faker   | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 9533.013 ± 176.400   | ops/ms |
| Java Faker   | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 7527.531 ± 189.662   | ops/ms |
| Java Faker   | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 7375.935 ± 78.468    | ops/ms |
| Kotlin Faker | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 1741.742 ± 32.551    | ops/ms |
| Kotlin Faker | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 2099.828 ± 97.784    | ops/ms |
| Kotlin Faker | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 1943.925 ± 115.053   | ops/ms |
| Kotlin Faker | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 1906.066 ± 235.425   | ops/ms |

#### Regexify:

| Subject      | Java Version                                        | Mode  | Cnt | Score               | Units  |
|:-------------|:----------------------------------------------------|:------|:----|:--------------------|:-------|
| Datafaker    | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 2265.164 ± 19.465   | ops/ms |
| Datafaker    | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 2454.718 ± 24.566   | ops/ms |
| Datafaker    | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 3582.163 ± 133.808  | ops/ms |
| Datafaker    | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 3530.937 ± 67.913   | ops/ms |
| Java Faker   | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 267.510 ± 1.897     | ops/ms |
| Java Faker   | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 269.823 ± 5.012     | ops/ms |
| Java Faker   | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 315.347 ± 2.404     | ops/ms |
| Java Faker   | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 309.691 ± 11.170    | ops/ms |
| Kotlin Faker | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 385.765 ± 3.909     | ops/ms |
| Kotlin Faker | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 391.847 ± 8.465     | ops/ms |
| Kotlin Faker | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 501.743 ± 4.972     | ops/ms |
| Kotlin Faker | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 514.578 ± 9.987     | ops/ms |

### Expressions

Since both Java Faker and Datafaker provide expression functionality like letterify, bothify, regexify, it would make
sense to see the difference.
Here, it is also worth mentioning that for different patterns score could be different, however it is impossible to
check all.

`net.datafaker.benchmark.templatestrings.DatafakerTemplateStrings`

#### Bothify expression:

| Project    | Java Version                                        | Mode  | Cnt | Score              | Units  |
|------------|:----------------------------------------------------|:------|:----|:-------------------|:-------|
| Datafaker  | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 4544.086 ± 163.034 | ops/ms |
| Datafaker  | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 5085.459 ± 313.491 | ops/ms |
| Datafaker  | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 5160.930 ± 115.282 | ops/ms |
| Datafaker  | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 5077.901 ± 19.463  | ops/ms |
| Java Faker | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 221.838 ± 6.682    | ops/ms |
| Java Faker | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 221.997 ± 2.176    | ops/ms |
| Java Faker | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 318.869 ± 10.933   | ops/ms |
| Java Faker | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 318.498 ± 1.566    | ops/ms |

#### Letterify expression:

| Project    | Java Version                                        | Mode  | Cnt | Score              | Units  |
|:-----------|:----------------------------------------------------|:------|:----|:-------------------|:-------|
| Datafaker  | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 4719.075 ± 110.980 | ops/ms |
| Datafaker  | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 5467.370 ± 415.581 | ops/ms |
| Datafaker  | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 5852.925 ± 263.841 | ops/ms |
| Datafaker  | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 4897.141 ± 223.026 | ops/ms |
| Java Faker | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 231.018 ± 4.009    | ops/ms |
| Java Faker | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 231.348 ± 1.540    | ops/ms |
| Java Faker | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 339.493 ± 7.604    | ops/ms |
| Java Faker | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 348.010 ± 4.090    | ops/ms |

#### Numerify expression:

| Project    | Java Version                                        | Mode  | Cnt | Score              | Units  |
|:-----------|:----------------------------------------------------|:------|:----|:-------------------|:-------|
| Datafaker  | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 4919.219 ± 173.782 | ops/ms |
| Datafaker  | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 5706.372 ± 200.001 | ops/ms |
| Datafaker  | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 6258.169 ± 208.217 | ops/ms |
| Datafaker  | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 5916.530 ± 119.690 | ops/ms |
| Java Faker | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 233.994 ± 4.356    | ops/ms |
| Java Faker | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 231.713 ± 1.796    | ops/ms |
| Java Faker | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 334.916 ± 3.537    | ops/ms |
| Java Faker | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 338.545 ± 19.560   | ops/ms |

#### Regexify expression:

| Project    | Java Version                                        | Mode  | Cnt | Score              | Units  |
|:-----------|:----------------------------------------------------|:------|:----|:-------------------|:-------|
| Datafaker  | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 1843.609 ± 25.113  | ops/ms |
| Datafaker  | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 2012.095 ± 24.405  | ops/ms |
| Datafaker  | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 2610.217 ± 101.287 | ops/ms |
| Datafaker  | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 2616.375 ± 39.477  | ops/ms |
| Java Faker | JDK 1.8.0_352, OpenJDK 64-Bit Server VM, 25.352-b08 | thrpt | 10  | 126.065 ± 4.649    | ops/ms |
| Java Faker | JDK 11.0.17, OpenJDK 64-Bit Server VM, 11.0.17+8    | thrpt | 10  | 125.829 ± 0.747    | ops/ms |
| Java Faker | JDK 17.0.5, OpenJDK 64-Bit Server VM, 17.0.5+8      | thrpt | 10  | 163.896 ± 0.420    | ops/ms |
| Java Faker | JDK 19.0.1, OpenJDK 64-Bit Server VM, 19.0.1+10     | thrpt | 10  | 164.620 ± 2.021    | ops/ms |

#### Method invocations

Method invocations for the case of Datafaker and Javafaker are covered by simple method calls like full name and address since 
under the hood there are method invocations. 


It makes sense to keep in mind that these tests do not cover all possible use cases and could be considered only as a
starting point for analysis.
