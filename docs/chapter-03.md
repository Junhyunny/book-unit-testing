
## 3. 단위 테스트 구조

이번 장에서는 준비(arrange), 실행(act), 검증(assert) 패턴으로 작성된 단위 테스트 구조를 살펴본다. 
단위 테스트 명명법을 소개한다. 
단위 테스트 프로세스를 간소화하는 데 도움이 되는 프레임워크의 몇 가지 기능을 이야기해본다. 

### 3.1. 단위 테스트를 구성하는 방법

준비, 실행, 검증 패턴을 사용해 단위 테스트를 구성하는 방법, 피해야 할 함정 그리고 테스트를 가능한 읽기 쉽게 만드는 방법 등을 알아본다. 

#### 3.1.1. AAA 패턴 사용

AAA 패턴은 테스트를 준비, 실행, 검증이라는 세 부분으로 나눈다. 

* 테스트가 단순하고 균일한 구조를 갖는 데 도움이 된다.
* 일관성으로 인해 모든 테스트를 쉽게 읽을 수 있고 이해할 수 있다.
* 전체 테스트 스위트 보수 비용이 줄어든다.

```java
package org.example.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTests {

    @Test
    void sum_of_two_numbers() {
        // 준비
        double first = 10;
        double second = 20;
        Calculator calculator = new Calculator();

        // 실행
        double result = calculator.sum(first, second);

        // 검증
        assertEquals(30, result);
    }
}
```

AAA 패턴의 구조는 다음과 같다.

* 준비 구절에서 테스트 대상 시스템(SUT, System Under Test)과 해당 의존성을 원하는 상태로 만든다.
* 실행 구절에선 `SUT`에서 메소드를 호출하고 준비된 의존성을 전달하며 출력 값을 캡처한다.
* 검증 구절에선 결과를 검증한다. 
    * 결과는 반환 값이나 `SUT`와 협력자의 최종 상태, `SUT`가 협력자에 호출한 메소드 등으로 표시될 수 있다.

Given-When-Then 패턴은 구조상 AAA 패턴과 동일하다. 
차이점은 개발자가 아닌 사람에게는 Given-When-Then 구조가 더 읽기 쉽다. 
비기술자들과 공유하는 테스트에 더 적합하다.

테스트를 작성할 때 준비 구절부터 시작하는 것이 자연스럽다. 
대부분의 경우에 효과적이지만, 검증 구절로 시작하는 것도 가능한 옵션이다. 
테스트 주도 개발(TDD, Test Driven Development)을 실천할 땐 다음과 같이 개발한다. 

* 먼저 기대하는 동작으로 윤곽을 잡는다.
* 이런 기대에 부응하기 위한 시스템을 개발한다. 

직관적이지는 않지만, 문제를 해결하는 방식이다. 
특정 동작이 무엇을 해야 하는지에 대한 목표를 생각하면서 시작한다. 
그 다음이 실제 문제 해결이다. 

#### 3.1.2. 여러 개의 준비, 실행, 검증 피하기

준비, 실행 또는 검증 구절이 여러 개 있는 테스트를 만날 수 있다. 

1. 테스트 준비
1. 실행
1. 검증
1. 좀 더 실행
1. 다시 검증

여러 개의 준비, 실행, 검증 구절은 테스트가 너무 많은 것을 한 번에 검증한다는 의미이다. 
이러한 테스트는 여러 테스트로 나눠서 해결한다. 
이는 여러 개의 동작 단위를 검증하는 테스트를 뜻한다. 
이러한 테스트는 더 이상 단위 테스트가 아니라 통합 테스트이다. 
이런 테스트 구조는 피하는 것이 좋다. 
실행이 하나면 테스트가 단위 테스트 범주에 있게끔 보장하고, 간단하고, 빠르며 이해하기 쉽다. 
일련의 실행과 검증이 포함된 테스트를 보면 리팩토링해라. 
각 동작을 고유의 테스트로 도출해라. 

통합 테스트는 실행 구절을 여러 개 두는 것이 괜찮을 때도 있다. 
시스템 상태의 흐름이 자연스럽다면, 실행이 동시에 후속 실행을 위한 준비로 제공될 때 유용하다. 

