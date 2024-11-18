Feature: Editar Evolucion a Historia Clinica de un Paciente

  Background:
    Given el medico "Dr House" esta autenticado
    And ha buscado el diagnostico "Angina" en la historia clinica del paciente con dni 123456789 que cuenta con los siguientes diagnosticos:
    | Gripe |
    | Fiebre |
    | Dolor de cabeza |
    | Angina |

  Scenario Outline: Editar un evolucion despues de 48 horas de ser agregada
    Given la ultima evolucion con informe "Informe" fue agregada hace "<horas>" horas
    When el medico escribe las modificaciones "<modificacion>" que desea hacer sobre la ultima evolucion
    And el medico confirma los cambios
    Then se indica que no se puede editar la evolucion
    Examples:
    Examples:
        | modificacion | horas |
        | El paciente se encuentra mejor | 48 |
        | El paciente se encuentra peor | 72 |
        | El paciente se encuentra igual | 96 |

  Scenario Outline: Editar una evolucion dentro de las 48 horas de ser agregada
    Given la ultima evolucion con informe "Informe" fue agregada hace "<horas>" horas
    When el medico escribe las modificaciones "<modificacion>" que desea hacer sobre la ultima evolucion
    And el medico confirma los cambios
    Then se guarda los cambios efectuados sobre la evolucion
    And el medico puede visualizar la historia clinica del paciente editada
    Examples:
      | modificacion | horas |
      | El paciente se encuentra mejor | 24 |
      | El paciente se encuentra peor | 0 |
      | El paciente se encuentra igual | 12 |