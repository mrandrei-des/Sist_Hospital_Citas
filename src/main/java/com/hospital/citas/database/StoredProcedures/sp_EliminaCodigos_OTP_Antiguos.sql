-- SP PARA ELIMINAR LOS CÓDIGOS OTP GENERADOS POR X USUARIO
DELIMITER $$
CREATE PROCEDURE sp_EliminaCodigos_OTP_Antiguos (
IN idUsuario bigint
)
BEGIN
	DELETE FROM CodigosRecuperacionContrasenna_Activos
    WHERE idUsuario = idUsuario;
END$$
DELIMITER ;