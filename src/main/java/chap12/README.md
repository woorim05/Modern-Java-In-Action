# 날짜와 시간 API

---
### 1. LocalDate, LocalTime 클래스
- 날짜와 시간 생성 및 읽기
  - from(Temporal): 주어진 Temporal로 인스턴스 생성
  - now: 시스템 시계로 인스턴스 생성
  - of: 주어진 구성 요소로 인스턴스 생성
  - parse: 문자열을 파싱해서 생성
  - get: Temproal 읽음
- 날짜 조정, 포매팅
  - format: [지정된 포매터(DateTimeFormatter)](#5.-DateTimeFormatter)를 이용해서 문자열로 변환
  - minus
  - plus
  - with

### 2. Instant 클래스
- 기계의 날짜와 시간
- 유닉스 에포크 시간(1970년 1월 1일 0시 0분 0초 UTC)을 기준으로 특정 지점까지의 시간을 초단위로 표현
- 나노초(10억분의 1초)의 정밀도를 제공
- ofEpochSecond() 로 Instant 생성
  - 첫 번째 인수: 구하고자 하는 초
  - 두 번째 인수: 수정하고 싶은 만큼의 나노초

### 3. Duration, Period
- Duration 클래스는 초/나노초로 시간 단위를 표현하므로 LocalTime, LocalDateTime, Instant 로만 비교 할 수 있음
- LocalDate를 사용하고 싶을 때는 Period 클래스 사용
- Duration & Period 공통 메서드
  - between: 두 시간 사이의 간격을 생성
  - from: 시간 단위로 간격을 생성
  - of: 주어진 구성 요소(amount)에서 간격 인스턴스를 생성
  - parse: 문자열 파싱해서 간격 인스턴스 생성
  - addTo: 복사본 생성 후 지정된 temporal 객체에 추가
  - get: 현재 간격 정보 읽음
  - isNegative: 간격이 음수인지 확인
  - isZero: 간격이 0인지 확인
  - minus: 주어진 시간을 뺀 복사본을 생성
  - multipliedBy: 주어진 값을 곱한 복사본 생성
  - negated: 주어진 값으 부호를 반전한 복사본 생성
  - plus: 주어진 시간을 더한 복사본을 생성
  - subtractFrom: 지정된 Temporal 객체에서 간격을 뺌
  
### 4. TemporalAdjusters
- 날짜 조정 기능 클래스
  - dayOfWeekInMonth
  - firstDayOfMonth
  - firstDayOfNextMonth
  - firstDayOfNextYear
  - firstDayOfYear
  - firstInMonth
  - lastDayOfMonth
  - lastDayOfNextMonth
  - lastDayOfNextYear
  - lastDayOfYear
  - lastInMonth
  - next
  - previous
  - nextOrSame
  - previousOrSame
  
### 5. DateTimeFormatter
- 정의된 상수 사용하기
  - ***ISO_LOCAL_DATE(YYYY-MM-dd)***
  - ISO_OFFSET_DATE
  - ISO_DATE
  - ISO_LOCAL_TIME
  - ISO_OFFSET_TIME
  - ISO_TIME
  - ISO_LOCAL_DATE_TIME
  - ISO_OFFSET_DATE_TIME
  - ISO_ZONED_DATE_TIME
  - ISO_DATE_TIME
  - ISO_ORDINAL_DATE
  - ISO_WEEK_DATE
  - ISO_INSTANT
  - ***BASIC_ISO_DATE(YYYYMMdd)***
  - RFC_1123_DATE_TIME
- 메서드
  - ofPattern
- 세부적인 포매터 제어
  - new DateTimeFormatterBuilder()
  