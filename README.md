
<!-- ABOUT THE PROJECT -->
## About The Project

###< 2019 카카오 페이 경력 사전과제 >

URL 을 입력받아 짧게 줄여주고, Shortening 된 URL 을 입력하면 원래 URL 로 리다이렉트하는 URL Shortening Service
 예) https://en.wikipedia.org/wiki/URL_shortening => http://localhost/JZfOQNro

### requirement
 * webapp으로 개발하고 URL 입력폼 제공 및 결과 출력
 * URL Shortening Key 는 8 Character 이내로 생성되어야 합니다.
 * 동일한 URL 에 대한 요청은 동일한 Shortening Key 로 응답해야 합니다.
 * Shortening 된 URL 을 요청받으면 원래 URL 로 리다이렉트 합니다.
 * Shortening Key 생성 알고리즘은 직접 구현해야 합니다. (라이브러리 사용 불가) * Unit Test 코드 작성
 * Database 사용은 필수 아님 (선택)
 
<!-- GETTING STARTED -->
## Getting Started

### Prerequisites

* jdk 1.8.X
* Spring Boot 2.1.x
* Gradle 
* H2 database
* JPA, Hibernate
* Thymeleaf
* Lombok

### Run in application

```sh
# Clone Repository
git clone https://github.com/kdgiant174/kakaopay.git

# Using terminal
./gradlew bootRun

# Using IntelliJ
1. Open project using IntelliJ
2. Rebuild proejct using gradle.
3. Run Application
```


## Implementation
* 단축 URL 생성 로직
    * 요청된 URL을 H2 database 에 select 질의한다.
    * 있다면 DB 상 ID 가 반환되고 없다면 새롭게 저장하여 auto increment 된 ID 값이 반환된다.
    * 유일한 값인 ID를 custom BASE62로 인코딩하여 단축 URL 생성. ID가 유일하므로 단축 URL도 유일함.
* 단축 URL 리다이렉션 로직
    * 요청된 단축 URL은 custom BASE62로 디코딩되어 ID 값으로 변경되고 H2 데이터베이스를 조회한다.
    * 조회된 URL을 Redirect 객체에 담아 반환한다.
* Custom BASE62
    * BASE62는 [0-9A-Za-z] 를 이용하여 인코딩하므로 +와 =이 포함된 BASE64보다 url safe 함.
    * 단축 URL의 길이는 5~8 범위로 제한함. 표현범위는 (62^8 - 62^5 = 218,340,090,808,561)
    * 사용자가 유추할 수 없도록 encoding 순서를 랜덤 배치 "6Y8E19bHPwqIifk2cCBDVtlRhjOuxzTgnMXoQA743FaSJymdeZW5Kp0sNGLvrU"
* Exception
    * Custom Rest Exception Case 를 정의하여 Exception을 처리함.
    * Java Runtime Error 도 Exception Handler 를 통해 일정한 형태로 response.

## API list
end point           | Http method | prarmeter             | response                     | description     |
|------------------ |------------ |-----------------------|------------------------------|-----------------|
localhost/          | GET         | 시작페이지 호출           | view = index.html            | 시작페이지 호출     |
localhost/          | POST        | param : {url : $url},  | view = index.html, value = {result : $shortUrl}            | 단축 URL 생성     |
localhost/{shortUrl}| GET         | path : {shortUrl: $shortUrl}     | redirect                     | 단축 URL 리다이렉션 |                                

## Response Error Message
Error Message                | HTTP Status Code          | Detailed Infomation                                  |
|------------------------------|---------------------------|------------------------------------------------|
NOT_VALID_SHORTEN_URL          | 400 Bad Request           | 인코딩에 맞지 않는 단축 URL 연결 요청
URL_IS_NOT_VALID           | 400 Bad Request           | URL 이 아닌 문자열에 대한 단축 URL 생성 요청        |
URL_NOT_EXIST          | 400 Bad Request           | 존재하지 않는 단축 URL 연결 요청         |
CAN_NOT_ENCODE_STRING | 500 Internal Server Error           | encoding 범위 초과               |
INTERNAL_SERVER_ERROR           | 500 Internal Server Error          | 어플리케이션 내부 런타임 에러| 

## Further works
* 단축 URL 표현 범위에 대한 한계가 있음. 갯수가 유한하기 때문에 실제 서비스라면 언젠가 문제가 발생함.
* H2 데이터베이스이므로 어플리케이션이 중지되면 휘발되어버림. docker mysql 이나 AWS RDS 를 사용하여 해결 가능.
* 좀 더 디테일한 에러코드 정의 필요.
* Validation 담당 컴포넌트의 부재.
    
## Directory Description

```bash
project root
│
└── kakao.pay
    ├── common -----------> 공통 package
    │   ├── exception ------------> custom exception 처리와 관련된 package
    │   │   │── RestException.java ---------------> custom exception 객체
    │   │   │── RestExceptionHandler.java --------> application 전체 exception 처리 handler
    │   │   └── RestExceptionType.java -----------> custom exception type 정의 (enum)
    │   │
    │   │── interceptor -----------> interceptor 관련 package
    │   │   └── RestInterceptor.java -------------> interceptor 정의. validation 담당.
    │   └── utils -----------------> 공통으로 사용될 utils package.
    │       │── CutomBase62Util.java -------------> short url의 base62 encoding decoding utility class.
    │       └── UrlUtil.java ---------------------> Url 관련 utility class.
    │
    ├── config
    │   │── SystemConfig.java -----> System default config 값 관리.
    │   └── WebMvcConfig.java -----> web mvc config class. interceptor register.
    │
    ├── controller
    │   └── ShorturlController.java -------> Request 처리 컨트롤러. 
    │
    ├── entity
    │   └── ShorturlEntity.java -----------> 요청된 url 관리 entity.
    │
    ├── repository
    │   └── ShorturlRepository.java -------> short_url 엔티티 작업 처리 respository.
    │
    └── service
       └── ShorturlService.java -----------> 단축 url 생성 및 맵핑 비즈니스 로직 구현 class
resources
    ├── templates
    │   ├── exception.html --------------> 에러 노출 페이지
    │   └── index.html ------------------> 기본 페이지
    │
    │── application.yml -----------------------> SpringBoot 기본 환경 설정 파일
    └── application-database.yml --------------> H2, JPA 설정 파일
```