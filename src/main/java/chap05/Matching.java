package chap05;

import chap04.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static chap04.Dish.menu;

public class Matching {
    public static void main(String[] args) {
        /**
         * 매칭: 특정 속성이 데이터 집합에 있는지 여부를 검색
         * - 최종연산임. boolean 타입 반환
         * - anyMatch(Predicate<? super T> predicate): 인수에 해당하는 요소가 한개라도 있으면 true
         * - allMatch(Predicate<? super T> predicate): 모든 요소가 일치하면 true
         * - noneMatch(Predicate<? super T> predicate): 일치하는 요소가 없으면 true
         *
         * 검색
         * - Optional<T> 반환
         * - findAny(): 현재 스트림에서 임의의 요소를 반환
         * - findFirst(): 스트림의 첫번째 요소 반환
         *
         * Optional<T>
         * - 값의 존재나 부재 여부를 표현하는 컨테이너 클래스
         * - isPresent(): boolean. 값의 존재 여부
         * - isPresent(Consumer<? super T> action): void. 값이 존재하면 action 실행
         * - get(): T. 값이 있으면 T 반환, 없으면 NoSuchElementException 발생
         * - orElse(T other): T. 값이 있으면 T, 없으면 other 반환
         */

        // anyMatch 메뉴에 채식 요리가 있는지 확인
        if (menu.stream().anyMatch(Dish::isVegetarian)) {
            System.out.println("The menu is vegetarian friendly");
        }

        // allMatch 모든 메뉴가 1000칼로리 이하면 건강식
        boolean isHealthy = menu.stream().allMatch(dish -> dish.getCalories() < 1000);
        System.out.println("Is all dishes Healthy? " + isHealthy);

        // noneMatch
        boolean isHealthy2 = menu.stream().noneMatch(dish -> dish.getCalories() >= 1000);
        System.out.println("Is all dishes Healthy? " + isHealthy2);

        // findAny
        Optional<Dish> dish = menu.stream().findAny();
        System.out.println("find any dish :: " + dish);

        Optional<Dish> vegetarianDish = menu.stream()
                                            .filter(Dish::isVegetarian)
                                            .findAny();
        System.out.println("find any vegetarian dish :: " + vegetarianDish);

        // optional - ifPresent
        vegetarianDish.ifPresent(d -> System.out.println(d.getName())); // 값이 있으면 출력

        // findFirst
        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> firstSquareDivisibleByThree = someNumbers.stream()
                                                                    .map(n -> n * n)
                                                                    .filter(n -> n % 3 == 0)
                                                                    .findFirst();
        System.out.println("first square divisible by three :: " + firstSquareDivisibleByThree);
    }
}
