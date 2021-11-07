package com.taehoon.board.service;

import com.taehoon.board.domain.Member;
import com.taehoon.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     * @param member
     * 멤버객체가 넘어옴. userId,email 중복이 있나를 검사한 뒤
     * 이상 없으면 repository에 저장.
     */
    public Long joinMember(Member member) {

        checkMemberDuplication(member); // 중복되면 illegal예외 터침.
        memberRepository.save(member);
        return member.getId();
    }

    public Long login(String userId, String password) {
        List<Member> memberByUserId = findMemberByUserId(userId);

        if (!memberByUserId.isEmpty()) {
            if (password.equals(memberByUserId.get(0).getPassword())) {
                return memberByUserId.get(0).getId();
            }
        }
        throw new IllegalArgumentException("아이디 또는 비밀번호가 잘못되었습니다");
    }

    /**
     * 서버에서 사용하는 id로 회원 찾기
     * @param memberId (서버에서 사용하는 값. uuid를 사용해서 만들어야함.)
     * @return 컨트롤러에서 사용할거니까 Member객체를 반환.
     */
    public Member findMemberById(Long memberId) {
        //memberId가 null로 넘어온경우나, db에 없는 경우 체크해줘야하지 않을까?
        //체크를 하긴 해야할거같은데, 어떻게 체크를 해줘야 할지 감도 안잡히네. 일단 keep
        //서버에서 사용하는 값이니까 필요 없을지도..? 조작이 가능한 부분이 아니지 않나?
        // 물론 정규 서버라면 테스트를 하긴 해야할듯. 근데 나는 임시적인 거니까 하지 말자.
        return memberRepository.findOne(memberId);
    }

    /**
     * 전체 회원 찾기
     * 필요한 기능인지 모르겠음. 그런데 컨트롤러에서 전체조회할 일이 있을거같아 일단 만들어둠.
     * 설계가 이런 부분들에서 제대로 되지 않았다는 의미 ㅜ
     * @return 가입된 전체 회원
     */
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    /**
     * member가 가입할때 사용한 userid로 회원 찾기
     * @param userId
     */
    public List<Member> findMemberByUserId(String userId) {

        List<Member> findMemberContainer = memberRepository.findOneByUserId(userId);
        if (findMemberContainer.isEmpty()) { //유저아이디로 가입된 회원이 없는 경우
            throw new IllegalArgumentException("해당 아이디로 가입된 회원을 찾을 수 없습니다.");
        }
        return findMemberContainer;
    }

    private void checkMemberDuplication(Member member) {
        duplicateUserId(member.getUserId());
        duplicateEmail(member.getEmail());
    }

    /**
     * 중복된 이메일이 있나 검사.
     * 회원가입할 때 버튼으로 이메일 중복을 검사해줌.
     * @param email
     */
    public void duplicateEmail(String email) {
        List<Member> sameEmailMember = memberRepository.findOneByEmail(email);

        if (!sameEmailMember.isEmpty()) {
            //중복 아이디가 있다는 거임. 오류 반환
            throw new IllegalArgumentException("이메일이 중복되었습니다.");
        }
    }

    /**
     * 중복된 userId가 있나 검사.
     * 회원가입할 때 버튼으로 중복을 검사해줌.
     * @param userId
     */
    public void duplicateUserId(String userId) {
        List<Member> sameUserIdMember = memberRepository.findOneByUserId(userId);

        if (!sameUserIdMember.isEmpty()) {
            //중복 아이디가 있다는 거임. 오류 반환
            throw new IllegalArgumentException("아이디가 중복되었습니다.");
        }
    }
}
