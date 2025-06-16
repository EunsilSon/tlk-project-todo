# CRUD 과제 - 투두리스트
https://github.com/user-attachments/assets/360ea3a5-f659-4d97-9fc2-0b001cc3f1b4

<br>

## 목차
1. [프로젝트 소개](#프로젝트-소개)
2. [개발 환경](#개발-환경)
3. [주요 개발 기능 및 기술 기록](#주요-개발-기능-및-기술-기록록)
4. [API 명세](#API-명세)
5. [ERD](#ERD)

<br>

## 프로젝트 소개
**투두리스트**는 날짜별로 할 일을 작성하고 이미지 첨부가 가능한 일정 관리 서비스입니다.  

신입 개발자 과제로 시작한 프로젝트이며 개발 요구 사항 및 피드백을 수용하여 일정 내 개발 완료를 목표로 했습니다.

<br>

## 개발 환경
| 구분      | 기술 스택       |
|----------|------------------|
| Back-end | Spring Boot 3, Java 17 |
| DB | MySQL 8.0 |
| Front-end | TypeScript, Axios |

<br>

## 주요 개발 기능 및 기술 기록

- **할 일 관리 기능**: 투두 등록, 삭제, 조회 등 기본 CRUD 구현
- **달력 UI/UX 직접 구현**: 날짜 유효성 검사, 예외 처리 등 사용자 친화적인 기능 고려
- **이미지 업로드 기능**:  
  - 초기에는 AWS S3 + Presigned URL 방식으로 구현  
  - 피드백 반영 후 로컬 환경 기반 업로드로 구조 변경  
  - [AWS S3 구현 과정 블로그 보기](https://velog.io/@eunsilson/Spring-Boot-3-AWS-S3-%EC%97%B0%EB%8F%99-Presigned-URL%EC%9D%84-%ED%86%B5%ED%95%9C-%EC%A1%B0%ED%9A%8C)

- **실무 유사 테스트 환경 구성**:  
  로컬 서버를 외부 IP로 접근 가능하게 설정하여 프론트엔드와 이미지 업로드 기능 연동 테스트



<br>

## API 명세
| 기능                 | HTTP  | 경로 |
|----------------------|------|-------------------------------------------|
| 할 일 생성          | POST  | /tasks |
| 할 일 삭제          | DELETE | /tasks/{id} |
| 일일 모든 task 조회 | GET   | /tasks/daily?year={}&month={}&day={}&page={} |
| 한 달 모든 task 조회 | GET   | /tasks/monthly?year={}&month={}&page={} |
| task 개수 조회     | GET   | /tasks/count?year={}&month={} |
| 파일 삭제    | DELETE   | /attaches/{id} |

<br>

## ERD
<img src="https://github.com/user-attachments/assets/d310ba90-ea80-4a7e-9c8c-6a7aa204031a" width="100%" />
