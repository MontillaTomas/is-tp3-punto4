package com.example.tp3.dominio;

import java.time.LocalDateTime;

public class Evolucion {
    private String informe;
    private Medico medico;
    private LocalDateTime fecha;

    public Evolucion(String informe, Medico medico, LocalDateTime fecha) {
        this.informe = informe;
        this.medico = medico;
        this.fecha = fecha;
    }

    public String getInforme() {
        return informe;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void editarInforme(Medico medico, String modificacion) {
        this.medico = medico;
        this.informe = modificacion;
    }
}
