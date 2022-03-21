package net.datafaker.fileformats;

import net.datafaker.FakeCollection;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

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

    public static Json toJson(Map<Supplier<String>, Supplier<Object>> map) {
        return new Json(map);
    }

    public static <T> Json.JsonForFakeCollection<T> toJson(FakeCollection<T> collection, Map<Function<T, String>, Function<T, Object>> map) {
        return new Json.JsonForFakeCollection<T>(collection, map);
    }

}
