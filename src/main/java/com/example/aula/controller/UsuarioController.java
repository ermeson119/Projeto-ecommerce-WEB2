package com.example.aula.controller;

import com.example.aula.model.entity.Pessoa;
import com.example.aula.model.entity.Role;
import com.example.aula.model.entity.Usuario;
import com.example.aula.model.repository.PessoaRepository;
import com.example.aula.model.repository.RoleRepository;
import com.example.aula.model.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/form")
    public String form() {
        return "usuario/form";
    }

    @PostMapping("/save")       //recebe os dados do formulario usuario
    public ModelAndView save(@RequestParam("nome") String nome,
                             @RequestParam("telefone") String telefone,
                             @RequestParam("username") String username,
                             @RequestParam("senha") String senha,
                             @RequestParam(value = "roleadmin", defaultValue = "false") boolean roleadmin){
        List<Role> roles = new ArrayList<>();


        Pessoa pessoa = new Pessoa(nome, telefone);
        if (roleadmin){
            roles.add(roleRepository.role(1L)); // Admin Role
        } else {
            roles.add(roleRepository.role(2L)); // User Role
        }
        Usuario usuario = new Usuario(username, senha, roles);

        if (usuarioRepository.usuarios().contains(usuario)){
            return new ModelAndView("redirect:/usuario/form");
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        pessoaRepository.save(pessoa);
        List<Pessoa> pessoas = pessoaRepository.buscarpessoa(pessoa.getNome());
        if (!pessoas.isEmpty()) {
            usuario.setPessoa(pessoas.get(0));
        }
        usuarioRepository.save(usuario);

        return new ModelAndView("redirect:/login?logout");
    }

    @GetMapping("/list")
    public ModelAndView listUsers(ModelMap model) {
        model.addAttribute("usuarios", usuarioRepository.usuarios());
        return new ModelAndView("usuario/form", model);
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id){
        usuarioRepository.remove(id);
        return new ModelAndView("redirect:/usuario/list");
    }


    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, ModelMap model){
        model.addAttribute("usuarios", usuarioRepository.usuario(id));
        return new ModelAndView("/usuario/form", model);
    }

    @PostMapping("/update")
    public ModelAndView update(Usuario usuario){
        usuarioRepository.update(usuario);
        return new ModelAndView("redirect:/usuario/form");
    }
}
