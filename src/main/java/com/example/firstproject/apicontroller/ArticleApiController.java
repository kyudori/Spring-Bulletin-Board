package com.example.firstproject.apicontroller;

import com.example.firstproject.dto.ArticleDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {
    @Autowired
    private ArticleService articleService;

    // GET
    @GetMapping("/api/articles/all")
    public List<Article> index() {
        return articleService.index();
    }

    // GET
    @GetMapping("/api/articles")
    public ResponseEntity<Page<Article>> index(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Article> articles = articleService.getArticles(pageable);
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) {
        return articleService.show(id);
    }

    // POST
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleDto dto) {
        Article created = articleService.create(dto);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleDto dto) {
        Article updated = articleService.update(id, dto);
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
        Article deleted = articleService.delete(id);
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // Search
    @GetMapping("/api/articles/search")
    public ResponseEntity<Page<Article>> search(@RequestParam(name = "keyword") String keyword,
                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Article> articles = articleService.search(keyword, keyword, pageable);
        return (articles != null) ?
                ResponseEntity.status(HttpStatus.OK).body(articles) :
                ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 내가 쓴 글 확인
    @GetMapping("/api/articles/check/{id}")
    public ResponseEntity<Boolean> checkMyArticle(@PathVariable Long id) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        boolean check = articleService.checkMyArticle(id, username);
        return ResponseEntity.status(HttpStatus.OK).body(check);
    }

    // 본인이 작성한 글 모두 보기
    @GetMapping("/api/articles/myarticles")
    public ResponseEntity<Page<Article>> showMyArticle(@RequestParam(value = "page", defaultValue = "0") int page,
                                                       @RequestParam(value = "size", defaultValue = "5") int size) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Pageable pageable = PageRequest.of(page, size);
        Page<Article> articles = articleService.printMyArticle(username, pageable);
        return (articles != null) ?
                ResponseEntity.status(HttpStatus.OK).body(articles) :
                ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
