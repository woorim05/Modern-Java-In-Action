package chap07;

import java.util.Spliterator;
import java.util.function.Consumer;

public class WordCounterSpliterator implements Spliterator<Character> {
    private final String string;
    private int currentChar = 0;

    public WordCounterSpliterator(String string) {
        this.string = string;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        action.accept(string.charAt(currentChar++)); // 현재 문자 소비
        return currentChar < string.length(); // 소비할 문자가 남아있으면 true
    }

    @Override
    public Spliterator<Character> trySplit() {
        int currentSize = string.length() - currentChar;
        if (currentSize < 10) { // 남은 문자열의 길이가 10보다 작으면 중단
            return null;
        }
        // splitPos: 파싱할 문자열의 분할 위치 = 중간
        for (int splitPos = currentSize / 2 + currentChar; splitPos < string.length(); splitPos++) {
            if (Character.isWhitespace(string.charAt(splitPos))) { // 분할할 위치가 공백이면 뒤로 이동 시킴
                // 처음부터 분할 위치까지 문자열을 파싱할 새로운 spliterator 생성
                Spliterator<Character> spliterator = new WordCounterSpliterator(string.substring(currentChar, splitPos));
                currentChar = splitPos; // 시작 위치를 분할 위치로 변경
                return spliterator; // 문자열 분할했으므로 루프 종료
            }
        }
        return null;
    }

    @Override
    public long estimateSize() {
        return string.length() - currentChar;
    }

    @Override
    public int characteristics() {
        return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    } // 이러한 특성임을 알려줌
    // ORDERED: 문자열의 문자 등장 순서가 유의미하고,
    // SIZED: 메서드의 반환값이 정확하고,
    // SUBSIZED: trySplit으로 생성된 Spliterator도 정확한 크기를 가지고,
    // NONNULL: 문자열에는 null문자가 존재하지 않고,
    // IMMUTABLE: 문자열 자체가 불편 클래스이므로 문자열을 파싱하면서 속성이 추가되지 않음

}
