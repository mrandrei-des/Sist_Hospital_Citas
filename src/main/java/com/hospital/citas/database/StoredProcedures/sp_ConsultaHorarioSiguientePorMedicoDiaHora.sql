DROP PROCEDURE IF EXISTS sp_ConsultaHorarioSiguientePorMedicoDiaHora;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ConsultaHorarioSiguientePorMedicoDiaHora`(IN idMedico bigint, IN idDia bigint, IN horaInicioConsulta time)
BEGIN
	SELECT d.idMedico, d.idDiaSemana, s.descripcion as dia, d.horaInicioAtencion as horaInicio, d.horaFinAtencion as horaFin
	FROM disponibilidadmedicos d
	JOIN diasdelasemana s on d.idDiaSemana = s.id
	WHERE d.idMedico = idMedico AND d.estado = 4 AND d.idDiaSemana = idDia
    AND ((d.horaInicioAtencion <= horaInicioConsulta AND horaInicioConsulta < d.horaFinAtencion) OR (horaInicioConsulta < d.horaFinAtencion))
	ORDER BY horaInicioAtencion ASC;
END$$
DELIMITER ;

CALL sp_ConsultaHorarioSiguientePorMedicoDiaHora(2, 1, '13:50:00')