DELIMITER $$
CREATE PROCEDURE `sp_Inserta_Registro_Bitacora_Cambios_Medicos`(
IN idAccion bigint, 
IN idMedicoAfectado bigint, 
IN descripcionAccion VARCHAR(500),
IN idUsuarioRealizoAccion bigint
)
BEGIN
	INSERT INTO bitacoracambiosmedicos(idAccion, idMedicoAfectado, descripcionAccion, idUsuarioRealizoAccion, fechaHoraAccion)
    VALUES(idAccion, idMedicoAfectado, descripcionAccion, idUsuarioRealizoAccion, NOW());
END$$
DELIMITER ;