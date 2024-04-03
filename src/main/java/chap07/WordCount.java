package chap07;


import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class WordCount {
    public static final String SENTENCE =
        " Nel   mezzo del cammin  di nostra  vita "
        + "mi  ritrovai in una  selva oscura"
        + " che la  dritta via era   smarrita ";

    public static void main(String[] args) {
//        System.out.println("반복형으로 단어 수 세기 :: " + countWordsIteratively(SENTENCE) + " words"); // 19

//        Stream<Character> stream = IntStream.range(0, SENTENCE.length()).mapToObj(SENTENCE::charAt);
//        System.out.println("함수형으로 단어 수 세기 :: " + countWords(stream) + " words"); // 19
//
//        Stream<Character> stream2 = IntStream.range(0, SENTENCE.length()).mapToObj(SENTENCE::charAt);
//        System.out.println("병렬로 수행하기 :: " + countWords(stream2.parallel()) + " words"); // 43
        // => 스트림 분할 위치에 따라 하나의 단어를 둘 이상으로 계산하는 상황이 발생 오류
        // 문자열을 임의의 위치에서 분할하지 않고 단어가 끝나는 위치에서만 분할해야함

        // Spliterator 구현
        Spliterator<Character> spliterator = new WordCounterSpliterator(SENTENCE);
        Stream<Character> stream3 = StreamSupport.stream(spliterator, true); // 두번째 인수는 병렬 스트림 생성 여부를 지시함
        System.out.println("Spliterator 구현 :: " + countWords(stream3) + " words"); // 19
    }

    // 반복형으로 단어 수 세기
    public static int countWordsIteratively(String s) {
        int counter = 0;
        boolean lastSpace = true;
        for (char c : s.toCharArray()) {
            if (Character.isWhitespace(c)) {
                lastSpace = true;
            } else {
                if (lastSpace) {
                    counter++;
                }
                lastSpace = false;
            }
        }
        return counter;
    }

    // 리듀싱(컬렉션 구현)
    public static int countWords(Stream<Character> stream) {
        WordCounter wordCounter = stream.reduce(
                new WordCounter(0, true),
                WordCounter::accumulate,
                WordCounter::combine
        );
        return wordCounter.getCounter();
    }
}
