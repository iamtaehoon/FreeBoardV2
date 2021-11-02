package com.taehoon.board.web.controller;

import com.taehoon.board.domain.Post;
import com.taehoon.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class PostController {
    private final PostService postService;

    @GetMapping("/")
    public String postList(Model model, @RequestParam Integer pagingNum) {
        System.out.println("postList에 들어옴 : pagingNum : " + pagingNum);
        // 페이징 디폴트 쿼리 : 1
        // 쿼리에 따라서 페이징 : 버튼 클릭하면 그 숫자 -> 쿼리로 날리기
        // << 누르면 1, >>누르면 가장 큰 수
        // < 누르면 -6 > 누르면 + 6
        List<Post> postsUsingPaging = postService.findPostsUsingPaging(pagingNum);
        model.addAttribute("postsUsingPaging", postsUsingPaging);
        return "post/postList";
    }

    @GetMapping("/post")
    public String onePost(Model model, @RequestParam("id") Long postId) {
        System.out.println("postId = " + postId);
        Post post = postService.findPost(postId);
        model.addAttribute("post", post);
        System.out.println("post = " + post);
        return "post/post";
    }
}
