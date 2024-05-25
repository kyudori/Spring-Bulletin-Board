package com.example.firstproject.apicontroller;

import com.example.firstproject.dto.LikeDto;
import com.example.firstproject.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeApiController {

    @Autowired
    private LikeService likeService;

    @PostMapping("api/likes/toggle")
    public ResponseEntity<?> toggleLike(@RequestBody LikeDto likeDto) {
        likeService.toggleLike(likeDto);
        return ResponseEntity.ok().build();
    }
}
