package com.taehoon.board.service;

import com.taehoon.board.domain.Post;
import com.taehoon.board.repository.PostRepository;
import com.taehoon.board.web.dto.PostModifyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional //이건 각자 메서드 단위로 붙여주는게 좋음
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    //게시물 등록
    public Long registerPost(Post post) { //테스트 때문에라도 반환을 해줘야함.
//        Post post = postCreateDto.toEntity();//dto단계에서 valid 체크는 다 해줌. 그냥 저장만 하면 됨.
//        위 작업은 컨트롤러 단계에서 처리해줄거임.
        postRepository.save(post);
        return post.getId();
    }

    //조회
    public Post findPost(Long postId) {
        //없는 경우 체크 로직 필요. -> 없는 경우 redirect 해주면 되려나?
        //해당 게시물은 존재하지 않습니다. 이걸 하려면 예외를 터치면 되지?
        Post post = postRepository.findOne(postId);
        if (post == null) {
            throw new IllegalArgumentException("해당 게시물은 존재하지 않습니다.");
        }
        return post;
    }

    //삭제
    public void deletePost(Long postId) { // 멤버가 글 작성자가 아니면, 글을 삭제하면 안되니까. -> member의 uuid를 그거하면 되나?
        //그냥 만들면 될듯. 이 버튼 자체도 로그인된 본인한테만 보이게 만들거고, 인터셉터를 사용해 url 막아줄거.
        postRepository.deleteOne(postRepository.findOne(postId));
    }

    //수정
    public Post updatePost(String title, String content, Long postId) {
        return postRepository.updateOne(title, content, postId);
    }

    //페이지 보기. 페이징 이건 컨트롤러 만들고 만들자.
}
