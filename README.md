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
-다른 ID로 수정 시, 수정 불가
  ![image](https://github.com/kyudori/Spring-Bulletin-Board/assets/57388014/55e5b532-71ca-4940-8eb5-1c9fcefbbf9b)
7. 내가 작성한 글 GET, http://localhost:8080/api/articles/myarticles
  ![image](https://github.com/kyudori/Spring-Bulletin-Board/assets/57388014/652175ef-0ab6-46a6-ac45-79936f7afd63)
