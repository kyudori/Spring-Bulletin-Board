package com.example.firstproject.dto;

import com.example.firstproject.entity.Comment;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommentDto {
    private Long id;
    private Long articleId;
    private String body;
    private String nickname; // Add nickname field

    public static CommentDto createCommentDto(Comment comment, String nickname) {
        return new CommentDto(
                comment.getId(),
                comment.getArticle().getId(),
                comment.getBody(),
                nickname // Assign nickname
        );
    }
}
