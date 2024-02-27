package chap03;

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
public class lamda {
    public static void main(String[] args) {
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
    }

    private static void process(Runnable r) {
        r.run();
    }

}
