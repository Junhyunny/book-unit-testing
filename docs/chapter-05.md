
## 5. 목과 테스트 취약성

### 5.1. 목과 스텁 구분

목(mock)은 테스트 대상 시스템(SUT)과 협력자 사이의 상호 작용을 검사할 수 있는 테스트 대역이다. 
테스트 대역에는 스텁(stub)이라는 또 다른 유형이 있다. 

#### 5.1.1. 테스트 대역 유형

테스트 대역은 모든 유형의 비운용영 가짜 의존성을 설명하는 포괄적인 용어이다. 
스턴트 대역(stunt double)이라는 개념에서 유래됬다. 
테스트 대역의 주 용도는 테스트를 편리하게 만드는 것이다. 
테스트 대상 시스템으로 실제 의존성 대신 전달된다. 

테스트 대역은 다음과 같은 종류를 가진다.

* 더미(dummy)
* 스텁(stub)
* 스파이(spy)
* 목(mock)
* 페이크(fake)

실제로는 목과 스텁은 크게 두 가지 유형으로 나눌 수 있다. 

* 목
    * 목, 스파이를 의미한다. 
    * 외부로 나가는 상호 작용을 모방하고 검사하는데 도움이 된다.
    * 상호 작용은 SUT가 상태를 변경하기 위해 의존성을 호출하는데 해당된다.
* 스텁
    * 스텁, 더미, 페이크를 의미합니다. 
    * 내부로 들어오는 상호 작용을 모방하는데 도움이 된다. 

두 유형의 차이점은 다음과 같다. 

* 목은 외부로 나가는 상호 작용을 모방하고 검사한다.
    * 이러한 상호 작용은 SUT 상태를 변경하기 위한 의존성을 호출하는 것에 해당한다.
* 스텁은 내부로 들어오는 상호 작용을 모방하는데 도움이 된다. 
    * 이러한 상호 작용은 SUT가 입력 데이터를 얻기 위한 의존성을 호출하는 것에 해당한다.

##### 목과 스텁 사용 예시

* SMTP 서버에 부작용을 초래하는 상호 작용은 외부로 나가는 상호 작용이다.
    * 목은 이러한 상호 작용을 모방하는 테스트 대역 역할을 수행한다.
* 데이터베이스에서 데이터를 검색하는 것은 내부로 들어오는 상호 작용이다.
    * 부작용을 일으키지 않으며 해당 테스트 대역은 스텁이다.

<p align="center">
    <kbd>
        <img src="/images/chapter-05-01.jpg">
    </kbd>
</p>

스파이는 수동으로 작성하는 반면, 목은 목 프레임워크의 도움을 받아 생성한다. 
스파이를 직접 작성한 목(hardwritten mock)이라고 부르기도 한다. 

스텁, 더미, 페이크의 차이는 얼마나 똑똑한 테스트 대역인지에 있다. 

* 더미 
    * NULL 값이나 가짜 문자열 같은 단순하고 하드 코딩된 값이다.
    * SUT 메소드의 시그니처를 만족시키기 위해 사용한다.
    * 최종 결과를 만드는 데 영향을 주지 않는다.
* 스텁
    * 더 정교하고, 시나리오마다 다른 값을 반환하게끔 구성할 수 있도록 필요한 것을 다 갖춘 완전한 의존성이다.
* 페이크
    * 대다수의 목적에 부합하는 스텁이다.
    * 스텁과 차이점은 아직 존재하지 않는 의존성을 대체하고자 구현한다.

#### 5.1.3. 스텁으로 상호 작용을 검증하지 말라

목은 SUT에서 나가는 상호 작용을 모방하고 검사한다. 
스텁은 내부로 들어오는 상호 작용만 모방하고 검사하지 않는다. 
이 두 가지 차이점은 스텁과 상호 작용을 검증하지 말라는 지침에서 나온다. 
SUT에서 스텁으로의 호출은 SUT가 생성하는 최종 결과가 아니다. 
이런 호출은 최종 결과를 산출하기 위한 수단이다. 
즉, 스텁은 SUT가 출력을 생성하도록 입력을 제공하는 것 뿐이다. 

테스트에서 거짓 양성을 피하고 리팩토링 내성을 향상시키는 방법은 구현 세부 사항이 아니라 최종 결과를 검증하는 것이다. 
그래야지 실제 결과에 부합하며, 해당 결과는 도메인 전문가에게 의미가 있다. 

다음은 깨지기 쉬운 테스트이다.

* 최종 결과가 아닌 사항을 검증하는 관행을 과잉 명세(overspecification)이라고 부른다. 
* 과잉 명세는 상호 작용을 검사할 때 가장 흔하게 발생한다.

```java
package com.example.book;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ApplicationTests {

    @Test
    void creating_a_report() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        when(userRepository.getNumberOfUsers()).thenReturn(10);


        ReportService sut = new DefaultReportService(userRepository);
        Report report = sut.createReport();


        assertThat(report.getNumberOfUser()).isEqualTo(10);
        // 스텁으로 상호 작용을 검증한 코드
        verify(userRepository, times(1)).getNumberOfUsers(); 
    }
}
```

#### 5.1.4. 목과 스텁 함께 쓰기


