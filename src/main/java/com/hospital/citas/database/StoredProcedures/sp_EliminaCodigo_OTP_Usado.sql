-- PROCEDIMIENTO ALMACENADO QUE ELIMINA EL CÓDIGO DE SEGURIDAD UTILIZADO
DELIMITER $$
CREATE PROCEDURE `sp_EliminaCodigo_OTP_Usado`(
IN codigoGenerado VARCHAR(500),
IN idUsuario bigint
)
BEGIN
	DELETE FROM CodigosRecuperacionContrasenna_Activos
    WHERE idUsuario = idUsuario AND CodigoGenerado = codigoGenerado;
END$$
DELIMITER ;