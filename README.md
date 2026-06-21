# 🍎 음식쇼핑몰 Java 프로그램
<br>

## 프로젝트 소개

- 식품쇼핑몰은 신선식품과 보존식품을 취급하며, 회원으로 가입하면 누구나 구매할 수 있습니다.
- 회원으로 로그인하면 상품을 검색하고, 장바구니에 담아 구매할 수 있습니다.
- 관리자는 상품을 등록/수정하고, 고객의 주문과 배달상황을 관리합니다.

<br>

## 팀원 구성과 역할

<div align="center">

|**공통작업**| **조경진** | **황지민** |
| :------: |  :------: |  :------: |
| <img src="https://github.com/user-attachments/assets/2cab9bdc-b67d-4130-8416-bae3324f1e3a" height=150 width=150> <br/> | [<img src="https://github.com/user-attachments/assets/42fe3dee-cf9f-41ab-b9ba-595cc5eb3ef5" height=150 width=150> <br/> @jin020709](https://github.com/jin020709)| [<img src="https://github.com/user-attachments/assets/7185f7eb-6df3-41b0-b609-ea94b91f2a09" height=150 width=150> <br/> @20221563](https://github.com/20221563) |
|공통작업부분<br>| 장바구니<br>주문 관리<br>상품정보<br>시스템 조율<br> | 고객관리<br>주문항목<br>카테고리<br>관리자 기능|

</div>

<br>

## 1. 개발 환경

**Language** <div><img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=Java&logoColor=white"></div>  

**Tools** <div><img src="https://img.shields.io/badge/IntelliJIDEA-000000?style=for-the-badge&logo=intellijidea&logoColor=white"></div>  

**Collaboration** <div><img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white"></div>

**AI** <div> <img src="https://github.com/user-attachments/assets/1c714b4e-3f05-470a-a7eb-3ed9184d211f"  height=25 width=50>
<img src="https://github.com/user-attachments/assets/12047116-b869-41f5-96ba-e10201c73342" height=25 width=60>



## 2. 개발 기간 및 작업 관리

### 개발 기간

- 전체 개발 기간 : 2026-05-29 ~ 2026-06-05
- 기획 : 2026-05-29 ~ 2026-06-02
- 기능 구현 : 2026-05-29 ~ 2026-06-02


<br>

## 3. 요구사항 명세 및 다이어그램

<details>
  <summary>요구사항 명세서</summary>

1.초기화면  
![image](https://github.com/user-attachments/assets/983de764-97a2-4f43-897c-e598a00be915)

2.회원 로그인 성공 후 화면  
![image](https://github.com/user-attachments/assets/b6983bd5-7dc1-44df-b44e-b0a56dfcf8b4)

3.장바구니  
![image](https://github.com/user-attachments/assets/4ed13283-0114-4ff3-9e8f-16f9749a3c52)

4.관리자 로그인 성공 후 메뉴  
![image](https://github.com/user-attachments/assets/6f6f099c-645a-47f9-9faf-22cfa0b29a19)




</details>

  
<br>

<details>
  <summary>클래스 다이어그램</summary>

![class](https://github.com/user-attachments/assets/f1656d7c-2511-41f1-aaf7-35e20380cbfe)



</details>

<br>

<details>
  <summary>유스케이스 다이어그램</summary>

![class](https://github.com/user-attachments/assets/8a87f9a9-6439-4bd3-8b10-4978969652fe)



</details>

<br>


## 4. 구현 기능

### [Main Menu]
- 프로그램을 실행하면 메인 메뉴가 나타납니다.
    - 로그인이 되어 있지 않은 경우 : 비활성화된 메뉴
    - 로그인이 되어 있는 경우 : 활성화된 메뉴
- 메인 메뉴에서는 회원과 관리자 로그인, 회원가입을 할 수 있습니다.

| 초기화면 | 회원 로그인 화면 |
| --- | --- |


<br>


### [회원 가입]
- 회원 가입을 하면 DB의 member 테이블에 입력 받은 정보를 저장합니다.
  


<br>

### [회원 로그인]
- 회원 로그인은 아이디와 비밀번호를 입력하면 DB의 데이터와 비교하여 일치한 경우 로그인에 성공하고 비밀번호가 일치하지 않을 경우 불일치 경고가 아이디가 없을 경우 회원가입 문구가 표시 됩니다.

- 로그인 성공 후, 메뉴창이 활성화되며, 각 카테고리의 값을 입력하여 메뉴를 사용할 수 있습니다.


| 패스워드 불일치 | 미등록 아이디 |
| --- | --- |


<br>


#### [1. 상품전체보기]
- 1을 입력 받으면 item 테이블에 저장되어 있는 상품들을 보여줍니다.

| 상품전체보기 |
|----------|

<br>

#### [2. 상품상세조회]
- 2를 입력 받으면 해당 item의 상세정보를 보여주며, 장바구니로 바로 담을 수 있도록 하였습니다.

| 상품전체보기 |
|----------|

<br>


#### [3. 주문/배송조회]
- 3을 입력 받으면 해당 구매한 상품의 정보를 확인할 수 있습니다
- 주문수정, 주문취소를 할 수 있습니다.
- 주문수정을 통해 배송지를 수정할 수 있습니다
- 주문 취소를 통해 구매한 상품을 삭제할 수 있습니다.

| 주문/배송조회 | 주문수정 | 주문 취소 |
| --- | --- | --- |

<br>

#### [5. 장바구니]
- 상세조회한 상품을 구매하려고 할 때, 장바구니 메뉴를 이용합니다.
- 장바구니에 물건이 담겼다면 상품 구매 결정을 할 수 있으며, 원치 않는다면 장바구니에서 상품을 삭제할 수 있습니다.
- 구매 결정에 맞는 번호를 클릭하였을 때, 총 금액을 확인할 수 있습니다. 
- 구매 번호 입력 시 주소/ 전화번호를 변경하여 배송이 문제없이 진행되도록 합니다.

| 장바구니 | 구매결정 화면 |
| --- | --- |


| 주소/전화번호 변경 | 주문 완료 |
| --- | --- |
<br>


#### [9. 내정보확인]
- 메인 메뉴에서 9번을 입력하면 내정보 확인 탭에 입장하여 이름, 아이디, 비밀번호 등의 본인 정보를 확인할 수 있습니다.
- 수정을 누르면 비밀번호, 주소, 전화번호를 변경할 수 있습니다.

| 내정보확인 | 내정보수정 |
| --- | --- |



<br>


## 6. 프로젝트 후기

### 🍊 조경진

프로젝트 후기

<br>

### 🍊 황지민

프로젝트 후기
