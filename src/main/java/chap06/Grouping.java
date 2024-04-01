package chap06;

import chap04.Dish;

import javax.swing.text.html.Option;
import java.util.*;

import static chap04.Dish.dishTags;
import static chap04.Dish.menu;
import static java.util.stream.Collectors.*;

public class Grouping {
    public static void main(String[] args) {
        // 메뉴를 타입별로 그룹화
        Map<Dish.Type, List<Dish>> dish = menu.stream().collect(groupingBy(Dish::getType)); // toList() 생략
        System.out.println(dish); // {OTHER=[french fries, rice, season fruit, pizza], MEAT=[pork, beef, chicken], FISH=[prawns, salmon]}]

        // 칼로리별로 그룹화
        Map<String, List<Dish>> dishesByCalorie = menu.stream().collect(
                groupingBy(d -> {
                    if (d.getCalories() <= 400) {
                        return "DIET";
                    } else if (d.getCalories() <= 700) {
                        return "NORMAL";
                    } else {
                        return "FAT";
                    }
                })
        );
        System.out.println(dishesByCalorie); // {DIET=[chicken, rice, season fruit, prawns], FAT=[pork], NORMAL=[beef, french fries, pizza, salmon]}

        // 칼로리가 500보다 큰 요리만 타입별로 그룹화
        Map<Dish.Type, List<Dish>> caloricDishesByType = menu.stream().collect(
                groupingBy(Dish::getType,
                        filtering(d -> d.getCalories() > 500, toList()))
        );
        System.out.println(caloricDishesByType); // {OTHER=[french fries, pizza], MEAT=[pork, beef], FISH=[]}

        // 타입별 요리 이름 목록
        Map<Dish.Type, List<String>> dishNamesByType = menu.stream().collect(
                groupingBy(Dish::getType,
                        mapping(Dish::getName, toList()))
        );
        System.out.println(dishNamesByType); // {OTHER=[french fries, rice, season fruit, pizza], MEAT=[pork, beef, chicken], FISH=[prawns, salmon]}

        // 요리별 태그 목록을 가진 맵으로 타입별 태그 목록 추출
        Map<Dish.Type, Set<String>> dishTagsByType = menu.stream().collect(
                groupingBy(Dish::getType,
                        flatMapping(d -> dishTags.get(d.getName()).stream(), toSet()))
        );
        System.out.println(dishTagsByType); // {OTHER=[salty, greasy, natural, light, tasty, fresh, fried], MEAT=[salty, greasy, roasted, fried, crisp], FISH=[roasted, tasty, fresh, delicious]}

        // 칼로리별 그룹화 + 타입별 그룹화
        Map<Dish.Type, Map<String, List<Dish>>> dishesByTypeCaloricLevel = menu.stream().collect(
                groupingBy(Dish::getType,
                        groupingBy(d -> {
                            if (d.getCalories() <= 400) {
                                return "DIET";
                            } else if (d.getCalories() <= 700) {
                                return "NORMAL";
                            } else {
                                return "FAT";
                            }
                        }))
        );
        System.out.println(dishesByTypeCaloricLevel); // {OTHER={DIET=[rice, season fruit], NORMAL=[french fries, pizza]}, MEAT={DIET=[chicken], FAT=[pork], NORMAL=[beef]}, FISH={DIET=[prawns], NORMAL=[salmon]}}

        // 타입별 요리 수
        Map<Dish.Type, Long> typesCount = menu.stream().collect(
                groupingBy(Dish::getType, counting())
        );
        System.out.println(typesCount); // {OTHER=4, MEAT=3, FISH=2}

        // 타입별 최대 칼로리인 요리
        Map<Dish.Type, Optional<Dish>> mostCaloricByType = menu.stream().collect(
                groupingBy(Dish::getType,
                        maxBy(Comparator.comparingInt(Dish::getCalories)))
        );
        System.out.println(mostCaloricByType); // {OTHER=Optional[pizza], MEAT=Optional[pork], FISH=Optional[salmon]}

        // 컬렉터 결과를 다른 형식에 적용(Optional 제거)
        // 리듀싱 컬렉터는 절대 Optional.empty()를 반환하지 않으므로 안전한 코드임
        Map<Dish.Type, Dish> mostCaloricByType2 = menu.stream().collect(
                groupingBy(Dish::getType, // 그룹화 할 분류 함수
                        collectingAndThen(maxBy(Comparator.comparingInt(Dish::getCalories)), // 감싸인 컬렉터
                                Optional::get)) // 변환 함수
        );
        System.out.println(mostCaloricByType2); // {OTHER=pizza, MEAT=pork, FISH=salmon}
    }
}