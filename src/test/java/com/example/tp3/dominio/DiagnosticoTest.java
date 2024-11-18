package com.example.tp3.dominio;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DiagnosticoTest {
    private Diagnostico diagnostico;
    private Medico medico;

    @BeforeEach
    public void setUp(){
        diagnostico = new Diagnostico("Gripe");
        medico = new Medico("Dr House");
        diagnostico.agregarEvolucion("Informe de ejemplo", medico, LocalDateTime.now());
    }

    @AfterEach
    public void tearDown(){
        diagnostico = null;
    }

    @Test
    public void editarEvolucionNoExistente(){
        assertThatThrownBy(() -> diagnostico.editarEvolucion(medico, "Informe no existente", "Modificacion", LocalDateTime.now()))
                .hasMessage("Evolucion no encontrada");
    }

    @Test
    public void editarEvolucionPasadas48horas(){
        assertThatThrownBy(() -> diagnostico.editarEvolucion(medico, "Informe de ejemplo", "Informe modificado", LocalDateTime.now().plusDays(2)))
                .hasMessage("No se puede editar una evolucion pasadas 48 horas");
    }

    @Test
    public void editarEvolucionEnLasPrimeras48horas(){
        diagnostico.editarEvolucion(medico, "Informe de ejemplo", "Informe modificado", LocalDateTime.now().plusHours(47));
        assertThat(diagnostico.buscarEvolucion("Informe modificado")).isNotNull();
    }

    @Test
    public void agregarEvolucionADiagnosticoConUnaEvolucion(){
        diagnostico.agregarEvolucion("Informe de ejemplo 2", medico, LocalDateTime.now());
        assertThat(diagnostico.buscarEvolucion("Informe de ejemplo 2")).isNotNull();
        assertThat(diagnostico.getEvoluciones()).hasSize(2);
    }

    @Test
    public void agregarEvolucionADiagnosticoSinEvoluciones(){
        Diagnostico diagnostico = new Diagnostico("Anemia");
        diagnostico.agregarEvolucion("Informe de ejemplo", medico, LocalDateTime.now());
        assertThat(diagnostico.buscarEvolucion("Informe de ejemplo")).isNotNull();
        assertThat(diagnostico.getEvoluciones()).hasSize(1);
    }
}