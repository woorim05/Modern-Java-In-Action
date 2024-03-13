package chap03;

import chap02.Apple;
import chap02.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.*;

public class MethodReference {
    public void main(String[] args) {
        /**
         * 메서드 참조
         * - 특정 메서드만을 호출하는 람다의 축약형
         * - 가독성을 높일 수 있음
         * - 구분자(::) 사용, 실제 메서드를 호출하는 것이 아니므로 괄호는 필요 없음
         *
         * ex) 람다 => 메서드 참조 단축 표현
         * (Apple apple) -> apple.getWeight() => Apple::getWeight
         * () -> Thread.currentThread().dumpStack() => Thread.currentThread()::dumpStack
         *
         * 메서드 참조 만드는 유형
         * 1. 정적 메서드 참조
         * 2. 다양한 형식의 인스턴스 메서드 참조
         * 3. 기존 객체의 인스턴스 메서드 참조
         */

        // 람다 표현식
        ToIntFunction<String> stringToInt = (String s) -> Integer.parseInt(s) + 2;
        BiPredicate<List<String>, String> contains = (list, element) -> list.contains(element);
        Predicate<String> startsWithNumber = (String s) -> this.startsWithNumber(s);
        // 메서드 참조
        ToIntFunction<String> stringToInt2 = Integer::parseInt;
        BiPredicate<List<String>, String> contains2 = List::contains;
        Predicate<String> startsWithNumber2 = this::startsWithNumber;

        Integer result = stringToInt2.applyAsInt("6"); // 8
        startsWithNumber2.test("1dd");

        /**
         * 생성자 참조
         *
         * - 생성자를 메서드 참조와 같이 표현할 수 있음
         * ex) ClassName::new
         */
        // Apple()
        Supplier<Apple> c1 = () -> new Apple();
        Apple a1 = c1.get();

        Supplier<Apple> c2 = Apple::new;
        Apple a2 = c2.get();


        // Apple(Integer weight)
        List<Integer> weights = Arrays.asList(10, 13, 15, 17);
        List<Apple> apples = map(weights, Apple::new);

        // Apple(Integer wieght, Color color)
        BiFunction<Integer, Color, Apple> c3 = Apple::new;
        Apple a3 = c3.apply(10, Color.GREEN);

    }

    private static <T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> result = new ArrayList<>();
        list.forEach(t -> {
            result.add(f.apply(t));
        });
        return result;
    }

    private boolean startsWithNumber(String s) {
        return s.charAt(0) >= 48 && s.charAt(0) <= 57;
    }

}
