-- SP PARA GUARDAR UN RESPALDO DEL CÓDIGO OTP QUE SE USÓ
DELIMITER $$
CREATE PROCEDURE sp_InsertaCodigo_OTP_Usado (
IN codigoGenerado VARCHAR(500),
IN idUsuario bigint
)
BEGIN
	INSERT INTO CodigosRecuperacionContrasenna_Usados (codigoGenerado, idUsuario, fechaHoraUso)
    VALUES(codigoGenerado, idUsuario, NOW());
END$$
DELIMITER ;