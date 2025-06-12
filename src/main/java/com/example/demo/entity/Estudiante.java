package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="estudiantes")
public class Estudiante {
    @Id

    private String cedula;

    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;

}
