DROP PROCEDURE IF EXISTS sp_ConsultaMedicosPorEspecialidadReserva;
DELIMITER $$
CREATE PROCEDURE sp_ConsultaMedicosPorEspecialidadReserva
(
IN idEspecialidad bigint,
IN filtoBusqueda varchar(32)
)
BEGIN
	SELECT m.id, m.nombre, m.primerApellido, m.segundoApellido
	FROM medicos m
	JOIN (
		select d.idMedico
		from disponibilidadmedicos d
		where d.estado = 4
		group by d.idMedico
	) di on m.id = di.idMedico
	where m.estado = 4 and m.idEspecialidad = idEspecialidad
	and lower(CONCAT(m.nombre, ' ', m.primerApellido, ' ', m.segundoApellido)) LIKE lower(filtoBusqueda);
END$$
DELIMITER ;

CALL sp_ConsultaMedicosPorEspecialidadReserva(4,'%lópez%')