머니 뿌리기 사전과제 
---
### 요구사항
* 뿌리기, 받기, 조회 기능을 수행하는 REST API 구현
* 다수의 서버에 다수 인스턴스로 동작하더라도 기능에 문제가 없도록 설계
* 각 기능 및 제약사항에 대한 단위테스트 작성
### 개발
* SpringBoot 2.4
* Rediss/Redisson
* Spring Data JPA
* MariaDB
---
### 핵심 문제해결 전략
* 다수의 서버에서 수행하기 위한 Distribute Lock 적용 
* 머니분배 전략은 Random 분배 적용
* 10분이 지난 요청에 대해 에러처리를 해야 하므로 Redis로 TTL 적용
* 요구사항에 부합하는 간소화된 API서버 구현을 위해 모델링 간소화
---
### 실행환경
* Redis Server 로컬 환경 필요 (Localhost:6379)
* MariaDb 10.5 
---
### 테스트 진행 방식
* Spring Boot
  * 각 기능 단위테스트 진행
    * 상황에 따른 Given 전략 수립 후 데이터 변경후 테스트 진행
* Postman
  * 결과 값 식별의 API 통합테스트 진행
---
### 에러코드(제약사항)
~~~java
INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
INTERNAL_SERVER_ERROR(500, "C003", "Server Error"),
INVALID_TYPE_VALUE(400, "C004", " Invalid Type Value"),
HANDLE_ACCESS_DENIED(403, "C005", "Access is Denied"),
INVALID_GET_LOCK(400, "C006", " get lock failed"),
FAILED_GET_MONEY_DAYS(400, "B001", " 받기 가능기간이 만료 되었습니다."),
FAILED_GET_MONEY_BAD_REQUEST(400, "B002", " 유효하지 않은 요청입니다."),
FAILED_GET_MONEY_UN_TARGET(400, "B003", " 받기 대상자가 아닙니다."),
FAILED_GET_MONEY_ONE_MORE(400, "B004", " 받기는 한번만 가능합니다."),
FAILED_GET_MONEY_MINUTE(400, "B005", " 받기 가능시간이 만료 되었습니다."),
FAILED_SEARCH_RECEIVER_INFO(400, "S001", " 조회 권한이 없거나 기간이 만료 되었습니다."),
FAILED_GET_MONEY(400, "S001", " 할당 된 금액을 받기 실패 하였습니다."),
FAILED_SAVE_MONEY(400, "S002", " 금액이나 뿌릴인원이 존재하지 않습니다."),
~~~
### API
*뿌리기 생성 요청*
~~~http request
POST http://localhost:8080/api/sprinkling
Content-Type: application/json
X-USER-ID: 1232
X-ROOM-ID: ABC2

{"distMoney" :  25000, "receiveNum" :  5}
~~~
*뿌리기 생성 응답*
~~~http request
POST http://localhost:8080/api/sprinkling

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 23 Nov 2020 09:41:09 GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
  "token": "113"
}

Response code: 200; Time: 101ms; Content length: 15 bytes
~~~
*받기 요청*
~~~http request
GET http://localhost:8080/api/receiving/{{token}}
Content-Type: application/json
X-USER-ID: 1233
X-ROOM-ID: ABC2

Postman http-client.private.env.json
{
  "dev": {
    "token": "a99"
  }
}
~~~
*받기 응답*
~~~http request
GET http://localhost:8080/api/receiving/113

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 23 Nov 2020 09:43:40 GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
  "receive_money": 22653
}

Response code: 200; Time: 125ms; Content length: 23 bytes
~~~
*조회하기 요청*
~~~http request
GET http://localhost:8080/api/receiving/{{token}}
Content-Type: application/json
X-USER-ID: 1232
X-ROOM-ID: ABC2

Postman http-client.private.env.json
{
  "dev": {
    "token": "a99"
  }
}
~~~
*조회하기 응답*
~~~http request
GET http://localhost:8080/api/searching/113

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 23 Nov 2020 09:53:00 GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
  "receive_sum_money": 24906,
  "dist_money": 25000,
  "dist_date_time": "2020-11-23 06:41:09",
  "receiver_Info": [
    {
      "receiver_id": "1234",
      "receive_money": 22653
    },
    {
      "receiver_id": "1233",
      "receive_money": 2253
    }
  ]
}

Response code: 200; Time: 53ms; Content length: 192 bytes
~~~
*에러코드 응답*
~~~http request
GET http://localhost:8080/api/receiving/113

HTTP/1.1 400 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 23 Nov 2020 09:54:34 GMT
Connection: close

{
  "message": " 받기 가능시간이 만료 되었습니다.",
  "status": 400,
  "errors": [],
  "code": "B005"
}

Response code: 400; Time: 80ms; Content length: 72 bytes
~~~