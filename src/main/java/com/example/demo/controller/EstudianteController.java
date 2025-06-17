package com.example.demo.controller;

import com.example.demo.entity.Estudiante;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
        cargarDatosUsuario(model, principal);
        return "index";
    }

    @GetMapping("/estudiantes/buscar")
    public String buscarPorCedula(String cedula, Model model, Principal principal) {
        var estudiante = service.getById(cedula).orElse(null);
        model.addAttribute("estudiantes", estudiante != null ? List.of(estudiante) : List.of());

        if (estudiante == null)
            model.addAttribute("mensaje", "No se encontró al estudiante con cédula: " + cedula);

        cargarDatosUsuario(model, principal);

        return "index";
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("/estudiantes")
    public String guardar(@ModelAttribute Estudiante estudiante, Model model, Principal principal) {
        if (service.getById(estudiante.getCedula()).isPresent()) {
            model.addAttribute("mensaje", "Ya existe un estudiante con la cédula: " + estudiante.getCedula());
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
        service.post(estudiante);
        return "redirect:/";
    }


    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("/estudiantes/editar/{cedula}")
    public String editar(@PathVariable String cedula, @ModelAttribute Estudiante estudiante) {
        service.post(estudiante);
        return "redirect:/";
    }
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/estudiantes/eliminar/{cedula}")
    public String eliminar(@PathVariable String cedula) {
        service.delete(cedula);
        return "redirect:/";
    }
    private void cargarDatosUsuario(Model model, Principal principal) {
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
    }

}

