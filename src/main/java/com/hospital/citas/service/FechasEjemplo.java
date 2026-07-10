package com.hospital.citas.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FechasEjemplo {
    public static void main(String[] args) {
        // LocalDateTime fecha = LocalDateTime.now().plusDays(3);
        // LocalDateTime fechaManana = LocalDateTime.now().plusDays(1);
        // System.out.println("Fecha hora actual: " + (fechaActual));
        // System.out.println("Dia del mes: " + (fechaActual.getDayOfMonth()));
        // System.out.println("Número del día del año: " + (fechaActual.getDayOfYear()));
        // System.out.println("Nombre del día de la semana: " + (fechaActual.getDayOfWeek()));
        // // EMPIEZA EN LUNES = 1 Y TERMINA EN DOMINGO CON 7
        // System.out.println("Número del día de la semana: " + (fechaActual.getDayOfWeek().getValue()));
        // System.out.println();
        // System.out.println("Fecha de mañana: " + fechaManana);

        // System.out.println("Lunes de la semana: " + (fechaActual.minusDays(fechaActual.getDayOfWeek().getValue() - 1)));
        // System.out.println("Viernes de la semana: " + (fechaActual.plusDays(5 - fechaActual.getDayOfWeek().getValue())));

        LocalDate fecha = LocalDate.now();
        System.out.println("Fecha específica: " + (fecha));
        LocalDate lunesSemana, viernesSemana;
        List<Integer> diasSemana = List.of(1, 2, 3, 4, 5);
        List<LocalDate> fechasSemana = new ArrayList<>();

        if(fecha.getDayOfWeek().getValue() >= 6) {
            // PARA OBTENER EL LUNES Y VIERNES DE LA SEMANA SIGUIENTE SI SE CONSULTA SÁBADO O DOMINGO
            lunesSemana = fecha.plusDays((7 - fecha.getDayOfWeek().getValue() + 1));
            viernesSemana = fecha.plusDays((7 - fecha.getDayOfWeek().getValue() + 5));
        }else {
            // PARA OBTENER EL LUNES Y VIERNES DE LA SEMANA ACTUAL SI SE CONSULTA DE LUNES A VIERNES
            lunesSemana = fecha.minusDays(fecha.getDayOfWeek().getValue() - 1);
            viernesSemana = fecha.plusDays(5 - fecha.getDayOfWeek().getValue());
        }
        System.out.println("Lunes de la semana: " + (lunesSemana));
        System.out.println("Viernes de la semana: " + (viernesSemana));
        System.out.println("Número de día de la semana: " + (fecha.getDayOfWeek().getValue()));

        Integer codigoDiaActual = fecha.getDayOfWeek().getValue();
        for (Integer numeroDia : diasSemana) {
            if(numeroDia >= codigoDiaActual) fechasSemana.add(lunesSemana.plusDays(numeroDia - 1));
        }

        System.out.println("Fechas de la semana");
        for (LocalDate fechaDia : fechasSemana) {
            System.out.println(fechaDia);
        }
    }
}

/*
LUNES - 1
MARTES - 2
MIERCOLES - 3 **
JUEVES - 4
VIERNES - 5
SÁBADO - 6
DOMINGO - 7
*/



