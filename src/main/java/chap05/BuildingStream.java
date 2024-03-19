package chap05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class BuildingStream {
    public static void main(String[] args) {
        /**
         * 스트림 만들기
         *
         * - of(T... values): value 로 이루어진 스트림 생성
         * - empty(): 스트림 비우기
         * - ofNullable(T t): value 가 null 일수있는 스트림 생성
         *    - 구현: t == null ? Stream.empty() : Stream.of(t)
         * - Arrays.stream(T[] array): 배열로 스트림 생성
         * - java.nio.file.Files 의 많은 정적 메서드가 스트림을 반환함
         *    - ex) Files.lines(): 주어진 파일의 행 스트림을 문자열로 반환
         * 무한 스트림
         * - iterate(final T seed, final UnaryOperator<T> f): 초기값과 람다를 인수로 받아 값을 무한으로 생산하는 스트림 생성
         *    - 생산된 값을 연속적으로 계산함
         *    - iterate(T seed, Predicate<? super T> hasNext, UnaryOperator<T> next): 조건이 만족할 때 까지
         * - generate(Supplier<? extends T> s): Supplier<T>를 인수로 받아서 새로운 값을 무한으로 생산하는 스트림 생성
         */

        // of()
        Stream<String> stream = Stream.of("Modern ", "Java ", "In ", "Action");
        stream.map(String::toUpperCase).forEach(System.out::println); // 대문자로 변환 후 출력

        // empty()
        Stream<String> emptyStream = Stream.empty();

        // ofNullable()
        Stream<String> homeValueStream = Stream.ofNullable(System.getProperty("home"));

        // Arrays.stream()
        int[] numbers = {2, 3, 5, 7, 11, 13};
        Arrays.stream(numbers); // int[] 배열이 들어오면 IntStream으로 변환됨됨

        // Files.lines() 고유한 단어 수 찾기
        long uniqueWords = 0;
        try (Stream<String> lines = Files.lines(Paths.get("src/main/java/chap03/data.txt"))) { // 스트림은 AutoCloseable 인터페이스를 구현하고 있음
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" "))).distinct().count();
            System.out.println("uniqueWords :: " + uniqueWords);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // iterate()
        System.out.println("=== iterate with limit ===");
        Stream.iterate(0, n -> n + 1)
                .limit(10)
                .forEach(System.out::println);

        System.out.println("=== iterate with predicate ===");
        Stream.iterate(0, n -> n < 10, n -> n + 1)
                .forEach(System.out::println);

        // generate()
        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);
    }
}

