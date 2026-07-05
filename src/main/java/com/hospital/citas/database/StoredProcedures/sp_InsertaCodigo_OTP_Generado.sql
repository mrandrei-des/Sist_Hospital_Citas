DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_InsertaCodigo_OTP_Generado`(
IN codigoGenerado VARCHAR(500),
IN idUsuario bigint
)
BEGIN
	INSERT INTO codigosrecuperacioncontrasenna_generados (codigoGenerado, idUsuario, fechaHoraGeneracion)
    VALUES(codigoGenerado, idUsuario, NOW());
END$$
DELIMITER ;