DROP PROCEDURE IF EXISTS sp_ConsultaEspecialidadesConMedicos;
DELIMITER $$
CREATE PROCEDURE sp_ConsultaEspecialidadesConMedicos
(
IN p_estado bigint
)
BEGIN
	SELECT e.id, e.descripcion, e.estado
	FROM ESPECIALIDADES e
	LEFT JOIN MEDICOS m on e.id = m.idEspecialidad AND e.Estado = p_estado and m.estado = p_estado
	WHERE m.id IS NOT NULL
	ORDER BY e.id ASC;
END$$
DELIMITER $$ ;
