import com.example.tp3.app.SistemaClinica;
import com.example.tp3.dominio.Evolucion;
import com.example.tp3.dominio.Medico;
import com.example.tp3.dominio.Paciente;
import com.example.tp3.repositorio.RepositorioPaciente;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class editarEvolucionAHistoriaClinicaDeUnPacienteStepdefs {
    private Medico medico;
    private int dniPaciente;
    private String diagnosticoBuscado;
    private Paciente paciente;
    private String modificacion;
    private String informeOriginal;
    private Paciente pacienteResultado;
    private RuntimeException error;
    private LocalDateTime ahora;
    private RepositorioPaciente repositorioPaciente;
    private SistemaClinica sistemaClinica;

    @Before
    public void setUp() {
        this.medico = null;
        this.paciente = null;
        this.pacienteResultado = null;
        this.error = null;
        this.ahora = LocalDateTime.now();

        this.repositorioPaciente = mock(RepositorioPaciente.class);
        this.sistemaClinica = new SistemaClinica(repositorioPaciente);

    }

    @Given("el medico {string} esta autenticado")
    public void elMedicoEstaAutenticado(String nombreMedico) {
        this.medico = new Medico(nombreMedico);
    }

    @And("ha buscado el diagnostico {string} en la historia clinica del paciente con dni {int} que cuenta con los siguientes diagnosticos:")
    public void haBuscadoElDiagnosticoEnLaHistoriaClinicaDelPacienteConDniQueCuentaConLosSiguientesDiagnosticos(String diagnosticoBuscado, int dniPaciente, List<String> diagnosticosPreexistentes) {
        this.dniPaciente = dniPaciente;
        this.diagnosticoBuscado = diagnosticoBuscado;
        Paciente paciente = new Paciente(dniPaciente, "Juan Perez", diagnosticosPreexistentes);
        when(this.repositorioPaciente.buscarPacientePorDni(dniPaciente)).thenReturn(Optional.of(paciente));
        this.paciente = paciente;
    }

    @Given("la ultima evolucion con informe {string} fue agregada hace {string} horas")
    public void laUltimaEvolucionConInformeFueAgregadoHaceHoras(String informe, String horas) {
        this.informeOriginal = informe;
        this.paciente.agregarEvolucion(diagnosticoBuscado, this.medico, informe, this.ahora.minusHours(Long.parseLong(horas)));
    }

    @When("el medico escribe las modificaciones {string} que desea hacer sobre la ultima evolucion")
    public void elMedicoEscribeLasModificacionesQueDeseaHacerSobreLaUltimaEvolucion(String modificacion) {
        this.modificacion = modificacion;
    }

    @And("el medico confirma los cambios")
    public void elMedicoConfirmaLosCambios() {
        try {
            this.pacienteResultado = this.sistemaClinica.editarEvolucion(
                    this.medico,
                    this.dniPaciente,
                    this.diagnosticoBuscado,
                    this.informeOriginal,
                    this.modificacion,
                    this.ahora);
        } catch (RuntimeException e) {
            this.error = e;
        }
    }

    @Then("se indica que no se puede editar la evolucion")
    public void seIndicaQueNoSePuedeEditarLaEvolucion() {
        assertEquals("No se puede editar una evolucion pasadas 48 horas", this.error.getMessage());
    }

    @Then("se guarda los cambios efectuados sobre la evolucion")
    public void seGuardaLosCambiosEfectuadosSobreLaEvolucion() {
        assertThat(this.pacienteResultado).isEqualTo(this.paciente);
        verify(this.repositorioPaciente, times(1)).actualizarPaciente(any());
    }

    @And("el medico puede visualizar la historia clinica del paciente editada")
    public void elMedicoPuedeVisualizarLaHistoriaClinicaDelPacienteEditada() {
        Evolucion evolucionModificada = this.pacienteResultado.buscarDiagnostico(this.diagnosticoBuscado).buscarEvolucion(this.modificacion);
        assertThat(evolucionModificada).isNotNull();
        assertThat(evolucionModificada.getInforme()).isEqualTo(this.modificacion);
    }
}
