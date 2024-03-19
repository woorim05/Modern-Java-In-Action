package chap05;

import chap04.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static chap04.Dish.menu;

public class Reducing {
    public static void main(String[] args) {
        /**
         * 리듀싱: 모든 스트림 요소를 처리해서 값으로 도출. 복잡한 질의에 용이
         * - T reduce(T identity, BinaryOperator<T> accumulator)
         *   : 초기값, 누적값 실행 연산 => 초기값의 타입으로 반환
         * - Optional<T> reduce(BinaryOperator<T> accumulator)
         *   : 초기값 없음 => 값이 없을 수 있으므로 Option 타입으로 반환
         *
         * reduce 장점
         * - reduce를 이용하면 내부 반복이 추상화되면서 내부 구현에서 병렬로 reduce를 실행하게 됨
         * - 단계적 반복(가변 누적자 패턴/mutable accumulator pattern)에서는 sum 변수를 공유해야 하므로 쉽게 병렬화하기 어려움
         */

        // numbers 합계 구하기
        List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);

        // 기존의 단계적 반복
        int sum = 0;
        for (int n : numbers) {
            sum += n;
        }
        System.out.println(sum);

        // 스트림 reduce 사용
        int sum2 = numbers.stream().reduce(0, (a, b) -> a + b);
        System.out.println(sum2);

        // sum2의 람다식을 메서드 참조 표현식으로 변경
        int sum3 = numbers.stream().reduce(0, Integer::sum);
        System.out.println(sum3);

        // 초기값 없이 reduce 사용
        Optional<Integer> min = numbers.stream().reduce(Integer::min);
        min.ifPresent(System.out::println);

        // map & reduce 활용하여 총 칼로리 계산
        int calories = menu.stream()
                            .map(Dish::getCalories)
                            .reduce(0, Integer::sum);
        System.out.println("Number of calories :: " + calories);

        // map & reduce 활용하여 스트림의 요리 개수 계산
        int dishes = menu.stream().map(d -> 1).reduce(0, Integer::sum);
        System.out.println("count of dishes :: " + dishes);
    }
}
