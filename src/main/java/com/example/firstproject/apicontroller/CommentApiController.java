package com.example.firstproject.apicontroller;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentApiController {
    @Autowired
    private CommentService commentService;

    // 1. 댓글 조회
    @GetMapping("/api/articles/comments/{articleId}")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId) {
        List<CommentDto> dtos = commentService.comments(articleId);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    // 2. 댓글 생성
    @PostMapping("/api/articles/comments/{articleId}")
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId, @RequestBody CommentDto dto) {
        CommentDto createdDto = commentService.create(articleId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(createdDto);
    }

    // 3. 댓글 수정
    @PatchMapping("/api/articles/comments/{articleId}/{commentId}")
    public ResponseEntity<CommentDto> update(@PathVariable Long articleId, @PathVariable Long commentId, @RequestBody CommentDto dto) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        CommentDto updatedDto = commentService.update(articleId, commentId, dto, username);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }

    // 4. 댓글 삭제
    @DeleteMapping("/api/articles/comments/{articleId}/{commentId}")
    public ResponseEntity<Boolean> delete(@PathVariable Long articleId, @PathVariable Long commentId) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        boolean isDeleted = commentService.delete(articleId, commentId, username);
        return ResponseEntity.status(isDeleted ? HttpStatus.OK : HttpStatus.FORBIDDEN).body(isDeleted);
    }

    // 5. 본인이 작성한 댓글 모두 보기
    @GetMapping("/api/articles/comments/mycomments")
    public ResponseEntity<List<CommentDto>> showMyComment(){
        List<CommentDto> myComment = commentService.showMyComment();
        return ResponseEntity.status(HttpStatus.OK).body(myComment);
    }
}
