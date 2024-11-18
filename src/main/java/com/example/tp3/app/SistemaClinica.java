package com.example.tp3.app;

import com.example.tp3.dominio.Medico;
import com.example.tp3.dominio.Paciente;
import com.example.tp3.repositorio.RepositorioPaciente;

import java.time.LocalDateTime;

public class SistemaClinica {
    private final RepositorioPaciente repositorioPaciente;

    public SistemaClinica(RepositorioPaciente repositorioPaciente) {
        this.repositorioPaciente = repositorioPaciente;
    }

    public Paciente editarEvolucion(Medico medico, int dniPaciente, String diagnosticoBuscado, String informeOriginal, String modificacion, LocalDateTime fecha) {
        Paciente paciente = repositorioPaciente.buscarPacientePorDni(dniPaciente).orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        paciente.editarEvolucion(medico, diagnosticoBuscado, informeOriginal, modificacion, fecha);
        repositorioPaciente.actualizarPaciente(paciente);

        return paciente;
    }
}
