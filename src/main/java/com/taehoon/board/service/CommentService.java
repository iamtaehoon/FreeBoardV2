package com.taehoon.board.service;

import com.taehoon.board.domain.Comment;
import com.taehoon.board.domain.Post;
import com.taehoon.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Long registerComment(Comment comment) {
        commentRepository.save(comment);
        return comment.getId();
    }

    //댓글 한개만 찾을 일이 있기는 한가? 서버 내에서 사용할듯
    public Comment findComment(Long commentId) {
        Comment comment = commentRepository.findOne(commentId);
        if (comment == null) { // 댓글 달라했던게 사라졌을수도 있지
            throw new IllegalArgumentException("해당 댓글이 존재하지 않습니다.");
        }
        return comment;
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteOne(commentRepository.findOne(commentId));
    }

    public Comment updateComment(String content, Long commentId) {
        return commentRepository.updateOne(content, commentId);
    }

    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    // 해당 Post에 달린 댓글 전체 조회
    public List<Comment> findAllCommentsOnPost(Post post) {
        return commentRepository.findAllOnPost(post);
    }
}
