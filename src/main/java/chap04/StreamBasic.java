package chap04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static chap04.Dish.menu;

public class StreamBasic {
    public static void main(String[] args) {
        /**
         * 스트림
         * : 데이터 처리 연산을 지원하도록 소스에서 추출된 연속된 요소
         *
         * - 선언형(구현 코드 대신 질의로 표현)으로 컬렉션 데이터 처리
         *    - filter, sorted, map, collect 와 같은 여러 빌딩 블록 연산을 연결(조립)해서 파이프라인 생성
         * - 멀티스레드 코드 구현 없이 데이터를 투명하게 병렬 처리 가능
         * => 가독성, 유연성, 성능 적으로 우수
         *
         * 스트림과 컬렉션의 차이
         * - 컬렉션은 현재 자료구조가 포함하는 모든 값을 메모리에 저장하는 자료구조
         *   = 컬렉션의 모든 요소는 컬렉션에 추가하기 전에 계산되어야 함
         * - 컬렉션은 외부 반복(반복문을 사용하여 사용자가 직접 요소 반복)
         * - 스트림은 요청할 때 요소를 계산하는 고정된 자료구조(스트림에 요소 추가/제거 불가능)
         * - 스트림은 단 한번만 소비 할 수 있음(반복사용 불가)
         * - 스트림은 내부 반복(반복을 알아서 처리하고 결과 스트림값을 어딘가에 저장해줌)
         *   => 내부 반복을 이용하면 작업을 투명하게 병렬로 처리하거나 최적화된 다양한 순서로 처리할 수 있음
         *
         * 스트림 연산
         * - 중간연산과 최종연산으로 구성
         * - 중간연산: 연결할 수 있는 스트림 연산(filter, map, limit, sorted, distinct 등), 결과 생성 안됨
         * - 최종연산: 스트림을 닫는 연산(collect, count, forEach 등), 결과 생성함
         */


        /** 저칼로리의 요리명 반환하기(칼로리 순으로 정렬) */
        // 기존 코드
        List<Dish> lowCaloricDishes = new ArrayList<>(); // <- 칼로리가 400 이하인 요리를 담기 위한 가비지 변수
        for (Dish dish : menu) {
            if (dish.getCalories() < 400) {
                lowCaloricDishes.add(dish);
            }
        }
        Collections.sort(lowCaloricDishes, new Comparator<Dish>() { // 익명 클래스로 정렬
            @Override
            public int compare(Dish o1, Dish o2) {
                return Integer.compare(o1.getCalories(), o2.getCalories());
            }
        });
        List<String> lowCaloricDishesName = new ArrayList<>(); // 정렬된 리스트에서 요리 이름 담음
        for (Dish dish : lowCaloricDishes) {
            lowCaloricDishesName.add(dish.getName());
        }

        // stream을 사용한 코드
        List<String> lowCaloricDishesName2 = menu.stream()
                                                .filter(d -> d.getCalories() < 400)
                                                .sorted(Comparator.comparing(Dish::getCalories))
                                                .map(Dish::getName)
                                                .collect(Collectors.toList()); // collect가 호출되기 전까지 출력 결과 없음


    }
}
