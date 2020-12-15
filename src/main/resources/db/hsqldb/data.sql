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

INSERT INTO users(username,password,enabled) VALUES ('vendedor3','vendedor3',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (8,'vendedor3','vendedor');

INSERT INTO users(username,password,enabled) VALUES ('moderador1','moderador1',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (9,'moderador1','moderador');

-- Introducimos un bloqueo.

INSERT INTO bloqueos VALUES (1, false, '');
INSERT INTO bloqueos VALUES (2, false, '');
INSERT INTO bloqueos VALUES (3, false, '');
INSERT INTO bloqueos VALUES (4, false, '');
INSERT INTO bloqueos VALUES (5, false, '');

-- Introducimos a un cliente.

INSERT INTO clientes VALUES (1, 'Fernandez', 'C/Boqueron 34', '23456789', 'Juan', '988733221', 'juan@gmail.com', 1,'cliente1');
INSERT INTO clientes VALUES (2, 'Martin', 'C/Konquero 4', '23456119', 'Francisco', '988733111', 'martin@gmail.com', 2,'cliente2');

-- Introducimos a un vendedor.

INSERT INTO vendedores VALUES (1, 'Lorca', 'C/Galindo 96', '29976789', 'Pepe', '678733221', 'pepe200@gmail.com', 3,'vendedor1');
INSERT INTO vendedores VALUES (2, 'Pérez', 'C/Real 2', '09456119', 'Lola', '688733111', 'lolaindigo@gmail.com', 4,'vendedor2');
INSERT INTO vendedores VALUES (3, 'López', 'C/Amargura 21', '11021873', 'Lidia', '722018928', 'lilop@gmail.com', 5,'vendedor3');

-- Introducimos a un moderador.

INSERT INTO moderadores VALUES (1, 'García', 'C/Buenavista 12', '49456789', 'Pedro', '663733221', 'moderador1');

-- Introducimos ofertas.

INSERT INTO ofertas VALUES (1, false, 5);
INSERT INTO ofertas VALUES (2, true, 50);
INSERT INTO ofertas VALUES (3, false, 5);
INSERT INTO ofertas VALUES (4, true, 20);
INSERT INTO ofertas VALUES (5, false, 5);
INSERT INTO ofertas VALUES (6, false, 5);
INSERT INTO ofertas VALUES (7, false, 5);
INSERT INTO ofertas VALUES (8, false, 5);
INSERT INTO ofertas VALUES (9, false, 5);
INSERT INTO ofertas VALUES (10, false, 5);


-- Introducimos géneros

INSERT INTO generos VALUES (1, 'Smartphones');
INSERT INTO generos VALUES (2, 'Ordenadores');
INSERT INTO generos VALUES (3, 'Tablets');
INSERT INTO generos VALUES (4, 'Consolas');
INSERT INTO generos VALUES (5, 'Smartwatches');
INSERT INTO generos VALUES (6, 'Televisores');
INSERT INTO generos VALUES (7, 'Videojuegos');
INSERT INTO generos VALUES (8, 'Audio');
INSERT INTO generos VALUES (9, 'Vídeo');
INSERT INTO generos VALUES (10, 'Multimedia');
INSERT INTO generos VALUES (11, 'Entretenimiento');
INSERT INTO generos VALUES (12, 'Electrodomésticos');
INSERT INTO generos VALUES (13, 'Frigoríficos');
INSERT INTO generos VALUES (14, 'Lavadoras');
INSERT INTO generos VALUES (15, 'Microondas');
INSERT INTO generos VALUES (16, 'Cocina');


-- Introducimos un artículo.

INSERT INTO articulos VALUES (1, 'MSI', 'Prestige Evo A11M-003ES',5,988.99, 10,8,'Nuevo','https://storage-asset.msi.com/global/picture/image/feature/nb/Prestige/Prestige15//mobility-nb.png', 1);
INSERT INTO articulos VALUES (2, 'Lenovo', 'Ideapad',2,500.99, 20,5,'Nuevo','https://images-na.ssl-images-amazon.com/images/I/71QQz9ZPLoL._AC_SL1500_.jpg', 2);
INSERT INTO articulos VALUES (3, 'Acer', 'Aspire One',3,700.99, 10,3,'Nuevo','https://images-na.ssl-images-amazon.com/images/I/31MpIy75yyL._AC_.jpg', 3);
INSERT INTO articulos VALUES (4, 'Apple', 'Iphone 12',3,909, 30,10,'Nuevo','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_78286831/fee_786_587_png/Apple-iPhone-12--Negro--64-GB--5G--6.1%22-OLED-Super-Retina-XDR--Chip-A14-Bionic--iOS', 4);
INSERT INTO articulos VALUES (5, 'Balay', '3KFE560WI',10,559, 10,8,'Nuevo','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_73890451/fee_786_587_png/Frigor%C3%ADfico-combi---Balay-3KFE560WI--42-dB--No-Frost--302-L--A----Blanco', 5);
INSERT INTO articulos VALUES (6, 'Bosch', 'WAU24T44ES',10,350, 10,4,'SemiNuevo','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_78528179/fee_786_587_png/Lavadora-carga-frontal---Bosch-WAU24T44ES--9-kg--1200-rpm--15-programas--A----%28-30%25%29--Blanco', 6);
INSERT INTO articulos VALUES (7, 'Xiaomi', 'Mi TV 4A',5,120, 50,5,'Reacondicionado','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_70644742/fee_786_587_png/TV-LED-32%22---Xiaomi-Mi-TV-4A--HD--Quad-Core--Bluetooth--Android-TV--PatchWall--Google-Assistant--Chromecast', 7);
INSERT INTO articulos VALUES (8, 'Xiaomi', 'Mi Band 5',0,30, 30,1,'Nuevo','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_76085795/fee_786_587_png/Pulsera-de-actividad---Xiaomi-Mi-Band-5--Negro--AMOLED-1.1%22--11-modos-deportivos--Bluetooth--Autonom%C3%ADa-14-d%C3%ADas', 8);
INSERT INTO articulos VALUES (9, 'Razer', 'Kraken X',3,38, 3,2,'Reacondicionado','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_73242826/fee_786_587_png', 9);
INSERT INTO articulos VALUES (10, 'Sony', 'PlayStation 5',5,400, 10,20,'Nuevo','https://i.blogs.es/8fdd98/ps51/450_1000.jpeg', 10);

-- Introducimos relaciones articulos-generos

INSERT INTO articulos_generos VALUES (1,2);
INSERT INTO articulos_generos VALUES (1,11);
INSERT INTO articulos_generos VALUES (2,2);
INSERT INTO articulos_generos VALUES (2,11);
INSERT INTO articulos_generos VALUES (3,2);
INSERT INTO articulos_generos VALUES (3,11);
INSERT INTO articulos_generos VALUES (4,1);
INSERT INTO articulos_generos VALUES (5,12);
INSERT INTO articulos_generos VALUES (5,13);
INSERT INTO articulos_generos VALUES (5,16);
INSERT INTO articulos_generos VALUES (6,14);
INSERT INTO articulos_generos VALUES (6,12);
INSERT INTO articulos_generos VALUES (7, 9);
INSERT INTO articulos_generos VALUES (7,11);
INSERT INTO articulos_generos VALUES (7,6);
INSERT INTO articulos_generos VALUES (8,5);
INSERT INTO articulos_generos VALUES (9,8);
INSERT INTO articulos_generos VALUES (9,10);
INSERT INTO articulos_generos VALUES (10,7);
INSERT INTO articulos_generos VALUES (10,4);
INSERT INTO articulos_generos VALUES (10,11);

-- Introducimos una solicitud.

INSERT INTO solicitudes VALUES (1, 'Solicitud de venta de MSI Prestige Evo A11M-003ES',5,'MSI','Prestige Evo A11M-003ES',988.99,
									'','Aceptada',50,8,'Nuevo','https://storage-asset.msi.com/global/picture/image/feature/nb/Prestige/Prestige15//mobility-nb.png', 1, 1);
INSERT INTO solicitudes VALUES (2, 'Solicitud de venta de Lenovo Ideapad',5,'Lenovo','Ideapad',500.99,
									'','Aceptada',50,8,'Nuevo','https://images-na.ssl-images-amazon.com/images/I/71QQz9ZPLoL._AC_SL1500_.jpg', 2, 1);
INSERT INTO solicitudes VALUES (3, 'Solicitud de venta de Iphone 12',3,'Apple','Iphone 12',909,
									'','Aceptada',50,8,'Nuevo','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_78286831/fee_786_587_png/Apple-iPhone-12--Negro--64-GB--5G--6.1%22-OLED-Super-Retina-XDR--Chip-A14-Bionic--iOS', 4, 1);
INSERT INTO solicitudes VALUES (4, 'Solicitud de venta de Frigorifico Balay',10,'Balay','3KFE560WI',559,
									'','Aceptada',50,8,'Nuevo','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_73890451/fee_786_587_png/Frigor%C3%ADfico-combi---Balay-3KFE560WI--42-dB--No-Frost--302-L--A----Blanco', 5, 1);
INSERT INTO solicitudes VALUES (5, 'Solicitud de venta de Lavadora Bosch',5,'Bosch','WAU24T44ES',350,
									'','Aceptada',50,8,'Nuevo','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_78528179/fee_786_587_png/Lavadora-carga-frontal---Bosch-WAU24T44ES--9-kg--1200-rpm--15-programas--A----%28-30%25%29--Blanco', 6, 2);
INSERT INTO solicitudes VALUES (6, 'Solicitud de venta de Xiaomi Mi TV',5,'Xiaomi','Mi TV 4A',120,
									'','Aceptada',50,8,'Nuevo','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_70644742/fee_786_587_png/TV-LED-32%22---Xiaomi-Mi-TV-4A--HD--Quad-Core--Bluetooth--Android-TV--PatchWall--Google-Assistant--Chromecast', 7, 2);							
INSERT INTO solicitudes VALUES (7, 'Solicitud de venta de Xiaomi Mi Band 5',5,'Xiaomi','Mi Band 5',20,
									'','Aceptada',50,8,'Nuevo','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_76085795/fee_786_587_png/Pulsera-de-actividad---Xiaomi-Mi-Band-5--Negro--AMOLED-1.1%22--11-modos-deportivos--Bluetooth--Autonom%C3%ADa-14-d%C3%ADas', 8, 3);
INSERT INTO solicitudes VALUES (8, 'Solicitud de venta de Razer Kraken X',5,'Razer','Kraken X',38,
									'','Aceptada',50,8,'Nuevo','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_73242826/fee_786_587_png', 9, 3);
INSERT INTO solicitudes VALUES (9, 'Solicitud de venta de PS5',5,'Sony','PlayStation 5',400,
									'','Aceptada',50,8,'Nuevo','https://i.blogs.es/8fdd98/ps51/450_1000.jpeg', 10, 1);
INSERT INTO solicitudes VALUES (10, 'Solicitud de venta de Acer Aspire One',5,'Acer','Aspire One',700.99,
									'','Denegada',50,8,'Nuevo','https://images-na.ssl-images-amazon.com/images/I/31MpIy75yyL._AC_.jpg', 3, 2);
INSERT INTO solicitudes VALUES (11, 'Solicitud de venta de Apple Macbook Pro',5,'Apple','Macbook Pro',1000.99,
									'','Pendiente',50,8,'Nuevo','https://images-na.ssl-images-amazon.com/images/I/71VHEQqByPL._AC_SL1500_.jpg', null, 2);

-- Introducimos una solicitud.

INSERT INTO comentarios VALUES (1,'Lo compré hará 1 año y todo va fenomenal, pero a veces noto que la batería se agota demasiado rápdio',3,1,1,null,null);
INSERT INTO comentarios VALUES (2,'Funciona perfectamente y es muy fácil de usar ',5,6,1,null,null);
INSERT INTO comentarios VALUES (3,'No recomendado para nada, se ha necesitado más de 5 reparaciones en 1 año.',5,1,1,null,null);
INSERT INTO comentarios VALUES (4,'Estaba deseando comprarla :-)',5,10,2,null,null);
INSERT INTO comentarios VALUES (5,'Pronto aumentaremos el stock de este producto para que no te quedes sin ninguno',0,3,null,null,1);
INSERT INTO comentarios VALUES (6,'Estamos viendo varios insultos en los comentarios de estos artículos, todo aquel que insulte se le bloqueará',0,5,null,1,null);
INSERT INTO comentarios VALUES (7,'¿Sabéis si este ordenador corre el fortnite?',5,1,2,null,null);
INSERT INTO comentarios VALUES (8,'Pronto se vendrá una nueva oferta de este producto.',0,1,null,null,1);
INSERT INTO comentarios VALUES (9,'Está chida esta lavadora',4,6,2,null,null);

