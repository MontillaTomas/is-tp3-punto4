package com.example.tp3.dominio;

import java.time.LocalDateTime;
import java.util.List;

public class Paciente {
    private int dni;
    private String nombre;
    private HistoriaClinica historiaClinica;

    public Paciente(int dni, String juanPerez, List<String> diagnosticosPreexistentes) {
        this.dni = dni;
        this.nombre = juanPerez;
        this.historiaClinica = new HistoriaClinica(diagnosticosPreexistentes);
    }

    public Diagnostico buscarDiagnostico(String diagnosticoBuscado) {
        return historiaClinica.buscarDiagnostico(diagnosticoBuscado);
    }

    public void editarEvolucion(Medico medico, String diagnosticoBuscado, String informeOriginal, String modificacion, LocalDateTime fecha) {
        this.historiaClinica.editarEvolucion(medico, diagnosticoBuscado, informeOriginal, modificacion, fecha);
    }

    public void agregarEvolucion(String diagnosticoBuscado,Medico medico, String informe, LocalDateTime fecha) {
        this.historiaClinica.agregarEvolucion(diagnosticoBuscado, medico, informe, fecha);
    }
}
