# Ipoteka : Bank and House loan example 


## Run Project

`#gradle bootRun`
or
`#./gradlew bootRun`


http://localhost:8081/swagger-ui.html  is the good place to start. Swagger has very simple and intuitive way of requesting and working with API. (Postman or any other tool also can be used)

![swagger-main-api](swagger-main.png)

## Token

Any operations except token-provider-controller forbidden for use:

- **/security/add-user** 사용자 추가 하기 (with requestBody)
- **/security/auth** 로그인하고 jwt 토큰을 받기
- **/security/refresh-token** jwt 토큰 Refresh 하기
- **/security/user-password-reset** 사용자 비밀 번호 reset 하기 (with requestBody)

토큰 받고 해더에다가 담가서 남어지 API들 호출 할수 있습니다. 
기본 사용자는 admin (비밀번호는: 1234) 입니다

Example: `GET /security/auth`

Request body: 
```
{
  "password": "1234",
  "username": "admin"
}
```

Response:
```
{
  "success": true,
  "message": "Authentication Successful",
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTU2Mzg3NjIxMCwiZXhwIjoxNTYzOTYyNjEwfQ.WA2MQlzwdxHVx0leyMNpER1XcUkbOxNPKdPUYwktvncxTrvqajUCY-2AbscwYiQ5bHsiryIbVdVJlnS7FiOm0A"
}
```

![swagger-put-token](swagger-put-token.png)


## Upload File

Three file controllers are included.

- **/input/fileUpload** 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API
- **/input/multipleFilesUpload** 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API (upload multiple files)
- **/input/loadTestData Resource** 에 있는 예지즐 업로드 와 parsing 하기 (upload file from resource folder)


**/input/fileUpload** is the main controller, other two can be considered as a supportive controllers. **/input/loadTestData** is the GET controller and used for loading the test data from the resources folder. It also performs file reading and parsing operations as it done by **/input/fileUpload** controller.

## Business API 

Is main controller which is doing various business requests over the uploaded data

- **/banks/** 주택금융 공급 금융기관(은행) 목록을 출력하는 API
- **/banks/extremeAveragePoints** 전체 년도(2005~2016)에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력하는 API
- **/banks/highestValuedBankYear** 각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관명을 출력하는 API
- **/banks/predict** 특정 은행의 특정 달에 대해서 2018 년도 해당 달에 금융지원 금액을 예측하는 API
- **/banks/yearlyTotalDetail** 년도별 각 금융기관의 지원금액 합계를 출력하는 API


If the one of the data processing APIs called while there is no data in Database, then No data available runtime exception is thrown

Example response body with no data available exception:
```
{
  "timestamp": "2019-07-23T10:24:14.705+0000",
  "status": 500,
  "error": "Internal Server Error",
  "message": "No data available for processing!",
  "path": "/banks/extremeAveragePoints"
}
```


## Detail Requirements for API:

* 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API
* 주택금융 공급 금융기관(은행) 목록을 출력하는 API
* 년도별 각 금융기관의 지원금액 합계를 출력하는 API
* 각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관명을 출력하는 API 개발
    * 예를들어, 2005 년 ~2017 년 중에 2010 년 국민은행의 전체 지원금액(1 월~12 월 지원 합계)이 가장 높았다면 { “year": “2010” , "bank": “국민은행”}을 결과로 출력합니다
* 전체 년도(2005~2016)에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력하는 API
    * 예를들어, 2005 년 ~ 2016 년 외환은행의 평균 지원금액 (매년 12 달의 지원금액 평균값)을 계산하여 가장 작은 값과 큰 값을 출력합니다. 소수점 이하는 반올림해서 계산하세요
    
* 특정 은행의 특정 달에 대해서 2018 년도 해당 달에 금융지원 금액을 예측하는 API
    * 단, 예측 알고리즘을 무엇을 써야하는지에 대한 제약은 없지만, 가장 근사치에 가까울 수록 높은 점수 부여


## Improvements:

- Detail validation checks are required
- Prediction model should be tested more and if necessary changed with more appropriate model
- RuntimeExceptions can be changed into a custom exception classes to handle the errors
- Test cases for token management is required


### Token For 1 Year
```
Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTU2Mzg4NDU3NiwiZXhwIjoxNTk1NDIwNTc2fQ.rFJqV9nKJG0Jf-vr4b3ycic0E5j_dBGFC_qMySNrxMeQZt1t6TCh8nRPxNddBoIHfEezrRJLB6j-_HkMPTguLw
```

####  Failure Token:
```
Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmZWRvciIsImlhdCI6MTU2Mzc5NDc1MSwiZXhwIjoxNTYzODgxMTUxfQ.kvfeCpwYETTLVbL5Deq3dCdCUFZTOFv5WN3JuXCWV0YV9Vwb4EnEjZ21u85_1J1lnTSaVVcDrWJmax5SYz9M9g
       eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmZWRvciIsImlhdCI6MTU2Mzg4MTE0MywiZXhwIjoxNjUwMjgxfQ.MNZqBmxBA1q61EdpWvmlxJZGBHtwSkNkmFE15CMsTkUoH7vD5M45Ebf1kgDTUWSn6ZMRjKJKmPmPwf4gxY6GZw
```


