DROP PROCEDURE IF EXISTS sp_ConsultaRegistrosHorarioMedico_HorasDentroRegistro;
DELIMITER $$
CREATE PROCEDURE sp_ConsultaRegistrosHorarioMedico_HorasDentroRegistro
(
IN p_idMedico bigint,
IN p_idDia bigint,
IN p_horaInicio TIME,
IN p_horaFin TIME
)
BEGIN
	SELECT d.*
	FROM Disponibilidadmedicos d
	WHERE d.idMedico = p_idMedico AND d.estado = 4 AND d.idDiaSemana = p_idDia 
    AND ((d.horaInicioAtencion <= p_horaInicio AND d.horaFinAtencion >= p_horaInicio) OR (d.horaInicioAtencion <= p_horaFin AND d.horaFinAtencion >= p_horaFin));
END$$
DELIMITER ;
    
CALL sp_ConsultaRegistrosHorarioMedico_HorasDentroRegistro (2, 2, '14:00:00', '14:30:00')