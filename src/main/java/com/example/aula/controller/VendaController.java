package com.example.aula.controller;

import com.example.aula.model.entity.ItemVenda;
import com.example.aula.model.entity.Produto;
import com.example.aula.model.entity.Venda;
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
    private Venda venda; //O spring vai criar o objeto na session

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("produto/add/{id}")
    public ModelAndView produtoAdd(@PathVariable("id") Long produtoId){
        List<ItemVenda> itensVenda = venda.getItemVendas();

        if (itensVenda.isEmpty()) {
            Produto produto = produtoRepository.produto(produtoId);
            ItemVenda itemNovo = new ItemVenda();
            itemNovo.setProduto(produto);
            itemNovo.setQuantidade(1);

            itensVenda.add(itemNovo);
            itemNovo.setVenda(venda);
        } else {
            int cont = 0;
            for (ItemVenda itemVenda : itensVenda) {
                cont++;
                if (itemVenda.getProduto().getId().equals(produtoId)) {
                    itemVenda.setQuantidade(itemVenda.getQuantidade() + 1);
                    break;
                } else if(itensVenda.size() == cont) { // Se chegar na ultima posição da lista, então ainda não existe
                    Produto produto = produtoRepository.produto(produtoId);
                    ItemVenda itemNovo = new ItemVenda();
                    itemNovo.setProduto(produto);
                    itemNovo.setQuantidade(1);

                    itensVenda.add(itemNovo);
                    itemNovo.setVenda(venda);
                    break;
                }
            }
        }

        return new ModelAndView("redirect:/produtos/area-compra");
    }

    @PostMapping("/save")
    public ModelAndView save(HttpSession session){
        this.venda.setDataHora(LocalDate.now());
        this.vendaRepository.save(this.venda);
        session.invalidate();
        return new ModelAndView("redirect:/vendas/carrinho-list");
    }


    // - VENDAS/CARRINHO
    @GetMapping("/carrinho")
    public String carrinholista(Venda venda, ItemVenda itemVenda){
        return "/vendas/carrinho";
    }



    @GetMapping(path = {"", "/", "list"})
    public ModelAndView listar(ModelMap model){
        model.addAttribute("vendas", vendaRepository.vendas());
        return new ModelAndView("/vendas/list", model);
    }

    @GetMapping("/{id}")
    public ModelAndView listar(@PathVariable String id, ModelMap model){
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
}