#### 3.1.3. 테스트 내 if 문 피하기

단위 테스트, 통합 테스트 모두 분기가 없는 간단한 일련의 단계여야 한다. 
if 문은 테스트가 한 번에 너무 많은 것을 검증한다는 표시이다. 
이런 테스트는 여러 테스트로 나눠야 한다. 
테스트에 분기가 있어서 얻는 이점은 없고, 추가 유지비용만 붙는다. 

#### 3.1.4. 각 구절은 얼마나 커야 하는가?

일반적으로 준비 구절이 가장 크다. 
실행과 검증을 합친 만큼 클 수도 있다. 
그러나 이보다 훨씬 더 크면, 같은 테스트 클래스 내 비공개 메소드 또는 별도의 팩토리 클래스로 도출하는 것이 좋다. 
준비 구절에서 코드 재사용에 도움이 되는 두 가지 패턴이 있다.

* 오브젝트 마더(object mother)
* 테스트 데이터 빌더(test data builder)

실행 구절은 보통 한 줄이다. 
두 줄 이상인 경우 SUT 공개 API에 문제가 있음을 의심해본다. 

아래 테스트는 적당히 잘 설계된 클래스 API 예제이다. 

```java
package org.example.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerTests {

    @Test
    void purchase_succeeds_when_enough_inventory() {

        Store store = new Store();
        store.addInventory(Product.SHAMPOO, 10);
        Customer customer = new Customer();


        boolean success = customer.purchase(store, Product.SHAMPOO, 5);


        assertEquals(success, true);
        assertEquals(5, store.getInventory(Product.SHAMPOO));
    }
}
```

아래 테스트는 검증 구문을 2개 작성하면서 문제가 발생한다. 
구매를 마치려면 두 번째 메소드를 호출해야 하므로, 캡슐화가 깨진다. 
Customer 클래스의 API에 문제가 있다. 

* 첫 번째 줄에서는 고객이 상점에서 샴푸 다섯 개를 얻는다.
* 두 번째 줄에서는 재고가 감소되는데, purchase 호출이 성공을 반환하는 경우에만 수행된다. 

```java
package org.example.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerTests {

    @Test
    void purchase_succeeds_when_enough_inventory_with_two_times_assert() {

        Store store = new Store();
        store.addInventory(Product.SHAMPOO, 10);
        Customer customer = new Customer();


        boolean success = customer.purchaseWithoutRemoveInventory(store, Product.SHAMPOO, 5);
        store.removeInventory(success, Product.SHAMPOO, 5);


        assertEquals(success, true);
        assertEquals(5, store.getInventory(Product.SHAMPOO));
    }
}
```

비즈니스 관점에서 구매가 정상적으로 이뤄지면 고객의 제품 획득과 매장 재고 감소 두 결과가 같이 만들어져야 한다. 
이는 단일한 공개 메소드가 있어야 한다는 뜻이다. 
잘못된 예시처럼 코드가 나눠진 경우 두 번째 메소드를 호출하지 않았을 때 결과의 모순이 생긴다. 
이런 모순을 불변 위반(invariant violation)이라고 하며, 잠재적 모순으로부터 코드를 보호하는 행위를 캡슐화(encapsulation)라고 한다. 

데이터베이스에 모순이 생기는 것은 큰 문제다. 
단순히 어플리케이션 재시작으로 상태를 되돌릴 수 없다. 
데이터베이스의 손상된 데이터를 처리하고, 오프라인으로 상황을 처리해야 할 수도 있다. 

실행 구절을 한 줄로 하는 지침은 비즈니스 로직을 포함하는 대부분의 코드에서 적용된다. 
하지만, 유틸리티나 인프라 코드는 덜 적용된다. 
절대는 없다. 

#### 3.1.5. 검증 구절에는 검증문이 얼마나 있어야 하는가

테스트당 하나의 검증을 갖는 지침은 전제가 올바르지 않다. 
단위 테스트는 동작의 단위이지 코드의 단위가 아니다. 
단일 동작은 여러 결과를 낼 수 있으며, 하나의 테스트로 모든 결과를 평가하는 것이 좋다. 

