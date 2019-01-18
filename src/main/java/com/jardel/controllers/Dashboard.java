package com.jardel.controllers;

import java.util.ArrayList;
import java.util.List;

import com.jardel.models.Serie;
import com.jardel.models.Usuario;
import com.jardel.repository.SerieRepository;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class Dashboard{

    @AutoConfigureOrder
    private SerieRepository serieRepository;

    @GetMapping("/dashboard")
    public ModelAndView dashboardPage(){
        ModelAndView modelAndView = new ModelAndView("dashboard/dashboard");
        // obtem autenticação, caso haja
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = new Usuario(); // instancia um novo usuario (vazio)
        List<Serie> series = new ArrayList<>();

        try {
            // obtem o usuario caso esteja autenticado
            usuario = (Usuario) auth.getPrincipal();
            // resgata todas as series cadastra pelo usuario
            series = serieRepository.findSerieByUser(usuario.getId());


        } catch (Exception e) {

        }
        usuario.setSeries(series);
        modelAndView.addObject("usuario", usuario);
        return modelAndView;

    }
}