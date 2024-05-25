package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleDto {
    private String title;
    private String content;

    public Article toEntity(Member member) {
        Article article = new Article();
        article.setTitle(this.title);
        article.setContent(this.content);
        article.setMember(member);
        return article;
    }
}
