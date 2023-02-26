package com.a05.simaya.testing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class test_controller {

    @GetMapping(value = "/test")
    public String test() {
        return "hello_world";
    }
}
