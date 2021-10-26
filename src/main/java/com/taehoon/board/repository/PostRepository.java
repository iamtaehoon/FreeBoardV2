package com.taehoon.board.repository;

import com.taehoon.board.domain.Post;
import com.taehoon.board.web.dto.PostModifyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public void save(Post post) {
        em.persist(post);
    }

    public Post findOne(Long id) {
        return em.find(Post.class, id);
    }

    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    // Service에서 기존 Post에 수정값들을 넣어주고. 리포지토리는 그냥 그걸 더티체킹만 해주자.

    // 많은 경우들을 찾아봤는데, 이 경우에는 DTO를 서비스계층까지 넘긴다. 나도 관례에 따라가자. dto 여기까지 안넘어오면 id 건들어야 한다.
    // id 안건들고 하려니까 머리쥐난다.
    // 레포지토리에서는 엔티티.toString을 쓰는데 이게 뭘까.
    //

    public Post updateOne(String title, String content,Long postId) { // 걍 title, content 그대로 받아버릴까. 성질나
        // 더티체킹해줘서 알아서 쿼리를 쏴줄듯. 안되면 dto를 받는 것도 고려해보자..
        //return post 안되네. 분명 id도 같은데.. 그렇다면 내가 직접 update 쿼리를 만들어서 날려주자.
        //안되는 이유는 아마 바꾼 값은 영속성 컨텍스트에서 관리하는 애가 아니기 떄문인듯.
        Post post = em.find(Post.class, postId);

        return post.modifyPost(title, content);
    }

    public void deleteOne(Post post) { // void가 맞나? Long타입이라도 반환해 줘야 하려나. 만약 삭제하다 오류가 생겼으면 어떻게 처리해야하지
        em.remove(post);
    }

    //페이징해서 보여주는건 어떻게 하지?
    //컨트롤러 만들고 만들자.


}