검증 구절이 너무 커지는 것은 경계해야 한다. 
제품 코드에서 추상화가 누락됐을 수 있다. 
SUT에서 반환된 객체 내에서 모든 속성을 검증하는 대신 객체 클래스 내에 적절한 동등 멤버(equality member)를 정의하는 것이 좋다. 
그러면 단일 검증문으로 객체를 기대값과 비교할 수 있다. 

#### 3.1.6. 종료 단계는 어떤가

네 번째 구절로 종료 구절을 따로 구분하기도 한다. 
예를 들면 테스트에 의해 작성된 파일을 지우거나 데이터베이스 연결을 종료하는 구절이다. 
일반적으로 별도 메소드로 도출하여 클래스 내 모든 테스트에 재사용된다. 

대부분의 단위 테스트는 종료 구절이 필요 없다. 
단위 테스트는 프로세스 외부에 종속적이지 않으므로 처리해야 할 부작용을 남기지 않는다. 
종료는 통합 테스트 영역이다. 

#### 3.1.7. 테스트 대상 시스템 구별하기

SUT는 테스트에서 호출하고자 하는 동작에 대한 진입점을 제공한다. 
동작은 여러 클래스에 걸쳐 있을 만큼 클 수도 있고 단일 메소드로 작을 수 있다. 
진입점은 오직 하나만 존재할 수 있다. 

SUT는 의존성과 구분하는 것이 좋다. 
테스트 대상을 찾는 데 시간을 너무 많이 들일 필요가 없다. 
테스트 내 SUT 이름을 `sut`로 지정해라.  

#### 3.1.8. 준비, 실행, 검증 주석 제거하기

의존성에서 SUT를 떼어내는 것이 중요하듯이, 테스트 내에서 특정 부분이 어떤 구절에 속해 있는지 파악하는 데 시간을 많이 들이지 않는 것이 좋다. 
각 구절을 주석 대신 빈 줄로 구분해라. 
간결성과 가독성 좋아진다. 
대규모 테스트는 복잡한 설정들을 포함하는 경우가 많으므로 적합하지 않을 수 있다. 
설정 단계는 준비 단계 이전에 빈 줄을 추가하여 구분할 수 있다. 

### 3.2. xUnit 테스트 프레임워크 살펴보기

`xUnit` 프레임워크는 `.NET`에서 사용할 수 있는 단위 테스트 도구이다. 
이 외에도 몇 가지 대안이 있다. 

* NUnit
* Microsoft MSTest

### 3.3. 테스트 간 테스트 픽스처 재사용

테스트에서 언제 어떻게 코드를 재사용하는지 아는 것이 중요하다. 
준비 구절에서 코드를 재사용하는 것이 테스트를 줄이면서 단순화하기 좋은 방법이다. 

우선 테스트 픽스처란 무엇인지 알아본다. 

* 테스트 실행 대상 객체이다. 
* 정규 의존성, 즉 SUT로 전달되는 인수다. 
* 데이터베이스에 있는 데이터나 하드 디스크의 파일일 수도 있다. 
* 이런 객체는 각 테스트 실행 전에 알려진 고정 상태로 유지하기 때문에 동일한 결과를 생성한다. 

테스트 픽스처를 준비하기 위해 코드를 많이 작성하게 된다. 
이런 준비는 별도의 메소드나 클래스로 도출한 후 테스트 간에 재사용하는 것이 좋다. 
테스트 픽스처를 재사용 방법은 두 가지가 있다. 

* 첫 번째로 테스트 생성자에서 픽스처를 초기화하는 방법이다.
* 이는 옳지 않은 방법으로 테스트 유지비를 증가시킨다. 
* 테스트 코드의 양을 크게 줄일 수 있지만 두 가지 단점이 있다.
    * 테스트 간 결합도가 높아진다.
    * 테스트 가독성이 떨어진다.

```java
package org.example.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerTests {

    Store store;
    Customer customer;

    @BeforeEach
    void setUp() {
        store = new Store();
        store.addInventory(Product.SHAMPOO, 10);
        customer = new Customer();
    }

    @Test
    void purchase_succeeds_when_enough_inventory() {

        boolean success = customer.purchase(store, Product.SHAMPOO, 5);


        assertEquals(success, true);
        assertEquals(5, store.getInventory(Product.SHAMPOO));
    }
}
```

