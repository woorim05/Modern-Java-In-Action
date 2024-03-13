package chap03;

import chap02.Apple;
import chap02.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;

/**
 * Apple을 weight 별로 비교해서 inventory 정렬하기
 * 동작 파라미터화, 익명 클래스, 람다 표현식, 메서드 참조(생성자 참조) 활용
 * => inventroy.sort(comparing(Apple::getWeight));
 */
public class Sort {
    public static void main(String... args) {
        // inventory 생성
        List<Apple> inventory = new ArrayList<>();
        inventory.addAll(Arrays.asList(
                new Apple(80, Color.GREEN),
                new Apple(155, Color.GREEN),
                new Apple(120, Color.RED)
        ));

        // step1. 코드 전달(클래스 생성)
        // [Apple{color=GREEN, weight=80}, Apple{color=RED, weight=120}, Apple{color=GREEN, weight=155}]
        inventory.sort(new AppleComparator());
        System.out.println(inventory);

        // reshuffling things a little
        inventory.set(1, new Apple(30, Color.GREEN));

        // step2. 익명 클래스 사용
        // [Apple{color=GREEN, weight=30}, Apple{color=GREEN, weight=80}, Apple{color=GREEN, weight=155}]
        inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple a1, Apple a2) {
                return a1.getWeight() - a2.getWeight();
            }
        });
        System.out.println(inventory);

        // reshuffling things a little
        inventory.set(1, new Apple(20, Color.RED));

        // step3. 람다 표현식 사용
        // [Apple{color=RED, weight=20}, Apple{color=GREEN, weight=30}, Apple{color=GREEN, weight=155}]
        inventory.sort((a1, a2) -> a1.getWeight() - a2.getWeight());
        /**
         * Comparator 는 Function을 인수로 받는 정적 메서드 comparing을 포함하고 있음
         * - inventory.sort(Comparator.comparing((apple) -> apple.getWeight()));
         */
        System.out.println(inventory);

        // reshuffling things a little
        inventory.set(1, new Apple(10, Color.RED));

        // step4. 메서드 참조 사용
        // [Apple{color=RED, weight=10}, Apple{color=RED, weight=20}, Apple{color=GREEN, weight=155}]
        inventory.sort(comparing(Apple::getWeight));
        System.out.println(inventory);
    }

    // step1
    static class AppleComparator implements Comparator<Apple> {
        @Override
        public int compare(Apple a1, Apple a2) {
            return a1.getWeight() - a2.getWeight();
        }
    }

}