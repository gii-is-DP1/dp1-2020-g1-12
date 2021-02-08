
--	USUARIO:cliente1, CONTRASEÑA: cliente1
INSERT INTO users(username,password,enabled) VALUES ('cliente1','$2a$10$QSvCoF1/GTt6J3zsnBfj2ull8tjIjtYqDt/8QfmskilpIlrJz2Mse',TRUE);
INSERT INTO authorities(id, version, username,authority) VALUES (4, 1, 'cliente1','cliente');

--	USUARIO:cliente2, CONTRASEÑA: cliente2
INSERT INTO users(username,password,enabled) VALUES ('cliente2','$2a$10$onbI6GuUb5dY./ib2yaIbufspV/tx3dwqJlzEVPXpqEapq5ubcLKu',TRUE);
INSERT INTO authorities(id, version, username,authority) VALUES (5, 1, 'cliente2','cliente');

--	USUARIO:vendedor1, CONTRASEÑA: vendedor1
INSERT INTO users(username,password,enabled) VALUES ('vendedor1','$2a$10$aAOHGFZ5iSltGw65xfbgGelRGDex/Y2tQUg/ijFxsi3cUHV2jgIwu',TRUE);
INSERT INTO authorities(id, version, username,authority) VALUES (6, 1, 'vendedor1','vendedor');

--	USUARIO:vendedor2, CONTRASEÑA: vendedor2
INSERT INTO users(username,password,enabled) VALUES ('vendedor2','$2a$10$sxWe/N/XFFxNA4pWbQrs8.0g.VwZZofxJ73wGuvBFGJ.12BL4K0G6',TRUE);
INSERT INTO authorities(id, version, username,authority) VALUES (7, 1, 'vendedor2','vendedor');

--	USUARIO:vendedor3, CONTRASEÑA: vendedor3
INSERT INTO users(username,password,enabled) VALUES ('vendedor3','$2a$10$iJmKtb066Zh57qlV0USrHeaa6SVcZ8CMPXSxUN4M.1EKE.m6HvkJm',TRUE);
INSERT INTO authorities(id, version, username,authority) VALUES (8, 1, 'vendedor3','vendedor');

--	USUARIO:moderador1, CONTRASEÑA: moderador1
INSERT INTO users(username,password,enabled) VALUES ('moderador1','$2a$10$6UySYOXXzF0u2DS4/lznBOeXrO.cvGePNT1W2Nie4oVwU1RhIOC8C',TRUE);
INSERT INTO authorities(id, version, username,authority) VALUES (9, 1, 'moderador1','moderador');

--	USUARIO:cliente3, CONTRASEÑA: cliente3
INSERT INTO users(username,password,enabled) VALUES ('cliente3','$2a$10$Kb1PSml6VhRzHDCfFvcKYukEZJvD3OuCLI5tW7I8.7cehSmAAG3my',TRUE);
INSERT INTO authorities(id, version, username,authority) VALUES (10, 1, 'cliente3','cliente');

-- Introducimos bloqueos.

INSERT INTO bloqueos VALUES (1, 1, false, '');
INSERT INTO bloqueos VALUES (2, 1, false, '');
INSERT INTO bloqueos VALUES (3, 1, false, '');
INSERT INTO bloqueos VALUES (4, 1, false, '');
INSERT INTO bloqueos VALUES (5, 1, false, '');
INSERT INTO bloqueos VALUES (6, 1, true, 'Uso inadecuado de la sección de comentarios (insultos y calumnias hacia el vendedor y demás usuarios).');

-- Introducimos vendedores.

INSERT INTO vendedores VALUES (1, 1, 'Lorca', 'C/Galindo 96', '29976789', 'Pepe', '678733221', 'pepe200@gmail.com', 3,'vendedor1');
INSERT INTO vendedores VALUES (2, 1, 'Pérez', 'C/Real 2', '09456119', 'Lola', '688733111', 'lolaindigo@gmail.com', 4,'vendedor2');
INSERT INTO vendedores VALUES (3, 1, 'López', 'C/Amargura 21', '11021873', 'Lidia', '722018928', 'lilop@gmail.com', 5,'vendedor3');

-- Introducimos a un moderador.

