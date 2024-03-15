package chap05;

import chap04.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static chap04.Dish.menu;
import static java.util.stream.Collectors.toList;

public class Mapping {
    public static void main(String[] args) {
        /**
         * 매핑: 특정 객체에서 특정 데이터를 선택하는 작업
         * - map(Function<? super T, ? extends R> mapper): 인수로 주어진 결과가 새로운 요소로 매핑됨
         * - flatMap(Function<? super T, ? extends Stream<? extends R>> mapper): 생성된 스트림을 하나의 스트림으로 평면화
         */

        // map
        List<Integer> dishNameLengths = menu.stream()
                                            .map(Dish::getName)
                                            .map(String::length)
                                            .collect(toList());
        System.out.println("lengths of dish name :: " + dishNameLengths); // [4, 4, 7, 12, 4, 12, 5, 6, 6]


        // map & flatMap 비교
        List<String> words = Arrays.asList("Hello", "World");
        List<String[]> splitWords = words.stream()
                                        .map(word -> word.split(""))
                                        .distinct()
                                        .collect(toList());
        System.out.println("split words with map :: ");
        splitWords.forEach(strings -> System.out.println(Arrays.toString(strings))); // [[H, e, l, l, o], [W, o, r, l, d]]
        // map을 사용해서 문자열을 분리하면 각각의 문자열에서 분리됨(배열이 생성됨)
        // flatMap을 활용하여 분리된 문자열을 스트림으로 만들어 하나의 스트림으로 만듬(평면화)
        List<String> splitWords2 = words.stream()
                                        .flatMap((String line) -> Arrays.stream(line.split(""))) // map(world -> word.split("")).flatMap(Arrays::stream) 과 같음
                                        .distinct()
                                        .collect(toList());
        System.out.println("split words with flatMap :: " + splitWords2); // [H, e, l, o, W, r, d]

        /* 퀴즈 */
        // 1. 숫자 리스트 [1, 2, 3, 4, 5]를 각 숫자의 제곱근 리스트로 반환
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> squares = numbers.stream()
                                        .map(n -> n * n)
                                        .collect(toList());
        System.out.println("squares :: " + squares); // [1, 4, 9, 16, 25]

        // 2. 두 개의 숫자 리스트 [1, 2, 3]과 [3, 4]가 있을 때 모든 숫자 쌍의 리스트 반환
        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);
        List<int[]> pairs = numbers1.stream()
                                    .flatMap(n1 -> numbers2.stream().map(n2 -> new int[] {n1, n2}))
                                    .collect(toList());
        System.out.println("pairs ::");
        pairs.forEach(strings -> System.out.println(Arrays.toString(strings))); // [1, 3], [1, 4], [2, 3], [2, 4], [3, 3], [3, 4]

        // 3. 2번 문제에서 합이 3으로 나누어 떨어지는 쌍만 반환
        List<int[]> pairs2 = numbers1.stream()
                                    .flatMap(n1 -> numbers2.stream()
                                                            .filter(n2 -> (n1 + n2) % 3 == 0)
                                                            .map(n2 -> new int[] {n1, n2}))
                                    .collect(toList());
        System.out.println("pairs2 ::");
        pairs2.forEach(strings -> System.out.println(Arrays.toString(strings))); // [2, 4], [3, 3]

    }
}
