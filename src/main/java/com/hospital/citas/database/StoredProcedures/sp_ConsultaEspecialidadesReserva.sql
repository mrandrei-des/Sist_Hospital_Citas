DROP PROCEDURE IF EXISTS sp_ConsultaEspecialidadesReserva;
DELIMITER $$
CREATE PROCEDURE sp_ConsultaEspecialidadesReserva
(
IN filtoBusqueda varchar(52)
)
BEGIN
    SELECT e.id, e.descripcion, count(*) as Cantidad
	FROM especialidades e
	JOIN (
		SELECT m.idEspecialidad
		FROM medicos m
		JOIN (
			select d.idMedico
			from disponibilidadmedicos d
			where d.estado = 4
			group by d.idMedico
		) di on m.id = di.idMedico
		where m.estado = 4 
	) j on e.id = j.idEspecialidad
	WHERE e.estado = 4 and lower(e.descripcion) like lower(filtoBusqueda)
	GROUP BY e.id, e.descripcion
	ORDER BY e.id ASC;
END$$
DELIMITER ;

CALL sp_ConsultaEspecialidadesReserva('%fisiodwsdwqqd%')