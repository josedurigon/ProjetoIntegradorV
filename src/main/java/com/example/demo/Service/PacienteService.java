package com.example.demo.Service;

import com.example.demo.Entities.Paciente;
import com.example.demo.Entities.User;
import com.example.demo.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PacienteService {
    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public List getAllPacientes() {
        return pacienteRepository.findAll();
    }

    public Optional getPacienteById(Long id) {
        return pacienteRepository.findById(id);
    }

    public boolean addPaciente(Paciente paciente) {
        if (paciente != null) {
            pacienteRepository.saveAndFlush(paciente);
            return true;
        } else {
            return false;
        }
    }

    public boolean updatePaciente(Long id, Paciente paciente) {
        Optional<Paciente> existingUserOptional = pacienteRepository.findById(id);
        if (existingUserOptional.isPresent()) {
            Paciente existingPaciente = existingUserOptional.get();
            existingPaciente.setNomePaciente(paciente.getNomePaciente());
            // Update other fields if needed
            pacienteRepository.save(existingPaciente);
            return true;
        } else {
            // Handle not found scenario
            return false;
        }
    }

    public void deletePaciente(Long id){
        pacienteRepository.deleteById(id);
    }
}