INSERT INTO moderadores VALUES (1, 1, 'García', 'C/Buenavista 12', '49456789', 'Pedro', '663733221', 'moderador1');

-- Introducimos ofertas.

INSERT INTO ofertas VALUES (1, 1, false, 5);
INSERT INTO ofertas VALUES (2, 1, true, 50);
INSERT INTO ofertas VALUES (3, 1, false, 5);
INSERT INTO ofertas VALUES (4, 1, true, 20);
INSERT INTO ofertas VALUES (5, 1, false, 5);
INSERT INTO ofertas VALUES (6, 1, false, 5);
INSERT INTO ofertas VALUES (7, 1, false, 5);
INSERT INTO ofertas VALUES (8, 1, false, 5);
INSERT INTO ofertas VALUES (9, 1, false, 5);
INSERT INTO ofertas VALUES (10, 1, false, 5);
INSERT INTO ofertas VALUES (11, 1, false, 5);


-- Introducimos géneros

INSERT INTO generos VALUES (1, 1, 'Smartphones');
INSERT INTO generos VALUES (2, 1, 'Ordenadores');
INSERT INTO generos VALUES (3, 1, 'Tablets');
INSERT INTO generos VALUES (4, 1, 'Consolas');
INSERT INTO generos VALUES (5, 1, 'Smartwatches');
INSERT INTO generos VALUES (6, 1, 'Televisores');
INSERT INTO generos VALUES (7, 1, 'Videojuegos');
INSERT INTO generos VALUES (8, 1, 'Audio');
INSERT INTO generos VALUES (9, 1, 'Vídeo');
INSERT INTO generos VALUES (10, 1, 'Multimedia');
INSERT INTO generos VALUES (11, 1, 'Entretenimiento');
INSERT INTO generos VALUES (12, 1, 'Electrodomésticos');
INSERT INTO generos VALUES (13, 1, 'Frigoríficos');
INSERT INTO generos VALUES (14, 1, 'Lavadoras');
INSERT INTO generos VALUES (15, 1, 'Microondas');
INSERT INTO generos VALUES (16, 1, 'Cocina');


-- Introducimos artículos.

