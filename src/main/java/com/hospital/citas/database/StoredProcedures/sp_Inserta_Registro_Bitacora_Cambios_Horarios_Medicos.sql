DROP PROCEDURE IF EXISTS sp_Inserta_Registro_Bitacora_Cambios_Horarios_Medicos;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_Inserta_Registro_Bitacora_Cambios_Horarios_Medicos`(
IN idAccion bigint, 
IN idDisponibilidadAfectada bigint, 
IN descripcionAccion VARCHAR(500),
IN idUsuarioRealizoAccion bigint
)
BEGIN
	INSERT INTO bitacoracambiosdisponibilidadmedicos(idAccion, idDisponibilidadAfectada, descripcionAccion, idUsuarioRealizoAccion, fechaHoraAccion)
    VALUES(idAccion, idDisponibilidadAfectada, descripcionAccion, idUsuarioRealizoAccion, NOW());
END$$
DELIMITER ;
