# 병렬 데이터 처리

---
## 병렬 스트림
- 각각의 스레드에서 처리할 수 있도록 스트림 요소를 여러 청크로 분할한 스트림
- 병렬화를 이용하면 순차나 반복 형식에 비해 성능 우수
- parallelStream을 호출하면 병렬 스트림이 생성됨

### <병렬 스트림의 올바른 사용법>
- 올바른 자료구조를 선택
  - LinkedList보다 ArrayList를 효율적으로 분할할 수 있음
- 전체 파이프라인 연산 비용 고려
  - 요소 처리 비용이 클수록 병렬 스트림에 적합
  - 순차 스트림과 병렬 스트림 중 어떤것이 좋은지 성능 측정 후 사용
- 특화 스트림 사용(IntStream, DoubleStream, LongStream)
  - 자동 박싱과 언박싱은 성능을 저하시킬 수 있음
- 공유된 상태를 바꾸는 알고리즘에서는 지양
- 요소의 순서에 의존하는 연산(limit, findFirst 같은) 지양
  - findAny는 요소의 순서와 상관없이 연산하므로 findFirst보다 성능 우수
  - 요소의 순서가 상관 없다면 비정렬된 스트림에 limit를 호출하는 것이 더 효율적
- 소량의 데이터는 병렬 스트림 사용 지양
  - 병렬화 과정에서 생기는 부가 비용이 더 큼
- 최종 연산의 병합 과정(combiner 메서드와 같은) 비용 확인 필요
  - 병합 과정의 비용이 크다면 병렬 스트림으로 얻은 이익이 서브스트림의 부분 결과를 합치는 과정에서 상쇄될 수 있음

---  
## 포크/조인 프레임워크
- 병렬화 할 수 있는 작업을 재귀적으로 작은 작업으로 분할한 다음 서브태스크 결과를 병합하여 전체 결과를 생성

---
## Spliterator 인터페이스
- 분할할 수 있는 반복자
```java
public interface Spliterator<T> {
  boolean tryAdvance(Consumer<? super T> action);
  Spliterator<T> trySplit();
  long estimateSize();
  int characteristics();
}
```
- T는 탐색하는 요소의 형식
- tryAdvance: 요소를 하나씩 순차적으로 소비하면서 탐색해야 할 요소가 남아있으면 true 반환
- trySplit: 일부 요소를 분할해서 두번째 Spliterator 생성
- estimateSize: 탐색 해야 할 요소 수
- characteristics: Spliterator 자체의 특성 집합을 포함하는 int 반환
  - ORDERED: 요소에 정해진 순서가 있으므로 요소 탐색 및 분할 시 순서 유의
  - DISTINCT: x, y 두 요소를 방문했을 때 x.equals(y)는 항상 false 반환(중복 없음)
  - SORTED: 탐색된 요소는 미리 정의된 정렬 순서를 따름
  - SIZED: 크기가 알려진 소스로 Spliterator를 생성했으므로 estimatedSize()는 정확한 값을 반환함
  - NON-NULL: 탐색하는 모든 요소는 NULL이 아님
  - IMMUTABLE: 불변. 요소를 탐색하는 동안 추가/삭제/수정 불가
  - CONCURRENT: 동기화 없이 여러 스레드에서 동시에 수정 불가
  - SUBSIZED: 현재 Spliterator & 분할되는 모든 Spliterator는 SIZED의 특성을 가짐