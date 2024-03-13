package chap03;

import chap02.Apple;
import chap02.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class FunctionalInterfaceEx {
    public static void main(String[] args) {
        // inventory 생성
        List<Apple> inventory = new ArrayList<>();
        inventory.addAll(Arrays.asList(
                new Apple(80, Color.GREEN),
                new Apple(155, Color.GREEN),
                new Apple(120, Color.RED)
        ));

        // Comparator
        inventory.sort(Comparator.comparing(Apple::getWeight)
                .reversed() // 역순으로 정렬
                .thenComparing(Apple::getColor)); // 무게가 같으면 색으로 정렬


        // Predicate
        Predicate<Apple> redApple = apple -> Color.RED.equals(apple.getColor());
        Predicate<Apple> notRedApple = redApple.negate(); // redApple 반전
        // and / or 사용하여 다양한 조건식으로 사용 가능
        Predicate<Apple> redAndHeavyApple = redApple.and(apple -> apple.getWeight() > 150);
        Predicate<Apple> redAndHeavyAppleorGreen = redApple.and(apple -> apple.getWeight() > 150)
                                                            .or(apple -> Color.GREEN.equals(apple.getColor()));

        // Function
        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        Function<Integer, Integer> h = f.andThen(g); // f함수 실행 후 해당 결과값을 g에 넘김
        int result = h.apply(1); // (x+1)*2 = 4

        Function<Integer, Integer> h2 = f.compose(g); // g함수 실행 후 해당 결과값을 f에 넘김
        int result2 = h.apply(1); // (x*2)+1 = 3

        Function<String, String> addHeader = Letter::addHeader;
        Function<String, String> transformationPipeline = addHeader.andThen(Letter::checkSpelling)
                                                                    .andThen(Letter::addFooter);
        String transformation = transformationPipeline.apply("labda");
        System.out.println(transformation); // From Raoul, Mario and Alan: lambda Kind regards
    }

    public class Letter{
        public static String addHeader(String text) {
            return "From Raoul, Mario and Alan: " + text;
        }
        public static String addFooter(String text) {
            return text + " Kind regards";
        }
        public static String checkSpelling(String text) {
            return text.replaceAll("labda", "lambda");
        }
    }
}
