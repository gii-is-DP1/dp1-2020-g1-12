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

INSERT INTO articulos VALUES (1, 'MSI', 'Prestige Evo A11M-003ES',5,988.99, 5,8,'Nuevo','https://storage-asset.msi.com/global/picture/image/feature/nb/Prestige/Prestige15//mobility-nb.png', 1);
INSERT INTO articulos VALUES (2, 'Lenovo', 'Ideapad',5,500.99, 5,8,'Nuevo','https://images-na.ssl-images-amazon.com/images/I/71QQz9ZPLoL._AC_SL1500_.jpg', 2);
INSERT INTO articulos VALUES (3, 'Acer', 'Aspire One',5,700.99, 5,8,'Nuevo','https://images-na.ssl-images-amazon.com/images/I/31MpIy75yyL._AC_.jpg', 3);
INSERT INTO articulos VALUES (4, 'Apple', 'Macbook Pro',5,1000.99, 5,8,'Nuevo','https://images-na.ssl-images-amazon.com/images/I/71VHEQqByPL._AC_SL1500_.jpg', 4);

-- Introducimos una solicitud.

INSERT INTO solicitudes VALUES (1, 'Solicitud de venta de MSI Prestige Evo A11M-003ES',5,'MSI','Prestige Evo A11M-003ES',988.99,
									'','Aceptada',5,8,'Nuevo','https://storage-asset.msi.com/global/picture/image/feature/nb/Prestige/Prestige15//mobility-nb.png', 1, 1);
INSERT INTO solicitudes VALUES (2, 'Solicitud de venta de Lenovo Ideapad',5,'Lenovo','Ideapad',500.99,
									'','Aceptada',5,8,'Nuevo','https://images-na.ssl-images-amazon.com/images/I/71QQz9ZPLoL._AC_SL1500_.jpg', 2, 1);
INSERT INTO solicitudes VALUES (3, 'Solicitud de venta de Acer Aspire One',5,'Acer','Aspire One',700.99,
									'','Denegada',5,8,'Nuevo','https://images-na.ssl-images-amazon.com/images/I/31MpIy75yyL._AC_.jpg', 3, 2);
INSERT INTO solicitudes VALUES (4, 'Solicitud de venta de Apple Macbook Pro',5,'Apple','Macbook Pro',1000.99,
									'','Pendiente',5,8,'Nuevo','https://images-na.ssl-images-amazon.com/images/I/71VHEQqByPL._AC_SL1500_.jpg', 4, 2);
