DELIMITER $$
CREATE PROCEDURE sp_Inserta_Registro_Bitacora_Cambios_Especialidades (
IN idAccion bigint, 
IN idEspecialidadAfectada bigint, 
IN descripcionAccion VARCHAR(500),
IN idUsuarioRealizoAccion bigint
)
BEGIN
	INSERT INTO bitacoracambiosespecialidades(idAccion, idEspecialidadAfectada, descripcionAccion, idUsuarioRealizoAccion, fechaHoraAccion)
    VALUES(idAccion, idEspecialidadAfectada, descripcionAccion, idUsuarioRealizoAccion, NOW());
END$$
DELIMITER ;