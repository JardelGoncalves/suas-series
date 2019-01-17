package com.jardel.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Index{

    @GetMapping("/")
    public ModelAndView indexPage(){
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }
}