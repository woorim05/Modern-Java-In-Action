package chap09;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static chap09.UnitTest.Point;

public class Debugging {
    public static void main(String[] args) {
        // 고의적으로 에러 발생시켜 스택트레이스 확인
//        List<Point> points = Arrays.asList(new Point(12, 2), null);
//        points.stream()
//                .map(p -> p.getX())
//                .forEach(System.out::println);

        // 로깅
        List<Integer> numbers = Arrays.asList(2, 3, 4, 5);
        numbers.stream()
                .peek(x -> System.out.println("from stream: " + x)) // 소스에서 처음 소비한 요소
                .map(x -> x + 17)
                .peek(x -> System.out.println("after map: " + x))
                .filter(x -> x % 2 == 0)
                .peek(x -> System.out.println("after filter: " + x))
                .limit(2)
                .peek(x -> System.out.println("after limit: " + x))
                .forEach(System.out::println);
    }
}
