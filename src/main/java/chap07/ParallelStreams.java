package chap07;

import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static chap04.Dish.menu;

public class ParallelStreams {
    public static void main(String[] args) {
        // 첫 번째 방식은 순차 스트림을 생성한 후에 해당 스트림을 병렬 스트림으로 변환해서 병렬로 처리하고
        // 두 번째 방식은 병렬 스트림을 생성해서 병렬로 처리한다
        menu.stream().parallel().collect(Collectors.toList());
        menu.parallelStream().collect(Collectors.toList());

        System.out.println(parallelSum(20));
        System.out.println(parallelRangedSum(20));
    }

    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .parallel() // 스트림을 병렬 스트림으로 변환
                .reduce(0L, Long::sum);
    }

    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n)
                .parallel()
                .reduce(0L, Long::sum);
    }
}
