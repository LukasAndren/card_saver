package com.card_saver.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class TestController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
