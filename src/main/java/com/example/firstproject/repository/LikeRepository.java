package com.example.firstproject.repository;

import com.example.firstproject.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Like findByArticleIdAndMemberId(Long articleId, Long memberId);
}
