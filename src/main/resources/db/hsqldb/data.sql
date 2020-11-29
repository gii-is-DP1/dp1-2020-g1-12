-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'owner1','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (3,'vet1','veterinarian');
-----------------------------------------------------------------------------------

-- Other owner user, named manu1 with passwor m4nu
INSERT INTO users(username,password,enabled) VALUES ('cliente1','cliente1',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (4,'cliente1','cliente');
-- One owner user, named jesbarsig with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('cliente2','cliente2',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (5,'cliente2','cliente');
-- Other owner user, named fer1 with passwor f3r
INSERT INTO users(username,password,enabled) VALUES ('vendedor1','vendedor1',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (6,'vendedor1','vendedor');

INSERT INTO users(username,password,enabled) VALUES ('vendedor2','vendedor2',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (7,'vendedor2','vendedor');

INSERT INTO users(username,password,enabled) VALUES ('moderador1','moderador1',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (8,'moderador1','moderador');

-- Introducimos un bloqueo.

INSERT INTO bloqueos VALUES (1, false, '');
INSERT INTO bloqueos VALUES (2, false, '');
INSERT INTO bloqueos VALUES (3, false, '');
INSERT INTO bloqueos VALUES (4, false, '');

-- Introducimos a un cliente.

INSERT INTO clientes VALUES (1, 'Fernandez', 'C/Boqueron 34', '23456789', 'Juan', '988733221', 'juan@gmail.com', 1,'cliente1');
INSERT INTO clientes VALUES (2, 'Martin', 'C/Konquero 4', '23456119', 'Francisco', '988733111', 'martin@gmail.com', 2,'cliente2');

-- Introducimos a un vendedor.

INSERT INTO vendedores VALUES (1, 'Lorca', 'C/Galindo 96', '29976789', 'Pepe', '678733221', 'pepe200@gmail.com', 3,'vendedor1');
INSERT INTO vendedores VALUES (2, 'Pérez', 'C/Real 2', '09456119', 'Lola', '688733111', 'lolaindigo@gmail.com', 4,'vendedor2');

-- Introducimos a un moderador.

INSERT INTO moderadores VALUES (1, 'García', 'C/Buenavista 12', '49456789', 'Pedro', '663733221', 'moderador1');

-- Introducimos un artículo.

INSERT INTO ofertas VALUES (1, false, 5);
INSERT INTO ofertas VALUES (2, true, 50);
INSERT INTO ofertas VALUES (3, false, 5);
INSERT INTO ofertas VALUES (4, true, 20);

-- Introducimos un artículo.

INSERT INTO articulos VALUES (1, 'MSI', 'Prestige Evo A11M-003ES',5,988.99, 5,8,'Nuevo','vacía', 1);
INSERT INTO articulos VALUES (2, 'Lenovo', 'Prestige Evo',5,500.99, 5,8,'Nuevo','vacía', 2);
INSERT INTO articulos VALUES (3, 'Acer', 'Aspire One',5,700.99, 5,8,'Nuevo','vacía', 3);
INSERT INTO articulos VALUES (4, 'Apple', 'Macbook Pro',5,1000.99, 5,8,'Nuevo','vacía', 4);

-- Introducimos una solicitud.

INSERT INTO solicitudes VALUES (1, 'Solicitud de venta de MSI Prestige Evo A11M-003ES',5,'MSI','Prestige Evo A11M-003ES',988.99,
									'','Aceptada',5,8,'Nuevo','vacía', 1, 1);
INSERT INTO solicitudes VALUES (2, 'Solicitud de venta de Lenovo Prestige Evo',5,'Lenovo',' Prestige Evo',500.99,
									'','Aceptada',5,8,'Nuevo','vacía', 2, 1);
INSERT INTO solicitudes VALUES (3, 'Solicitud de venta de Acer Aspire One',5,'Acer','Aspire One',700.99,
									'','Denegada',5,8,'Nuevo','vacía', 3, 2);
INSERT INTO solicitudes VALUES (4, 'Solicitud de venta de Apple Macbook Pro',5,'Apple','Macbook Pro',1000.99,
									'','Pendiente',5,8,'Nuevo','vacía', 4, 2);

INSERT INTO vets VALUES (1, 'James', 'Carter');
INSERT INTO vets VALUES (2, 'Helen', 'Leary');
INSERT INTO vets VALUES (3, 'Linda', 'Douglas');
INSERT INTO vets VALUES (4, 'Rafael', 'Ortega');
INSERT INTO vets VALUES (5, 'Henry', 'Stevens');
INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins');

INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'cat');
INSERT INTO types VALUES (2, 'dog');
INSERT INTO types VALUES (3, 'lizard');
INSERT INTO types VALUES (4, 'snake');
INSERT INTO types VALUES (5, 'bird');
INSERT INTO types VALUES (6, 'hamster');

INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner1');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner1');
INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);



INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');

