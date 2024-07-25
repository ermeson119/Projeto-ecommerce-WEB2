package com.example.aula.controller;

import com.example.aula.model.entity.*;
import com.example.aula.model.repository.PessoaFisicaRepository;
import com.example.aula.model.repository.PessoaJuridicaRepository;
import com.example.aula.model.repository.ProdutoRepository;
import com.example.aula.model.repository.VendaRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Scope("request")
@Controller
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    Venda venda; //O spring vai criar o objeto na session

    @Autowired
    private ProdutoRepository produtoRepository;


    @Autowired
    PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    PessoaJuridicaRepository pessoaJuridicaRepository;


    @GetMapping("produto/add/{id}")
    public ModelAndView produtoAdd(@PathVariable("id") Long produtoId){

        if (venda.getItemVendas().isEmpty()) {
            Produto produto = produtoRepository.produto(produtoId);
            ItemVenda itemNovo = new ItemVenda();
            itemNovo.setProduto(produto);
            itemNovo.setQuantidade(1);

            itemNovo.setVenda(venda);
            venda.getItemVendas().add(itemNovo);
        } else {
            int cont = 0;
            for (ItemVenda itemVenda : venda.getItemVendas()) {
                cont++;
                if (itemVenda.getProduto().getId().equals(produtoId)) {
                    itemVenda.setQuantidade(itemVenda.getQuantidade() + 1);
                    break;
                } else if(venda.getItemVendas().size() == cont) { // Se chegar na ultima posição da lista, então ainda não existe
                    Produto produto = produtoRepository.produto(produtoId);
                    ItemVenda itemNovo = new ItemVenda();
                    itemNovo.setProduto(produto);
                    itemNovo.setQuantidade(1);

                    itemNovo.setVenda(venda);
                    venda.getItemVendas().add(itemNovo);
                    break;
                }
            }
        }

        return new ModelAndView("redirect:/produtos/area-compra");
    }

    @PostMapping ("/save")
    public ModelAndView save(@RequestParam("pessoaId") Long id ,  HttpSession session){
//        if (result.hasErrors()) {
//            // Se houver erros de validação, redireciona para a página anterior com erros
//            return new ModelAndView("nomeDaSuaPaginaDeFormulario");
//        }
        if(venda.getItemVendas().isEmpty()) {
            return new ModelAndView("redirect:/produtos/area-compra");
        }

        Pessoa pessoa = buscarPessoa(id);
        venda.setPessoa(pessoa);
        venda.setDataHora(LocalDate.now());
        vendaRepository.save(venda);
        session.invalidate();
        return new ModelAndView("redirect:/vendas/list");
    }


    // - VENDAS/CARRINHO
    @GetMapping("/carrinho")
    public ModelAndView carrinholista(ModelMap model){
        model.addAttribute("pf", pessoaFisicaRepository.pessoaFisica());
        model.addAttribute("pj", pessoaJuridicaRepository.pessoaJuridica());
        model.addAttribute("itens", vendaRepository.vendas());
        return new ModelAndView();
    }


    @GetMapping(path = {"", "/", "list"})
    public ModelAndView listar(ModelMap model){
        model.addAttribute("vendas", vendaRepository.vendas());
        return new ModelAndView("/vendas/list", model);
    }

    @GetMapping("/{id}")
    public ModelAndView listar(@PathVariable("id") String id, ModelMap model){
        try {
            Venda venda = vendaRepository.venda(Long.parseLong(id));
            if (venda == null) throw new RuntimeException("Object(Venda) is null");

            model.addAttribute("venda", venda);
            return new ModelAndView("/vendas/view", model);
        } catch (Exception e) {
            model.clear();
            return new ModelAndView("/vendas/list", model);
        }
    }

    @GetMapping("/session/remove/{index}")
    public ModelAndView removeFromSession(@PathVariable("index") int index) {
        List<ItemVenda> itemVendas = this.venda.getItemVendas();
        ItemVenda item = itemVendas.get(index);

        if (item.getQuantidade() > 1) {
            item.setQuantidade(item.getQuantidade() - 1);
        } else {
            this.venda.getItemVendas().remove(index);
        }

        return new ModelAndView("redirect:/vendas/carrinho");
    }


    private Pessoa buscarPessoa(Long id){
        PessoaFisica pessoaFisica = pessoaFisicaRepository.pessoaFisica(id);
        if (pessoaFisica != null){
            return pessoaFisica;
        }

        PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.pessoaJuridica(id);

        if (pessoaJuridica != null){
            return pessoaJuridica;
        }
        return null;
    }
}
