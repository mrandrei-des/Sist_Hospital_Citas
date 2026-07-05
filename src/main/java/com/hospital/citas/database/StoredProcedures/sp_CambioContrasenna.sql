-- PROCEDIMIENTO ALMACENADO PARA ACTUALIZAR LA CONTRASEÑA DEL USUARIO
DELIMITER $$
CREATE PROCEDURE `sp_CambioContrasenna`(
IN idUsuario bigint,
IN contrasennaNueva VARCHAR(900)
)
BEGIN
	UPDATE Usuarios 
    SET ContrasennaHash = contrasennaNueva
    WHERE id = idUsuario;
END$$
DELIMITER ;