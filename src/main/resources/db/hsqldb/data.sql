INSERT INTO users(username,password,enabled) VALUES ('cliente1','cliente1',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (4,'cliente1','cliente');

INSERT INTO users(username,password,enabled) VALUES ('cliente2','cliente2',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (5,'cliente2','cliente');

INSERT INTO users(username,password,enabled) VALUES ('vendedor1','vendedor1',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (6,'vendedor1','vendedor');

INSERT INTO users(username,password,enabled) VALUES ('vendedor2','vendedor2',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (7,'vendedor2','vendedor');

INSERT INTO users(username,password,enabled) VALUES ('vendedor3','vendedor3',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (8,'vendedor3','vendedor');

INSERT INTO users(username,password,enabled) VALUES ('moderador1','moderador1',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (9,'moderador1','moderador');

-- Introducimos bloqueos.

INSERT INTO bloqueos VALUES (1, false, '');
INSERT INTO bloqueos VALUES (2, false, '');
INSERT INTO bloqueos VALUES (3, false, '');
INSERT INTO bloqueos VALUES (4, false, '');
INSERT INTO bloqueos VALUES (5, false, '');

-- Introducimos vendedores.

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


-- Introducimos artículos.

INSERT INTO articulos VALUES (1,'MSI', 'Prestige Evo A11M-003ES','MSI aprovecha el espíritu de exploración y creatividad al diseñar la mejor colección de computadoras portátiles: la serie Prestige. Para seguir empujando los límites creativos, estas máquinas finamente diseñadas no solo muestran un gusto único, sino que también son inmensamente poderosas. Es hora de dejar volar la inspiración y crear nuestros propios momentos en la vida. Posee un procesador Tiger lake i7-1185G7, memoria DDRIV 16GB*2 (3200MHz) y un almacenamiento 1TB NVMe PCIe Gen4x4 SSD, además de una gráfica GeForce® GTX 1650 Ti MAX Q, GDDR5 4GB.'
, 5,988.99, 10,8,'Nuevo','https://storage-asset.msi.com/global/picture/image/feature/nb/Prestige/Prestige15//mobility-nb.png', 1);
INSERT INTO articulos VALUES (2,'Lenovo', 'Ideapad', 'Lleva allá donde necesites el portátil Lenovo Ideapad 3 15IIL05 con pantalla de 15.6" Full HD y en color gris platino. Tiene un diseño ligero y fino y con él podrás realizar todas tus tareas gracias a su potente procesador. El portátil Lenovo 3 15IIL05 cuenta con un procesador Intel® Core™ i5-1035G1 de 4 núcleos, 8 subprocesos por cada uno, con velocidad máxima en turbo de 3.60 GHz con Intel® Turbo Boost y 8 GB de RAM DDR4 (2 x 4GB) de 2666 MHz con lo que no tendrás problemas tanto si quieres trabajar en multitarea como correr procesos que requieran mucho espacio. Diseñado para ser tan inteligente como el usuario, con tecnologías como Intel® Deep Learning Boost o añadiendo memoria Intel® Optane™ para obtener más capacidad de respuesta acelerando las aplicaciones que más utilizas y la tecnología Intel® Hyper-Threading te permitirá realizar más trabajo en paralelo en las aplicaciones con muchos subprocesos, completando antes las tareas. Haz todas tus tareas cotidianas sin problemas de cuelgues o ralentizaciones y sin preocuparte por la batería ya que te ofrece muchas horas de autonomía para tus sesiones.'
, 2,500.99, 20,5,'Nuevo','https://images-na.ssl-images-amazon.com/images/I/71QQz9ZPLoL._AC_SL1500_.jpg', 2);
INSERT INTO articulos VALUES (3, 'Apple', 'MacBook Pro', 'El MacBook Pro de 13 pulgadas incorpora un procesador Intel Core i5 de cuatro núcleos de décima generación para hacer en un suspiro las tareas más exigentes. Compilar código, superponer varias pistas en una composición musical o codificar vídeo, no importa la complejidad de lo que te propongas, sea lo que sea irá por la vía rápida. Además, Intel Iris Plus Graphics te da unos gráficos hasta un 80 % más rápidos que la generación anterior'
, 3,1000.99, 10,3,'Nuevo','https://img.pccomponentes.com/articles/29/291068/apple-macbook-pro-intel-core-i5-8gb-512gb-ssd-133-gris-espacial.jpg', 3);
INSERT INTO articulos VALUES (4, 'Apple', 'Iphone 12', 'Bienvenido a la experiencia del iPhone 12 de 64 GB en color negro: Pantalla OLED Super Retina XDR de 6.1 pulgadas. Tecnología 5G. Chip A14 Bionic, con una inteligencia y potencia nunca vistas, además de ser el más veloz en un smartphone. Ceramic Shield, cuatro veces más resistente a las caídas. Modo Noche en cada una de las cámaras. Sí, el iPhone 12 tiene todo lo que esperas de un iPhone, pero llevado al extremo. El móvil Apple iPhone 12 de 64 GB de capacidad lleva integrado el sistema operativo iOS 14. Incorpora también el chip A14 Bionic con Neural Engine de última generación integrado. El objetivo del Neural Engine es ampliar las posibilidades del aprendizaje automático del teléfono. Por eso puede reconocer patrones y basarse en experiencias previas para hacer predicciones. Es decir, aprende casi como tú. Además, este chip tiene una alta eficiencia energética que asegura una gran duración de la batería, hasta 17 horas de reproducción de vídeo. Y por si fuera poco, la batería es cuenta con carga rápida y es compatible con carga inalámbrica (Qi).'
, 3,909, 30,10,'Nuevo','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_78286831/fee_786_587_png/Apple-iPhone-12--Negro--64-GB--5G--6.1%22-OLED-Super-Retina-XDR--Chip-A14-Bionic--iOS', 4);
INSERT INTO articulos VALUES (5, 'Balay', '3KFE560WI', 'Frigorifico muy eficiente y es que cuenta con categoría A++ en eficiencia energética lo que es sinónimo de ahorro ya que el consumo es muy bajo. Se trata de un aparato que cuenta con hasta 346 litros de capacidad. La iluminación LED no solo consume poco, es que comparada con una bombilla convencional consume ¡hasta 10 veces menos! Y además dura más, de modo que el ahorro se multiplica. Si aún no lo ves claro, echa un vistazo al interior del frigorífico: los LED iluminan con claridad hasta el último rincón de forma progresiva, para permitir que la vista se adapte de forma natural y que ni un tomate cherry se quede olvidado al fondo del cajón.'
, 10,559, 10,8,'Nuevo','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_73890451/fee_786_587_png/Frigor%C3%ADfico-combi---Balay-3KFE560WI--42-dB--No-Frost--302-L--A----Blanco', 5);
INSERT INTO articulos VALUES (6, 'Bosch', 'WAU24T44ES', 'Esta lavadora Bosch de color blanco, te ofrece un alto rendimiento y una limpieza perfecta, gracias a sus tecnologías innovadoras. El motor EcoSilence permite un lavado y centrifugado a toda potencia pero siendo más silencioso. Además, la incorporación de unos paneles laterales antivibración garantizan mayor estabilidad de la máquina y reduce su vibración; respetando así el descanso de los tuyos.'
, 10,350, 10,4,'SemiNuevo','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_78528179/fee_786_587_png/Lavadora-carga-frontal---Bosch-WAU24T44ES--9-kg--1200-rpm--15-programas--A----%28-30%25%29--Blanco', 6);
INSERT INTO articulos VALUES (7,'Xiaomi', 'Mi TV 4A', 'Smart TV para todos con los esperados televisores Xiaomi Mi TV 4A. Disfruta de todo el contenido que quieras en este excelente televisor con potente hardware, Motion Smooth y cuerpo de metal.'
,5,120, 50,5,'Reacondicionado','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_70644742/fee_786_587_png/TV-LED-32%22---Xiaomi-Mi-TV-4A--HD--Quad-Core--Bluetooth--Android-TV--PatchWall--Google-Assistant--Chromecast', 7);
INSERT INTO articulos VALUES (8,'Xiaomi', 'Mi Band 5', 'La pulsera súper ventas de Xiaomi vuelve un año más con jugosas novedades a nuestra muñeca. La nueva Xiaomi Mi Band 5 ya está aquí. Xiaomi Mi Band 5 deja más protagonismo a la pantalla. La correa se vuelve más delgada entorno a la muñeca y podemos encontrar diferentes colores y combinaciones de estos. El panel AMOLED de la pantalla tiene mejor imagen, de los 400 nits ha pasado a los 450 nits, y así podemos ver todavía mejor su imagen en días con mucha luz. '
,0,30, 30,1,'Nuevo','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_76085795/fee_786_587_png/Pulsera-de-actividad---Xiaomi-Mi-Band-5--Negro--AMOLED-1.1%22--11-modos-deportivos--Bluetooth--Autonom%C3%ADa-14-d%C3%ADas', 8);
INSERT INTO articulos VALUES (9, 'Razer', 'Kraken X', 'Comodidad ultraligera para jugar sin parar. Nunca más volverás a estar agotado por el peso de tus auriculares. El Razer Kraken X está diseñado para ser ultraligero, por lo que apenas sientes que tienes algo en la cabeza, incluso cuando experimentas un audio de juego superior para una experiencia realmente envolvente.'
,3,38, 3,2,'Reacondicionado','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_73242826/fee_786_587_png', 9);
INSERT INTO articulos VALUES (10, 'Sony', 'PlayStation 5', 'Versión Digital de la consola de Sony. Déjate sorprender por unos gráficos increíbles y experimenta nuevas funciones de PS5™. Cuenta con la CPU más potente en una consola hasta la fecha: 10.3 Teraflops, 36 CUs a 2.23GHz (frecuencia variable)'
,5,400, 10,20,'Nuevo','https://i.blogs.es/8fdd98/ps51/450_1000.jpeg', 10);

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

-- Introducimos solicitudes.

INSERT INTO solicitudes VALUES (1, 'MSI aprovecha el espíritu de exploración y creatividad al diseñar la mejor colección de computadoras portátiles: la serie Prestige. Para seguir empujando los límites creativos, estas máquinas finamente diseñadas no solo muestran un gusto único, sino que también son inmensamente poderosas. Es hora de dejar volar la inspiración y crear nuestros propios momentos en la vida. Posee un procesador Tiger lake i7-1185G7, memoria DDRIV 16GB*2 (3200MHz) y un almacenamiento 1TB NVMe PCIe Gen4x4 SSD, además de una gráfica GeForce® GTX 1650 Ti MAX Q, GDDR5 4GB.'
,5,'MSI','Prestige Evo A11M-003ES',988.99,'','Aceptada',50,8,'Nuevo','https://storage-asset.msi.com/global/picture/image/feature/nb/Prestige/Prestige15//mobility-nb.png', 1, 1);
INSERT INTO solicitudes VALUES (2, 'Lleva allá donde necesites el portátil Lenovo Ideapad 3 15IIL05 con pantalla de 15.6" Full HD y en color gris platino. Tiene un diseño ligero y fino y con él podrás realizar todas tus tareas gracias a su potente procesador. El portátil Lenovo 3 15IIL05 cuenta con un procesador Intel® Core™ i5-1035G1 de 4 núcleos, 8 subprocesos por cada uno, con velocidad máxima en turbo de 3.60 GHz con Intel® Turbo Boost y 8 GB de RAM DDR4 (2 x 4GB) de 2666 MHz con lo que no tendrás problemas tanto si quieres trabajar en multitarea como correr procesos que requieran mucho espacio. Diseñado para ser tan inteligente como el usuario, con tecnologías como Intel® Deep Learning Boost o añadiendo memoria Intel® Optane™ para obtener más capacidad de respuesta acelerando las aplicaciones que más utilizas y la tecnología Intel® Hyper-Threading te permitirá realizar más trabajo en paralelo en las aplicaciones con muchos subprocesos, completando antes las tareas. Haz todas tus tareas cotidianas sin problemas de cuelgues o ralentizaciones y sin preocuparte por la batería ya que te ofrece muchas horas de autonomía para tus sesiones.'
,5,'Lenovo','Ideapad',500.99,'','Aceptada',50,8,'Nuevo','https://images-na.ssl-images-amazon.com/images/I/71QQz9ZPLoL._AC_SL1500_.jpg', 2, 1);
INSERT INTO solicitudes VALUES (3, 'Bienvenido a la experiencia del iPhone 12 de 64 GB en color negro: Pantalla OLED Super Retina XDR de 6.1 pulgadas. Tecnología 5G. Chip A14 Bionic, con una inteligencia y potencia nunca vistas, además de ser el más veloz en un smartphone. Ceramic Shield, cuatro veces más resistente a las caídas. Modo Noche en cada una de las cámaras. Sí, el iPhone 12 tiene todo lo que esperas de un iPhone, pero llevado al extremo. El móvil Apple iPhone 12 de 64 GB de capacidad lleva integrado el sistema operativo iOS 14. Incorpora también el chip A14 Bionic con Neural Engine de última generación integrado. El objetivo del Neural Engine es ampliar las posibilidades del aprendizaje automático del teléfono. Por eso puede reconocer patrones y basarse en experiencias previas para hacer predicciones. Es decir, aprende casi como tú. Además, este chip tiene una alta eficiencia energética que asegura una gran duración de la batería, hasta 17 horas de reproducción de vídeo. Y por si fuera poco, la batería es cuenta con carga rápida y es compatible con carga inalámbrica (Qi).'
,3,'Apple','Iphone 12',909,'','Aceptada',50,8,'Nuevo','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_78286831/fee_786_587_png/Apple-iPhone-12--Negro--64-GB--5G--6.1%22-OLED-Super-Retina-XDR--Chip-A14-Bionic--iOS', 4, 1);
INSERT INTO solicitudes VALUES (4, 'Frigorifico muy eficiente y es que cuenta con categoría A++ en eficiencia energética lo que es sinónimo de ahorro ya que el consumo es muy bajo. Se trata de un aparato que cuenta con hasta 346 litros de capacidad. La iluminación LED no solo consume poco, es que comparada con una bombilla convencional consume ¡hasta 10 veces menos! Y además dura más, de modo que el ahorro se multiplica. Si aún no lo ves claro, echa un vistazo al interior del frigorífico: los LED iluminan con claridad hasta el último rincón de forma progresiva, para permitir que la vista se adapte de forma natural y que ni un tomate cherry se quede olvidado al fondo del cajón.'
,10,'Balay','3KFE560WI',559,'','Aceptada',50,8,'Nuevo','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_73890451/fee_786_587_png/Frigor%C3%ADfico-combi---Balay-3KFE560WI--42-dB--No-Frost--302-L--A----Blanco', 5, 1);
INSERT INTO solicitudes VALUES (5, 'Esta lavadora Bosch de color blanco, te ofrece un alto rendimiento y una limpieza perfecta, gracias a sus tecnologías innovadoras. El motor EcoSilence permite un lavado y centrifugado a toda potencia pero siendo más silencioso. Además, la incorporación de unos paneles laterales antivibración garantizan mayor estabilidad de la máquina y reduce su vibración; respetando así el descanso de los tuyos.'
,5,'Bosch','WAU24T44ES',350,'','Aceptada',50,8,'Nuevo','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_78528179/fee_786_587_png/Lavadora-carga-frontal---Bosch-WAU24T44ES--9-kg--1200-rpm--15-programas--A----%28-30%25%29--Blanco', 6, 2);
INSERT INTO solicitudes VALUES (6, 'Smart TV para todos con los esperados televisores Xiaomi Mi TV 4A. Disfruta de todo el contenido que quieras en este excelente televisor con potente hardware, Motion Smooth y cuerpo de metal.'
,5,'Xiaomi','Mi TV 4A',120,'','Aceptada',50,8,'Nuevo','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_70644742/fee_786_587_png/TV-LED-32%22---Xiaomi-Mi-TV-4A--HD--Quad-Core--Bluetooth--Android-TV--PatchWall--Google-Assistant--Chromecast', 7, 2);							
INSERT INTO solicitudes VALUES (7, 'La pulsera súper ventas de Xiaomi vuelve un año más con jugosas novedades a nuestra muñeca. La nueva Xiaomi Mi Band 5 ya está aquí. Xiaomi Mi Band 5 deja más protagonismo a la pantalla. La correa se vuelve más delgada entorno a la muñeca y podemos encontrar diferentes colores y combinaciones de estos. El panel AMOLED de la pantalla tiene mejor imagen, de los 400 nits ha pasado a los 450 nits, y así podemos ver todavía mejor su imagen en días con mucha luz. '
,5,'Xiaomi','Mi Band 5',20,'','Aceptada',50,8,'Nuevo','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_76085795/fee_786_587_png/Pulsera-de-actividad---Xiaomi-Mi-Band-5--Negro--AMOLED-1.1%22--11-modos-deportivos--Bluetooth--Autonom%C3%ADa-14-d%C3%ADas', 8, 3);
INSERT INTO solicitudes VALUES (8, 'Comodidad ultraligera para jugar sin parar. Nunca más volverás a estar agotado por el peso de tus auriculares. El Razer Kraken X está diseñado para ser ultraligero, por lo que apenas sientes que tienes algo en la cabeza, incluso cuando experimentas un audio de juego superior para una experiencia realmente envolvente.'
,5,'Razer','Kraken X',38,'','Aceptada',50,8,'Nuevo','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_73242826/fee_786_587_png', 9, 3);
INSERT INTO solicitudes VALUES (9, 'Versión Digital de la consola de Sony. Déjate sorprender por unos gráficos increíbles y experimenta nuevas funciones de PS5™. Cuenta con la CPU más potente en una consola hasta la fecha: 10.3 Teraflops, 36 CUs a 2.23GHz (frecuencia variable)'
,5,'Sony','PlayStation 5',400,'','Aceptada',50,8,'Nuevo','https://i.blogs.es/8fdd98/ps51/450_1000.jpeg', 10, 1);
INSERT INTO solicitudes VALUES (10, 'Cubre todas tus necesidades informáticas diarias con el portátil Aspire One. Su combinación de potente tecnología y características de cuidado diseño lo convierten en el dispositivo ideal para el trabajo o la diversión. Cuenta con un procesador Intel Core i5-1035G1 (3.6GHz, 6MB) y una memoria RAM 12 GB DDR4 SDRAM.'
,5,'Acer','Aspire One',700.9,'','Denegada',50,8,'Nuevo','https://images-na.ssl-images-amazon.com/images/I/31MpIy75yyL._AC_.jpg', null, 2);
INSERT INTO solicitudes VALUES (11, 'El MacBook Pro de 13 pulgadas incorpora un procesador Intel Core i5 de cuatro núcleos de décima generación para hacer en un suspiro las tareas más exigentes. Compilar código, superponer varias pistas en una composición musical o codificar vídeo, no importa la complejidad de lo que te propongas, sea lo que sea irá por la vía rápida. Además, Intel Iris Plus Graphics te da unos gráficos hasta un 80 % más rápidos que la generación anterior'
,5,'Apple','Macbook Pro',1000.99,'','Pendiente',50,8,'Nuevo','https://images-na.ssl-images-amazon.com/images/I/71VHEQqByPL._AC_SL1500_.jpg', 3, 2);

-- Introducimos cestas

INSERT INTO cestas VALUES (1,1688.99);
INSERT INTO cestas VALUES (2,0.0);

-- Introducimos linea cestas

INSERT INTO lineas_cestas VALUES (1,1,1,1);
INSERT INTO lineas_cestas VALUES (2,2,6,1);

-- Introducimos clientes.

INSERT INTO clientes VALUES (1, 'Fernandez', 'C/Boqueron 34', '23456789', 'Juan', '988733221', 'juan@gmail.com', 1,1,'cliente1');
INSERT INTO clientes VALUES (2, 'Martin', 'C/Konquero 4', '23456119', 'Francisco', '988733111', 'martin@gmail.com', 2,2,'cliente2');

-- Introducimos comentarios.

INSERT INTO comentarios VALUES (1,'Lo compré hará 1 año y todo va fenomenal, pero a veces noto que la batería se agota demasiado rápdio',3,1,1,null,null);
INSERT INTO comentarios VALUES (2,'Funciona perfectamente y es muy fácil de usar ',5,6,1,null,null);
INSERT INTO comentarios VALUES (3,'No recomendado para nada, se ha necesitado más de 5 reparaciones en 1 año.',5,1,1,null,null);
INSERT INTO comentarios VALUES (4,'Estaba deseando comprarla :-)',5,10,2,null,null);
INSERT INTO comentarios VALUES (5,'Pronto aumentaremos el stock de este producto para que no te quedes sin ninguno',0,3,null,null,1);
INSERT INTO comentarios VALUES (6,'Estamos viendo varios insultos en los comentarios de estos artículos, todo aquel que insulte se le bloqueará',0,5,null,1,null);
INSERT INTO comentarios VALUES (7,'¿Sabéis si este ordenador corre el fortnite?',5,1,2,null,null);
INSERT INTO comentarios VALUES (8,'Pronto se vendrá una nueva oferta de este producto.',0,1,null,null,1);
INSERT INTO comentarios VALUES (9,'Está chida esta lavadora',4,6,2,null,null);

-- Introducimos tarjetas de crédito

INSERT INTO tarjetas_credito VALUES (1,'442','03/22','1234567899876087','Juan Fernández Tirado');

-- Introducimos relaciones tarjeta-cliente

INSERT INTO clientes_tarjetas VALUES (1,1)