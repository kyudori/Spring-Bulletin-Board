package com.example.firstproject.security;

import com.example.firstproject.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, CustomUserDetailsService customUserDetailsService) {
        this.tokenProvider = tokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            //요청에서 JWT 토큰을 추출
            String jwt = getJwtFromRequest(request);

            //토큰의 유효성을 검증
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                //tokenProvider.getUsername(jwt)를 호출하여 토큰에서 사용자 이메일을 추출
                String userEmail = tokenProvider.getUsername(jwt);

                // customUserDetailsService.loadUserByUsername(userEmail)를 호출하여 사용자 정보를 로드
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);
                // UsernamePasswordAuthenticationToken 객체를 생성하여 인증
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                // 현재 보안 컨텍스트에 인증 정보를 설정
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //Principal: 주로 사용자의 식별자 (예: 사용자명)
                //Credentials: 인증에 사용된 정보 (예: 비밀번호, 일반적으로 보안상의 이유로 null로 설정)
                //Authorities: 사용자의 권한 목록 (예: ROLE_USER, ROLE_ADMIN)
                //요청 정보를 설정

                SecurityContextHolder.getContext().setAuthentication(authentication);
                //현재 보안 컨텍스트에 Authentication 객체를 설정

                //SecurityContext는 Authentication 객체를 보유
                //SecurityContextHolder를 통해 SecurityContext 객체에 접근
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
