package com.example.firstproject.dto;

import com.example.firstproject.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
    private Long id;
    private String nickname;
    private String email;
    private String password;
    private String provider;
    private String role;

    public Member toEntity() {
        return new Member(id, nickname, email, password, provider, role);
    }
}
