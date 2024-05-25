package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MemberRepository memberRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Page<Article> getArticles(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    public Page<Article> search(String keyword, String content, Pageable pageable) {
        return articleRepository.findByTitleContainingOrContentContaining(keyword, content, pageable);
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleDto dto) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));

        dto.setMember(member);
        Article article = dto.toEntity();
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleDto dto) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new RuntimeException("Article not found"));
        article.patch(dto.toEntity());
        return articleRepository.save(article);
    }

    public Article delete(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new RuntimeException("Article not found"));
        articleRepository.delete(article);
        return article;
    }

    public boolean checkMyArticle(Long id, String username) {
        Article article = articleRepository.findById(id).orElse(null);
        return article != null && article.getMember().getEmail().equals(username);
    }

    public Page<Article> printMyArticle(String username, Pageable pageable) {
        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        return articleRepository.findByMember(member, pageable);
    }

    public List<Article> createArticles(List<ArticleDto> dtos) {
        List<Article> articles = dtos.stream().map(ArticleDto::toEntity).toList();
        return articleRepository.saveAll(articles);
    }

    // 새로운 메서드 추가
    public Page<Article> getArticleList(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    public boolean getSizeCheck(Pageable pageable) {
        return articleRepository.findAll(pageable).getTotalElements() > 0;
    }
}
