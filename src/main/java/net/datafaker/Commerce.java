package net.datafaker;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @since 0.8.0
 */
public class Commerce {
    private final Faker faker;
    private final DecimalFormatSymbols decimalFormatSymbols;

    protected Commerce(Faker faker) {
        this.faker = faker;
        decimalFormatSymbols = new DecimalFormatSymbols(faker.getLocale());
    }

    public String department() {
        int numberOfDepartments = Math.max(faker.random().nextInt(4), 1);
        SortedSet<String> departments = new TreeSet<>();
        while (departments.size() < numberOfDepartments) {
            departments.add(faker.fakeValuesService().resolve("commerce.department", this, faker));
        }
        if (departments.size() > 1) {
            String lastDepartment = departments.last();

            return String.join(", ", departments.headSet(lastDepartment)) + " & " + lastDepartment;
        } else {
            return departments.first();
        }
    }

    public String productName() {
        return String.join(" ",
            faker.fakeValuesService().resolve("commerce.product_name.adjective", this, faker),
            faker.fakeValuesService().resolve("commerce.product_name.material", this, faker),
            faker.fakeValuesService().resolve("commerce.product_name.product", this, faker)
        );
    }

    public String material() {
        return faker.fakeValuesService().resolve("commerce.product_name.material", this, faker);
    }

    public String brand() {
        return faker.fakeValuesService().resolve("commerce.brand", this, faker);
    }

    public String vendor() {
        return faker.fakeValuesService().resolve("commerce.vendor", this, faker);
    }

    /**
     * Generate a random price between 0.00 and 100.00
     */
    public String price() {
        return price(0, 100);
    }

    public String price(double min, double max) {
        double price = min + (faker.random().nextDouble() * (max - min));
        return new DecimalFormat("#0.00", decimalFormatSymbols).format(price);
    }

    public String promotionCode() {
        return promotionCode(6);
    }

    public String promotionCode(int digits) {
        return String.join(faker.resolve("commerce.promotion_code.adjective"),
            faker.resolve("commerce.promotion_code.noun"),
            faker.number().digits(digits));
    }
}
