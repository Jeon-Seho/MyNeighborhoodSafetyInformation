# My Neighborhood Safety Information
> 충남 우리동네안전정보 시스템

## 프로젝트 개요 및 목적
### 개요
충남 우리동네안전정보 시스템은 거주하는 지역의 간략한 안전 정보를 모아 직관적으로 표시해주는 시스템입니다.

### 목적
본 시스템은 쉽게 알아차리기 힘든 주변의 안전 정보를 보다 간편하고 빠르게 확인할 수 있도록 하는 것에 목적을 두어 개발되었습니다.

## 주요 기능
- 충남 내 도시 선택
- 재난 속보 확인
- 재난 문자 확인
- 대피소 정보 확인
- 정적 지도 정보 확인
- 도시 재선택
- 프로그램 종료
 
## 기술 스택
- Java: 메인 개발 언어
- Socket: Server - Client 구조 TCP/IP 통신 구현
- Multi-Thread: Server에서 다수의 클라이언트가 동시에 접속할 수 있게끔 관리, Client에서 Sever와의 송수신을 역할을 나누어 동시에 확인
- Swing: 주요 GUI 컴포넌트 배치
- MySQL: 도시 정보 및 대피소 정보 저장
- API: 충남 재난속보, 긴급재난문자 API를 사용하여 Json 형태의 데이터를 받아 GSON 라이브러리를 이용해 역직렬화, 지도 이미지는 Google Static Map API를 사용하여 저장 후 전송

## 설치 및 실행 방법
1. CommonClass 프로젝트를 Server와 Client 프로젝트의 Build Path에서 Projects - Classpath에 등록 필요(추후 .jar 변환 예정)
2. GSON, JDBC jar 파일을 Server 프로젝트의 Build Path에서 Libraries - Classpath에 등록 필요
3. MySQL 설치 후 Database명은 myneighborhoodsafefyimpormation, 테이블 명은 각각 chungnam_city, chungnam_shelter로 하여 csv 파일 import 필요
4. 공공데이터포털, 재난안전데이터공유플랫폼, Google Cloud API 서비스 키, MySQL Password 입력 필요
5. Server.java 실행 후 Client.java 실행

## 문의
전세호
