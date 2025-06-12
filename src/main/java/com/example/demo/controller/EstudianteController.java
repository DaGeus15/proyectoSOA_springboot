package com.example.demo.controller;

import com.example.demo.entity.Estudiante;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class EstudianteController {
    @Autowired
    private EstudianteService service;

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        model.addAttribute("estudiantes", service.get());

        if (principal != null) {
            model.addAttribute("username", principal.getName());

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean isAdmin = auth.getAuthorities().stream()
                    .anyMatch(g -> g.getAuthority().equals("ROLE_ADMINISTRADOR"));
            boolean isSecretary = auth.getAuthorities().stream()
                    .anyMatch(g -> g.getAuthority().equals("ROLE_SECRETARIA"));

            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("isSecretary", isSecretary);
        }

        return "index";
    }

    @GetMapping("/estudiantes/buscar")
    public String buscarPorCedula(String cedula, Model model) {
        var estudiante = service.getById(cedula).orElse(null);
        model.addAttribute("estudiantes", estudiante != null ? List.of(estudiante) : List.of());
        if (estudiante == null)
            model.addAttribute("mensaje", "No se encontró al estudiante con cédula: " + cedula);
        return "index";
    }

    @PostMapping("/estudiantes")
    public String guardar(@ModelAttribute Estudiante estudiante) {
        service.post(estudiante);
        return "redirect:/";
    }

    @PostMapping("/estudiantes/editar/{cedula}")
    public String editar(@PathVariable String cedula, @ModelAttribute Estudiante estudiante) {
        service.post(estudiante);
        return "redirect:/";
    }

    @GetMapping("/estudiantes/eliminar/{cedula}")
    public String eliminar(@PathVariable String cedula) {
        service.delete(cedula);
        return "redirect:/";
    }
}

