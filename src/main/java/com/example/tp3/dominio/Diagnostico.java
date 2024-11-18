package com.example.tp3.dominio;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Diagnostico {
    private String nombre;
    private List<Evolucion> evoluciones;

    public Diagnostico(String nombre) {

        this.nombre = nombre;
        this.evoluciones = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public List<Evolucion> getEvoluciones() {
        return evoluciones;
    }

    public Evolucion buscarEvolucion(String informeOriginal) {
        return evoluciones
                .stream()
                .filter(evolucion -> evolucion.getInforme().equalsIgnoreCase(informeOriginal))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Evolucion no encontrada"));
    }

    public void editarEvolucion(Medico medico, String informeOriginal, String modificacion, LocalDateTime fecha) {
        Evolucion evolucion = buscarEvolucion(informeOriginal);
        if (ChronoUnit.HOURS.between(evolucion.getFecha(), fecha) >= 48){
            throw new RuntimeException("No se puede editar una evolucion pasadas 48 horas");
        }
        // Nota: se decidio no modificar la fecha de la evolucion
        evolucion.editarInforme(medico, modificacion);
    }

    public void agregarEvolucion(String informe, Medico medico, LocalDateTime fecha) {
        evoluciones.add(new Evolucion(informe, medico, fecha));
    }
}
