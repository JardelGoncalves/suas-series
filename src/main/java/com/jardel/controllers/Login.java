package com.jardel.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Login {

    @GetMapping("/login")
    public ModelAndView loginPage(){
        ModelAndView modelAndView = new ModelAndView("login");
        return modelAndView;
    }
}