#### 3.3.1. 테스트 간의 높은 결합도는 안티 패턴이다

모든 테스트가 서로 결합되어 있다. 
테스트의 준비 로직을 수정하면 클래스의 모든 테스트에 영향을 미친다. 
예를 들어 인벤토리 초기화를 10이 아니라 15로 하는 경우 모든 테스트가 깨진다. 

이는 중요한 지침을 위반한다. 

> 테스트를 수정해도 다른 테스트에 영향을 주어서는 안된다. 

#### 3.3.2. 테스트 가독성을 떨어뜨리는 생성자 사용

테스트만 보고 더 이상 전체 그림을 볼 수 없다. 
테스트 메소드가 무엇을 하는지 이해하려면 클래스의 다른 부분도 봐야 한다. 
준비 로직이 별로 없더라도 테스트 메소드로 바로 옮기는 것이 좋다. 

#### 3.3.3. 더 나은 테스트 픽스처 재사용법

두 번째 방법은 테스트 클래스에 비공개 팩토리 메소드를 두는 방법이다. 

```java
package org.example.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerTests {

    private Store createStoreWithInventory(Product product, int count) {
        Store store = new Store();
        store.addInventory(product, count);
        return store;
    }

    private Customer createCustomer() {
        return new Customer();
    }

    @Test
    void purchase_succeeds_when_enough_inventory() {

        Store store = createStoreWithInventory(Product.SHAMPOO, 10);
        Customer sut = createCustomer();

        
        boolean success = sut.purchase(store, Product.SHAMPOO, 5);


        assertEquals(success, true);
        assertEquals(5, store.getInventory(Product.SHAMPOO));
    }

    @Test
    void purchase_fails_when_not_enough_inventory() {

        Store store = createStoreWithInventory(Product.SHAMPOO, 10);
        Customer sut = createCustomer();


        boolean success = sut.purchase(store, Product.SHAMPOO, 15);


        assertEquals(success, false);
        assertEquals(10, store.getInventory(Product.SHAMPOO));
    }
}
```

공통 초기화 코드를 비공개 팩토리 메소드로 추출하면 테스트 코드를 짧게하면서, 동시에 테스트 진행 상황에 대한 전체 맥락을 유지할 수 있다. 
비공개 메소드를 충분히 일반화하는 한 테스트는 서로 결합되지 않는다. 
테스트에서 픽스처를 어떻게 생성할지 지정할 수 있다. 

테스트 픽스처 재사용 규칙에 한 가지 예외가 있다. 
모든 테스트에, 또는 거의 대부분의 테스트에 사용되는 경우 생성자에 픽스처를 인스턴스화할 수도 있다. 
데이터베이스와 작동하는 통합 테스트가 종종 여기에 해당한다. 
모든 테스트가 데이터베이스 연결이 필요한 경우 한 번 초기화하여 어디에서나 재사용한다. 

### 3.4. 단위 테스트 명명법

테스트에 표현력이 있는 이름을 붙이는 것은 중요하다. 
올바른 명칭은 테스트가 검증하는 내용과 기본 시스템의 동작을 이해하는 데 도움을 준다. 

가장 유명하지만 도움이 되지 않는 방법 중 하나가 다음과 같은 관습이다. 

* [테스트 대상 메소드]_[시나리오]_[예상결과]
* 테스트 대상 메소드 - 테스트 중인 메소드의 이름
* 시나리오 - 메소드를 테스트하는 조건
* 예상 결과 - 현재 시나리오에서 테스트 대상 메소드에 기대하는 것

간단하고 쉬운 영어 구분이 더 효과적이며, 엄격한 명명 구조에 얽매이지 않고 표현력이 뛰어나다. 

#### 3.4.1. 단위 테스트 명명 지침

표현력 있고 읽기 쉬운 테스트 이름을 짓기 위해 다음과 같은 지침을 따른다. 

