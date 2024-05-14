package com.example.demo.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String idUser;
    public String nome;
    public String username;
    public String password;
    public String email;
}
