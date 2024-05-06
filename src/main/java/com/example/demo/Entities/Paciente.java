package com.example.demo.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "paciente")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long idPaciente;
    public Long idMri;
    public String nomePaciente;
    public String contatoPaciente;
    public String descricaoDiagnostico;

}
