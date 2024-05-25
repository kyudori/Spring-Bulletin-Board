package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@ToString
@Getter
@Setter
public class ArticleDto {
    private Long id;
    private String writer;
    private String title; // 제목을 받을 필드
    private String content; // 내용을 받을 필드
    private List<Comment> comments;
    private Member member; // Member 필드 추가

    public Article toEntity() {
        return new Article(id, writer, title, content, comments, member);
    }
}
