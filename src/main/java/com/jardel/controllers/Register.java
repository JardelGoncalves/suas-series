package com.jardel.controllers;

import com.jardel.models.Usuario;
import com.jardel.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Register{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/register")
    public ModelAndView registerPage(){
        // desautenticando o usuario
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.setAuthenticated(false);
        
        ModelAndView modelAndView = new ModelAndView("register");
        return modelAndView;
    }

    @PostMapping("/cadastro-usuario")
    public String cadastroUsuario(Usuario usuario){
        usuarioRepository.save(usuario);
        return "redirect:/login";
    }
}