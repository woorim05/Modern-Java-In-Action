package chap05;

import chap04.Dish;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static chap04.Dish.menu;

public class Specialization {
    public static void main(String[] args) {
        /**
         * 기본형 특화 스트림(primitive stream specialization)
         * - 숫자형 스트림을 제공하고 있음(IntStream, DoubleStream, LongStream)
         * - 위 인터페이스는 sum, max 등과 같이 자주 사용하는 숫자 관련 리듀싱 연산 수행 메서드를 제공함
         *
         * - mapToInt(): map()과 같지만 Stream<Integer>가 아닌 IntStream 반환함
         *    - mapToDouble, mapToLong 마찬가지
         * - boxed(): 숫자 스트림을 스트림으로 변환
         * 숫자 범위
         * - range(start, end): start + 1 ~ end - 1
         * - rangeClose(start, end): start ~ end
         */

        // IntStream 으로 반환 후 sum 메서드 사용
        int calories = menu.stream()
                .mapToInt(Dish::getCalories)
                .sum();
        System.out.println(calories);

        // InStream 을 Stream으로 변환
        IntStream intStream = menu.stream().mapToInt(Dish::getCalories);
        Stream<Integer> stream = intStream.boxed();
        System.out.println(stream);

        // OptionalInt
        OptionalInt maxCalories = menu.stream()
                .mapToInt(Dish::getCalories)
                .max();
        int max = maxCalories.orElse(1); // 값이 없을 때 기본 최대값을 1로 설정
        System.out.println(max);

        // rangeClosed
        IntStream evenNumbers = IntStream.rangeClosed(1, 100)
                .filter(n -> n % 2 == 0);
        System.out.println(evenNumbers.count()); // 50개

        // 숫자 스트림 활용: 피타고라스 수
        Stream<int[]> pythagoreanTriples = IntStream.rangeClosed(1, 100).boxed()             // 1 ~ 100 까지의 a값 스트림 생성
                .flatMap(a ->                                                                // 생성된 스트림을 평면화된 스트림으로
                        IntStream.rangeClosed(a, 100)                                        // a ~ 100 까지의 b값 스트림 생성
                                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0).boxed()      // a^2 + b^2의 제곱근이 정수인지 확인
                                .map(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)})); // int[a, b, a^2 + b^2의 제곱근] 생성
        pythagoreanTriples.limit(5) // 5개 제한
                .forEach(t ->       // 출력
                        System.out.println(t[0] + ", " + t[1] + ", " + t[2]));

        // 개선할점: 위 코드는 제곱근을 두번 계산하고 있음(filter, map)
        // => 스트림을 먼저 만들고 피타고라스에 맞는 집합을 필터링
        Stream<double[]> pythagoreanTriples2 = IntStream.rangeClosed(1, 100).boxed() // 1 ~ 100 까지의 a값 스트림 생성
                .flatMap(a -> IntStream.rangeClosed(a, 100)                          // a ~ 100 까지의 b값 스트림 생성
                        // IntStream -> double 바꾸는거기 때문에 mapToObj 사용
                        .mapToObj(b -> new double[]{a, b, Math.sqrt(a * a + b * b)}) // double[a, b, a^2 + b^2의 제곱근] 생성(정수 체크를 하기 전이므로 double)
                        .filter(t -> t[2] % 1 == 0));                                // 제곱근 정수인제 확인
        pythagoreanTriples.limit(5) // 5개 제한
                .forEach(t ->       // 출력
                        System.out.println(t[0] + ", " + t[1] + ", " + t[2]));
    }
}
