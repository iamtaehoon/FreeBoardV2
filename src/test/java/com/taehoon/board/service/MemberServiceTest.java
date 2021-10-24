package com.taehoon.board.service;

import com.taehoon.board.domain.Comment;
import com.taehoon.board.domain.Gender;
import com.taehoon.board.domain.Member;
import com.taehoon.board.domain.Post;
import com.taehoon.board.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() {

        //given
        Member member1 = new Member("user1", "password*123", "k123wl@naver.com", "010-0000-0000", Gender.MAN, LocalDateTime.now());
        
        //when
        Long saveId = memberService.joinMember(member1);
        
        //then
        Member findMember = memberRepository.findOne(saveId);

        assertThat(member1.getBirth()).isEqualTo(findMember.getBirth());
        assertThat(member1.getPassword()).isEqualTo(findMember.getPassword());
        assertThat(member1.getUserId()).isEqualTo(findMember.getUserId());
        assertThat(member1.getPhoneNum()).isEqualTo(findMember.getPhoneNum());
    }

    @Test
    public void 예외_회원가입_동일_유저아이디() {
        //given
        Member member1 = new Member("user1", "password*123", "a123wl@naver.com", "010-0000-0000", Gender.MAN, LocalDateTime.now());
        Member member2 = new Member("user1", "password*1234", "b123wl@naver.com", "010-0000-0001", Gender.MAN, LocalDateTime.now());

        memberService.joinMember(member1);

        //when
        //then
        assertThrows(IllegalArgumentException.class,()->{
            Long member2Id = memberService.joinMember(member2);
        });

    }

    @Test
    public void 예외_회원가입_동일_이메일() {
        //given
        Member member1 = new Member("user1", "password*123", "a123wl@naver.com", "010-0000-0000", Gender.MAN, LocalDateTime.now());
        Member member2 = new Member("user2", "password*1234", "a123wl@naver.com", "010-0000-0001", Gender.MAN, LocalDateTime.now());

        memberService.joinMember(member1);

        //when
        //then
        assertThrows(IllegalArgumentException.class,()->{
            Long member2Id = memberService.joinMember(member2);
        });

    }

    //memberId가 없는 경우도 체크해줘야하나? 일단 keep

    //userId가 없는 경우도 체크???

    @Test
    void memberId로_회원_조회() {
        //given
        Member member1 = new Member("user1", "password*123", "a123wl@naver.com", "010-0000-0000", Gender.MAN, LocalDateTime.now());
        Member member2 = new Member("user2", "password*1234", "b123wl@naver.com", "010-0000-0001", Gender.MAN, LocalDateTime.now());
        Member member3 = new Member("user3", "password*12345", "c123wl@naver.com", "010-0000-0002", Gender.WOMAN, LocalDateTime.now());

        Long member1Id = memberService.joinMember(member1);
        Long member2Id = memberService.joinMember(member2);
        Long member3Id = memberService.joinMember(member3);

        //when
        Member findMember2 = memberService.findMemberById(member2Id);

        //then
        assertThat(findMember2.getUserId()).isEqualTo(member2.getUserId());
        assertThat(findMember2.getEmail()).isEqualTo(member2.getEmail());
        assertThat(findMember2.getPhoneNum()).isEqualTo(member2.getPhoneNum());

    }

//    //이런 상황이 나올리가 없지 않음? 그래서 안해서 될듯.
//    @Test
//    public void 예외_회원_조회_DB_memberId_없음() {
//        //given
//
//        //when
//        Member findMember = memberService.findMemberById(1L);
//        //then
//    }

    @Test
    void 전체_회원_조회() {
        //given
        Member member1 = new Member("user1", "password*123", "a123wl@naver.com", "010-0000-0000", Gender.MAN, LocalDateTime.now());
        Member member2 = new Member("user2", "password*1234", "b123wl@naver.com", "010-0000-0001", Gender.MAN, LocalDateTime.now());
        Member member3 = new Member("user3", "password*12345", "c123wl@naver.com", "010-0000-0002", Gender.WOMAN, LocalDateTime.now());

        Long member1Id = memberService.joinMember(member1);
        Long member2Id = memberService.joinMember(member2);
        Long member3Id = memberService.joinMember(member3);

        //when
        List<Member> allMembers = memberService.findAllMembers();

        //then
        assertThat(allMembers.size()).isEqualTo(3);
    }

    @Test
    public void 유저아이디로_회원_조회(){
        //given
        Member member1 = new Member("user1", "password*123", "a123wl@naver.com", "010-0000-0000", Gender.MAN, LocalDateTime.now());
        Member member2 = new Member("user2", "password*1234", "b123wl@naver.com", "010-0000-0001", Gender.MAN, LocalDateTime.now());
        memberService.joinMember(member1);
        memberService.joinMember(member2);

        //when
        Member findMember = memberService.findMemberByUserId(member2.getUserId()).get(0);

        //then
        assertThat(findMember.getUserId()).isEqualTo(member2.getUserId());
        assertThat(findMember.getPhoneNum()).isEqualTo(member2.getPhoneNum());
    }

    @Test
    public void 예외_회원_조회_DB_유저아이디_없음(){
        //given

        //when
        //then
        assertThrows(IllegalArgumentException.class,() -> {
            List<Member> findMemberContainer = memberService.findMemberByUserId("none");
        });


    }


    @Test
    void 이메일_중복_검사() {
        //given
        Member member1 = new Member("user1", "password*123", "sameemail@naver.com", "010-0000-0000", Gender.MAN, LocalDateTime.now());
        memberService.joinMember(member1);

        //when
        //then
//        memberService.duplicateEmail("sameemail@naver.com");
        assertThrows(IllegalArgumentException.class, () -> {
            memberService.duplicateEmail("sameemail@naver.com");
        });

    }

    @Test
    void 유저아이디_중복_검사() {
        //given
        Member member1 = new Member("sameid", "password*123", "sameemail@naver.com", "010-0000-0000", Gender.MAN, LocalDateTime.now());
        memberService.joinMember(member1);

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            memberService.duplicateUserId("sameid");
        });

    }
}