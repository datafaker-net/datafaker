package net.datafaker.formats;

import net.datafaker.sequence.FakeSequence;

import java.util.List;

@Deprecated // Use Transformer
public class Format {
    public static <T> Csv.CsvCollectionBasedBuilder<T> toCsv(FakeSequence<T> sequence) {
        return new Csv.CsvCollectionBasedBuilder<T>().sequence(sequence);
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

    public static <T> Json.JsonFromCollectionBuilder<T> toJson(FakeSequence<T> sequence) {
        return new Json.JsonFromCollectionBuilder<>(sequence);
    }

}
