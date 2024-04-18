# 컬렉션 API 개선

---

### 1. 컬렉션 팩토리 메서드
: 객체 생성을 캡슐화하는 디자인 패턴.  
팩토리 메서드로 컬렉션 생성하면 변경 불가능 함.
- 리스트: List.of(e1, e2, ...)
- 집합: Set.of(e1, e2, ...)
- 맵: Map.of(k1, v1, k2, v2, ...)

### 2. 리스트와 집합 처리
- removeIf(p): 프레디케이트를 만족하는 요소 제거 
- replaceAll(o): 오퍼레이터 함수를 이용해 요소 변경
- sort(): 정렬

### 3. 맵 처리
- forEach
- 정렬: 스트림으로 사용
  - Entry.comparingByValue: 값으로 정렬
  - Entry.comparingByKey: 키로 정렬
- getOrDefault(k, default): 키가 존재하지 않으면 디폴트값 지정 가능(null 일때 default 값)
- 계산 패턴
    - computeIfAbsent(k, new v): 키가 존재하지 않으면 새 값을 계산하고 맵에 저장
    - computeIfPresent(k, new v): 키가 존재하면 새 값을 계산하고 맵에 저장(변경)
    - compute(k, new v): 키 여부 상관 없이 새 값을 계산하고 맵에 저장
- 삭제 패턴
    - remove(k, v): 키 존재 판단 후 키의 값과 인수로 넘긴 값 비교 후 일치하면 삭제
- 교체 패턴
    - replaceAll(BiFunction): 키의 값 전체 변경
    - Replace: 키가 존재하면 값 변경
- 병합
    - putAll(map): 맵 그대로 넣기
    - merge(k, v, BiFunction): 키에 해당하는 값이 없거나 널이면 v값을,  
                               값이 있으면 기존 값에 연산(BiFunction) 했을 때
                               값이 null이 아니면 put, null이면 remove
    ```java
      default V merge(K key, V value,
            BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        Objects.requireNonNull(value);
        V oldValue = get(key);
        V newValue = (oldValue == null) ? value :
                   remappingFunction.apply(oldValue, value);
        if (newValue == null) {
            remove(key);
        } else {
            put(key, newValue);
        }
        return newValue;
      }
    ```
    
### 4. 개선된 ConcurrentHashMap 클래스
- 다중 스레드 환경에서 사용할 수 있도록 설계되어 있음
- 내부 자료구조의 특정 부분만 잠궈 동시에 추가 및 갱신 작업을 지원함
  - 리듀스와 검색
    - forEach: 주어진 액션 실행
    - reduce: 리듀스 함수를 이용해 결과로 합침
    - search: null이 아닌 값을 반환할 때까지 각 (키, 값) 쌍에 함수를 적용
    - 쌍(키, 값)으로 연산: 그냥 연산자
    - 키로 연산: forEachKey, reduceKeys, searchKeys
    - 값으로 연산: forEachValue, reduceValues, searchValues
    - Map.Entry 객체로 연산: forEachEntry, reduceEntries, searchEntries
  - 계수
    - mappingCount: 맵의 매핑 개수 반환(int)
  - 집합뷰
    - keySet: 집합 뷰로 반환