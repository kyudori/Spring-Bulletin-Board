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
        //Spring Security의 SecurityContextHolder를 사용하여 현재 인증된 사용자의 정보를 가져옴
        //메서드에서 현재 인증된 사용자의 정보를 가져올 때 SecurityContextHolder.getContext().getAuthentication().getPrincipal()을 사용
        //게시글 작성자가 아니면 SecurityException을 발생시켜 수정/삭제를 방지
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));

        Article article = dto.toEntity(member);
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleDto dto, String username) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new RuntimeException("Article not found"));
        if (!article.getMember().getEmail().equals(username)) {
            throw new SecurityException("Article update failed! You are not the author.");
        }
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        return articleRepository.save(article);
    }

    public boolean delete(Long id, String username) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new RuntimeException("Article not found"));
        if (!article.getMember().getEmail().equals(username)) {
            return false;
        }
        articleRepository.delete(article);
        return true;
    }

    public Page<Article> printMyArticle(String username, Pageable pageable) {
        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        return articleRepository.findByMember(member, pageable);
    }

    public List<Article> createArticles(List<ArticleDto> dtos) {
        List<Article> articles = dtos.stream().map(dto -> {
            String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            Member member = memberRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
            return dto.toEntity(member);
        }).toList();
        return articleRepository.saveAll(articles);
    }

    public Page<Article> getArticleList(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    public boolean getSizeCheck(Pageable pageable) {
        return articleRepository.findAll(pageable).getTotalElements() > 0;
    }
}
