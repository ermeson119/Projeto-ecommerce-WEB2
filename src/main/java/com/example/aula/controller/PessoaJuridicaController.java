package com.example.aula.controller;

import com.example.aula.model.entity.PessoaFisica;
import com.example.aula.model.entity.PessoaJuridica;
import com.example.aula.model.repository.PessoaFisicaRepository;
import com.example.aula.model.repository.PessoaJuridicaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Transactional // gerencia a transações.
@Controller
@RequestMapping("/pessoaJuridica")
public class PessoaJuridicaController {

    @Autowired  //Spring fará a injeção dessa dependência no momento que o controller for criado.
    PessoaJuridicaRepository repository;

    @GetMapping("/form")
    public String form(PessoaJuridica pessoaJuridica){
        return "/pessoaJuridica/form";
    }

    @GetMapping(path = {"","/", "/list"})
    public ModelAndView listar(ModelMap model){
        model.addAttribute("pessoaJuridica", repository.pessoaJuridica());
        return new ModelAndView("/pessoaJuridica/list", model);  //caminho da view
    }


    @PostMapping("/buscar")
    public ModelAndView buscar(@RequestParam("nomebusca") String nome, ModelMap model){
        model.addAttribute("pessoaJuridica", repository.buscarpessoaJuridica(nome));
        return new ModelAndView("/pessoaJuridica/list", model);  //caminho da view
    }

    @PostMapping("/save")
    public ModelAndView save(PessoaJuridica pessoaJuridica){
        repository.save(pessoaJuridica);
        return new ModelAndView("redirect:/pessoaJuridica/list");
    }


    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id){
        repository.remove(id);
        return new ModelAndView("redirect:/pessoaJuridica/list");
    }


    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, ModelMap model){
        model.addAttribute("pessoaJuridica", repository.pessoaJuridica(id));
        return new ModelAndView("/pessoaJuridica/form", model);
    }

    @PostMapping("/update")
    public ModelAndView update(PessoaJuridica pessoaJuridica){
        repository.update(pessoaJuridica);
        return new ModelAndView("redirect:/pessoaJuridica/list");
    }
}

