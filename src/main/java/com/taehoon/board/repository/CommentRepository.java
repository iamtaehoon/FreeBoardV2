package com.taehoon.board.repository;

import com.taehoon.board.domain.Comment;
import com.taehoon.board.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    public Comment findOne(Long id) {
        return em.find(Comment.class, id);
    }

    public List<Comment> findAll() {
        return em.createQuery("select c from Comment c", Comment.class)
                .getResultList();
    }

    public Comment updateOne(String content, Long commentId) {
        Comment comment = em.find(Comment.class, commentId);
        return comment.modifyContent(content);
    }

    public void deleteOne(Comment comment) {
        em.remove(comment);
    }

    public List<Comment> findAllOnPost(Post post) {
        //아! 밑에 c.post는 객체구나!
        return em.createQuery("select c from Comment c where c.post = :post", Comment.class)
                .setParameter("post",post)
                .getResultList();
    }


}
