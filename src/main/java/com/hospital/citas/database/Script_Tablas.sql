USE sist_hospital_citas;

-- TABLA ESTADOS para cualquier registro que lo necesite
CREATE TABLE Estados (
id bigint NOT NULL auto_increment PRIMARY KEY,
descripcion VARCHAR(30) NOT NULL
);

-- TABLA TIPO IDENTIFICACIONES para registro usuarios
CREATE TABLE TipoIdentificaciones (
id bigint NOT NULL auto_increment PRIMARY KEY,
descripcion VARCHAR(30) NOT NULL
);

-- TABLA ACCIONES EN EL SISTEMA para bitácoras
CREATE TABLE AccionesEnSistema (
id bigint NOT NULL auto_increment PRIMARY KEY,
nombreAccion VARCHAR(30) NOT NULL UNIQUE,
descripcion VARCHAR(30) NOT NULL
);

-- TABLA ROLES EN EL SISTEMA para usuarios
CREATE TABLE Roles (
id bigint NOT NULL auto_increment PRIMARY KEY,
descripcion VARCHAR(30) NOT NULL,
estado bigint NOT NULL,
FOREIGN KEY (estado) REFERENCES Estados(id)
);

-- TABLA BITÁCORA DE CAMBIOS REALIZADOS A LOS ROLES
CREATE TABLE BitacoraCambiosRoles (
id bigint NOT NULL auto_increment PRIMARY KEY,
idAccion bigint NOT NULL,
idRolAfectado bigint NOT NULL,
descripcionAccion VARCHAR(500) NOT NULL,
idUsuarioRealizoAccion bigint NOT NULL,
fechaHoraAccion datetime NOT NULL,
FOREIGN KEY (idAccion) REFERENCES Accionesensistema(id),
FOREIGN KEY (idRolAfectado) REFERENCES Roles(id),
FOREIGN KEY (idUsuarioRealizoAccion) REFERENCES Usuarios(id)
);

-- TABLA USUARIOS 
CREATE TABLE Usuarios (
id bigint NOT NULL auto_increment PRIMARY KEY,
idTipoIdentificacion bigint NOT NULL,
identificacion VARCHAR(30) NOT NULL UNIQUE,
ContrasennaHash VARCHAR(900) NOT NULL,
nombre VARCHAR(30) NOT NULL,
primerApellido VARCHAR(30) NOT NULL,
segundoApellido VARCHAR(30) NOT NULL,
correoElectronico VARCHAR(100) NOT NULL UNIQUE,
estado bigint NOT NULL,
idRol bigint NOT NULL,
FOREIGN KEY (idTipoIdentificacion) REFERENCES TipoIdentificaciones (id),
FOREIGN KEY (estado) REFERENCES Estados (id),
FOREIGN KEY (idRol) REFERENCES Roles (id)
);

-- TABLA BITÁCORA DE CAMBIOS REALIZADOS AL USUARIO
CREATE TABLE BitacoraCambiosUsuario (
id bigint NOT NULL auto_increment PRIMARY KEY,
idAccion bigint NOT NULL,
idUsuarioAfectado bigint NOT NULL,
descripcionAccion VARCHAR(500) NOT NULL,
idUsuarioRealizoAccion bigint NOT NULL,
fechaHoraAccion datetime NOT NULL,
FOREIGN KEY (idAccion) REFERENCES Accionesensistema(id),
FOREIGN KEY (idUsuarioAfectado) REFERENCES Usuarios(id),
FOREIGN KEY (idUsuarioRealizoAccion) REFERENCES Usuarios(id)
);

-- TABLA de configuración para los Códigos de recuperación de contraseña
CREATE TABLE CodigosRecuperacionContrasenna_Config (
id bigint NOT NULL auto_increment PRIMARY KEY,
descripcion VARCHAR(100) NOT NULL,
valorInt INT NULL,
ValorVarchar VARCHAR(100) NULL,
ValorDate DATETIME NULL
);

-- TABLA de Códigos generados para recuperar la contraseña
CREATE TABLE CodigosRecuperacionContrasenna_Generados (
id bigint NOT NULL auto_increment PRIMARY KEY,
codigoGenerado bigint NOT NULL,
idUsuario bigint NOT NULL,
fechaHoraGeneracion DATETIME NOT NULL,
FOREIGN KEY (idUsuario) REFERENCES Usuarios(id)
);

