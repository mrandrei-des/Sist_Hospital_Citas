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

/*
	IMPLEMENTAR LAS VALIDACIONES PARA EXCLUIR ALGUNOS HORARIOS SEGÚN RESTRICCIONES
	REVISAR EL CALCULO DE LAS FECHAS PORQUE NO MOSTRÓ LA FECHAS CORRECTAS
	FALTA IMPLEMENTAR LA RESERVA DE CITAS
	revisar reglas de negocio
*/