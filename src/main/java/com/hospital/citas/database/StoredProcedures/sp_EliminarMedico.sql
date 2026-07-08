DROP PROCEDURE IF EXISTS sp_EliminarMedico;
DELIMITER $$
CREATE PROCEDURE `sp_EliminarMedico`(IN idMedico BIGINT)
BEGIN
    UPDATE medicos m
    SET m.Estado = 6
    WHERE m.id = idMedico;
END$$
DELIMITER ;