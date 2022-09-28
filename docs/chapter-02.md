
## 2. 단위 테스트란 무엇인가

단위 테스트의 정의에 따라 접근하는 방법이 두 가지로 나뉘었다. 

* 고전파(classical school)
    * 모든 사람이 단위 테스트와 테스트 주도 개발에 원론적으로 접근하는 방식
    * 디트로이트 스타일
    * 켄트 벡의 "테스트 주도 개발"이 가장 고전적인 책
* 런던파(london school)
    * 런던의 프로그래밍 커뮤티니에서 유래
    * 목 추종자(mockist)
    * 스티브 프리먼(Steve Freeman), 냇 프라이스(Nat Pryce)가 유명

### 2.1. 단위 테스트 정의

중요하지 않은 것을 제외한 단위 테스트 정의는 다음과 같다.

* 작은 코드 조각을 검증
* 빠르게 수행
* 격리된 방식으로 처리하는 자동화된 테스트

작은 코드 조각을 검증하는 것과 빠르게 수행하는 것은 논란의 여지가 없다. 
격리 문제에 대한 해석 차이에서 고전파와 런던파로 나뉜다. 

#### 2.1.1. 격리 문제에 대한 런던파의 접근

테스트 대상 시스템을 협력자에게서 격리하는 방식이다. 
하나의 클래스가 다른 클래스 또는 여러 클래스에 의존하면 이 모든 의존성을 테스트 대역(test double)으로 대체하는 방식이다. 
테스트 대역을 사용하면 의존성이 포함된 테스트 대상 시스템을 검증하는 단위 테스트는 의존성과 무관하게 동작한다. 

다음과 같은 장점이 있다.

* 테스트가 실패하면 코드 베이스의 어느 부분이 고장 났는지 확실히 알 수 있다. 
* 테스트 대역은 에러가 발생하지 않으니 분명 테스트 대상 시스텀이 고장이 난 것이다. 
* 객체 그래프(같은 문제를 해결하는 클래스 통신망)를 분할할 수 있다. 

의존성을 가진 코드 베이스를 테스트하는 것은 테스트 대역 없이는 어렵다. 
객체 그래프를 다시 만드는 것이 유일한 방법이지만, 클래스가 너무 많은 경우 어려운 작업이 된다. 
테스트 대역을 사용하면 객체 그래프를 다시 만들지 않아도 된다. 

단위 테스트를 격리하면 프로젝트 전반적으로 한 번에 한 클래스만 테스트하라는 지침을 도입할 수 있다. 
테스트 스위트가 간단한 구조로 정해진다. 

##### 고전 스타일 테스트 코드 예시

* 의존성과 테스트 대상 시스템을 준비
    * 테스트 대상 시스템(SUT, System Under Test) - Customer 클래스
    * 협력자 - Store 클래스
* `purchase` 메소드 호출이 검증하고자 하는 동작을 수행
* 검증(assert) 구문들이 검증 단계
* 동작 결과가 예상 결과로 이어지는지 확인

```java
package org.example;

import org.example.domain.Customer;
import org.example.domain.Product;
import org.example.domain.Store;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClassicTests {

    @Test
    void purchase_succeeds_when_enough_inventory() {

        Store store = new Store();
        store.addInventory(Product.Shampoo, 10);
        Customer customer = new Customer();


        boolean success = customer.purchase(store, Product.Shampoo, 5);


        assertTrue(success);
        assertEquals(5, store.getInventory(Product.Shampoo));
    }

    @Test
    void purchase_fails_when_not_enough_inventory() {

        Store store = new Store();
        store.addInventory(Product.Shampoo, 10);
        Customer customer = new Customer();


        boolean success = customer.purchase(store, Product.Shampoo, 15);


        assertFalse(success);
        assertEquals(10, store.getInventory(Product.Shampoo));
    }
}
```

##### 고전 스타일 예시 코드 고찰

고전 스타일 테스트는 협력자를 대체하지 않고 운영용 인스턴스를 사용한다. 
고전적인 방식의 자연스러운 결과로 `Customer`, `Store` 클래스 모두를 검증한다. 
`Customer` 객체가 올바르게 동작하더라도 `Store` 내부에 버그가 있으면 단위 테스트가 실패한다. 
두 클래스는 서로 격리되어 있지 않다. 

##### 런던 스타일 테스트 코드 예시

* 실제 인스턴스를 사용하지 않고, `Mockito`를 사용한 스텁 객체 생성
* 인터페이스를 선언하여 사용
* 스텁 객체가 반환하는 값을 미리 지정
* 객체의 상태를 검증하지 않고, 객체의 동작을 검증
    * 호출 메소드뿐만 아니라 호출 횟수까지 검증

```java
package org.example;

import org.example.domain.Customer;
import org.example.domain.IStore;
import org.example.domain.Product;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class LondonTests {

    @Test
    void purchase_succeeds_when_enough_inventory() {

        IStore store = Mockito.mock(IStore.class);
        when(store.hasEnoughInventory(Product.Shampoo, 5)).thenReturn(true);


        Customer customer = new Customer();
        boolean success = customer.purchase(store, Product.Shampoo, 5);


        assertTrue(success);
        verify(store, times(1)).removeInventory(Product.Shampoo, 5);
    }

    @Test
    void purchase_fails_when_not_enough_inventory() {

        IStore store = Mockito.mock(IStore.class);
        when(store.hasEnoughInventory(Product.Shampoo, 15)).thenReturn(false);


        Customer customer = new Customer();
        boolean success = customer.purchase(store, Product.Shampoo, 15);


        assertFalse(success);
        verify(store, times(0)).removeInventory(Product.Shampoo, 5);
    }
}
```

##### 런던 스타일 예시 코드 고찰

런던 스타일은 협업 객체가 내부에서 동작하는 모습을 실제로 알아야 한다. 
즉, 테스트 대상 클래스의 실제 구현체 모습을 알고 있어야 한다. 
협력 객체를 인터페이스로 정의하면 테스트 대상 객체 내부에서 어떤 기능을 노출하지 아웃 라인이 보인다.

#### 2.1.2. 격리 문제에 대한 고전파의 접근

런던 스타일은 테스트 대역으로 테스트 대상 코드 조각을 분리해서 격리 요구 사항에 다가간다. 
