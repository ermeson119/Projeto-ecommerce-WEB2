package com.example.aula.controller;

import com.example.aula.model.entity.PessoaFisica;
import com.example.aula.model.repository.PessoaFisicaRepository;
import com.example.aula.model.repository.PessoaJuridicaRepository;
import com.example.aula.model.repository.ProdutoRepository;
import com.example.aula.model.entity.Produto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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

    @PostMapping("/buscar")
    public ModelAndView buscar(@RequestParam("nomebusca") String descricao, ModelMap model){

        model.addAttribute("produtos", repository.buscarProduto(descricao));
        return new ModelAndView("/produtos/area-compra-client", model);  //caminho da view

    }
    @PostMapping("/buscar-produto")
    public ModelAndView buscarProduto(@RequestParam("nomebusca") String descricao, ModelMap model){

        model.addAttribute("produtos", repository.buscarProduto(descricao));
        return new ModelAndView("/produtos/list", model);  //caminho da view

    }


    @PostMapping("/save")
    public ModelAndView save(@Valid Produto produto, BindingResult result){
        if (result.hasErrors()) {
            return new ModelAndView("/produtos/form");
        }
        repository.save(produto);
        return new ModelAndView("redirect:/produtos/list");
    }


    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id){
        repository.remove(id);
        return new ModelAndView("redirect:/produtos/list");
    }

    @GetMapping("/remove/session/{id}")
    public ModelAndView removeSession(@PathVariable("id") Long id){
        repository.remove(id);
        return new ModelAndView("redirect:/produtos/area-compra");
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
