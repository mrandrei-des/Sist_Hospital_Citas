DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_Inserta_Registro_Bitacora_ReservaCitas`(
IN idAccion bigint, 
IN idReservaAfectada bigint, 
IN descripcionAccion VARCHAR(500),
IN idUsuarioRealizoAccion bigint
)
BEGIN
	INSERT INTO BitacoraCambiosResevarCitas(idAccion, idReservaAfectada, descripcionAccion, idUsuarioRealizoAccion, fechaHoraAccion)
    VALUES(idAccion, idReservaAfectada, descripcionAccion, idUsuarioRealizoAccion, NOW());
END$$
DELIMITER ;