package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    //    @Override
    //    ArrayList<Article> findAll();
    Page<Article> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
//    Page<Article> findByWriter(String username, Pageable pageable);
    Page<Article> findByMember(Member member, Pageable pageable);
    Page<Article> findByTitleContaining(String keyword, Pageable pageable);
}
