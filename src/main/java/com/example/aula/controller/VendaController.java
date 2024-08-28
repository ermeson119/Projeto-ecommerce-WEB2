package com.example.aula.controller;

import com.example.aula.model.entity.*;
import com.example.aula.model.repository.*;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

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
    private UsuarioRepository usuarioRepository;

    @Autowired
    PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    PessoaJuridicaRepository pessoaJuridicaRepository;


    @GetMapping("produto/add/{id}")
    public ModelAndView produtoAdd(@PathVariable("id") Long produtoId) {
        Produto produto = produtoRepository.produto(produtoId);

        //Verifica se o produto retornado do repositório não é nulo antes de continuar.
        if (produto != null) {
            ItemVenda itemExistente = null;

        //apenas percorre a lista de itens de venda para encontrar o item existente.
            for (ItemVenda itemVenda : venda.getItemVendas()) {
                if (itemVenda.getProduto().getId().equals(produtoId)) {
                    itemExistente = itemVenda;
                    break;
                }
            }

            //Usa uma variável itemExistente para armazenar o item encontrado e atualiza
            //sua quantidade, ou adiciona um novo item de venda se não for encontrado.
            if (itemExistente != null) {
                itemExistente.setQuantidade(itemExistente.getQuantidade() + 1);
            } else {
                ItemVenda itemNovo = new ItemVenda();
                itemNovo.setProduto(produto);
                itemNovo.setQuantidade(1);
                itemNovo.setVenda(venda);
                venda.getItemVendas().add(itemNovo);
            }
        }

        return new ModelAndView("redirect:/produtos/area-compra");
    }


    @PostMapping ("/save")
    public ModelAndView save(HttpSession session){

        if(venda.getItemVendas().isEmpty()) {
            return new ModelAndView("redirect:/produtos/area-compra");
        }

        UserDetails current = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Pessoa pessoa = usuarioRepository.findByNome(current.getUsername()).getPessoa();
        venda.setPessoa(pessoa);
        venda.setDataHora(LocalDate.now());
        vendaRepository.save(venda);
        session.removeAttribute("venda");
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
        UserDetails current = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario user = usuarioRepository.findByNome(current.getUsername());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth.getAuthorities().stream().findFirst().get().getAuthority().equals("ROLE_ADMIN")) {
            model.addAttribute("vendas", vendaRepository.vendas());
            return new ModelAndView("/vendas/list", model);
        }

        model.addAttribute("vendas", user.getPessoa().getVendas());
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



    @PostMapping("/buscar")
    public ModelAndView listarDate(@RequestParam("nomebusca") String nome,
                                   @RequestParam("databusca") LocalDate data, ModelMap model){

        List<Venda> vendas = vendaRepository.vendas();

        if (!nome.equals("")){
            vendas = vendaRepository.findByNome(nome);
        }

        if(data != null){
            vendas = vendas.stream()
                    .filter(v -> v.getDataHora().equals(data))
                    .toList(); //Para cada venda onde a dataHora for igual a 'data' passada pelo parametro do metodo.
        }

        model.addAttribute("vendas", vendas);
        return new ModelAndView("/vendas/list", model);
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
