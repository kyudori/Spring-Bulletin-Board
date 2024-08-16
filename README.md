# 기능 요약

## 1. 게시글 관리 기능

### - 게시글 목록 조회 (페이징 지원)
- URL: `GET /api/articles`
- 설명: 모든 게시글을 페이지 단위로 조회합니다.

### - 특정 게시글 조회
- URL: `GET /api/articles/{id}`
- 설명: 특정 ID에 해당하는 게시글을 조회합니다.

### - 게시글 생성
- URL: `POST /api/articles`
- 설명: 새 게시글을 생성하여 데이터베이스에 저장합니다.

### - 게시글 수정
- URL: `PATCH /api/articles/{id}`
- 설명: 기존 게시글의 내용을 수정합니다. 게시글 작성자만 수정할 수 있습니다.

### - 게시글 삭제
- URL: `DELETE /api/articles/{id}`
- 설명: 게시글을 삭제합니다. 게시글 작성자만 삭제할 수 있습니다.

### - 게시글 검색
- URL: `GET /api/articles/search?keyword={keyword}&page={page}&size={size}`
- 설명: 제목이나 내용에 특정 키워드를 포함하는 게시글을 검색합니다.

### - 본인이 작성한 게시글 조회
- URL: `GET /api/articles/myarticles?page={page}&size={size}`
- 설명: 현재 로그인한 사용자가 작성한 게시글을 조회합니다.

## 2. 댓글 관리 기능

### - 댓글 목록 조회
- URL: `GET /api/articles/comments/{articleId}`
- 설명: 특정 게시글에 달린 모든 댓글을 조회합니다.

### - 댓글 생성
- URL: `POST /api/articles/comments/{articleId}`
- 설명: 특정 게시글에 새로운 댓글을 추가합니다.

### - 댓글 수정
- URL: `PATCH /api/articles/comments/{articleId}/{commentId}`
- 설명: 기존 댓글의 내용을 수정합니다. 댓글 작성자만 수정할 수 있습니다.

### - 댓글 삭제
- URL: `DELETE /api/articles/comments/{articleId}/{commentId}`
- 설명: 특정 댓글을 삭제합니다. 댓글 작성자만 삭제할 수 있습니다.

### - 본인이 작성한 댓글 조회
- URL: `GET /api/articles/comments/mycomments`
- 설명: 현재 로그인한 사용자가 작성한 모든 댓글을 조회합니다.

## 3. 좋아요 기능

### - 좋아요 토글
- URL: `POST /api/articles/likes/toggle`
- 설명: 게시글에 대한 좋아요를 추가하거나 제거합니다.

## 4. 사용자 인증 및 인가

### - JWT 기반 인증
- 설명: 로그인 시 JWT를 발급하며, 이후 요청 시 Authorization 헤더에 토큰을 포함시켜 인증을 수행합니다.

### - OAuth2 기반 소셜 로그인
- 설명: Google, Naver, Kakao 등의 소셜 로그인을 지원합니다.

### - Spring Security 설정
- 설명: 엔드포인트 별로 접근 권한을 설정하며, 인증되지 않은 사용자는 특정 엔드포인트에 접근할 수 없습니다.

## 5. 회원 관리

### - 회원 가입 및 로그인
- URL: `POST /auth/register` (회원 가입)
- URL: `POST /auth/login` (로그인)
- URL: `POST /auth/refresh` (리프레시 토큰을 이용한 액세스 토큰 재발급)
- 설명: 사용자는 이메일과 비밀번호를 이용하여 회원 가입 및 로그인을 할 수 있습니다. 소셜 로그인 시에도 동일한 회원 관리 로직을 따릅니다.

### - 회원 정보 조회 및 관리
- URL: `GET /members` (회원 목록 조회)
- URL: `GET /members/{id}` (회원 정보 조회)
- URL: `POST /join` (회원 가입)
- URL: `POST /members/update` (회원 정보 수정)
- URL: `GET /members/{id}/delete` (회원 삭제)
- 설명: 사용자 정보를 조회하거나, 인증된 사용자가 자신의 정보를 관리할 수 있습니다.

## 6. 관리 기능

### - 회원 권한 부여
- 설명: 관리자는 특정 사용자에게 권한을 부여하거나 수정할 수 있습니다.

## 7. 추가 기능

### - 랜덤 명언 제공
- URL: `GET /random-quote`
- 설명: 랜덤한 명언을 제공하는 페이지입니다.

### - 간단한 인사 페이지
- URL: `GET /hi` (인사 페이지)
- URL: `GET /bye` (작별 인사 페이지)
- 설명: 간단한 인사말을 포함한 페이지입니다.