* 엄격한 명명 정책을 따르지 않는다. 
    * 복잡한 동작에 대한 높은 수준의 설명을 설명하기 어렵다.
    * 표현의 자유를 허용한다.
* 문제 도메인에 익숙한 비개발자들에게 시나리오를 설명하는 것처럼 테스트 이름을 짓는다.
    * 도메인 전문가나 비즈니스 분석가가 좋은 예시이다.
* 단어를 밑줄(_) 표시로 구분한다.
    * 긴 이름에서 가독성을 향상시키는데 도움을 준다.

테스트 클래스 이름을 지을 때 [클래스명]Tests 패턴을 사용하지만, 테스트가 해당 클래스만 검증하는 것으로 제한하는 것은 아니다. 
단위 테스트의 단위는 동작이지 클래스가 아니다. 
단위는 하나 이상의 클래스에 걸쳐 있을 수 있으며, 실제 테스트 크기에 영향을 주지 않는다. 
클래스명을 시작 지점을 결정하기 위한 동작의 진입점 또는 API로 생각하자.

#### 3.4.2. 예제: 지침에 따른 테스트 이름 변경

DeliveryService가 잘못된 날짜의 배송을 올바르게 식별하는지 검증한다. 

* isDeliveryValid_invalidDate_returnsFalse
    * 가독성에 도움이 되지 않는 엄격한 명명 정책이다.
* delivery_with_invalid_date_should_be_considered_invalid
    * 개발자가 아닌 사람들에게 납득된다.
    * 개발자들도 더 쉽게 이해할 수 있다.
    * SUT 메소드 이름이 더 이상 테스트 이름에 포함되지 않는다.
* delivery_with_past_date_should_be_considered_invalid
    * 유효하지 않은 날짜보다 과거 날짜라는 표현이 명확하므로 변경한다.
* delivery_with_past_date_should_be_invalid
    * 너무 장황하고, `considered`라는 단어를 제거해도 의미가 퇴색되지 않는다.
* delivery_with_past_date_is_invalid
    * `should be`는 일반적인 안티 패턴이다.
    * 테스트는 동작 단위에 대해 단순하고 원자적이다.
    * 사실을 서술할 때 소망이나 욕구가 들어가지 않는다.
* delivery_with_a_past_date_is_invalid
    * 관사를 붙여 문법적으로 더 완벽을 가한다.

```java
package org.example.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeliveryServiceTests {

    @Test
    void isDeliveryValid_invalidDate_returnsFalse() {
        DeliveryService sut = new DeliveryService();
        LocalDate localDate = LocalDate.now().minusDays(1);
        Delivery delivery = new Delivery(localDate);


        boolean isValid = sut.isDeliveryValid(delivery);


        assertEquals(isValid, false);
    }

    @Test
    void delivery_with_invalid_date_should_be_considered_invalid() {
        DeliveryService sut = new DeliveryService();
        LocalDate localDate = LocalDate.now().minusDays(1);
        Delivery delivery = new Delivery(localDate);


        boolean isValid = sut.isDeliveryValid(delivery);


        assertEquals(isValid, false);
    }

    @Test
    void delivery_with_past_date_should_be_considered_invalid() {
        DeliveryService sut = new DeliveryService();
        LocalDate localDate = LocalDate.now().minusDays(1);
        Delivery delivery = new Delivery(localDate);


        boolean isValid = sut.isDeliveryValid(delivery);


        assertEquals(isValid, false);
    }

    @Test
    void delivery_with_past_date_should_be_invalid() {
        DeliveryService sut = new DeliveryService();
        LocalDate localDate = LocalDate.now().minusDays(1);
        Delivery delivery = new Delivery(localDate);


        boolean isValid = sut.isDeliveryValid(delivery);


        assertEquals(isValid, false);
    }

    @Test
    void delivery_with_past_date_is_invalid() {
        DeliveryService sut = new DeliveryService();
        LocalDate localDate = LocalDate.now().minusDays(1);
        Delivery delivery = new Delivery(localDate);


        boolean isValid = sut.isDeliveryValid(delivery);


        assertEquals(isValid, false);
    }


    @Test
    void delivery_with_a_past_date_is_invalid() {
        DeliveryService sut = new DeliveryService();
        LocalDate localDate = LocalDate.now().minusDays(1);
        Delivery delivery = new Delivery(localDate);


        boolean isValid = sut.isDeliveryValid(delivery);


        assertEquals(isValid, false);
    }
}
```

