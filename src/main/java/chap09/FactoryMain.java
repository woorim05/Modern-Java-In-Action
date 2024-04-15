package chap09;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class FactoryMain {
    public static void main(String[] args) {
        Product p = ProductFactory.createProduct("loan");
        System.out.println(p.getClass().getSimpleName());

        // refactor
        Product p2 = map.computeIfAbsent("loan", k -> {
            throw new RuntimeException("No such product " + k);
        }).get();
        System.out.println(p2.getClass().getSimpleName());
    }

    public static class ProductFactory {
        public static Product createProduct(String name) {
            switch (name) {
                case "loan" :
                    return new Loan();
                case "stock":
                    return new Stock();
                case "bond":
                    return new Bond();
                default:
                    throw new RuntimeException("No such product " + name);
            }
        }
    }

    public static interface Product {}
    public static class Loan implements Product {}
    public static class Stock implements Product {}
    public static class Bond implements Product {}

    // refactor
    public static final Map<String, Supplier<Product>> map = new HashMap<>();
    static {
        map.put("loan", Loan::new);
        map.put("stock", Stock::new);
        map.put("bond", Bond::new);
    }
}
