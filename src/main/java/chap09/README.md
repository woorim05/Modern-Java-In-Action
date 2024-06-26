# 리팩터링, 테스팅, 디버깅

---

### <리팩터링>
각 디자인 패턴에서 람다를 활용하여 재구현
- 전략: 한 유형의 알고리즘(인터페이스)을 보유한 상태에서 런타임에 적절한 알고리즘(재정의)을 선택하는 기법
  - 다양한 기준을 갖는 입력값을 검증, 다양한 파싱 방법 사용, 입력 형식 설정 등에 활용
  

- 템플릿 메서드: 알고리즘의 개요를 제시(추상 클래스/메서드 생성)한 다음에 알고리즘의 일부를 고칠 수 있는 유연함(재정의)을 제공해야할 때 사용 
  - 어떤 알고리즘을 사용하고 싶은데 그대로는 안되고 조금 고쳐야 하는 상황에 적합

  
- 옵저버: 어떤 이벤트가 발생했을 때 한 객체(Subject)가 다른 객체 리스트에 자동으로 알림(notify)을 보내야 하는 상황에서 사용
  - 버튼 같은 GUI 컴포넌트에 사용
  - 옵저버가 상태를 가지며, 여러 메서드를 정의하는 등 복잡하다면 랍다표현식보다 기존의 클래스 구현방식을 고수하는것이 바람직


- 의무체인: 어떤 작업을 처리한 다음에 다른 객체로 결과를 전달하고 다른 객체도 작업을 처리한 다음에 또 다른 객체로 전달하는 방식 


- 팩토리: 인스턴스화 로직을 클라이언트에 노출하지 않고 객체를 만들 때 사용

### <람다 표현식 테스트 & 디버깅>
- 테스트
  - 람다를 필드에 저장해서 재사용 및 테스트
  - 예상 결과와 비교(Comparator 사용)
  - equals 재정의 등
- 디버깅
  - peek 메서드로 중간값 확인 가능