-- TABLA de Códigos de recuperación de contraseña que se encuentran activos y vigentes
CREATE TABLE CodigosRecuperacionContrasenna_Activos (
id bigint NOT NULL auto_increment PRIMARY KEY,
codigoGenerado bigint NOT NULL,
idUsuario bigint NOT NULL,
fechaHoraExpiracion DATETIME NOT NULL,
FOREIGN KEY (idUsuario) REFERENCES Usuarios(id)
);

-- TABLA de Códigos de recuperación de contraseña que vencieron
CREATE TABLE CodigosRecuperacionContrasenna_Expirados (
id bigint NOT NULL auto_increment PRIMARY KEY,
codigoGenerado bigint NOT NULL,
idUsuario bigint NOT NULL,
fechaHoraExpiro DATETIME NOT NULL,
FOREIGN KEY (idUsuario) REFERENCES Usuarios(id)
);

-- TABLA de Códigos de recuperación de contraseña que sí se utilizaron
CREATE TABLE CodigosRecuperacionContrasenna_Usados (
id bigint NOT NULL auto_increment PRIMARY KEY,
codigoGenerado bigint NOT NULL,
idUsuario bigint NOT NULL,
fechaHoraUso DATETIME NOT NULL,
FOREIGN KEY (idUsuario) REFERENCES Usuarios(id)
);

-- TABLA de Grupos de permisos, corresponde a la gerarquía más alta de los permisos, cada permiso debe pertenecer a un grupo
CREATE TABLE GruposPermisos (
id bigint NOT NULL auto_increment PRIMARY KEY,
descripcion VARCHAR(100) NOT NULL,
estado bigint NOT NULL,
FOREIGN KEY (estado) REFERENCES Estados(id)
);

-- TABLA de Permisos para usuarios, cada permiso representa una página que se puede acceder
CREATE TABLE Permisos (
id bigint NOT NULL auto_increment PRIMARY KEY,
titulo VARCHAR(100) NOT NULL UNIQUE,
descripcion VARCHAR(200) NOT NULL,
estado bigint NOT NULL,
idGrupoPermiso bigint NOT NULL,
FOREIGN KEY (estado) REFERENCES Estados(id),
FOREIGN KEY (idGrupoPermiso) REFERENCES GruposPermisos(id)
);

-- TABLA BITÁCORA DE CAMBIOS REALIZADOS A LOS PERMISOS
CREATE  TABLE BitacoraCambiosPermisos (
id bigint NOT NULL auto_increment PRIMARY KEY,
idAccion bigint NOT NULL,
idPermisoAfectado bigint NOT NULL,
descripcionAccion VARCHAR(500) NOT NULL,
idUsuarioRealizoAccion bigint NOT NULL,
fechaHoraAccion datetime NOT NULL,
FOREIGN KEY (idAccion) REFERENCES Accionesensistema(id),
FOREIGN KEY (idPermisoAfectado) REFERENCES Permisos(id),
FOREIGN KEY (idUsuarioRealizoAccion) REFERENCES Usuarios(id)
);

-- TABLA de Permisos por cada rol
CREATE TABLE PermisosPorRol (
id bigint NOT NULL auto_increment PRIMARY KEY,
idRol bigint NOT NULL,
idPermiso bigint NOT NULL,
idUsuarioRegistro bigint NOT NULL,
fechaHoraRegistro DATETIME NOT NULL,
FOREIGN KEY (idRol) REFERENCES Roles(id),
FOREIGN KEY (idPermiso) REFERENCES Permisos(id),
FOREIGN KEY (idUsuarioRegistro) REFERENCES Usuarios(id)
);

-- TABLA bitácora de Permisos por cada rol, útil cuando a un rol le quitan permisos
CREATE TABLE BitacoraCambiosPermisosPorRol (
id bigint NOT NULL auto_increment PRIMARY KEY,
idRolAfectado bigint NOT NULL,
idPermisoAfectado bigint NOT NULL,
idUsuarioRegistro bigint NOT NULL,
fechaHoraRegistro DATETIME NOT NULL,
idUsuarioCambio bigint NOT NULL,
fechaHoraCambio DATETIME NOT NULL,
FOREIGN KEY (idRolAfectado) REFERENCES Roles(id),
FOREIGN KEY (idPermisoAfectado) REFERENCES Permisos(id),
FOREIGN KEY (idUsuarioRegistro) REFERENCES Usuarios(id),
FOREIGN KEY (idUsuarioCambio) REFERENCES Usuarios(id)
);

