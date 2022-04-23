package net.datafaker.fileformats;

import net.datafaker.FakeCollection;

import java.util.List;

public class Format {
    public static <T> Csv.CsvCollectionBasedBuilder<T> toCsv(FakeCollection<T> collection) {
        return new Csv.CsvCollectionBasedBuilder<T>().collection(collection);
    }

    public static Csv.CsvColumnBasedBuilder<String, Csv.Column> toCsv(Csv.Column... columns) {
        return new Csv.CsvColumnBasedBuilder<String, Csv.Column>().columns(columns);
    }

    public static Csv.CsvColumnBasedBuilder<String, Csv.Column> toCsv(List<Csv.Column> columns) {
        return new Csv.CsvColumnBasedBuilder<String, Csv.Column>().columns(columns);
    }

    public static Json.JsonBuilder toJson() {
        return new Json.JsonBuilder();
    }

    public static <T> Json.JsonFromCollectionBuilder<T> toJson(FakeCollection<T> collection) {
        return new Json.JsonFromCollectionBuilder<>(collection);
    }

}
