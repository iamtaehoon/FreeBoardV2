package com.taehoon.board.web.controller;

import com.taehoon.board.domain.Member;
import com.taehoon.board.service.MemberService;
import com.taehoon.board.web.dto.member.MemberCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
