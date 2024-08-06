package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberDto;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import com.example.firstproject.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginForm() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "auth/register";
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody MemberDto memberDto) {
        if (memberRepository.findByEmail(memberDto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email is already in use");
        }
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword())); // 패스워드 암호화
        Member member = memberDto.toEntity();
        member.setProvider("spring"); // 일반 회원가입은 spring으로 설정
        member.setRole("ROLE_USER"); // 기본 역할 설정
        memberRepository.save(member);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.get("email"), credentials.get("password")));
        // 사용자를 인증

        String accessToken = jwtTokenProvider.createAccessToken(authentication);
        String refreshToken = jwtTokenProvider.createRefreshToken(authentication);
        //토큰 발급 후 저장

        return ResponseEntity.ok(Map.of("accessToken", accessToken, "refreshToken", refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        //리프레시 토큰을 검증
        if (jwtTokenProvider.validateToken(refreshToken)) {
            String username = jwtTokenProvider.getUsername(refreshToken);
            UserDetails userDetails = (UserDetails) jwtTokenProvider.getAuthentication(refreshToken).getPrincipal();
            String newAccessToken = jwtTokenProvider.createAccessToken(new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()));

            //새 토큰 발급
            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
