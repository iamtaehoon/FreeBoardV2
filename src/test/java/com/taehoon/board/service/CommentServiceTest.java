package com.taehoon.board.service;

import com.taehoon.board.domain.Comment;
import com.taehoon.board.domain.Gender;
import com.taehoon.board.domain.Member;
import com.taehoon.board.domain.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest // 이거 없이 순수 자바 코드로 어떻게 테스트를하지?
@Transactional
class CommentServiceTest {
    @Autowired CommentService commentService;
    @Autowired PostService postService;
    @Autowired MemberService memberService;

    @Test
    @Rollback
    public void 댓글_등록() throws Exception {
        //given
        Member member1 = new Member("user1", "password*123", "a123wl@naver.com", "010-0000-0000", Gender.MAN, LocalDateTime.now());
        Long findMember1Id = memberService.joinMember(member1);
        Member findMember1 = memberService.findMemberById(findMember1Id);

        Post post1 = new Post("제목1", "내욘내용내용ㅇㅇㅇㅇㅇ1", findMember1); //컨트롤러에서 객체를 호출해서 가져옴.
        Long findPost1Id = postService.registerPost(post1);
        Post findPost1 = postService.findPost(findPost1Id);


        Comment comment1 = new Comment("댓글입니다 1", 0, 0, 0, findMember1, findPost1);

        //when
        Long commentId1 = commentService.registerComment(comment1);

        //then
        Comment comment = commentService.findComment(commentId1);
        assertThat(comment.getContent()).isEqualTo(comment1.getContent());
        assertThat(comment.getCommentClass()).isEqualTo(comment1.getCommentClass());
    }

    @Test
    @Rollback(value = false)
    public void 댓글_수정() throws Exception {
        //given
        Member member1 = new Member("user1", "password*123", "a123wl@naver.com", "010-0000-0000", Gender.MAN, LocalDateTime.now());
        Long findMember1Id = memberService.joinMember(member1);
        Member findMember1 = memberService.findMemberById(findMember1Id);

        Post post1 = new Post("제목1", "내욘내용내용ㅇㅇㅇㅇㅇ1", findMember1); //컨트롤러에서 객체를 호출해서 가져옴.
        Long findPost1Id = postService.registerPost(post1);
        Post findPost1 = postService.findPost(findPost1Id);

        Comment comment1 = new Comment("댓글입니다 1", 0, 0, 0, findMember1, findPost1);
        Long commentId1 = commentService.registerComment(comment1);

        //방법 1 : service의 updateComment를 호출해서 사용.
        //when
        Comment commentUsingService = commentService.findComment(commentId1);
        Comment newComment = commentService.updateComment("수정된 내용입니다.", commentId1);
        //then
        assertThat(newComment.getContent()).isEqualTo("수정된 내용입니다.");


        // 방법 2: 나는 이게 맞는거같은데 // 도메인 모델에 있는 modifyContent로 바꿔도 영속성 컨텍스트가 사용되는거같음. findComment하면 어차피 영속성에서 관리한다 아님?
//        //when
//        Comment commentUsingDomainMethod = commentService.findComment(commentId1);
//        commentUsingDomainMethod.modifyContent("수정된 내용입니다.");
//
//        //then
//        Comment newComment = commentService.findComment(commentId1);
//        assertThat(newComment.getContent()).isEqualTo("수정된 내용입니다.");



    }

