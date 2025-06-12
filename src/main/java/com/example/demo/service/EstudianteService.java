package com.example.demo.service;


import com.example.demo.entity.Estudiante;
import com.example.demo.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstudianteService {
    @Autowired
    private EstudianteRepository repository;

    public List<Estudiante> get() {
        return repository.findAll();
    }

    public Optional<Estudiante> getById(String cedula) {
        return repository.findById(cedula);
    }

    public Estudiante post(Estudiante estudiante) {
        return repository.save(estudiante);
    }

    public void delete(String cedula) {
        repository.deleteById(cedula);
    }
}
