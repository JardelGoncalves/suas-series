package com.jardel.controllers;

import com.jardel.models.Usuario;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdicionarSerie {

    @GetMapping("/dashboard/adicionar-serie")
    public ModelAndView cadastroPage(){
        ModelAndView modelAndView = new ModelAndView("dashboard/adicionar-serie");
        // obtem autenticação, caso haja
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = new Usuario(); // instancia um novo usuario (vazio)

        try {
            // obtem o usuario caso esteja autenticado
            usuario = (Usuario) auth.getPrincipal();


        } catch (Exception e) {

        }
        modelAndView.addObject("usuario", usuario);
        return modelAndView;
    }
}