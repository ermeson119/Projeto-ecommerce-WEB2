package com.example.aula.controller;

import com.example.aula.model.entity.PessoaFisica;
import com.example.aula.model.repository.PessoaFisicaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Transactional // gerencia a transações.
@Controller
@RequestMapping("/pessoaFisica")
public class PessoaFisicaController {

    @Autowired  //Spring fará a injeção dessa dependência no momento que o controller for criado.
    PessoaFisicaRepository repository;

    @GetMapping("/form")
    public String form(PessoaFisica pessoaFisica){
        return "/pessoaFisica/form";
    }

    @GetMapping(path = {"","/", "/list"})
    public ModelAndView listar(ModelMap model){
        model.addAttribute("pessoaFisica", repository.pessoaFisica());
        return new ModelAndView("/pessoaFisica/list", model);  //caminho da view
    }

    @PostMapping("/buscar")
    public ModelAndView buscar(@RequestParam("nomebusca") String nome, ModelMap model){
        model.addAttribute("pessoaFisica", repository.buscarpessoaFisica(nome));
        return new ModelAndView("/pessoaFisica/list", model);  //caminho da view
    }

    @PostMapping("/save")
    public ModelAndView save(PessoaFisica pessoaFisica){
        repository.save(pessoaFisica);
        return new ModelAndView("redirect:/pessoaFisica/list");
    }


    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id){
        repository.remove(id);
        return new ModelAndView("redirect:/pessoaFisica/list");
    }


    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, ModelMap model){
        model.addAttribute("pessoaFisica", repository.pessoaFisica(id));
        return new ModelAndView("/pessoaFisica/form", model);
    }

    @PostMapping("/update")
    public ModelAndView update(PessoaFisica pessoaFisica){
        repository.update(pessoaFisica);
        return new ModelAndView("redirect:/pessoaFisica/list");
    }
}
