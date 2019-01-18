package com.jardel.controllers;

import com.jardel.models.Usuario;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Index {

    @GetMapping("/")
    public ModelAndView indexPage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = new Usuario();

        try {
            usuario = (Usuario) auth.getPrincipal();
        } catch (Exception e) {

        }
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("usuario", usuario);
        return modelAndView;
    }
}