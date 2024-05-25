package com.example.firstproject.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LikeDto {
    private long id;
    private long memberId;
    private long articleId;
}