-- TABLA de especialidades médicas
CREATE TABLE Especialidades (
id bigint NOT NULL auto_increment PRIMARY KEY,
descripcion VARCHAR(50) NOT NULL,
estado bigint NOT NULL,
FOREIGN KEY (estado) REFERENCES Estados(id)
);

-- TABLA BITÁCORA DE CAMBIOS REALIZADOS A LAS ESPECIALIDADES
CREATE TABLE BitacoraCambiosEspecialidades (
id bigint NOT NULL auto_increment PRIMARY KEY,
idAccion bigint NOT NULL,
idEspecialidadAfectada bigint NOT NULL,
descripcionAccion VARCHAR(500) NOT NULL,
idUsuarioRealizoAccion bigint NOT NULL,
fechaHoraAccion datetime NOT NULL,
FOREIGN KEY (idAccion) REFERENCES Accionesensistema(id),
FOREIGN KEY (idEspecialidadAfectada) REFERENCES Especialidades(id),
FOREIGN KEY (idUsuarioRealizoAccion) REFERENCES Usuarios(id)
);

-- TABLA de Médicos, cada médico debe pertenecer a una especialidad 
CREATE TABLE Medicos (
id bigint NOT NULL auto_increment PRIMARY KEY,
nombre VARCHAR(30) NOT NULL,
primerApellido VARCHAR(30) NOT NULL,
segundoApellido VARCHAR(30) NOT NULL,
idEspecialidad bigint NOT NULL,
estado bigint NOT NULL,
FOREIGN KEY (idEspecialidad) REFERENCES Especialidades(id),
FOREIGN KEY (estado) REFERENCES Estados(id)
);

-- TABLA BITÁCORA DE CAMBIOS REALIZADOS A LOS MÉDICOS
CREATE TABLE BitacoraCambiosMedicos (
id bigint NOT NULL auto_increment PRIMARY KEY,
idAccion bigint NOT NULL,
idMedicoAfectado bigint NOT NULL,
descripcionAccion VARCHAR(500) NOT NULL,
idUsuarioRealizoAccion bigint NOT NULL,
fechaHoraAccion datetime NOT NULL,
FOREIGN KEY (idAccion) REFERENCES Accionesensistema(id),
FOREIGN KEY (idMedicoAfectado) REFERENCES Medicos(id),
FOREIGN KEY (idUsuarioRealizoAccion) REFERENCES Usuarios(id)
);

-- TABLA DÍAS DE LA SEMANA
CREATE TABLE DiasDeLaSemana (
id bigint NOT NULL auto_increment PRIMARY KEY,
inicial CHAR(1) NOT NULL,
descripcion VARCHAR(25) NOT NULL
);

-- TABLA Disponibilidad de los médicos, registros que indican los espacios de atención del médico
CREATE TABLE DisponibilidadMedicos (
id bigint NOT NULL auto_increment PRIMARY KEY,
idMedico bigint NOT NULL,
idDiaSemana bigint NOT NULL,
horaInicioAtencion time NOT NULL,
horaFinAtencion time NOT NULL,
estado bigint NOT NULL,
FOREIGN KEY (idMedico) References Medicos(id),
FOREIGN KEY (idDiaSemana) References DiasDeLaSemana(id),
FOREIGN KEY (estado) References Estados(id)
);

-- TABLA BITÁCORA DE CAMBIOS REALIZADOS A LOS HORARIOS DISPONIBLES DEL MÉDICO
CREATE TABLE BitacoraCambiosDisponibilidadMedicos (
id bigint NOT NULL auto_increment PRIMARY KEY,
idAccion bigint NOT NULL,
idDisponibilidadAfectada bigint NOT NULL,
descripcionAccion VARCHAR(500) NOT NULL,
idUsuarioRealizoAccion bigint NOT NULL,
fechaHoraAccion datetime NOT NULL,
FOREIGN KEY (idAccion) REFERENCES Accionesensistema(id),
FOREIGN KEY (idDisponibilidadAfectada) REFERENCES DisponibilidadMedicos(id),
FOREIGN KEY (idUsuarioRealizoAccion) REFERENCES Usuarios(id)
);
