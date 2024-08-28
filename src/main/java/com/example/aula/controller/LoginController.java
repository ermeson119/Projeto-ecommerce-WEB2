package com.example.aula.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {

    @GetMapping("/login")
    public String form(){
        return "/autentiicacao/login";
    }

    @GetMapping("/paginaerror")
    public String paginaerror(){
        return "/autentiicacao/pagina-error";
    }

}
