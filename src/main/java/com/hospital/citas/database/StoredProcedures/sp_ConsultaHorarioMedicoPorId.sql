DROP PROCEDURE IF EXISTS sp_ConsultaHorarioMedicoPorId;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ConsultaHorarioMedicoPorId`(IN idMedico bigint)
BEGIN
	SELECT d.idMedico, d.idDiaSemana, s.descripcion as dia, d.horaInicioAtencion as horaInicio, d.horaFinAtencion as horaFin
	FROM disponibilidadmedicos d
	JOIN diasdelasemana s on d.idDiaSemana = s.id
	WHERE d.idMedico = idMedico
	ORDER BY d.idDiaSemana ASC, d.horaInicioAtencion ASC;
END$$
DELIMITER ;