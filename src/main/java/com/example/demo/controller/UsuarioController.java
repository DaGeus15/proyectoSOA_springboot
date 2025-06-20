package com.example.demo.controller;

import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("/crear-secretaria")
    public String crearSecretaria(@RequestParam String username, @RequestParam String password) {
        usuarioService.registrarSecretaria(username, password);
        return "redirect:/";
    }
}
