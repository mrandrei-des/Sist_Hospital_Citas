package com.hospital.citas;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class prueba {

    /*
        HAY DOS TIPOS DE RESTRICCIONES
            1. En general, todos los espacios reservados entre todos los pacientes
                consultar si para X día ya hay reservas:
                    si no continua
                    si si entonces
                        consultar todas las reservas de ese día y excluir esas horas de la lista completa de espacios de reserva

            2. Por usuario, el usuario reserva un día a hora con un médico, no puede reservar ese mismo día a la misma hora con otro doctor
                consultar si para el día que intenta reservar ese usuario ya tiene reservas:
                    si no continua
                    si si entonces
                        consultar las horas de las reservas hechas de ese día y excluirlas de todos los espacios de todos los médicos
    */
    public static void main(String[] args) {
        List<String> listaPorRevisar = new ArrayList<>();
        listaPorRevisar.add("08:00");
        listaPorRevisar.add("08:30");
        listaPorRevisar.add("09:00");
        listaPorRevisar.add("09:30");
        listaPorRevisar.add("10:00");
        listaPorRevisar.add("10:30");
        listaPorRevisar.add("11:00");
        listaPorRevisar.add("11:30");
        listaPorRevisar.add("12:00");
        listaPorRevisar.add("12:30");

        List<String> listaEspaciosOcupados = new ArrayList<>();
        listaEspaciosOcupados.add("14:00");
        listaEspaciosOcupados.add("08:00");
        listaEspaciosOcupados.add("09:30");

        List<String> listaEspaciosYaReservados = new ArrayList<>();
        listaEspaciosOcupados.add("14:00");
        listaEspaciosOcupados.add("08:00");
        listaEspaciosOcupados.add("09:30");

        HashSet<String> reestriccionesEspaciosOcupados = new HashSet<String>();
        for (String reestriccion : listaEspaciosOcupados) {
            reestriccionesEspaciosOcupados.add(reestriccion);
        }

        List<String> listaEspaciosDisponibles = new ArrayList<>();
        for (String espacio : listaPorRevisar) {
            if(!reestriccionesEspaciosOcupados.contains(espacio)) listaEspaciosDisponibles.add(espacio);
        }

        if (listaEspaciosDisponibles.size() > 0) {
            for (String espacio : listaEspaciosDisponibles) {
                // System.out.println(espacio);
            }
        }

        LocalDate fecha = LocalDate.of(2026, 7, 1) ;
        LocalTime hora = LocalTime.of(19, 30);
        LocalDateTime fechaHoraCita = LocalDateTime.of(fecha, hora);
        LocalDateTime fechaHoraActual = LocalDateTime.of(2026, 8, 1, 18, 0);

        Long totalHoras = ChronoUnit.HOURS.between(fechaHoraActual, fechaHoraCita);
        System.out.println("Cantidad de horas: " + totalHoras);
    }
}