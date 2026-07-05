DELIMITER $$
CREATE PROCEDURE `sp_ConsultaFechaHoraActualServer`(
OUT fechaHoraActualServer datetime
)
BEGIN
	SELECT Now() INTO fechaHoraActualServer;
END$$
DELIMITER ;