package com.example.aula.controller;

import com.example.aula.model.repository.ProdutoRepository;
import com.example.aula.model.entity.Produto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Transactional // gerencia a transações.
@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired  //Spring fará a injeção dessa dependência no momento que o controller for criado.
    ProdutoRepository repository;

    @GetMapping("/form")
    public String form(Produto produto){
        return "/produtos/form";
    }

    @GetMapping("/list")
    public ModelAndView listar(ModelMap model){
        model.addAttribute("produtos", repository.produtos());
        return new ModelAndView("/produtos/list", model);  //caminho da view
    }

    @GetMapping("/area-compra")
    public ModelAndView listProdutos(ModelMap model){
        model.addAttribute("produtos", repository.produtos());
        return new ModelAndView("/produtos/area-compra-client");
    }

    @PostMapping("/save")
    public ModelAndView save(Produto produto){
        repository.save(produto);
        return new ModelAndView("redirect:/produtos/list");
    }


    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id){
        repository.remove(id);
        return new ModelAndView("redirect:/produtos/list");
    }


    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, ModelMap model){
        model.addAttribute("produto", repository.produto(id));
        return new ModelAndView("/produtos/form", model);
    }

    @PostMapping("/update")
    public ModelAndView update(Produto produto){
        repository.update(produto);
        return new ModelAndView("redirect:/produtos/list");
    }
}
