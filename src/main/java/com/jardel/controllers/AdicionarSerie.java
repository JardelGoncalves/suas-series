package com.jardel.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import com.jardel.models.Serie;
import com.jardel.models.Usuario;
import com.jardel.repository.SerieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String adicionarSeriePost(Serie serie, @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = new Usuario();
        String hash_img;

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        try {
            // obtem o usuario caso esteja autenticado
            usuario = (Usuario) auth.getPrincipal();
            // seta o usuário para obter o id na hora de salvar
            serie.setUsuario(usuario);

            // monta o nome da imagem
            MessageDigest md = MessageDigest.getInstance("MD5");
            hash_img = timestamp.toString() + usuario.getEmail();
            md.update(hash_img.getBytes());
            BigInteger hash = new BigInteger(1, md.digest());
            hash_img = hash.toString(16) + ".png";

        } catch (Exception e) {
            return "redirect:/login";
        }

        // verifica se o usuario enviou o arquivo
        if (file.isEmpty()) {
            return "redirect:/dashboard/adicionar-serie";
        }
        // tenta salvar o arquivo
        try {
            byte[] bytes = file.getBytes();
            BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(workspace + FOLDER + hash_img));
            bout.write(bytes);
            bout.flush();
            bout.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        // adiciona o nome da imagem ao objeto
        serie.setFilename(hash_img);
        // salva o usuario
        serieRepository.save(serie);

        // monta uma mensagem de sucesso
        redirectAttributes.addFlashAttribute("message", "Série adicionada com sucesso!");
        return "redirect:/dashboard/adicionar-serie";
    }

    @GetMapping("/dashboard/remove-serie/{id}")
    public String deleteSerie(@PathVariable("id") int id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = new Usuario();
        try {
            // obtem o usuario caso esteja autenticado
            usuario = (Usuario) auth.getPrincipal();
            // obtendo a série
            Optional<Serie> serie = serieRepository.findById(id);
            // verifica se o user no db bate com o que ta tentando excluir
            if (usuario.equals(serie.get().getUsuario())) {
                // recupero a imagem
                File file = new File(workspace + FOLDER + serie.get().getFilename());
                // verifico se é possivel deletar
                if (file.delete()) {
                    //faço log
                    System.out.println("REMOVE AT: "+workspace + FOLDER + serie.get().getFilename());
                } else
                    System.out.println("Arquivo não encontrado");
                // remove o registro no database
                serieRepository.deleteById(id);

            } else {
                return "redirect:/login";
            }
        } catch (Exception e) {
            return "redirect:/login";
        }

        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard/view/{id}")
    public Object viewSeriePage(@PathVariable("id") int id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        Usuario usuario = new Usuario();
        Optional<Serie> serie;

        ModelAndView modelAndView;

        try {
            // obtem o usuario caso esteja autenticado
            usuario = (Usuario) auth.getPrincipal();
            // obtendo a série
            serie = serieRepository.findById(id);
            // verifica se o user no db bate com o que ta tentando excluir
            if (serie != null && usuario.equals(serie.get().getUsuario())) {

                modelAndView = new ModelAndView("dashboard/view-serie");
                
            }else{
                return "redirect:/dashboard";
            }
        }catch(Exception e){
            return "redirect:/dashboard";
        }
        modelAndView.addObject("usuario", usuario);
        modelAndView.addObject("serie", serie.get());
        return modelAndView;
    }

    @GetMapping("/dashboard/view/edit/{id}")
    public Object editSeriePage(@PathVariable("id") int id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = new Usuario();
        Optional<Serie> serie;

        ModelAndView modelAndView;

        try {
            // obtem o usuario caso esteja autenticado
            usuario = (Usuario) auth.getPrincipal();
            // obtendo a série
            serie = serieRepository.findById(id);
            // verifica se o user no db bate com o que ta tentando excluir
            if (serie != null && usuario.equals(serie.get().getUsuario())) {

                modelAndView = new ModelAndView("dashboard/edit-serie");
            }else{
                return "redirect:/dashboard";
            }
        }catch(Exception e){
            return "redirect:/dashboard";
        }
        modelAndView.addObject("usuario", usuario);
        modelAndView.addObject("serie", serie.get());
        return modelAndView;
    }

    @PostMapping("/dashboard/edit-serie")
    public String editSerie(Serie newserie){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = new Usuario();
        Optional<Serie> serie;
        try {
            // obtem o usuario caso esteja autenticado
            usuario = (Usuario) auth.getPrincipal();
            // obtendo a série
            serie = serieRepository.findById(newserie.getId());
            // verifica se o user no db bate com o que ta tentando excluir
            if (serie != null && usuario.equals(serie.get().getUsuario())) {
                newserie.setFilename(serie.get().getFilename());
                newserie.setUsuario(usuario);
                newserie.setData_modificacao(new Date());
                serieRepository.save(newserie);
            }else{
                return "redirect:/dashboard";
            }
        }catch(Exception e){
            return "redirect:/dashboard";
        }
        return "redirect:/dashboard";
    }
}