package com.jardel.controllers;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.jardel.models.Serie;
import com.jardel.models.Usuario;
import com.jardel.repository.SerieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdicionarSerie {
    Path currentRelativePath = Paths.get("");
    String workspace = currentRelativePath.toAbsolutePath().toString();
    private static final String FOLDER = "/src/main/resources/static/img/";

    @Autowired
    private SerieRepository serieRepository;

    @GetMapping("/dashboard/adicionar-serie")
    public ModelAndView cadastroPage() {
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

    @PostMapping("/dashboard/adicionar-serie")
    public String adicionarSeriePost(Serie serie, @RequestParam("file") MultipartFile file) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = new Usuario();
        
        if (file.isEmpty()) {
            return "redirect:/dashboard/adicionar-serie";
        }
        try {
            byte[] bytes = file.getBytes();
            BufferedOutputStream bout = new BufferedOutputStream(
                    new FileOutputStream(workspace + FOLDER + file.getOriginalFilename()));
            bout.write(bytes);
            bout.flush();
            bout.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // setando usuário
        try {
            // obtem o usuario caso esteja autenticado
            usuario = (Usuario) auth.getPrincipal();
            serie.setUsuario(usuario);

        } catch (Exception e) {
            return "redirect:/login";
        }
        serie.setFilename(file.getOriginalFilename());

        serieRepository.save(serie);
        return "redirect:/dashboard";
    }
}