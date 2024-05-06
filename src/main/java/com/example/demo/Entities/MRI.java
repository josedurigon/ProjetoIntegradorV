package com.example.demo.Entities;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "\"MRI\"")
public class MRI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long idMri;

    @ManyToOne
    @JoinColumn(name="idUser")
    public User user;
    public String nomePaciente;
    public String contatoPaciente;
    public String descricaoDiagnostico;


}
