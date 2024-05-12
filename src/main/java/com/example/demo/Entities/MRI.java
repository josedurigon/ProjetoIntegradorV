package com.example.demo.Entities;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "mri")
public class MRI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_mri")
    public Long idMri;

    @ManyToOne
    @JoinColumn(name="idUser")
    public User user;

    public Paciente paciente;





}
