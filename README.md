<전체 흐름>

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