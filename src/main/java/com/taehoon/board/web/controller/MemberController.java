package com.taehoon.board.web.controller;

import com.taehoon.board.domain.Gender;
import com.taehoon.board.domain.Member;
import com.taehoon.board.service.MemberService;
import com.taehoon.board.web.SessionConst;
import com.taehoon.board.web.dto.member.LoginDTO;
import com.taehoon.board.web.dto.member.MemberCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        String genderString = memberCreateDto.getGender();
        Gender gender;
        if (genderString.equals("man")) {
            gender = Gender.MAN;
        } else {
            gender = Gender.WOMAN;
        }

        String birth = memberCreateDto.getBirth();
        System.out.println("birth = " + birth);
        LocalDate date = LocalDate.parse(birth, DateTimeFormatter.ISO_DATE);

        Member member = new Member(memberCreateDto.getUserId(), memberCreateDto.getPassword(), memberCreateDto.getEmail(),
                memberCreateDto.getPhoneNum(), gender, date);

        memberService.joinMember(member);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        LoginDTO loginDTO = new LoginDTO();
        model.addAttribute("loginDTO", loginDTO);
        return "member/loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute @Valid LoginDTO loginDTO, BindingResult result, HttpServletRequest request) {

        if (result.hasErrors()) {
            return "member/loginForm";
        }

        try {
            System.out.println("loginDTO = " + loginDTO);
            Member loginMember = memberService.login(loginDTO.getUserId(), loginDTO.getPassword());

            HttpSession session = request.getSession();

            session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        } catch (IllegalArgumentException e) {        //로그인 실행 이상
            System.out.println("e.getMessage() = " + e.getMessage());
            result.reject(e.getMessage());
            return "member/loginForm";
        }


        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
