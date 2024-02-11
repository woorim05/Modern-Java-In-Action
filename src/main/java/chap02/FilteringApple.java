package chap02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

// 동작 파라미터화
public class FilteringApple {

    public static void main(String... args) {
        List<Apple> inventory = Arrays.asList(
            new Apple(80, Color.GREEN),
            new Apple(155, Color.GREEN),
            new Apple(120, Color.RED)
        );

        // first try: 녹색 사과 필터링
        List<Apple> greenApples1 = filterGreenApples(inventory);
        System.out.println(greenApples1); // [Apple{color=GREEN, weight=80}, Apple{color=GREEN, weight=155}]

        // second try: 녹색/빨간색 사과 필터링 > 색을 파라미터화, 무게 필터링
        List<Apple> greenApples2 = filterApplesByColor(inventory, Color.GREEN);
        System.out.println(greenApples2); // [Apple{color=GREEN, weight=80}, Apple{color=GREEN, weight=155}]
        List<Apple> redApples = filterApplesByColor(inventory, Color.RED);
        System.out.println(redApples); // [Apple{color=RED, weight=120}]
        // +) weight filtering
        List<Apple> heavyApples = filterApplesByWeight(inventory, 150);
        System.out.println(heavyApples); // [Apple{color=GREEN, weight=155}]

        // third try: 위 조건들을 한번에 실행하는 메서드를 만듬 -> 동작을 파라미터화
        List<Apple> greenApples3 = filterApples(inventory, new AppleColorPredicate());
        System.out.println(greenApples3); // [Apple{color=GREEN, weight=80}, Apple{color=GREEN, weight=155}]
        List<Apple> heavyApples2 = filterApples(inventory, new AppleWeightPredicate());
        System.out.println(heavyApples2); // [Apple{color=GREEN, weight=155}]
        List<Apple> redAndHeavyApples = filterApples(inventory, new AppleRedAndHeavyPredicate());
        System.out.println(redAndHeavyApples); // []

        // fourth try: 익명 클래스 활용
        List<Apple> redApples2 = filterApples(inventory, new ApplePredicate() {
            public boolean test(Apple apple) {
                return Color.RED.equals(apple.getColor());
            }
        });
        System.out.println(redApples2); // [Apple{color=RED, weight=120}]

        // fifth try: 람다 표현식 사용
        List<Apple> result = filterApples(inventory, (Apple apple) -> Color.RED.equals(apple.getColor()));

        // sixth try: 리스트 형식으로 추상화
        List<Apple> result2 = filter(inventory, (Apple apple) -> Color.RED.equals(apple.getColor()));
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> evenNumbers = filter(numbers, (Integer i) -> i % 2 == 0);
        System.out.println(evenNumbers);
    }

    // first try
    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (Color.GREEN.equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    // second try
    public static List<Apple> filterApplesByColor(List<Apple> inventory, Color color) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (color.equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    // second try: weight filtering
    private static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getWeight() > weight) {
                result.add(apple);
            }
        }
        return result;
    }

    // third try
    public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    // sixth try
    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();
        for (T element : list) {
            if (p.test(element)) {
                result.add(element);
            }
        }
        return result;
    }
}

