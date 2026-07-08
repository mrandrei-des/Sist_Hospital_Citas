DELIMITER $$
CREATE PROCEDURE `sp_listarMedicosRegistrados`()
BEGIN
	select m.id, m.nombre, m.primerApellido, m.segundoApellido, e.descripcion as Especialidad
	from medicos m
	JOIN especialidades e on m.idEspecialidad = e.id and m.estado = 4 and e.estado = 4
	ORDER BY e.id ASC, m.primerApellido ASC, m.segundoApellido ASC, m.nombre ASC;
END$$
DELIMITER ;
