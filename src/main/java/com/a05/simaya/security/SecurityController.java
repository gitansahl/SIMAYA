package com.a05.simaya.security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SecurityController {

    @GetMapping("/login")
    public String loginPage(Model model,
                            @RequestParam(value = "code", required = false) String code) {
        model.addAttribute("code", code);
        return"security/login";
    }
}
