package com.example.firstproject.service;

import com.example.firstproject.dto.LikeDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Like;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.LikeRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final EntityManager entityManager;

    public LikeService(LikeRepository likeRepository, EntityManager entityManager) {
        this.likeRepository = likeRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public void toggleLike(LikeDto likeDto) {
        Long articleId = likeDto.getArticleId();
        Long memberId = likeDto.getMemberId();
        Like like = likeRepository.findByArticleIdAndMemberId(articleId, memberId);
        if (like != null) {
            likeRepository.delete(like);
        } else {
            Article article = entityManager.getReference(Article.class, articleId);
            Member member = entityManager.getReference(Member.class, memberId);
            like = new Like();
            like.setArticle(article);
            like.setMember(member);
            likeRepository.save(like);
        }
    }
}