##### 테스트명 내 테스트 대상 메소드

* 테스트 이름에 SUT의 메소드 이름을 포함하지 마라.
* 코드를 테스트하는 것이 아니라 어플리케이션의 동작을 테스트하는 것이다.
* 테스트 대상 이름은 중요하지 않다.
* SUT는 단지 진입점, 동작을 호출하는 수단이다.
* 테스트 대상 메소드 이름은 언제든지 바뀔 수 있다.
* 동작 대신 코드를 목표로 하면 해당 코드의 구현 세부 사항과 테스트 간의 결합도가 높아진다.
* 유일한 예외는 유틸리티 코드를 작업하는 것이다.
* 유틸리티 코드는 비즈니스 로직이 없고, 코드의 동작이 단순한 보조 기능에서 크게 벗어나지 않으므로 비즈니스 담당자에게는 아무런 의미가 없다.

### 3.5. 매개변수화된 테스트 리팩토링하기

보통 테스트 하나로 동작 단위를 완전하게 설명하기 충분하지 않다. 
동작 단위는 일반적으로 여러 구성 요소를 포함하며, 각 구성 요소는 자체 테스트로 캡처해야 한다. 
동작에 대한 검증을 충분히 하려면 더 많은 사실 확인이 필요하다. 
그렇기 때문에 동작이 복잡한 경우 이를 테스트하기 위한 숫자가 급격하게 증가하고 관리가 어려워진다. 
대부분의 테스트 프레임워크는 이런 문제를 해결하기 위해 매개변수화된 테스트 기능을 제공한다. 

간단한 예시로 가장 빠른 배송일이 오늘로부터 이틀 후가 되도록 작동하는 배송 기능에 대한 테스트를 해보자. 

* 배송일이 오늘인 경우 유효하지 않다.
* 배송일이 내일인 경우 유효하지 않다.
* 배송일이 2일 뒤인 경우부터 유효하다.

적어도 3개의 테스트 코드가 필요하지만, 매개변수화된 테스트를 통해 이를 하나의 테스트로 처리한다. 
매개변수화된 테스트를 사용하면 테스트 코드 양을 줄일 수 있지만, 비용이 발생한다.

* 테스트 메소드가 나타내는 사실을 파악하기 힘들어진다.
* 매개변수가 많을수록 어렵다.
* 절충안으로 긍정적인 테스트 케이스는 고유 테스트로 도출한다.

```java
package org.example.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeliveryServiceTests {

    // ...

    private static Stream<Arguments> provideParameters() {
        return Stream.of(
                Arguments.of(-1, false),
                Arguments.of(0, false),
                Arguments.of(1, false),
                Arguments.of(2, true),
                Arguments.of(3, true)
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1})
    void detect_an_invalid_delivery_date(int daysFromNow) {
        DeliveryService sut = new DeliveryService();
        LocalDate localDate = LocalDate.now().plusDays(daysFromNow);
        Delivery delivery = new Delivery(localDate);


        boolean isValid = sut.isDeliveryValid(delivery);


        assertEquals(isValid, false);
    }

    @Test
    void the_soonest_delivery_date_is_two_days_from_now() {
        DeliveryService sut = new DeliveryService();
        LocalDate localDate = LocalDate.now().plusDays(2);
        Delivery delivery = new Delivery(localDate);


        boolean isValid = sut.isDeliveryValid(delivery);


        assertEquals(isValid, true);
    }
}
```

매개변수만으로 테스트 케이스를 판단할 수 있다면 긍정적인 테스트 케이스와 부정적인 테스트 케이스 모두 하나의 메소드로 두는 것이 좋다. 
그렇지 않으면 긍정적인 테스트 케이스를 도출해라. 
동작이 너무 복잡하면 매개변수화된 테스트를 조금도 사용하지 마라. 
긍정적인 테스트 케이스와 부정적인 테스트 케이스 모두 각각 고유의 테스트 메소드로 표현해라.
