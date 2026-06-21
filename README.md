# 🍎 음식쇼핑몰 Java 프로그램
<br>

## 프로젝트 소개

- 식품쇼핑몰은 신선식품과 보존식품을 취급하며, 회원으로 가입하면 누구나 구매할 수 있습니다.
- 회원으로 로그인하면 상품을 검색하고, 장바구니에 담아 구매할 수 있습니다.
- 관리자는 상품을 등록/수정하고, 고객의 주문과 배달상황을 관리합니다.

<br>

## 팀원 구성과 역할

<div align="center">

| **조경진** | **황지민** |
|  :------: |  :------: |
| [<img src="https://github.com/user-attachments/assets/42fe3dee-cf9f-41ab-b9ba-595cc5eb3ef5" height=150 width=150> <br/> @jin020709](https://github.com/jin020709)| [<img src="https://github.com/user-attachments/assets/7185f7eb-6df3-41b0-b609-ea94b91f2a09" height=150 width=150> <br/> @20221563](https://github.com/20221563) |
 장바구니<br>주문 관리<br>쿠폰 적용<br>상품정보<br>시스템 조율<br>READE.md | 고객관리<br>주문항목<br>카테고리<br>관리자 기능|

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

- 전체 개발 기간 : 2026-05-29 ~ 2026-06-21
- 기획 : 2026-05-29 ~ 2026-06-10
- 기능 구현 : 2026-05-29 ~ 2026-06-12


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
- 메인 메뉴에서는 회원과 관리자 로그인, 회원가입을 할 수 있습니다.

| 초기화면 |
| --- |
| ![image](https://github.com/user-attachments/assets/827de7d6-6495-4349-bd47-53c3fc797682) |



<br>

### [회원가입 & 회원 로그인]
- 회원 가입을 하고 종료하면 CSV 파일에 회원정보가 저장됩니다.
- 회원 로그인은 아이디와 비밀번호를 입력하면 종료전에는 자바의 데이터와 비교하여 일치한 경우 로그인에 성공하고 재실행후 에는 CSV 파일과 비교하여 비밀번호가 일치하지 않을 경우 불일치 경고가 표시 됩니다



| 회원가입&로그인 | 아이디 불일치 |
| --- | --- |
| ![image](https://github.com/user-attachments/assets/40ade120-c60b-49d8-b4ce-15523f89ff31) | ![image](https://github.com/user-attachments/assets/bb2af471-3e55-440a-99e8-cf448e1fe815) |

<br>


#### [1. 카테고리별보기]
-카테고리별 보기를 선택하고 해당 카테고리를 선택하면 해당되어있는 상품 목록이 표시됩니다.

| 카테고리별보기 |
|----------|
| ![image](https://github.com/user-attachments/assets/8b16e55d-9846-4305-a443-d4eac6e3bdeb) |
<br>

#### [2. 상품상세조회]
- 상품 상세조회를 하면 상품번호를 통해 해당 상품을 확인하고 상품 설명과 함께 구매혹은 장바구니를 넣을수 있습니다.

| 상품상세보기 |
|----------|
| ![image](https://github.com/user-attachments/assets/80c27552-2f05-4143-b848-fbd9e51c3ab1) |
<br>


#### [3. 구매내역 확인]
- 구매한 상품의 정보를 확인할 수 있습니다

| 주문조회 |
| --- |
| ![image](https://github.com/user-attachments/assets/b96927dd-9e13-47b8-8d50-35c324356138) |
<br>

#### [5. 장바구니 &쿠폰 사용 & 주문완료]
- 상세조회한 상품을 구매하려고 할 때, 장바구니 메뉴를 이용합니다.
- 장바구니에 물건이 담겼다면 상품 구매 결정을 할 수 있으며, 원치 않는다면 장바구니에서 상품을 삭제할 수 있습니다.
- 구매를 할때 쿠폰 사용여부를 확인하여 쿠폰을 사용할 수도 있습니다

| 장바구니 | 쿠폰결정 화면 |
| --- | --- |
| ![image](https://github.com/user-attachments/assets/2b45c21f-90f9-422c-8980-ce7dfbcc3eba) | ![image](https://github.com/user-attachments/assets/92c50ca9-f085-464a-b5cf-7b1d75446b58) |

| 쿠폰적용 주문완료 |
| --- |
| ![image](https://github.com/user-attachments/assets/5a80da31-ba68-4b3d-b073-a3861f30a495) |
<br>


#### [5. 내정보보기]
- 내정보보기를 들어가면 이름, 아이디, 비밀번호 등의 본인 정보를 확인할 수 있습니다.
- 수정을 누르면 주소, 전화번호를 변경할 수 있습니다.

| 내정보확인 | 내정보수정 |
| --- | --- |
| ![image](https://github.com/user-attachments/assets/ad0fb1d9-0b7f-4903-a0f1-abba6a6778fc) | ![image](https://github.com/user-attachments/assets/f767fe81-a3b7-4156-af8c-7d5de4980309) |
<br>

#### [6. 상품추기]
- 관리자 아이디로 로그인후 상품을 추가 할 수 있습니다
- 추가를 원하는 카테고리와 상품이름 수량 가격 설명을 입력해 추가 할 수 있습니다.

| 상품추가 |
| --- |
| ![image](https://github.com/user-attachments/assets/fc2e3f31-2c90-4f20-bbd3-d09f16827f90) |


<br>

#### [7. 고객 벼송현황 변경]
- 관리자 로그인후에 고객의 배송현황을 확인하여 배송상태를 변경할 수 있습니다

| 배송현황 변경 |
| --- |
| ![image](https://github.com/user-attachments/assets/4689b046-a878-463f-a98c-9f7acdb9ccc5) |
<br>

#### [8. 종료]
- 종료시에 새롭게 추가된 회원정보가 삭제된 회원정보를 CSV파일에 저장합니다

| 종료 |
| --- |
| ![image](https://github.com/user-attachments/assets/6c19a808-24a5-401e-b470-1c13914b68ac) |

## 6. 프로젝트 후기

### 🍊 조경진

프로젝트 후기 : 기존에 과제를 하면서 AI 활용법이라던지 자바의 상속 구조 라던지 기본 틀을 연습했던거 덕분
에 이번 기말 과제를 진행함에 있어서 더 편하게 했던거 같습니다. 하지만 이번 프로젝트를 진행하면서 
기존에는 하나의 기능을 제대로 넣는 느낌이였다면 이번에는 여러 기능을 복합적으로 사용하고 A를 사
용하면 B가 버그가 걸리는 경우가 많이 생겼습니다. 프로그램을 만들고 버그를 찾아내고 그 버그를 수정
하는데 생각보다 많은 시간을 사용하게 된 것 같습니다. 이번에 처음으로 조원과 협업을 하여 진행하여 
팀원과 소을 통해 해결하면서 협업의 중요성을 알 수 있게 되었습니다. 

<br>

### 🍊 황지민

프로젝트 후기 : 지금까지는 혼자서 과제로 작은 프로그램을 만들어서 제출해 보는 건만 해봤는데, 이렇게 누군
가와 같이 협력해서 프로그램을 만드는 건 처음이었습니다. 그러다보니, 혼자 할 때는 내 의견만 가지고 
진행하면 됐었는데 둘이서 해보니깐 프로젝트 구성 방향은 어떻게 하는 게 좋을지, 파트 담당은 어떻게 
해야 될 지, 처음이라서 어려움이 있었습니다. 하지만, 어찌어찌해서 결과물을 만들어낼 수 있어서 좋은 
경험이 되었고, 깃허브를 아주 조금 밖에 안 써봤지만, 경험삼아 연습해 볼 수 있어서 좋았던 것 같습니
다.   
