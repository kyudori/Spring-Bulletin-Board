package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import com.example.firstproject.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MemberRepository memberRepository;

    public List<CommentDto> comments(Long articleId) {
        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(comment -> {
                    String nickname = comment.getMember().getNickname();
                    return CommentDto.createCommentDto(comment, nickname);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto create(Long articleId, CommentDto dto) {
        try {
            Article article = articleRepository.findById(articleId)
                    .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다."));

            String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            Member member = memberRepository.findByEmail(username)
                    .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 사용자 정보가 없습니다."));

            dto.setArticleId(article.getId());  // DTO에 올바른 articleId 설정
            Comment comment = Comment.createComment(dto, article, member);

            Comment created = commentRepository.save(comment);

            return CommentDto.createCommentDto(created, member.getNickname());
        } catch (Exception e) {
            log.error("댓글 생성 실패: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public CommentDto update(Long articleId, Long commentId, CommentDto dto, String username) {
        try {
            Comment target = commentRepository.findById(commentId)
                    .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없습니다."));

            if (!target.getMember().getEmail().equals(username)) {
                throw new SecurityException("댓글 수정 실패! 작성자가 아닙니다.");
            }

            target.patch(dto);

            Comment updated = commentRepository.save(target);

            return CommentDto.createCommentDto(updated, target.getMember().getNickname());
        } catch (Exception e) {
            log.error("댓글 수정 실패: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public boolean delete(Long articleId, Long commentId, String username) {
        try {
            Comment target = commentRepository.findById(commentId)
                    .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 대상이 없습니다."));

            if (!target.getMember().getEmail().equals(username)) {
                return false;
            }

            commentRepository.delete(target);
            return true;
        } catch (Exception e) {
            log.error("댓글 삭제 실패: {}", e.getMessage());
            return false;
        }
    }

    public List<CommentDto> showMyComment() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return commentRepository.findByMemberEmail(username)
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment, comment.getMember().getNickname()))
                .collect(Collectors.toList());
    }
}
