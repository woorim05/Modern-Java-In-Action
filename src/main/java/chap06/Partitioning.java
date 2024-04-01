package chap06;

import chap04.Dish;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static chap04.Dish.menu;
import static java.util.stream.Collectors.partitioningBy;

public class Partitioning {
    public static void main(String[] args) {
        // vegetarian 여부
        Map<Boolean, List<Dish>> partitionedMenu = menu.stream().collect(
                partitioningBy(Dish::isVegetarian)
        );
        System.out.println(partitionedMenu); // {false=[pork, beef, chicken, prawns, salmon], true=[french fries, rice, season fruit, pizza]}

        // vegetarian인 요리 목록
        List<Dish> vegetarianDishes = partitionedMenu.get(true);
        System.out.println(vegetarianDishes); // [french fries, rice, season fruit, pizza]

        // filter를 사용해도 위와 같은 결과 도출
        List<Dish> vegetarianDishes2 = menu.stream().filter(Dish::isVegetarian).collect(Collectors.toList());
        System.out.println(vegetarianDishes2); // [french fries, rice, season fruit, pizza]

        // 숫자를 소수와 비소수로 분활하기
        Map<Boolean, List<Integer>> partitionPrimes = IntStream.rangeClosed(2, 100).boxed().collect(
                partitioningBy(candidate -> isPrime(candidate))
        );
        System.out.println(partitionPrimes);
    }

    public static boolean isPrime(int candidate) {
        return IntStream.rangeClosed(2, candidate - 1) // 숫자 범위
                .limit((long) Math.floor(Math.sqrt(candidate)) - 1) // 소수의 대상을 주어진 수의 제곱근 이하의 수로 제한
                .noneMatch(i -> candidate % i == 0); // 스트림의 정수로 candidate를 나눌 수 없으면 참
    }
}
