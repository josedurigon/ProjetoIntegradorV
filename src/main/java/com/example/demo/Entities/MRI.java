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
    public Integer idMri;

    @ManyToOne
    @JoinColumn(name="idUser")
    public User user;


}
