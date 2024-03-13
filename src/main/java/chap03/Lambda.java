package chap03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 람다
 *
 * 메서드로 전달할 수 있는 익명 함수를 단순화한 것.
 * 이름은 없지만 파라미터 리스트, 바디, 반환 형식, 예외 리스트 등을 가질 수 있음
 *
 * 표현식: 파라미터 -> 바디
 * (String s) -> s.length()
 * (Apple a) -> a.getWeight() > 150
 * (int x, int y) -> {
 *     sout("Result: " + x + y);
 * }
 * () -> 42
 * (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight())
 */
public class Lambda {
    public static void main(String[] args) throws IOException {
        // 1. 람다 사용해보기
        Runnable r1 = () -> System.out.println("Hello World");
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World2");
            }
        };
        process(r1);
        process(r2);
        process(() -> System.out.println("Hello World3"));


        // 2. 람다 활용: 실행 어라운드 패턴(실제 자원을 처리하는 코드가 초기화 코드와 정리 코드로 둘러싸인 형태)
        String oneLine = processFile((BufferedReader br) -> br.readLine());
        String twoLines = processFile((BufferedReader br) -> br.readLine() + br.readLine());
        System.out.println(oneLine);
        System.out.println(twoLines);


        // 3. 함수형 인터페이스 사용
        // 반환 타입: Predicate = boolean, Consumer = void, Function = object
        List<String> nonEmpty = filter(Arrays.asList("A", "", "C", ""), (String s) -> !s.isEmpty());
        System.out.println(nonEmpty.toString());

        List<Integer> length = map(Arrays.asList("aaa", "bb", "ccccc"), (String s) -> s.length());
        // [3, 2, 5]
    }

    // 3. 함수형 인터페이스 사용
    private static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> results = new ArrayList<>();
        list.forEach(t -> { // forEach는 Consumer를 인수로 받는 함수임
            if (p.test(t)) {
                results.add(t);
            }
        });
        return results;
    }
    private static <T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> result = new ArrayList<>();
        list.forEach(t -> {
            result.add(f.apply(t));
        });
        return result;
    }

    // 2. 람다 활용
    @FunctionalInterface
    private interface BufferedReaderProcessor {
        String process(BufferedReader b) throws IOException;
    }
    private static String processFile(BufferedReaderProcessor p) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/chap03/data.txt"))) {
            return p.process(br);
        }
    }

    // 1. 람다 사용해보기
    private static void process(Runnable r) {
        r.run();
    }

}
