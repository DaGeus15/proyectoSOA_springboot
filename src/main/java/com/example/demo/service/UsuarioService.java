package com.example.demo.service;

import com.example.demo.entity.Rol;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.RolRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registrarSecretaria(String username, String password) {
        Rol rolSecretaria = rolRepository.findAll().stream()
                .filter(r -> r.getNombre().equalsIgnoreCase("SECRETARIA"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Rol SECRETARIA no existe"));

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(username);
        nuevoUsuario.setPassword(passwordEncoder.encode(password));
        nuevoUsuario.setEnabled(true);
        nuevoUsuario.setRoles(Collections.singleton(rolSecretaria));

        usuarioRepository.save(nuevoUsuario);
    }
}
