package com.a05.simaya.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestingController {

    @GetMapping(value = "/test")
    public String getPage() {
        return "hello_world";
    }

}
