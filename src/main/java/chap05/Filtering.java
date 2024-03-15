package chap05;

import chap04.Dish;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Filtering {
    public static void main(String[] args) {
        /**
         * 필터링: 스트림의 요소를 선택
         * - filter(Predicate<? super T> predicate): 프레디케이트를 인수로 받아 일치하는 모든 요소를 포함하는 스트림 반환
         * - distinct(): 고유 요소만 반환
         *
         * 슬라이싱: 스트림의 요소를 스킵하여 반환
         * - takeWhile(Predicate<? super T> predicate): 인수에 해당하는 요소까지만 작업하고 중단됨
         * - dropWhile(Predicate<? super T> predicate): takeWhile과 반대. 인수가 거짓이 되면 그 지점에서 작업을 중단하고 남은 모든 요소를 반환
         * - limit(long maxSize): maxSize개 요소 까지만 반환
         * - skip(long n): 처음 n개 요소 제외하여 반환
         */

        // filter, distinct
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream()
                .filter(i -> i % 2 == 0)        // 짝수 요소 선택
                .distinct()                     // 중복 제거
                .forEach(System.out::println);  // 출력 2 \n 4

        List<Dish> menu = Arrays.asList(
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", false, 530, Dish.Type.OTHER)
        );

        // takeWhile
        List<Dish> slicedMenu1 = menu.stream()
                                    .takeWhile(dish -> dish.getCalories() < 320) // season fruit, prawns 선택 후 내부반복 중단
                                    .collect(toList());
        System.out.println("sliced with takeWhile() :: " + slicedMenu1); // [season fruit, prawns]

        // dropWhile
        List<Dish> slicedMenu2 = menu.stream()
                                    .dropWhile(dish -> dish.getCalories() < 320) // 요소가 rice일 때 season fruit, prawns 버리고 내부반복 중단
                                    .collect(toList());                          // 남은 요소 반환(rice, chicken, french fries)
        System.out.println("sliced with dropWhile() :: " + slicedMenu2); // [rice, chicken, french fries]

        // limit
        List<Dish> dishes = menu.stream()
                                .filter(dish -> dish.getCalories() > 300)
                                .limit(3)                                       // filter 인수가 참인 처음 3개 요소를 선택 후 중단
                                .collect(toList());
        System.out.println("sliced with limit() :: " + dishes); // [rice, chicken, french fries]

        // skip
        List<Dish> dishes2 = menu.stream()
                                .filter(dish -> dish.getCalories() > 300)
                                .skip(2)                                       // filter 인수가 참인 처음 2개 요소를 스킵한 후 나머지 선택
                                .collect(toList());
        System.out.println("skip() :: " + dishes2); // [french fries]
    }
}
