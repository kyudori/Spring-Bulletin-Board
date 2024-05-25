package com.example.firstproject.apicontroller;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentApiController {
    @Autowired
    private CommentService commentService;

    // 1. 댓글 조회
    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId) {
        // 서비스에게 작업 위임
        List<CommentDto> dtos = commentService.comments(articleId);

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    // 2. 댓글 생성
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId, @RequestBody CommentDto dto) {
        // 서비스에게 위임
        CommentDto createdDto = commentService.create(articleId, dto);

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(createdDto);
    }

    // 3. 댓글 수정
    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id, @RequestBody CommentDto dto) {
        // 서비스에게 위임
        CommentDto updatedDto = commentService.update(id, dto);

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }

    // 4. 댓글 삭제
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long id) {
        // 서비스에게 위임
        CommentDto deletedDto = commentService.delete(id);

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(deletedDto);
    }

    // 작성자 확인
    @GetMapping("api/comments/check/{id}")
    public ResponseEntity<Boolean> checkMyComment(@PathVariable Long id){
        String username = "kyudori"; //이름 임시로 고정
        boolean check = commentService.checkMyComment(id, username);
        return ResponseEntity.status(HttpStatus.OK).body(check);
    }

    // 본인이 작성한 댓글 모두 보기
    @GetMapping("api/comments/mycomments")
    public ResponseEntity<List<Comment>> showMyComment(){
        String username = "kyudori"; //이름 임시로 고정

        List<Comment> myComment = commentService.showMyComment(username);

        return (myComment != null) ?
                ResponseEntity.status(HttpStatus.OK).body(myComment):
                ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
