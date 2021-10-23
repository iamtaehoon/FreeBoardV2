package com.taehoon.board.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping("/test")
    public String testTemplate(Model model) {
        model.addAttribute("name", "taehoon~~");
        return "testView";
    }

}
