package com.example.tp3.dominio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class HistoriaClinica {
    private List<Diagnostico> diagnosticos;

    public HistoriaClinica(List<String> diagnosticos) {
        this.diagnosticos = diagnosticos
                .stream()
                .map(Diagnostico::new)
                .collect(Collectors.toList());
    }

    public Diagnostico buscarDiagnostico(String diagnosticoBuscado) {
        return diagnosticos
                .stream()
                .filter(diagnostico -> diagnostico.getNombre().equalsIgnoreCase(diagnosticoBuscado))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Diagnostico no encontrado"));
    }

    public void editarEvolucion(Medico medico, String diagnosticoBuscado, String informeOriginal, String modificacion, LocalDateTime fecha) {
        Diagnostico diagnostico = buscarDiagnostico(diagnosticoBuscado);
        diagnostico.editarEvolucion(medico, informeOriginal, modificacion, fecha);
    }

    public void agregarEvolucion(String diagnosticoBuscado, Medico medico, String informe, LocalDateTime fecha) {
        Diagnostico diagnostico = buscarDiagnostico(diagnosticoBuscado);
        diagnostico.agregarEvolucion(informe, medico, fecha);
    }
}
