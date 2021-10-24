package com.taehoon.board.repository;

import com.taehoon.board.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em; //생성자 주입 방식으로 엔티티매니저 주입

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long memberId) { //멤버 조회는 다른곳에서 멤버를 쓰려고 찾는거겠지?
        return em.find(Member.class, memberId);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m",Member.class)
                .getResultList();
    }

    public List<Member> findOneByUserId(String userId) {
        String jpql = "select m from Member m where m.userId = :userId";

        return em.createQuery(jpql, Member.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    //아이디를 잃어버린 경우, 이메일로 찾으려고. => 전화번호로도 가능한데 일단 이메일만 먼저 구현.
    public List<Member> findOneByEmail(String email) {
        String jpql = "select m from Member m where m.email = :email";

        return em.createQuery(jpql, Member.class)
                .setParameter("email", email)
                .getResultList();
    }


}