    @Test
    public void 댓글_삭제() throws Exception {
        //given
        Member member1 = new Member("user1", "password*123", "a123wl@naver.com", "010-0000-0000", Gender.MAN, LocalDateTime.now());
        Long findMember1Id = memberService.joinMember(member1);
        Member findMember1 = memberService.findMemberById(findMember1Id);

        Post post1 = new Post("제목1", "내욘내용내용ㅇㅇㅇㅇㅇ1", findMember1); //컨트롤러에서 객체를 호출해서 가져옴.
        Long findPost1Id = postService.registerPost(post1);
        Post findPost1 = postService.findPost(findPost1Id);

        Comment comment1 = new Comment("댓글입니다 1", 0, 0, 0, findMember1, findPost1);
        Long commentId1 = commentService.registerComment(comment1);

        //when
        commentService.deleteComment(commentId1);

        //then
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            commentService.findComment(commentId1);});
    }

    @Test
    public void 댓글_전체_조회() throws Exception {
        //given
        Member member1 = new Member("user1", "password*123", "a123wl@naver.com", "010-0000-0000", Gender.MAN, LocalDateTime.now());
        Long findMember1Id = memberService.joinMember(member1);
        Member findMember1 = memberService.findMemberById(findMember1Id);

        Post post1 = new Post("제목1", "내욘내용내용ㅇㅇㅇㅇㅇ1", findMember1); //컨트롤러에서 객체를 호출해서 가져옴.
        Long findPost1Id = postService.registerPost(post1);
        Post findPost1 = postService.findPost(findPost1Id);

        Comment comment1 = new Comment("댓글입니다 1", 0, 0, 0, findMember1, findPost1);
        Long commentId1 = commentService.registerComment(comment1);

        Comment comment2 = new Comment("댓글1 대댓글 - 댓글입니다 2", 0, 1, 1, findMember1, findPost1);
        Long commentId2 = commentService.registerComment(comment2);

        Comment comment3 = new Comment("댓글입니다 3", 1, 0, 2, findMember1, findPost1);
        Long commentId3 = commentService.registerComment(comment3);

        //when
        List<Comment> allComments = commentService.findAllComments();
        //then
        assertThat(allComments.size()).isEqualTo(3);
    }

    @Test
    public void 댓글_게시물단위_조회() throws Exception {
        //given
        Member member1 = new Member("user1", "password*123", "a123wl@naver.com", "010-0000-0000", Gender.MAN, LocalDateTime.now());
        Long findMember1Id = memberService.joinMember(member1);
        Member findMember1 = memberService.findMemberById(findMember1Id);

        Post post1 = new Post("제목1", "내욘내용내용ㅇㅇㅇㅇㅇ1", findMember1); //컨트롤러에서 객체를 호출해서 가져옴.
        Long findPost1Id = postService.registerPost(post1);
        Post findPost1 = postService.findPost(findPost1Id);

        Post post2 = new Post("제목2", "내욘내용내용ㅇㅇㅇㅇㅇ2", findMember1); //컨트롤러에서 객체를 호출해서 가져옴.
        Long findPost2Id = postService.registerPost(post2);
        Post findPost2 = postService.findPost(findPost2Id);

        Comment comment1 = new Comment("댓글입니다 1", 0, 0, 0, findMember1, findPost1);
        Long commentId1 = commentService.registerComment(comment1);

        Comment comment2 = new Comment("댓글1 대댓글 - 댓글입니다 2", 0, 1, 1, findMember1, findPost1);
        Long commentId2 = commentService.registerComment(comment2);

        Comment comment3 = new Comment("댓글입니다 3", 1, 0, 2, findMember1, findPost1);
        Long commentId3 = commentService.registerComment(comment3);

        Comment post2Comment1 = new Comment("댓글입니다 1", 0, 0, 0, findMember1, findPost2);
        Long post2CommentId1 = commentService.registerComment(post2Comment1);
        //이런애들은 변수명을 post2_comment1 이런식으로 써도 되나? 아니면 걍 이름을 바꾸는 게 낫나? 되기야 하지만 회사 내에서 규칙이 있을거니까.

        Comment post2Comment2 = new Comment("댓글1 대댓글 - 댓글입니다 2", 0, 1, 1, findMember1, findPost2);
        Long post2Comment2Id = commentService.registerComment(post2Comment2);


        //when
        List<Comment> allCommentsOnPost1 = commentService.findAllCommentsOnPost(findPost1);
        List<Comment> allCommentsOnPost2 = commentService.findAllCommentsOnPost(findPost2);

        //then
        assertThat(allCommentsOnPost1.size()).isEqualTo(3);
        assertThat(allCommentsOnPost2.size()).isEqualTo(2);
    }
}