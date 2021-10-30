package com.taehoon.board.web.controller;

import com.taehoon.board.domain.Member;
import com.taehoon.board.service.MemberService;
import com.taehoon.board.web.dto.member.MemberCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

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
        System.out.println("memberCreateDto = " + memberCreateDto.getEmail());
        System.out.println("memberCreateDto = " + memberCreateDto.getPassword());
        System.out.println("memberCreateDto = " + memberCreateDto.getPhoneNum());
        System.out.println("memberCreateDto = " + memberCreateDto.getUserId());
        System.out.println("memberCreateDto = " + memberCreateDto.getBirth());
        System.out.println("memberCreateDto = " + memberCreateDto.getGender());

        if (result.hasErrors()) {
            return "member/joinForm";
        }

        return "redirect:/join";
    }
}