INSERT INTO articulos VALUES (1, 1,'MSI', 'Prestige Evo A11M-003ES','MSI aprovecha el espíritu de exploración y creatividad al diseñar la mejor colección de computadoras portátiles: la serie Prestige. Para seguir empujando los límites creativos, estas máquinas finamente diseñadas no solo muestran un gusto único, sino que también son inmensamente poderosas. Es hora de dejar volar la inspiración y crear nuestros propios momentos en la vida. Posee un procesador Tiger lake i7-1185G7, memoria DDRIV 16GB*2 (3200MHz) y un almacenamiento 1TB NVMe PCIe Gen4x4 SSD, además de una gráfica GeForce® GTX 1650 Ti MAX Q, GDDR5 4GB.'
, 5,988.99, 10,8,'Nuevo','https://storage-asset.msi.com/global/picture/image/feature/nb/Prestige/Prestige15//mobility-nb.png', 1);
INSERT INTO articulos VALUES (2, 1,'Lenovo', 'Ideapad', 'Lleva allá donde necesites el portátil Lenovo Ideapad 3 15IIL05 con pantalla de 15.6" Full HD y en color gris platino. Tiene un diseño ligero y fino y con él podrás realizar todas tus tareas gracias a su potente procesador. El portátil Lenovo 3 15IIL05 cuenta con un procesador Intel® Core™ i5-1035G1 de 4 núcleos, 8 subprocesos por cada uno, con velocidad máxima en turbo de 3.60 GHz con Intel® Turbo Boost y 8 GB de RAM DDR4 (2 x 4GB) de 2666 MHz con lo que no tendrás problemas tanto si quieres trabajar en multitarea como correr procesos que requieran mucho espacio. Diseñado para ser tan inteligente como el usuario, con tecnologías como Intel® Deep Learning Boost o añadiendo memoria Intel® Optane™ para obtener más capacidad de respuesta acelerando las aplicaciones que más utilizas y la tecnología Intel® Hyper-Threading te permitirá realizar más trabajo en paralelo en las aplicaciones con muchos subprocesos, completando antes las tareas. Haz todas tus tareas cotidianas sin problemas de cuelgues o ralentizaciones y sin preocuparte por la batería ya que te ofrece muchas horas de autonomía para tus sesiones.'
, 2,500.99, 20,5,'Nuevo','https://images-na.ssl-images-amazon.com/images/I/71QQz9ZPLoL._AC_SL1500_.jpg', 2);
INSERT INTO articulos VALUES (3, 1, 'Apple', 'MacBook Pro', 'El MacBook Pro de 13 pulgadas incorpora un procesador Intel Core i5 de cuatro núcleos de décima generación para hacer en un suspiro las tareas más exigentes. Compilar código, superponer varias pistas en una composición musical o codificar vídeo, no importa la complejidad de lo que te propongas, sea lo que sea irá por la vía rápida. Además, Intel Iris Plus Graphics te da unos gráficos hasta un 80 % más rápidos que la generación anterior'
, 3,1000.99, 10,3,'Nuevo','https://img.pccomponentes.com/articles/29/291068/apple-macbook-pro-intel-core-i5-8gb-512gb-ssd-133-gris-espacial.jpg', 3);
INSERT INTO articulos VALUES (4, 1, 'Apple', 'Iphone 12', 'Bienvenido a la experiencia del iPhone 12 de 64 GB en color negro: Pantalla OLED Super Retina XDR de 6.1 pulgadas. Tecnología 5G. Chip A14 Bionic, con una inteligencia y potencia nunca vistas, además de ser el más veloz en un smartphone. Ceramic Shield, cuatro veces más resistente a las caídas. Modo Noche en cada una de las cámaras. Sí, el iPhone 12 tiene todo lo que esperas de un iPhone, pero llevado al extremo. El móvil Apple iPhone 12 de 64 GB de capacidad lleva integrado el sistema operativo iOS 14. Incorpora también el chip A14 Bionic con Neural Engine de última generación integrado. El objetivo del Neural Engine es ampliar las posibilidades del aprendizaje automático del teléfono. Por eso puede reconocer patrones y basarse en experiencias previas para hacer predicciones. Es decir, aprende casi como tú. Además, este chip tiene una alta eficiencia energética que asegura una gran duración de la batería, hasta 17 horas de reproducción de vídeo. Y por si fuera poco, la batería es cuenta con carga rápida y es compatible con carga inalámbrica (Qi).'
, 3,909, 30,10,'Nuevo','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_78286831/fee_786_587_png/Apple-iPhone-12--Negro--64-GB--5G--6.1%22-OLED-Super-Retina-XDR--Chip-A14-Bionic--iOS', 4);
INSERT INTO articulos VALUES (5, 1, 'Balay', '3KFE560WI', 'Frigorifico muy eficiente y es que cuenta con categoría A++ en eficiencia energética lo que es sinónimo de ahorro ya que el consumo es muy bajo. Se trata de un aparato que cuenta con hasta 346 litros de capacidad. La iluminación LED no solo consume poco, es que comparada con una bombilla convencional consume ¡hasta 10 veces menos! Y además dura más, de modo que el ahorro se multiplica. Si aún no lo ves claro, echa un vistazo al interior del frigorífico: los LED iluminan con claridad hasta el último rincón de forma progresiva, para permitir que la vista se adapte de forma natural y que ni un tomate cherry se quede olvidado al fondo del cajón.'
, 10,559, 10,8,'Nuevo','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_73890451/fee_786_587_png/Frigor%C3%ADfico-combi---Balay-3KFE560WI--42-dB--No-Frost--302-L--A----Blanco', 5);
INSERT INTO articulos VALUES (6, 1, 'Bosch', 'WAU24T44ES', 'Esta lavadora Bosch de color blanco, te ofrece un alto rendimiento y una limpieza perfecta, gracias a sus tecnologías innovadoras. El motor EcoSilence permite un lavado y centrifugado a toda potencia pero siendo más silencioso. Además, la incorporación de unos paneles laterales antivibración garantizan mayor estabilidad de la máquina y reduce su vibración; respetando así el descanso de los tuyos.'
, 10,350, 10,4,'SemiNuevo','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_78528179/fee_786_587_png/Lavadora-carga-frontal---Bosch-WAU24T44ES--9-kg--1200-rpm--15-programas--A----%28-30%25%29--Blanco', 6);
INSERT INTO articulos VALUES (7, 1, 'Xiaomi', 'Mi TV 4X 65''''', 'Smart TV para todos con los esperados televisores Xiaomi Mi TV 4A. Disfruta de todo el contenido que quieras en este excelente televisor con potente hardware, Motion Smooth y cuerpo de metal.'
,5,120, 50,5,'Reacondicionado','https://i01.appmifile.com/webfile/globalimg/products/pc/mi-tv-4x-65_1//specs_01.png', 7);
INSERT INTO articulos VALUES (8, 1, 'Xiaomi', 'Mi Band 5', 'La pulsera súper ventas de Xiaomi vuelve un año más con jugosas novedades a nuestra muñeca. La nueva Xiaomi Mi Band 5 ya está aquí. Xiaomi Mi Band 5 deja más protagonismo a la pantalla. La correa se vuelve más delgada entorno a la muñeca y podemos encontrar diferentes colores y combinaciones de estos. El panel AMOLED de la pantalla tiene mejor imagen, de los 400 nits ha pasado a los 450 nits, y así podemos ver todavía mejor su imagen en días con mucha luz. '
,0,30, 30,1,'Nuevo','https://www.movilzona.es/app/uploads/2020/03/mi-band-4-sin-fondo.png', 8);
INSERT INTO articulos VALUES (9, 1, 'Razer', 'Kraken X', 'Comodidad ultraligera para jugar sin parar. Nunca más volverás a estar agotado por el peso de tus auriculares. El Razer Kraken X está diseñado para ser ultraligero, por lo que apenas sientes que tienes algo en la cabeza, incluso cuando experimentas un audio de juego superior para una experiencia realmente envolvente.'
,3,38, 3,2,'Reacondicionado','https://i0.wp.com/www.pcmgames.com/wp-content/uploads/2020/03/Razer-Kraken-X-Lite-An%C3%A1lisis-Producto-3.png?fit=1504%2C1128&ssl=1', 9);
INSERT INTO articulos VALUES (10, 1, 'Sony', 'PlayStation 5', 'Versión Digital de la consola de Sony. Déjate sorprender por unos gráficos increíbles y experimenta nuevas funciones de PS5™. Cuenta con la CPU más potente en una consola hasta la fecha: 10.3 Teraflops, 36 CUs a 2.23GHz (frecuencia variable)'
,5,400, 10,20,'Nuevo','https://www.fonehouse.co.uk/shared/products/manufacturers/sony/PlayStation-5-Digital-Edition-3.png', 10);
INSERT INTO articulos VALUES (11, 1, 'Xiami', 'Redmi Note 9 Pro 128GB', 'Si necesitas un móvil versátil y funcional al mismo tiempo que no quieres renunciar al rendimiento, no te pierdas la oportunidad de hacerte con tu móvil Xiaomi Redmi Note 9 Pro Verde, de 6 GB de RAM y 128 GB de capacidad. Tecnología y componentes de calidad a tu alcance.<br><b>La pantalla que buscabas</b><br>Xiaomi te ofrece una pantalla Gorilla Glass de de 6.67" para que no pierdas detalle de nada. Siendo Full HD+ notarás la diferencia. Además, acabarás de fliparlo con su batería de gran capacidad, 5020 mAh, para que puedas disfrutar sin límite de esta pantalla.<br><b>Un procesador muy rápido</b><br>Con su potente y espectacular procesador Qualcomm® Snapdragon™ con una velocidad de 2.3 GHz con la que tendrás infinitas posibilidades para gestionar tus aplicaciones y procesos. Ya no te quedes esperando a que tu viejo móvil abra las aplicaciones, no abra las imágenes o no reproduzca tu música. Además, incorpora conectividad NFC, para que puedas cargar el móvil y hasta pagar de forma inalámbrica.<br><b>Captura todos tus momentos</b><br>El equipo fotográfico de este Redmi Note 9 Pro llega coronado por una cuádruple cámara trasera de 64 megapíxeles (f/1.72), que se hace acompañar por otras 3 más de 8 megapíxeles (ultra gran angular f/2.2), 5 megapíxeles y la última de 2 megapíxeles. En el frontal, 16 megapíxeles para selfies y todo el equipo fotográfico, trasera y frontal, está apoyado por la inteligencia artificial que los teléfonos de Xiaomi.<br><b>Capacidad para todo con Xiaomi</b><br>Con el Xiaomi Redmi Note 9 Pro podrás tener una memoria RAM de 6 GB, para que sea mucho más que veloz. Además, gracias a sus 128 GB de capacidad tendrás espacio para todo lo que necesites.'
,5,219, 35,5,'Nuevo','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_75024168/fee_786_587_png/M%C3%B3vil---Xiaomi-Redmi-Note-9-Pro--Verde--128-GB--6-GB--6.67%22-Full-HD---Qualcomm%C2%AE-Snapdragon%E2%84%A2--5020-mAh--Android', 11);

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
INSERT INTO articulos_generos VALUES (11,1);

-- Introducimos solicitudes.

INSERT INTO solicitudes VALUES (1, 1, 'MSI aprovecha el espíritu de exploración y creatividad al diseñar la mejor colección de computadoras portátiles: la serie Prestige. Para seguir empujando los límites creativos, estas máquinas finamente diseñadas no solo muestran un gusto único, sino que también son inmensamente poderosas. Es hora de dejar volar la inspiración y crear nuestros propios momentos en la vida. Posee un procesador Tiger lake i7-1185G7, memoria DDRIV 16GB*2 (3200MHz) y un almacenamiento 1TB NVMe PCIe Gen4x4 SSD, además de una gráfica GeForce® GTX 1650 Ti MAX Q, GDDR5 4GB.'
,5,'MSI','Prestige Evo A11M-003ES',988.99,'','Aceptada',50,8,'Nuevo','https://storage-asset.msi.com/global/picture/image/feature/nb/Prestige/Prestige15//mobility-nb.png', 1, 1);
INSERT INTO solicitudes VALUES (2, 1, 'Lleva allá donde necesites el portátil Lenovo Ideapad 3 15IIL05 con pantalla de 15.6" Full HD y en color gris platino. Tiene un diseño ligero y fino y con él podrás realizar todas tus tareas gracias a su potente procesador. El portátil Lenovo 3 15IIL05 cuenta con un procesador Intel® Core™ i5-1035G1 de 4 núcleos, 8 subprocesos por cada uno, con velocidad máxima en turbo de 3.60 GHz con Intel® Turbo Boost y 8 GB de RAM DDR4 (2 x 4GB) de 2666 MHz con lo que no tendrás problemas tanto si quieres trabajar en multitarea como correr procesos que requieran mucho espacio. Diseñado para ser tan inteligente como el usuario, con tecnologías como Intel® Deep Learning Boost o añadiendo memoria Intel® Optane™ para obtener más capacidad de respuesta acelerando las aplicaciones que más utilizas y la tecnología Intel® Hyper-Threading te permitirá realizar más trabajo en paralelo en las aplicaciones con muchos subprocesos, completando antes las tareas. Haz todas tus tareas cotidianas sin problemas de cuelgues o ralentizaciones y sin preocuparte por la batería ya que te ofrece muchas horas de autonomía para tus sesiones.'
,5,'Lenovo','Ideapad',500.99,'','Aceptada',50,8,'Nuevo','https://images-na.ssl-images-amazon.com/images/I/71QQz9ZPLoL._AC_SL1500_.jpg', 2, 1);
INSERT INTO solicitudes VALUES (3, 1, 'Bienvenido a la experiencia del iPhone 12 de 64 GB en color negro: Pantalla OLED Super Retina XDR de 6.1 pulgadas. Tecnología 5G. Chip A14 Bionic, con una inteligencia y potencia nunca vistas, además de ser el más veloz en un smartphone. Ceramic Shield, cuatro veces más resistente a las caídas. Modo Noche en cada una de las cámaras. Sí, el iPhone 12 tiene todo lo que esperas de un iPhone, pero llevado al extremo. El móvil Apple iPhone 12 de 64 GB de capacidad lleva integrado el sistema operativo iOS 14. Incorpora también el chip A14 Bionic con Neural Engine de última generación integrado. El objetivo del Neural Engine es ampliar las posibilidades del aprendizaje automático del teléfono. Por eso puede reconocer patrones y basarse en experiencias previas para hacer predicciones. Es decir, aprende casi como tú. Además, este chip tiene una alta eficiencia energética que asegura una gran duración de la batería, hasta 17 horas de reproducción de vídeo. Y por si fuera poco, la batería es cuenta con carga rápida y es compatible con carga inalámbrica (Qi).'
,3,'Apple','Iphone 12',909,'','Aceptada',50,8,'Nuevo','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_78286831/fee_786_587_png/Apple-iPhone-12--Negro--64-GB--5G--6.1%22-OLED-Super-Retina-XDR--Chip-A14-Bionic--iOS', 4, 1);
INSERT INTO solicitudes VALUES (4, 1, 'Frigorifico muy eficiente y es que cuenta con categoría A++ en eficiencia energética lo que es sinónimo de ahorro ya que el consumo es muy bajo. Se trata de un aparato que cuenta con hasta 346 litros de capacidad. La iluminación LED no solo consume poco, es que comparada con una bombilla convencional consume ¡hasta 10 veces menos! Y además dura más, de modo que el ahorro se multiplica. Si aún no lo ves claro, echa un vistazo al interior del frigorífico: los LED iluminan con claridad hasta el último rincón de forma progresiva, para permitir que la vista se adapte de forma natural y que ni un tomate cherry se quede olvidado al fondo del cajón.'
,10,'Balay','3KFE560WI',559,'','Aceptada',50,8,'Nuevo','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_73890451/fee_786_587_png/Frigor%C3%ADfico-combi---Balay-3KFE560WI--42-dB--No-Frost--302-L--A----Blanco', 5, 1);
INSERT INTO solicitudes VALUES (5, 1, 'Esta lavadora Bosch de color blanco, te ofrece un alto rendimiento y una limpieza perfecta, gracias a sus tecnologías innovadoras. El motor EcoSilence permite un lavado y centrifugado a toda potencia pero siendo más silencioso. Además, la incorporación de unos paneles laterales antivibración garantizan mayor estabilidad de la máquina y reduce su vibración; respetando así el descanso de los tuyos.'
,5,'Bosch','WAU24T44ES',350,'','Aceptada',50,8,'Nuevo','https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_78528179/fee_786_587_png/Lavadora-carga-frontal---Bosch-WAU24T44ES--9-kg--1200-rpm--15-programas--A----%28-30%25%29--Blanco', 6, 2);
INSERT INTO solicitudes VALUES (6, 1, 'Smart TV para todos con los esperados televisores Xiaomi Mi TV 4A. Disfruta de todo el contenido que quieras en este excelente televisor con potente hardware, Motion Smooth y cuerpo de metal.'
,5,'Xiaomi','Mi TV 4X 65''''',120,'','Aceptada',50,8,'Nuevo','https://i01.appmifile.com/webfile/globalimg/products/pc/mi-tv-4x-65_1//specs_01.png', 7, 2);							
INSERT INTO solicitudes VALUES (7, 1, 'La pulsera súper ventas de Xiaomi vuelve un año más con jugosas novedades a nuestra muñeca. La nueva Xiaomi Mi Band 5 ya está aquí. Xiaomi Mi Band 5 deja más protagonismo a la pantalla. La correa se vuelve más delgada entorno a la muñeca y podemos encontrar diferentes colores y combinaciones de estos. El panel AMOLED de la pantalla tiene mejor imagen, de los 400 nits ha pasado a los 450 nits, y así podemos ver todavía mejor su imagen en días con mucha luz. '
,5,'Xiaomi','Mi Band 5',20,'','Aceptada',50,8,'Nuevo','https://www.movilzona.es/app/uploads/2020/03/mi-band-4-sin-fondo.png', 8, 3);
INSERT INTO solicitudes VALUES (8, 1, 'Comodidad ultraligera para jugar sin parar. Nunca más volverás a estar agotado por el peso de tus auriculares. El Razer Kraken X está diseñado para ser ultraligero, por lo que apenas sientes que tienes algo en la cabeza, incluso cuando experimentas un audio de juego superior para una experiencia realmente envolvente.'
,5,'Razer','Kraken X',38,'','Aceptada',50,8,'Nuevo','https://i0.wp.com/www.pcmgames.com/wp-content/uploads/2020/03/Razer-Kraken-X-Lite-An%C3%A1lisis-Producto-3.png?fit=1504%2C1128&ssl=1', 9, 3);
INSERT INTO solicitudes VALUES (9, 1, 'Versión Digital de la consola de Sony. Déjate sorprender por unos gráficos increíbles y experimenta nuevas funciones de PS5™. Cuenta con la CPU más potente en una consola hasta la fecha: 10.3 Teraflops, 36 CUs a 2.23GHz (frecuencia variable)'
,5,'Sony','PlayStation 5',400,'','Aceptada',50,8,'Nuevo','https://www.fonehouse.co.uk/shared/products/manufacturers/sony/PlayStation-5-Digital-Edition-3.png', 10, 1);
INSERT INTO solicitudes VALUES (10, 1, 'Cubre todas tus necesidades informáticas diarias con el portátil Aspire One. Su combinación de potente tecnología y características de cuidado diseño lo convierten en el dispositivo ideal para el trabajo o la diversión. Cuenta con un procesador Intel Core i5-1035G1 (3.6GHz, 6MB) y una memoria RAM 12 GB DDR4 SDRAM.'
,5,'Acer','Aspire One',700.9,'','Denegada',50,8,'Nuevo','https://images-na.ssl-images-amazon.com/images/I/31MpIy75yyL._AC_.jpg', null, 2);
INSERT INTO solicitudes VALUES (11, 1, 'El MacBook Pro de 13 pulgadas incorpora un procesador Intel Core i5 de cuatro núcleos de décima generación para hacer en un suspiro las tareas más exigentes. Compilar código, superponer varias pistas en una composición musical o codificar vídeo, no importa la complejidad de lo que te propongas, sea lo que sea irá por la vía rápida. Además, Intel Iris Plus Graphics te da unos gráficos hasta un 80 % más rápidos que la generación anterior'
,5,'Apple','Macbook Pro',1000.99,'','Pendiente',50,8,'Nuevo','https://images-na.ssl-images-amazon.com/images/I/71VHEQqByPL._AC_SL1500_.jpg', 3, 2);
INSERT INTO solicitudes VALUES (12, 1, 'Si necesitas un móvil versátil y funcional al mismo tiempo que no quieres renunciar al rendimiento, no te pierdas la oportunidad de hacerte con tu móvil Xiaomi Redmi Note 9 Pro Verde, de 6 GB de RAM y 128 GB de capacidad. Tecnología y componentes de calidad a tu alcance.<br><b>La pantalla que buscabas</b><br>Xiaomi te ofrece una pantalla Gorilla Glass de de 6.67" para que no pierdas detalle de nada. Siendo Full HD+ notarás la diferencia. Además, acabarás de fliparlo con su batería de gran capacidad, 5020 mAh, para que puedas disfrutar sin límite de esta pantalla.<br><b>Un procesador muy rápido</b><br>Con su potente y espectacular procesador Qualcomm® Snapdragon™ con una velocidad de 2.3 GHz con la que tendrás infinitas posibilidades para gestionar tus aplicaciones y procesos. Ya no te quedes esperando a que tu viejo móvil abra las aplicaciones, no abra las imágenes o no reproduzca tu música. Además, incorpora conectividad NFC, para que puedas cargar el móvil y hasta pagar de forma inalámbrica.<br><b>Captura todos tus momentos</b><br>El equipo fotográfico de este Redmi Note 9 Pro llega coronado por una cuádruple cámara trasera de 64 megapíxeles (f/1.72), que se hace acompañar por otras 3 más de 8 megapíxeles (ultra gran angular f/2.2), 5 megapíxeles y la última de 2 megapíxeles. En el frontal, 16 megapíxeles para selfies y todo el equipo fotográfico, trasera y frontal, está apoyado por la inteligencia artificial que los teléfonos de Xiaomi.<br><b>Capacidad para todo con Xiaomi</b><br>Con el Xiaomi Redmi Note 9 Pro podrás tener una memoria RAM de 6 GB, para que sea mucho más que veloz. Además, gracias a sus 128 GB de capacidad tendrás espacio para todo lo que necesites.'
,7,'Xiaomi','Redmi Note 9 Pro 128GB',219,'','Aceptada',100,2,'Nuevo', 'https://assets.mmsrg.com/isr/166325/c1/-/ASSET_MMS_75024168/fee_786_587_png/M%C3%B3vil---Xiaomi-Redmi-Note-9-Pro--Verde--128-GB--6-GB--6.67%22-Full-HD---Qualcomm%C2%AE-Snapdragon%E2%84%A2--5020-mAh--Android', 11, 2);

