package chap06;

import chap04.Dish;

import java.util.*;
import java.util.stream.Stream;

import static chap04.Dish.menu;
import static java.util.stream.Collectors.*;

public class ReducingAndSummarizing {
    public static void main(String[] args) {
        // counting()
        long howManyDishes = menu.stream().collect(counting());
        long howManyDishes2 = menu.stream().count();
        System.out.println(howManyDishes + ", " + howManyDishes2); // 9, 9

        // maxBy() 칼로리 최대값
        Optional<Dish> mostCalorieDish = menu.stream().collect(maxBy(Comparator.comparingInt(Dish::getCalories)));
        System.out.println(mostCalorieDish); // Optional[pork]

        // summingInt()
        int totalCalories = menu.stream().collect(summingInt(Dish::getCalories));
        System.out.println(totalCalories); // 4300

        // averagingInt()
        double avgCalories = menu.stream().collect(averagingInt(Dish::getCalories));
        System.out.println(avgCalories); // 477.77777777777777

        // summarizingInt()
        IntSummaryStatistics menuStatistics = menu.stream().collect(summarizingInt(Dish::getCalories));
        System.out.println(menuStatistics); // IntSummaryStatistics{count=9, sum=4300, min=120, average=477.777778, max=800}

        // joining()
        String shortMenu = menu.stream().map(Dish::getName).collect(joining());
        System.out.println(shortMenu); // porkbeefchickenfrench friesriceseason fruitpizzaprawnssalmon
        String shortMenuSep = menu.stream().map(Dish::getName).collect(joining(", "));
        System.out.println(shortMenuSep); // pork, beef, chicken, french fries, rice, season fruit, pizza, prawns, salmon

        // reducing()
        int totalCalories2 = menu.stream().collect(reducing(0, Dish::getCalories, (i, j) -> i + j));
        System.out.println(totalCalories2); // 4300
        Optional<Dish> mostCalorieDish2 = menu.stream().collect(reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));
        System.out.println(mostCalorieDish2); // Optional[pork]

        // 새로운 숫자 스트림 리스트 만들기
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6).stream().reduce(
                new ArrayList<Integer>(),
                (List<Integer> l, Integer e) -> {
                    l.add(e);
                    return l;
                },
                (List<Integer> l1, List<Integer> l2) -> {
                    l1.addAll(l2);
                    return l1;
                }
        );
        System.out.println(numbers);

        List<Integer> numbers2 = Arrays.asList(1, 2, 3, 4, 5, 6).stream().collect(toList());
        System.out.println(numbers2);

    }
}
