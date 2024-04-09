package chap05;

import javax.swing.text.html.Option;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class Practice {
    static Trader raoul = new Trader("Raoul", "Cambridge");
    static Trader mario = new Trader("Mario", "Milan");
    static Trader alan = new Trader("Alan", "Cambridge");
    static Trader brian = new Trader("Brian", "Cambridge");

    public static List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300, "A12"),
            new Transaction(raoul, 2012, 1000, "a13"),
            new Transaction(mario, 2012, 700, "1ca"),
            new Transaction(raoul, 2011, 400, "C13"),
            new Transaction(mario, 2012, 710, "b14"),
            new Transaction(alan, 2012, 950, "2dd")
    );
    public static void main(String[] args) {

        // 1. 2011년에 일어난 모든 트랜잭션을 찾아 값을 오름차순으로 정리하시오.
        List<Transaction> transactions2011 = transactions.stream()
                .filter(t -> t.getYear() == 2011)
                .sorted(comparing(Transaction::getValue))
                .collect(Collectors.toList());
        System.out.println(transactions2011.toString());

        // 2. 거래자가 근무하는 모든 도시를 중복 없이 나열하시오.
        List<String> cities = transactions.stream()
                .map(t -> t.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());
        System.out.println(cities);

        // 3. 케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오.
        List<Trader> cambridgeTraders = transactions.stream()
                .map(Transaction::getTrader)
                .filter(t -> t.getCity().equals("Cambridge"))
                .distinct()
                .sorted(comparing(Trader::getName))
                .collect(Collectors.toList());
        System.out.println(cambridgeTraders);

        // 4. 모든 거래자의 이름을 알파벳순으로 정렬해서 문자열로 반환하시오.
        String names = transactions.stream()
                .map(t -> t.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (n1, n2) -> n1 + n2);
        System.out.println(names);

        // 5. Milan에 거래자가 있는가?
        boolean isMilan = transactions.stream()
                .anyMatch(t -> t.getTrader().getCity().equals("Milan"));
        System.out.println(isMilan);

        // 6. 케임브리지에 거주하는 거래자의 모든 트랜잭션값을 출력하시오.
        transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .forEach(System.out::println);

        // 7. 전체 트랜잭션 중 최대값은 얼마인가?
        Optional<Integer> maximum = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);
        System.out.println(maximum);

        // 8. 가장 작은 값을 가진 트랜잭션은?
        Optional<Transaction> minimum = transactions.stream()
                .reduce((t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2);
    }
}
