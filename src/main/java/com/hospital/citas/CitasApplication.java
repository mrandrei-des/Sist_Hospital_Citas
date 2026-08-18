package com.hospital.citas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CitasApplication {
	public static void main(String[] args) {
		SpringApplication.run(CitasApplication.class, args);
	}
}

/* PARA AGREGAR PAGINACIÓN
* IMPLEMENTAR LA PAGINACIÓN EN SP, REPOSITORY, SERVICE Y CONTROLLER

* IMPLEMENTAR API QUE CONSULTE CANTIDAD TOTAL DE REGISTROS
* AGREGAR HTML DE LA PAGINACIÓN
* AGREGAR VARIABLES PARA CONTROL DE PAGINACIÓN (CANTIDAD DE REGISTROS POR PÁGINA, CANTIDAD DE PÁGINAS, PÁGINA ACTUAL)
* CONSUMIR API DE CANTIDAD TOTAL DE REGISTROS
* IMPLEMENTAR PAGINADO EN CONSUMO DE API DE CONSULTA
* CREAR FUNCIONES PARA RENDERIZAR PAGINACIÓN
* CREAR FUNCIONES PARA AVANZAR Y RETROCEDER
* ACTUALIZAR CONTADORES SI PROCEDE*
*/