package chap06;

import chap04.Dish;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static chap04.Dish.menu;

public class CollectorBasic {
    public static void main(String[] args) {
        // toListCollector
        List<Dish> dishes = menu.stream().collect(new ToListCollector<Dish>());
        System.out.println(dishes);

        // toList
        List<Dish> dishes2 = menu.stream().collect(Collectors.toList());
        System.out.println(dishes2);

        // 컬렉터 구현체를 만들지 않고 커스텀: IDENTITY_FINISH 수집 연산에서는 Collector 인터페이스를 구현하지 않고 같은결과를 얻을 수 있음
        // 하지만 커스텀 컬렉터를 구현하는게 중복을 피하고 재사용성을 높일 수 잇음
        List<Dish> dishes3 = menu.stream().collect(ArrayList::new, List::add, List::addAll);
        System.out.println(dishes3);
    }
}
