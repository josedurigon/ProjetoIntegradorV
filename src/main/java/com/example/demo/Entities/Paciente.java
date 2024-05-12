package com.example.demo.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "paciente")
public class Paciente {
    @Id
    @SequenceGenerator(name="paciente_generator_id",
            sequenceName="paciente_generator_id",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY,
            generator="paciente_generator_id")
    @Column(name = "id_paciente")
    private Integer idPaciente;

    @Column(name="nome_paciente")
    public String nomePaciente;

    @Column(name="contato_paciente")
    public String contatoPaciente;

    @Column(name="descricao_diagnostico")
    public String descricaoDiagnostico;

    @OneToOne
    @JoinColumn(name = "id_mri", referencedColumnName = "id_mri")
    public MRI mri;

}
