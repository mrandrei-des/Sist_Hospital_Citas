package com.hospital.citas.service;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.citas.exceptions.ReporteCitaCsvException;
import com.hospital.citas.model.dto.CitasMedicasFiltrosDTO;
import com.hospital.citas.model.dto.ReporteCitaCsvDTO;
import com.hospital.citas.repository.ReservaCitasRepository;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

// Servicio para la confección de los datos formateados para el reporte y exportación de citas médicas.
@Service
public class ReporteCitaService {
    private final ReservaCitasRepository reservaCitasRepository;

    ReporteCitaService(ReservaCitasRepository reservaCitasRepository) {
        this.reservaCitasRepository = reservaCitasRepository;
    }

    // Consulta la información aplicando los filtros utilizados por el usuario y construye el arreglo de bytes ya con los separadores.
    // Utilizado en la exportación a CSV del reporte de citas médicas.
    public byte[] generarReporteCitasCsv(CitasMedicasFiltrosDTO citasFiltro) {
        List<ReporteCitaCsvDTO> listadoCitas = reservaCitasRepository.consultaCitasReporteCsv(citasFiltro.getFiltEstado(), citasFiltro.getFiltEspecialidad(), citasFiltro.getFiltMedico(), citasFiltro.getFiltFechaInicio(), citasFiltro.getFiltFechaFin());

        try (StringWriter writer = new StringWriter()) {
            StatefulBeanToCsv<ReporteCitaCsvDTO> beanParaCsv = new StatefulBeanToCsvBuilder<ReporteCitaCsvDTO>(writer)
                    .withSeparator(',')
                    .build();
            beanParaCsv.write(listadoCitas);
            return writer.toString().getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new ReporteCitaCsvException("Error al generar el reporte de citas en CSV.");
        }
    }
}
