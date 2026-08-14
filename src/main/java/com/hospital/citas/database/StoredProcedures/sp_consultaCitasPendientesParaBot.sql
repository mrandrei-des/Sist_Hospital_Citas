DROP PROCEDURE IF EXISTS sp_consultaCitasPendientesParaBot;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_consultaCitasPendientesParaBot`(IN fechaCorte date, horaCorte time)
BEGIN
	SELECT *
	FROM resevarcitas
	WHERE estado = 1 and fecha <= fechaCorte and hora <= horaCorte;
END$$
DELIMITER ;

call sp_consultaCitasPendientesParaBot('2026-08-05', '12:00:00');
