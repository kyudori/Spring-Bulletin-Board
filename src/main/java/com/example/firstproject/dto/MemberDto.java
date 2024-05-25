package com.example.firstproject.dto;

import com.example.firstproject.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
@Setter
public class MemberDto {
    private Long id;
    private String nickname;
    private String email;
    private String password;
    private String role = "USER";  // 기본 역할을 USER로 설정

    public Member toEntity() {
        return new Member(id, nickname, email, password, role);
    }
}
