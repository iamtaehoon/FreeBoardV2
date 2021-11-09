package com.taehoon.board.web.controller;

import com.taehoon.board.domain.Comment;
import com.taehoon.board.domain.Member;
import com.taehoon.board.service.CommentService;
import com.taehoon.board.web.SessionConst;
import com.taehoon.board.web.dto.PageDTO;
import com.taehoon.board.domain.Post;
import com.taehoon.board.service.PostService;
import com.taehoon.board.web.dto.PostCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class PostController {
    private final PostService postService;

    private final CommentService commentService;

    @GetMapping("/")
    public String postList(Model model, @RequestParam(defaultValue = "1") Integer pagingNum, HttpServletRequest request) {
        System.out.println("postList에 들어옴 : pagingNum : " + pagingNum);

        if (pagingNum <= 0) {
            pagingNum += 6;
        }
        // 페이징 디폴트 쿼리 : 1
        // 쿼리에 따라서 페이징 : 버튼 클릭하면 그 숫자 -> 쿼리로 날리기
        // << 누르면 1, >>누르면 가장 큰 수
        // < 누르면 -6 > 누르면 + 6

        List<Post> postsUsingPaging = postService.findPostsUsingPaging(pagingNum);
        model.addAttribute("postsUsingPaging", postsUsingPaging);

        //아 페이징 어렵다. 프래그먼트로 구현해야 하나?
        //인터넷을 찾아보자. 직접 구현하려니 막히는 부분이 있음.
        // 아 이것도 자바스크립트 써가면서 해야되네. 그냥 일단 여기까지만 단순하게 구현.
        PageDTO pageDTO = new PageDTO(pagingNum);
        model.addAttribute("pageDTO",pageDTO);
        //26~40번째 줄까지 메서드로 따로 만들기. 가독성이 좋지 않음.

        HttpSession session = request.getSession(false);
        if (session == null) {
            return "post/postList";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER); // 회원을 세션에서 찾아봄.
        if (loginMember == null) {
            return "post/postList";
        }

        model.addAttribute("member", loginMember);
        return "post/postListAfterLogin";
    }

    @GetMapping("/post") // post-> 로그인 한 유저 : 댓글 작성 가능 / 로그인 안한 유저 : 지금 만든 기능만
    // 본인인 경우 -> 글 수정 가능. (이건 본인임을 인증하면 되지 여기서)
    public String onePost(Model model, @RequestParam("id") Long postId, HttpServletRequest request) {
        System.out.println("postId = " + postId);
        Post post = postService.findPost(postId);
        model.addAttribute("post", post);
        System.out.println("post = " + post);

        List<Comment> comments = post.getComments();
        model.addAttribute("comments", comments);


        HttpSession session = request.getSession(false);
        if (session == null) {
            return "post/post";
        }

        //로그인이 되어있을 경우
        return "post/postAfterLogin";
    }

    @PostMapping("/comment") //별개의 컨트롤러를 만들어야 하나 싶기도 한데, 그냥 여기다가 만들기 일단.
    public String comment(@RequestParam String content, @RequestParam Long postId,
                          @RequestParam Integer commentCount, HttpServletRequest request) {

        System.out.println(content+ Long.toString(postId)+ Integer.toString(commentCount));
        // 내일 보충할 거. 현재 댓글 서비스 안되는거 고치기. => 내가 db에 임의로 넣어준 값이 id 무결성을 해치고 있었음. 해결

        Post post = postService.findPost(postId);
        // postId를 그대로 받는게 더 나은 방법인듯? 근데 멤버객체도 그대로 쓰는거보면 Post객체 가져와서 이거처럼 쓰는게 맞는건가?

        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        Comment comment = new Comment(content,commentCount,0,0,member,post);
        commentService.registerComment(comment);

        return "redirect:/post?id=" + postId;
    }


    @GetMapping("/write") //로그인한 사람만 접근이 가능하게.
    public String postWriteForm(Model model) {
        PostCreateDto postCreateDto = new PostCreateDto();
        model.addAttribute("postCreateDto", postCreateDto);

        return "write/writeForm";
    }
}
