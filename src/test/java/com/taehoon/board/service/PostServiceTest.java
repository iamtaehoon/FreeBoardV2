package com.taehoon.board.service;

import com.taehoon.board.domain.Gender;
import com.taehoon.board.domain.Member;
import com.taehoon.board.domain.Post;
import com.taehoon.board.web.dto.PostModifyDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    MemberService memberService;

    @Test
    public void 글_등록() throws Exception {
        //given
        Member member1 = new Member("user1", "password*123", "a123wl@naver.com", "010-0000-0000", Gender.MAN, LocalDateTime.now());
        Long memberId1 = memberService.joinMember(member1);
        Member findMember = memberService.findMemberById(memberId1);

        Post post1 = new Post("제목1", "내욘내용내용ㅇㅇㅇㅇㅇ1", findMember);

        //when
        Long postId1 = postService.registerPost(post1);

        //then
        Post post = postService.findPost(postId1);
        assertThat(post.getTitle()).isEqualTo(post1.getTitle());
        assertThat(post.getComments()).isEqualTo(post1.getComments());
        assertThat(post.getMember()).isEqualTo(post1.getMember());
    }

    @Test
    public void 글_수정() throws Exception {
        //given
        Member member1 = new Member("user1", "password*123", "a123wl@naver.com", "010-0000-0000", Gender.MAN, LocalDateTime.now());
        Long memberId1 = memberService.joinMember(member1);
        Member findMember = memberService.findMemberById(memberId1);

        Post post1 = new Post("제목1", "내욘내용내용ㅇㅇㅇㅇㅇ1", findMember);
        Long postId1 = postService.registerPost(post1);

        //when
        Post updatePost = postService.updatePost("바꾼제목", "바꾼내용", postId1);

        //then
        assertThat(updatePost.getTitle()).isSameAs("바꾼제목");
        assertThat(updatePost.getContent()).isSameAs("바꾼내용");

    }

    @Test
    public void 글_삭제() throws Exception {
        //given
        Member member1 = new Member("user1", "password*123", "a123wl@naver.com", "010-0000-0000", Gender.MAN, LocalDateTime.now());
        Long memberId1 = memberService.joinMember(member1);
        Member findMember = memberService.findMemberById(memberId1);

        Post post1 = new Post("제목1", "내욘내용내용ㅇㅇㅇㅇㅇ1", findMember);
        Long postId1 = postService.registerPost(post1);

        //when
        postService.deletePost(postId1);

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            postService.findPost(postId1);
        });

    }

    @Test
    public void 글_조회() throws Exception {
        //글 등록이랑 로직이 똑같음.
    }
}