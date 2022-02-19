/* Estructura de base de datos utilizada por Barganizer */

CREATE TABLE carta (
	id INT(3) PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARACTER SET=UTF8 COLLATE=utf8_spanish_ci;

CREATE TABLE empleado(
	id int(3) PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(20) NOT NULL,
	apellidos VARCHAR(20) NOT NULL,
	genero enum("Hombre","Mujer") NOT NULL,
	fnac DATE NOT NULL,
	fechaIngreso DATE NOT NULL,
	foto MEDIUMBLOB,
	pass BLOB NOT NULL
) ENGINE=InnoDB DEFAULT CHARACTER SET=UTF8 COLLATE=utf8_spanish_ci;

CREATE TABLE mesa(
	numero INT(3) PRIMARY KEY AUTO_INCREMENT,
	cantPersonas INT(3) NOT NULL,
	activa TINYINT(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARACTER SET=UTF8 COLLATE=utf8_spanish_ci;

CREATE TABLE alergeno(
	id INT(3) PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(70) NOT NULL,
	icono MEDIUMBLOB
) ENGINE=InnoDB DEFAULT CHARACTER SET=UTF8 COLLATE=utf8_spanish_ci;

CREATE TABLE tipo_plato(
	id INT(2) PRIMARY KEY AUTO_INCREMENT,
	nombre enum("Entrante", "Principal", "Postre", "Vegetariano") NOT NULL
) ENGINE=InnoDB DEFAULT CHARACTER SET=UTF8 COLLATE=utf8_spanish_ci;

CREATE TABLE plato(
	id INT(3) PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(80) NOT NULL,
	foto MEDIUMBLOB,
	tipo INT NOT NULL,
	precio DECIMAL(6,2) NOT NULL,
	carta INT(3),
	CONSTRAINT fk_plato_tipo FOREIGN KEY(tipo) REFERENCES tipo_plato(id),
	CONSTRAINT fk_plato_carta FOREIGN KEY(carta) REFERENCES carta(id)
) ENGINE=InnoDB DEFAULT CHARACTER SET=UTF8 COLLATE=utf8_spanish_ci;

CREATE TABLE plato_alergeno(
	id INT(3) PRIMARY KEY AUTO_INCREMENT,
	plato INT(3) NOT NULL,
	alergeno INT(3) NOT NULL,
	CONSTRAINT fk_pltalerg_plato FOREIGN KEY(plato) REFERENCES plato(id),
	CONSTRAINT fk_pltalerg_alerg FOREIGN KEY(alergeno) REFERENCES alergeno(id)
) ENGINE=InnoDB DEFAULT CHARACTER SET=UTF8 COLLATE=utf8_spanish_ci;

CREATE TABLE bebida(
	id INT(3) PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(80) NOT NULL,
	foto MEDIUMBLOB,
	precio DECIMAL(6,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARACTER SET=UTF8 COLLATE=utf8_spanish_ci;


CREATE TABLE comanda(
	id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	mesa INT(3) NOT NULL,
	plato INT(3) NOT NULL,
	cantidad INT(2) NOT NULL DEFAULT 1,
	CONSTRAINT fk_com_mesa FOREIGN KEY(mesa) REFERENCES mesa(numero),
	CONSTRAINT fk_com_pl FOREIGN KEY(plato) REFERENCES plato(id)
) ENGINE=InnoDB DEFAULT CHARACTER SET=UTF8 COLLATE=utf8_spanish_ci;

CREATE TABLE reserva(
	id INT(11) PRIMARY KEY AUTO_INCREMENT,
	mesa INT(3) NOT NULL,
	emp INT(3) NOT NULL,
	fechaHora DATETIME NOT NULL,
	cantPersonas INT(2) NOT NULL,
	CONSTRAINT fk_res_emp FOREIGN KEY(emp) REFERENCES empleado(id),
	CONSTRAINT fK_res_mesa FOREIGN KEY(mesa) REFERENCES mesa(numero)
);

INSERT INTO carta(nombre) VALUES 
("Completa"),
("Vegetariana");

/* Inserción de alérgenos */
INSERT INTO alergeno(nombre) VALUES
("Pescados blancos y rojos"),
("Crustáceos, mariscos"),
("Apio"),
("Mostaza"),
("Huevos"),
("Semillas de sésamo"),
("Cereales con gluten o trigo"),
("Moluscos, caracoles"),
("Cacahuetes"),
("Altramuces"),
("Frutos secos"),
("Lácteos"),
("Sulfitos"),
("Soja");

INSERT INTO tipo_plato (nombre) VALUES
("Entrante"),
("Principal"),
("Postre");