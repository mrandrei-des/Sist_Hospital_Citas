-- VALORES INICIALES Estados
INSERT INTO Estados (descripcion)
VALUES ('Pendiente'), ('Confirmado'), ('Inactivo'), ('Activo'), ('Temporal'), ('Eliminado'), ('Cancelado');

-- VALORES INICIALES TipoIdentificaciones
INSERT INTO TipoIdentificaciones (descripcion)
VALUES ('Cédula Física'), ('Pasaporte'), ('DIMEX');

-- VALORES INICIALES AccionesEnSistema
INSERT INTO AccionesEnSistema (nombreAccion, descripcion)
VALUES ('INSERTAR', 'Ha insertado'), 
('ACTUALIZAR', 'Ha actualizadoo'), 
('ELIMINAR', 'Ha eliminado'),
('ACTIVAR', 'Ha pasado de Inactivo a Activo'), 
('INACTIVAR', 'Ha pasado de Activo a Inactivo'), 
('CONSULTAR', 'Ha consultado registros'), 
('ASIGNAR_PERMISO', 'Ha asignado permisos'), 
('ELIMINAR_PERMISO', 'Ha eliminado permisos');

-- VALORES INICIALES Roles
INSERT INTO Roles (descripcion, estado, paginaInicio)
VALUES ('Paciente', 4, 'homePaciente'), ('Administrador', 4, 'homeAdmin');

-- VALORES INICIALES Usuarios
INSERT INTO Usuarios (idTipoIdentificacion, identificacion, contrasennaHash, nombre, primerApellido, segundoApellido, correoElectronico, estado, idRol)
VALUES (1, '305310569', '123456', 'Admin', 'Admin', '', 'andrei14mesen04@gmail.com', 4, 2),
(1, '101110111', '123456', 'Paciente', 'Paciente', 'Paciente', 'paciente@gmail.com', 4, 1);

SELECT * FROM Estados;
-- VALORES INICIALES GruposPermisos
INSERT INTO GruposPermisos (descripcion, estado)
VALUES ('Usuarios', 4), ('Médicos', 4), ('Citas', 4), ('Reportes', 4);

-- VALORES INICIALES Permisos
INSERT INTO permisos (Titulo, Descripcion, estado, idGrupoPermiso)
VALUES 
('Creación de usuarios', 'Permite que el usuario pueda crear usuarios en el sistema', 4, 1),
('Mantenimiento de usuarios', 'Permite que el usuario pueda darle mantenimiento a los usuarios registrados', 4, 1),
('Listado de usuarios', 'Permite que el usuario pueda ver los usuarios registrados', 4, 1),
('Cambio Contraseña', 'Permite que el usuario pueda realizar su propio cambio de contraseña', 4, 1),
('Creación de especialidades', 'Permite que el usuario pueda crear especialidades en el sistema', 4, 2),
('Listado de especialidades', 'Permite que el usuario pueda ver las especialidades registradas', 4, 2),
('Mantenimiento de especialidades', 'Permite que el usuario pueda darle mantenimiento a las especialidades registradas', 4, 2),
('Creación de médicos', 'Permite que el usuario pueda crear médicos en el sistema', 4, 2),
('Mantenimiento de médicos', 'Permite que el usuario pueda darle mantenimiento a los médicos en el sistema', 4, 2),
('Listado de médicos', 'Permite que el usuario pueda ver los médicos registrados', 4, 2),
('Mantenimiento de horarios', 'Permite que el usuario pueda crearle y darle mantenimiento a los horarios de los médicos', 4, 2),
('Reservar de citas', 'Permite que el usuario pueda reservar citas en el sistema', 4, 3),
('Cancelar citas', 'Permite que el usuario pueda cancerlar citas en el sistema', 4, 3),
('Reporte Especialidades', 'Permite que el usuario pueda generar reportes sobre especialidades', 4, 4),
('Reporte Citas', 'Permite que el usuario pueda generar reportes sobre citas', 4, 4);

-- VALORES INICIALES PermisosPorRol
INSERT INTO PermisosPorRol (idRol, idPermiso, idUsuarioRegistro, fechaHoraRegistro)
VALUES 
(1, 4, 1, NOW()), (1, 12, 1, NOW()), (1, 13, 1, NOW()),
(2, 1, 1, NOW()), (2, 2, 1, NOW()), (2, 3, 1, NOW()), (2, 4, 1, NOW()), (2, 5, 1, NOW()), (2, 6, 1, NOW()), (2, 7, 1, NOW()), (2, 8, 1, NOW()), (2, 9, 1, NOW()), (2, 10, 1, NOW()), 
(2, 11, 1, NOW()), (2, 13, 1, NOW()),  (2, 14, 1, NOW()), (2, 15, 1, NOW());

-- VALORES INICIALES Especialidades
INSERT INTO Especialidades (descripcion, estado)
VALUES ('Cardiología', 4), ('Ortodóncia', 4);

-- VALORES INICIALES Medicos
INSERT INTO Medicos (nombre, primerApellido, segundoApellido, idEspecialidad, estado)
VALUES 
('Ana', 'Salazar', 'Muñoz', 1, 4),
('Luis', 'Mora', 'Spencer', 2, 4);

-- VALORES INICIALES DiasDeLaSemana
INSERT INTO DiasDeLaSemana (inicial, descripcion)
VALUES 
('D', 'Domingo'),
('L', 'Lunes'),
('K', 'Martes'),
('M', 'Miércoles'),
('J', 'Jueves'),
('V', 'Viernes'),
('S', 'Sábado')