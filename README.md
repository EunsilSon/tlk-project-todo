# CRUD 과제 - 투두리스트
<img src="https://github.com/user-attachments/assets/661094c8-7dab-4ad7-bb8e-d435739334a2" width="80%" />

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
| 파일 삭제    | DELETE   | /tasks/images/{id} |

<br>

## ERD
<img src="https://github.com/user-attachments/assets/92198ead-236c-4cb1-a358-ec26eac99410" width="100%" />
