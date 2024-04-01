# Collector 인터페이스

---
- collect: ***스트림의 요소를 요약 결과로 누적하는 다양한 방법***(컬렉터라 불리는)을 인수로 갖는 최종 연산
- collect 메서드로 Collector 인터페이스 구현을 전달하여 Collection 반환   
> Collection = stream.collect(Collectors.method())

### <Collectors 클래스의 정적 팩토리 메서드>
- toList()
- toSet()
- toCollection()
- counting()
- summingInt()
- averagingInt()
- summarizingInt()
- joining()
- maxBy()
- minBy()
- reducing()
- collectingAndThen()
- groupingBy()
- partitioningBy()
---
## <Collector 인터페이스 구현: 스트림의 요소를 어떤 식으로 도출할지 지정>
### 1. 스트림 요소를 하나의 값으로 리듀스하고 요약
- collect로 스트림의 모든 항목을 하나의 결과로 합칠 수 있음
- 요약 연산: 스트림에 있는 객체의 숫자 필드의 합이나 평균 등을 반환하는 연산에 사용되는 리듀싱 기능
    - counting(): 요소 수 반환
    - maxBy(comparator), minBy(comparator): 요소 중 최대/최소값 반환
    - summingInt(f): 요소 합계 반환
    - averagingInt(f): 평균
    - summarizingInt(f): IntSummaryStatistics를 반환(count, sum, min, max, avg 모두 포함되어 있음)
    - joining(delimiter): 문자열 연결
    - reducing(identity, operator): 위 컬렉터들 모두 reducing으로 정의할 수 있음
        - 초기값, 변환 함수, 실행할 코드
  
> ### collect & reduce  
>  collect 메서드는 도출하려는 결과를 누적하는 컨테이너를 바꾸도록 설계된 메서드  
>  reduce는 두 값을 하나로 도출하는 불변형 연산
> ```
> Stream<Integer> stream = Arrays.asList(1, 2, 3, 4, 5, 6).stream();
> List<Integer> numbers = stream.reduce(
>                                 new ArrayList<Integer>(),
>                                 (List<Integer> l, Integer e) -> {
>                                     l.add(e);
>                                     return l;
>                                 },
>                                 (List<Integer> l1, List<Integer> l2) -> {
>                                     l1.addAll(l2);
>                                     return l1;
>                                 }
> );
> ``` 
> - 의미론적 문제: 위 예제에서 reduce 메서드는 누적자로 사용된 리스트를 반환시키므로 reduce를 잘못 활용한 예에 해당함
> - 실용성 문제: 여러 스레드가 동시에 같은 데이터 구조체를 고치면 리스트 자체가 망가져 버리므로 리듀싱 연산을 병렬로 수행할 수 없음  
> 
> => 가변 컨테이너 관련 작업이면서 병렬성을 확보하려면 collect 메서드로 리듀싱 연산을 구현해야 함

### 2. 요소 그룹화
- 데이터 집합을 하나 이상의 특성으로 분류해서 그룹화하는 연산
  - groupingBy(f): 스트림을 그룹화 시킴(groupingBy(f, toList()) 의 축약형)
    - filtering(p, c)
    - mapping(f, c)
    - flatMapping(f, c)
    - groupingBy(f): 다수준 그룹화 가능
    - counting()
    - maxBy(comparator), minBy(comparator)
    - collectingAndThen(c, f): 적용할 컬렉터와 변환 함수를 인수로 받음
  
### 3. 요소 분할
- 프레디케이트를 분류 함수로 사용하는 특수한 그룹화 기능
- 분할 함수가 반환하는 참, 거짓 두 가지 요소의 스트림 리스트를 유지 할 수 있음
  - partitioningBy(f): boolean 반환
---

## <Collector 인터페이스의 메서드 살펴보기>
```
public interface Collector<T, A, R> {  
  Supplier<A> supplier();  
  BiConsumer<A, T> accumulator();
  Function<A, R> finisher();
  BinaryOperator<A> combiner();
  Set<Characteristics> characteristics();
}
```
- T는 수집될 스트림 항목의 제네릭 형식
- A는 누적자, 즉 수집 과정에서 중간 결과를 누적하는 객체의 형식
- R은 수집 연산 결과 객체의 형식(대부분 컬렉션 형식)
- 위에 4개 메서드는 실행하는 함수를 반환하고,  
  아래 characteristics는 collect 메서드가 어떤 최적화를 이용해서  
  리듀싱 연산을 수행할 것인지 결정하도록 돕는 힌트 특성 집합을 제공
  
### 1. supplier: 새로운 결과 컨테이너 만들기
- 빈 결과로 이루어진 Supplier 반환
- 수집 과정에서 빈 누적자 인스턴스를 만드는 파라미터가 없는 함수임

### 2. accumulator: 결과 컨터에너에 요소 추가하기
- 리듀싱 연산을 수행하는 함수(void) 반환
- 요소를 탐색할 때 두 인수, 누적자(n-1번째)와 n번째 요소를 함수에 적용(수행)

### 3. finisher: 최종 변환값을 결과 컨테이너로 적용하기
- 스트림 탬색을 끝내고 누적자 객체를 최종 결과로 변환
- 누적 과정을 끝낼 때 호출할 함수 반환
- 누적자 객체가 이미 최종 결과인 상황도 있음 => 이때는 항등 함수를 반환

> 순차 리듀싱 과정의 논리적 순서
> 1. 새로운 인스턴스 생성(supplier)
> 2. 스트림에 요소가 없을때까지 연산 수행(accumulator)
> 3. 결과 반환(finisher)

### 4. combiner: 두 결과 컨테이너 병합
- 스트림의 서로 다른 서브파트를 병렬로 처리할 때 누적자가 이 결과를 어떻게 처리할지 정의
- 이를 사용하면 스트림의 리듀싱을 병렬로 수행할 수 있음 -> 7장에서 다룸

> 병렬화 리듀싱 과정에서 combiner 메서드 활용
> 1. 스트림을 두 개의 서브파트로 분할(서브파트 크기가 충분히 작아지도록 스트림 분할 반복)
> 2. 위의 순차 알고리즘으로 각각의 서브스트림 병렬로 처리(supplier, accumulator)
> 3. 각 서브스트림을 독립적으로 처리한 결과 병합(combiner)
> 4. 결과 반환(finisher)
    
### 5. characteristics: 컬렉터의 연산을 정의
- Characteristics 형식의 불변 집합 반환
- 스트림을 병렬로 리듀스할 것인지/병렬로 리듀스 한다면 어떤 최적화를 선택해야 할지와 같은 컬렉터에 대한 힌트를 제공
  - UNORDERED: 스트림 요소의 순서에 영향을 받지 않음
  - CONCURRENT: 다중 스레드에서 accumulator 함수를 동시에 호출할 수 있음(스트림의 병렬 리듀싱 수행 가능)
      - UNORDERED를 함께 설정하지 않으면 데이터 소스가 정렬되어 있지 않은 상황에서만 병렬 리듀싱 수행할 수 있음
  - IDENTITY_FINISH: finisher 메서드가 반환하는 함수는 단순히 identity(초기값)를 적용함(생략 가능)