### - 보호된 엔드포인트 접근
- URL: `GET /auth/protected-endpoint`
- 설명: `ROLE_USER` 권한이 필요한 보호된 엔드포인트로, 인증된 사용자만 접근할 수 있습니다.

### - 데이터베이스 설계
![Untitled](https://github.com/user-attachments/assets/aa8be03a-a845-4558-87ae-4a2418e96899)


# 전체 흐름

SecurityConfig
- 설정
UserDetails, UserDetailsService
- 사용자의 정보를 담는 인터페이스
- 사용자의 정보를 불러오기 위해서 구현해야 하는 인터페이스로 기본 오버라이드 메서드

1. 클라이언트에서 로그인 요청
- 클라이언트는 사용자 이름과 비밀번호를 포함하여 로그인 요청

2. 서버에서 토큰 생성
- 서버는 사용자 인증 정보를 확인한 후, JwtTokenProvider를 사용하여 JWT 토큰을 생성
- 생성된 토큰은 클라이언트에게 응답으로 반환

3. 클라이언트에서 토큰 사용
- 클라이언트는 이후 요청 시 Authorization 헤더에 Bearer <JWT 토큰> 형식으로 토큰을 포함하여 서버에 요청

4. 서버에서 토큰 검증 및 인증 설정:
- 서버는 JwtAuthenticationFilter를 사용하여 요청을 가로채고, Authorization 헤더에서 JWT 토큰을 추출
- 추출한 토큰을 JwtTokenProvider를 사용하여 검증
- 토큰이 유효하면, UserDetails 객체를 로드하고 Authentication 객체를 생성하여 SecurityContextHolder에 설정

==========================================
# 예제
- POSTMAN TEST 시, Header에 KEY Autorization, Value Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlcjJAZXhhbXBsZS5jb20iLCJpYXQiOjE3MTY2OTIxMTUsImV4cCI6MTcxNjY5NTcxNX0.dvrco3hiZ9Z3I1ofXb5L2DW7G2clRD7yw42KlGOTQpw 넣어줘야 함.
1. 회원가입 POST http://localhost:8080/auth/register
   ![image](https://github.com/kyudori/Spring-Bulletin-Board/assets/57388014/0a064f1c-2fd6-49d9-95bd-e6cfb18113c9)
   ![image](https://github.com/kyudori/Spring-Bulletin-Board/assets/57388014/8155c2f5-42a4-460e-9376-87a952b1ff48)
2. 로그인 POST http://localhost:8080/auth/login
  ![image](https://github.com/kyudori/Spring-Bulletin-Board/assets/57388014/cf196f74-9f6a-45d8-bcc3-db7917351fce)
3. 게시판 작성 POST http://localhost:8080/api/articles
  ![image](https://github.com/kyudori/Spring-Bulletin-Board/assets/57388014/90ffd713-8816-4b37-992c-2e6bb4da0547)
  ![image](https://github.com/kyudori/Spring-Bulletin-Board/assets/57388014/fde1720d-0d56-4d64-913d-0b2728d24483)
  ![image](https://github.com/kyudori/Spring-Bulletin-Board/assets/57388014/5af17eb4-51c7-424e-862a-88af4ecdf77f)
4. 게시판 삭제 DELETE http://localhost:8080/api/articles/6
  ![image](https://github.com/kyudori/Spring-Bulletin-Board/assets/57388014/26eea4be-53ac-47d2-a322-6c1fd3966c92)
  ![image](https://github.com/kyudori/Spring-Bulletin-Board/assets/57388014/8de187aa-230d-4738-98e6-9802caa17fd5)
- 다른 ID로 삭제 시, false
  ![image](https://github.com/kyudori/Spring-Bulletin-Board/assets/57388014/3dd1f44f-6255-40ea-8e31-7b4ff0bf4f2f)
5. 게시판 수정 http://localhost:8080/api/articles/7
  ![image](https://github.com/kyudori/Spring-Bulletin-Board/assets/57388014/d737e191-d86f-44e3-ab79-5dae54994356)
  ![image](https://github.com/kyudori/Spring-Bulletin-Board/assets/57388014/c5b3c2ec-7453-40bd-8cf8-4e3bda22df08)
  ![image](https://github.com/kyudori/Spring-Bulletin-Board/assets/57388014/13074d22-9f9d-4064-89f6-04298dd670c0)
- 다른 ID로 수정 시, 수정 불가
  ![image](https://github.com/kyudori/Spring-Bulletin-Board/assets/57388014/55e5b532-71ca-4940-8eb5-1c9fcefbbf9b)
7. 내가 작성한 글 GET, http://localhost:8080/api/articles/myarticles
  ![image](https://github.com/kyudori/Spring-Bulletin-Board/assets/57388014/652175ef-0ab6-46a6-ac45-79936f7afd63)
