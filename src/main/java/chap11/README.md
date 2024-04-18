# Optional 클래스

---
### 1. Optional 객체 생성
- Optional.empty()
  - 빈 Optional 생성
- Optional.of(object)
  - null이 아닌 값으로 Optional 생성
  - object가 null이면 즉시 NullPointerException 발생
- Optional.ofNullable(object)
  - null이 가능한 Optional
  - object가 null 이면 빈 Optional 반환
    
### 2. 값 추출
- get()
  - 값이 있으면 값 반환
  - 값이 없으면 NoSuchElementException 발생시킴
- orElse(object)
  - 빈 Optinal일 때 기본값 반환
- orElseGet(supplier)
  - lazy 방삭의 orElse
  - 디폴트 메서드를 만드는 데 시간이 걸리거나 비어있을 때만 기본값을 생성하려고 할 때 사용
- orElseThrow(exceptonSupplier)
  - 비어있을 때 발생시킬 예외 선택
- ifPresent(consumer)
  - 값이 존재할 때 인수로 넘겨준 동작 실행
- ifPresentOrElse(consumer, emptyAction)
  - 값이 존재하면 consumer인수로 넘겨준 동작 실행하고 없으면 emptyAction(Runnable) 실행
- map(object.key)
  - key가 null이 아니면 value 반환
  - key가 null이면 아무일도 일어나지 않음
- flatMap(object.key)
  - 이차원 Optional을 일차원 Optional로 평준화
  - 스트림의 flatMap과 비슷
  - 체인으로 여러개 사용 가능
- stream()
  - 값이 존재하면 존재하는 값만 포함하는 스트림 반환하고 없으면 빈 스트림 반환

### 3. 두 개의 Optional 합치기
- o1.flatMap(a -> o2.map(b -> consume(a, b)))
  - o1의 값이 없으면 람다 표현식 실행되지 않고 빈 Optional 반환
  - o2이 값이 있으면 Optional<T> 를 반환하여 T에 해당하는 값(a)을 사용
  - o2가 값이 없으면 consume하지 않고 빈 Optional 반환
  - o2가 값이 있으면(즉 o1, o2 모두 값이 있으면) consume 실행
  
### 4. 필터
- filter(predicate)
  - 필터링 하려는 Optional이 비어있는지 확인하고 비어있으면 프레디케이트 실행하지 않음
  - 프레디케이트와 일치하면 그 값을 반환하고 그렇지 않으면 빈 Optional 반환
```java
Insurance insurance = ...;
if (insurance != null && "Cambridge".equals(insurance.getName())) {
    System.out.println("ok");
}
// 위 코드를 filter를 사용하면
Optional<Insurance> optInsurance = ...;
optInsurance.filter(i -> "Cambridge".equals(i.getName()))
            .ifPresent(x -> System.out.print("ok"));
```