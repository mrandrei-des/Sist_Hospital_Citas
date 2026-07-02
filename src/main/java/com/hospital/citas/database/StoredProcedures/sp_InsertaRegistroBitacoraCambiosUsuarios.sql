-- SP PARA GUARDAR EL REGISTRO DE BITÁCORA EN LA TABLA DE CAMBIOS USUARIO
DELIMITER $$
CREATE PROCEDURE sp_Inserta_Registro_Bitacora_Cambios_Usuarios (
IN idAccion bigint, 
IN idUsuarioAfectado bigint, 
IN descripcionAccion VARCHAR(500),
IN idUsuarioRealizoAccion bigint
)
BEGIN
	INSERT INTO bitacoracambiosusuario(idAccion, idUsuarioAfectado, descripcionAccion, idUsuarioRealizoAccion, fechaHoraAccion)
    VALUES(idAccion, idUsuarioAfectado, descripcionAccion, idUsuarioRealizoAccion, NOW());
END$$
DELIMITER ;