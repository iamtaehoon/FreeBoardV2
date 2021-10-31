package com.taehoon.board.web.controller;

import com.taehoon.board.domain.Member;
import com.taehoon.board.service.MemberService;
import com.taehoon.board.web.dto.member.MemberCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/join")
    public String joinForm(Model model) {
        MemberCreateDto memberCreateDto = new MemberCreateDto();
        model.addAttribute("member", memberCreateDto);
        return "member/joinForm";
    }

    @PostMapping("/join") //메인페이지로 보내야지
    public String join(@ModelAttribute("member") @Valid MemberCreateDto memberCreateDto, BindingResult result) {
        System.out.println("memberCreateDto = " + memberCreateDto);
        if (result.hasErrors()) {
            return "member/joinForm";
        }

        return "redirect:/join";
    }

//    @GetMapping("check-id-duplicate")
//    public String check_id_duplicate(@RequestParam String userId, BindingResult bindingResult) {
//
//        List<Member> memberByUserId = memberService.findMemberByUserId(userId);
//        if (memberByUserId.isEmpty()) {
//            //올바른로직
//            return "redirect:/join";
//        }
//        //틀린 로직
//        bindingResult.reject("","아이디가 중복되었습니다.");
//        return "redirect/join";
//
//    }
}
