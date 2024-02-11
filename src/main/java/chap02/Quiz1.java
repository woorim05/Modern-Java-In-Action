package chap02;

import java.util.Arrays;
import java.util.List;

public class Quiz1 {
    public static void main(String... args) {
        List<Apple> inventory = Arrays.asList(
                new Apple(80, Color.GREEN),
                new Apple(155, Color.GREEN),
                new Apple(120, Color.RED)
        );

        prettyPrintApple(inventory, new AppleFancyFormatter());
        prettyPrintApple(inventory, new AppleSimpleFormatter());
    }

    public static void prettyPrintApple(List<Apple> inventory, AppleFormatter p) {
        for (Apple apple : inventory) {
            String output = p.output(apple);
            System.out.println(output);
        }
    }

    public interface AppleFormatter {
        String output(Apple a);
    }

    public static class AppleFancyFormatter implements AppleFormatter {
        public String output(Apple apple) {
            String weight = apple.getWeight() > 150 ? "heavy" : "light";
            return weight + " " + apple.getColor() + " apple";
        }
    }

    public static class AppleSimpleFormatter implements AppleFormatter {
        public String output(Apple apple) {
            return "An apple of " + apple.getWeight() + "g";
        }
    }
}
