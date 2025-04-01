# CRUD 과제 - 투두리스트
https://github.com/user-attachments/assets/360ea3a5-f659-4d97-9fc2-0b001cc3f1b4

<br><br>

## 개발 환경
| 구분      | 기술 스택        |
|----------|------------------|
| Back-end | Spring Boot 3, Java 17 |
| DB | MySQL 8.0 |
| Front-end | TypeScript, Axios |

<br>

## API 명세
| 기능                 | HTTP  | 경로 |
|----------------------|------|-------------------------------------------|
| task 생성          | POST  | /tasks |
| task 삭제          | DELETE | /tasks/{id} |
| 일일 모든 task 조회 | GET   | /tasks/daily?year={}&month={}&day={}&page={} |
| 한 달 모든 task 조회 | GET   | /tasks/monthly?year={}&month={}&page={} |
| task 개수 조회     | GET   | /tasks/count?year={}&month={} |
| 파일 삭제    | DELETE   | /attaches/{id} |

<br>

## ERD
<img src="https://github.com/user-attachments/assets/d310ba90-ea80-4a7e-9c8c-6a7aa204031a" width="100%" />
