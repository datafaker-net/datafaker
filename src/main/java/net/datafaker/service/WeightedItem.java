package net.datafaker.service;

public record WeightedItem<T>(double weight, T item) {
    public WeightedItem {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be positive");
        }
    }
}
