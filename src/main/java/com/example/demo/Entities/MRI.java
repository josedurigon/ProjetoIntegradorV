package com.example.demo.Entities;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "\"MRI\"")
public class MRI {

    @Id
    public String idMri;

    @ManyToOne
    @JoinColumn(name="idUser")
    public User user;


}
