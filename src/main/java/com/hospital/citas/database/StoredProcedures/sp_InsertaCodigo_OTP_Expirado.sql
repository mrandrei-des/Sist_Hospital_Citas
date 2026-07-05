-- SP PARA GUARDAR UN RESPALDO DEL CÓDIGO OTP QUE EXPIRÓ
DELIMITER $$
CREATE PROCEDURE sp_InsertaCodigo_OTP_Expirado (
IN codigoGenerado VARCHAR(500),
IN idUsuario bigint,
IN fechaExpiro DATETIME
)
BEGIN
	INSERT INTO CodigosRecuperacionContrasenna_Expirados (codigoGenerado, idUsuario, fechaHoraExpiro)
    VALUES(codigoGenerado, idUsuario, fechaExpiro);
END$$
DELIMITER ;