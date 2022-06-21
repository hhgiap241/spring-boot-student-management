package com.kms.mywebapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping(value = {"index"})
    public String showHomePage() {
        return "index";
    }
    @GetMapping("login")
    public String getLoginPage() {
        return "login";
    }
}