-- Introducimos cestas

INSERT INTO cestas VALUES (1, 1, 1688.99);
INSERT INTO cestas VALUES (2, 1, 0.0);
INSERT INTO cestas VALUES (3, 1, 0.0);

-- Introducimos linea cestas

INSERT INTO lineas_cestas VALUES (1, 1, 1, 1, 1);
INSERT INTO lineas_cestas VALUES (2, 1, 2, 6, 1);

-- Introducimos clientes.

INSERT INTO clientes VALUES (1, 1, 'Fernandez', 'C/Boqueron 34', '23456789', 'Juan', '988733221', 'juan@gmail.com', 1,1,'cliente1');
INSERT INTO clientes VALUES (2, 1, 'Martin', 'C/Konquero 4', '23456119', 'Francisco', '988733111', 'martin@gmail.com', 2,2,'cliente2');
INSERT INTO clientes VALUES (3, 1, 'López', 'C/Real betis 11', '98577888', 'Antonio', '877433224', 'pepe@gmail.com', 6,3,'cliente3');

-- Introducimos comentarios.

INSERT INTO comentarios VALUES (1, 1, 'Lo compré hará 1 año y todo va fenomenal, pero a veces noto que la batería se agota demasiado rápdio',3,1,1,null,null);
INSERT INTO comentarios VALUES (2, 1, 'Funciona perfectamente y es muy fácil de usar ',5,6,1,null,null);
INSERT INTO comentarios VALUES (3, 1, 'No recomendado para nada, se ha necesitado más de 5 reparaciones en 1 año.',1,1,1,null,null);
INSERT INTO comentarios VALUES (4, 1, 'Estaba deseando comprarla :-)',5,10,1,null,null);
INSERT INTO comentarios VALUES (5, 1, 'Pronto aumentaremos el stock de este producto para que no te quedes sin ninguno',0,3,null,null,1);
INSERT INTO comentarios VALUES (6, 1, 'Estamos viendo varios insultos en los comentarios de estos artículos, todo aquel que insulte se le bloqueará',0,5,null,1,null);
INSERT INTO comentarios VALUES (7, 1, '¿Sabéis si este ordenador corre el fortnite?',5,1,2,null,null);
INSERT INTO comentarios VALUES (8, 1, 'Pronto se vendrá una nueva oferta de este producto.',0,1,null,null,1);
INSERT INTO comentarios VALUES (9, 1, 'Está chida esta lavadora',4,6,2,null,null);

-- Introducimos tarjetas de crédito

INSERT INTO tarjetas_credito VALUES (1, 1, '442', '03/22', '4572240486955232', 'Juan Fernández Tirado');

-- Introducimos relaciones tarjeta-cliente

INSERT INTO clientes_tarjetas VALUES (1,1);

-- Introducimos mensajes entre clientes y vendedores

INSERT INTO mensajes VALUES (1, 1, '29976789', '23456789', '2020-05-15', '11', 'Buenas, el articulo viene roto', 1, 1);
INSERT INTO mensajes VALUES (2, 1, '23456789', '29976789', '2020-05-16', '01', 'Una pena...', 1, 1);
INSERT INTO mensajes VALUES (3, 1, '23456789', '29976789', '2020-05-16', '01', 'Pues compra otro, a ver si sale mejor', 1, 1);

--Insertar pedido al cliente1

INSERT INTO pedidos VALUES (1, 1,to_date('02-12-2019', 'DD-MM-YYYY') , '405.', 1, 1);

-- Insertar linea pedido al cliente1

INSERT INTO lineas_pedidos VALUES (1, 1, 1, 'Entregado','400', 10, 1);
