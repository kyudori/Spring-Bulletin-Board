//package com.example.firstproject.controller;
//
//import com.example.firstproject.dto.ArticleDto;
//import com.example.firstproject.dto.CommentDto;
//import com.example.firstproject.entity.Article;
//import com.example.firstproject.repository.ArticleRepository;
//import com.example.firstproject.service.ArticleService;
//import com.example.firstproject.service.CommentService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.List;
//import java.util.Optional;
//
//@Slf4j
//@Controller
//public class ArticleController {
//    @Autowired
//    private ArticleRepository articleRepository;
//
//    @Autowired
//    private CommentService commentService; //서비스 객체 주입
//
//    @Autowired
//    private ArticleService articleService;
//
//    @GetMapping("/articles/new")
//    public String newArticleForm() {
//        return "articles/new";
//    }
//
//    @PostMapping("/articles/create")
//    public String createArticle(ArticleDto form) {
//        log.info(form.toString());
//        // System.out.println(form.toString());
//
//        // 1. DTO를 엔티티로 변환
//        Article article = form.toEntity();
//        log.info(article.toString());
//        // System.out.println(article.toString());
//
//        // 2. 리파지터리로 엔티티를 DB에 저장
//        Article saved = articleRepository.save(article);
//        log.info(saved.toString());
//        // System.out.println(saved.toString());
//
//        return "redirect:/articles/" + saved.getId();
//    }
//
//    @GetMapping("/articles/{id}") // 데이터 조회 요청 접수
//    public String show(@PathVariable Long id, Model model) { // 매개변수로 id 받아오기
//        log.info("id = " + id); // id를 잘 받았는지 확인하는 로그 찍기
//
//        // 1. id를 조회하여 데이터 가져오기
//        Article articleEntity = articleRepository.findById(id).orElse(null);
//        List<CommentDto> commentDtos = commentService.comments(id);
//
//        // 2. 모델에 데이터 등록하기
//        model.addAttribute("article", articleEntity);
//        model.addAttribute("commentDtos", commentDtos); //댓글 목록 모델 등록
//
//        // 3. 뷰 페이지 반환하기
//        return "articles/show";
//    }
//
//    @GetMapping("/articles")
//    public String index(Model model, @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
//        model.addAttribute("articleList", articleService.getArticleList(pageable));
//        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
//        model.addAttribute("next", pageable.next().getPageNumber());
//        model.addAttribute("check", articleService.getSizeCheck(pageable));
//        return "articles/index";
//    }
//
//    @GetMapping("/articles/search")
//    public String search(Model model, @RequestParam(name = "keyword") String keyword, @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
//        Page<Article> articles = articleService.search(keyword, keyword, pageable);
//        model.addAttribute("articleList", articles);  // 검색 결과를 모델에 추가
//        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
//        model.addAttribute("next", pageable.next().getPageNumber());
//        model.addAttribute("check", articles.hasNext());
//        return "articles/index";
//    }
//
//    @GetMapping("/articles/{id}/edit")
//    public String edit(@PathVariable Long id, Model model) {
//        // 수정할 데이터 가져오기
//        Article articleEntity = articleRepository.findById(id).orElse(null);
//
//        // 모델에 데이터 등록하기
//        model.addAttribute("article", articleEntity);
//
//        // 뷰 페이지 설정하기
//        return "articles/edit";
//    }
//
//    @PostMapping("/articles/update")
//    public String update(ArticleDto form) {
//        log.info(form.toString());
//
//        Optional<Article> existingArticleOpt = articleRepository.findById(form.getId());
//
//        if (existingArticleOpt.isPresent()) {
//            Article existingArticle = existingArticleOpt.get();
//
//            // DTO에서 받은 정보로 기존 기사 업데이트 (writer 제외)
//            existingArticle.setTitle(form.getTitle());
//            existingArticle.setContent(form.getContent());
//
//            articleRepository.save(existingArticle);
//            log.info("Updated article: {}", existingArticle);
//
//            return "redirect:/articles/" + existingArticle.getId();
//        } else {
//            log.warn("Attempted to update non-existent article with id {}", form.getId());
//            return "redirect:/articles";
//        }
//    }
//
//    @GetMapping("/articles/{id}/delete")
//    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
//        log.info("삭제 요청이 들어왔습니다!!");
//
//        // 1. 삭제할 대상 가져오기
//        Article target = articleRepository.findById(id).orElse(null);
//        log.info(target.toString());
//
//        // 2. 대상 엔티티 삭제하기
//        if (target != null) {
//            articleRepository.delete(target);
//            rttr.addFlashAttribute("msg", "삭제됐습니다!");
//        }
//
//        // 3. 결과 페이지로 리다이렉트하기
//        return "redirect:/articles";
//    }
//}
