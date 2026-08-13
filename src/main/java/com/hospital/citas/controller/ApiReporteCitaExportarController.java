package com.hospital.citas.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.citas.model.dto.CitasMedicasFiltrosDTO;
import com.hospital.citas.service.ReporteCitaService;

@RestController
@RequestMapping("/api/citas/reporte")
public class ApiReporteCitaExportarController {

    private final ReporteCitaService reporteCitaService;

    ApiReporteCitaExportarController(ReporteCitaService reporteCitaService) {
        this.reporteCitaService = reporteCitaService;
    }

    @PostMapping("/export/csv")
    public ResponseEntity<byte[]> exportToCsv(@RequestBody CitasMedicasFiltrosDTO citasFiltros) {
        byte[] infoReporte = reporteCitaService.generarReporteCitasCsv(citasFiltros);

        if (infoReporte.length == 0) {
            return ResponseEntity.notFound().build();
        }

        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        String fechaHoraGeneracion = fechaHoraActual.format(formater);
        String nombreReporte = "ReporteCitas_" + fechaHoraGeneracion + ".csv";

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
            .header("attachment; filename=" + nombreReporte)
            .body(infoReporte);
    }
}