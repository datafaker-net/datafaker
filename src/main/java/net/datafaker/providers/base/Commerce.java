package net.datafaker.providers.base;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @since 0.8.0
 */
public class Commerce extends AbstractProvider<BaseProviders> {
    private final DecimalFormatSymbols decimalFormatSymbols;

    protected Commerce(BaseProviders faker) {
        super(faker);
        decimalFormatSymbols = new DecimalFormatSymbols(faker.getContext().getLocale());
    }

    public String department() {
        int numberOfDepartments = Math.max(faker.random().nextInt(4), 1);
        SortedSet<String> departments = new TreeSet<>();
        while (departments.size() < numberOfDepartments) {
            departments.add(faker.resolve("commerce.department"));
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
            resolve("commerce.product_name.adjective"),
            resolve("commerce.product_name.material"),
            resolve("commerce.product_name.product")
        );
    }

    public String material() {
        return resolve("commerce.product_name.material");
    }

    public String brand() {
        return resolve("commerce.brand");
    }

    public String vendor() {
        return resolve("commerce.vendor");
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
            resolve("commerce.promotion_code.noun"),
            faker.number().digits(digits));
    }
}
