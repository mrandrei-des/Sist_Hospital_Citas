DROP PROCEDURE IF EXISTS sp_ConsultaRegistrosHorarioMedico_HorasAfueraRegistro;
DELIMITER $$
CREATE PROCEDURE sp_ConsultaRegistrosHorarioMedico_HorasAfueraRegistro
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
    AND (
    (p_horaInicio < d.horaInicioAtencion AND p_horaFin > d.horaFinAtencion) 
	OR 
	(p_horaInicio < d.horaInicioAtencion AND (p_horaFin >= d.horaInicioAtencion AND p_horaFin <= d.horaFinAtencion)
	OR 
	(p_horaInicio >= d.horaInicioAtencion AND p_horaInicio <= d.horaFinAtencion) AND p_horaFin > d.horaFinAtencion)
    );
END$$
DELIMITER ;
    
CALL sp_ConsultaRegistrosHorarioMedico_HorasAfueraRegistro (2, 1, '18:00:00', '19:30:00')