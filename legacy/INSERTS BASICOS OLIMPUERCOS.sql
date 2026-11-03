--select * from ACCESORIOS
--SQL para guardar inserts predeterminados para el funcionamiento del ñemb"O" sistema
---------------------------------------------------------rol
insert into rol values (1,'TEMBO');
---------------------------------------------------------usuario
insert into usuario values (1,1,'202cb962ac59075b964b07152d234b70','FALCON');
---------------------------------------------------------estado
insert into estado values(0,'ANULADO'),(1,'VIGENTE');
---------------------------------------------------------sucursal
insert into sucursal values(1,'021558899','CERCA DEL DEF. DEL CHACO','LA.O@HOTMAIL.COM','CASA CENTRAL',1);
---------------------------------------------------------cargo
insert into cargo values (1,'OLIMPISTA',1),(2,'GERENTE',1),(0,'ROOT',1);
---------------------------------------------------------personal select * from personal
insert into personal values (777,'CRISTIAN DANIEL','FALCON BOGARIN',CURRENT_DATE,'CERCA DEL ESTADIO DEF. DEL CHACO','021555444',1,1,1,1,1);--select * from personal
---------------------------------------------------------nacionalidad
INSERT INTO nacionalidad VALUES (1,'PARAGUAYA',1);
INSERT INTO nacionalidad VALUES (2,'ARGENTINA',1);
INSERT INTO nacionalidad VALUES (3,'BRASILERA',1);
INSERT INTO nacionalidad VALUES (4,'PERUANA',1);
INSERT INTO nacionalidad VALUES (5,'BOLIVIANA',1);
INSERT INTO nacionalidad VALUES (6,'COLOMBIANA',1);
INSERT INTO nacionalidad VALUES (7,'ALEMANA',1);
INSERT INTO nacionalidad VALUES (8,'URUGUAYA',1);
INSERT INTO nacionalidad VALUES (9,'ITALIANA',1);
INSERT INTO nacionalidad VALUES (10,'AUSTRIACA',1);
---------------------------------------------------------tipo de cliente
insert into tipo_cli values (1,'PERSONA'),(2,'EMPRESA');
---------------------------------------------------------tipo de documento
insert into tipo_doc values(1,'CIP'),(2,'DNI'),(3,'CIB'),(4,'RUC'),(5,'PASA');
---------------------------------------------------------pais
insert into pais values (1,'PARAGUAY',1,5000),(2,'KUREPILANDIA',1,3000),(3,'RAPAILANDIA',1,7500);
---------------------------------------------------------dpto
insert into estad_prov values (1,1,'CENTRAL'),(2,1,'ALTO PARANA'),(3,1,'CORDILLERA'),(4,1,'ÑEEMBUCU'),(5,1,'SAN PEDRO'),(6,1,'AMAMBAY'),(7,1,'MISIONES');
---------------------------------------------------------ciudad
insert into ciudad values(1,1,1,'ASUNCION',1),(2,1,1,'LUQUE',1),(3,1,1,'SAN LORENZO',1),(4,1,1,'LAMBARE',1),
				(5,1,1,'M. R. ALONSO',1),(6,1,1,'LIMPIO',1);
---------------------------------------------------------tipo de mercaderia
insert into tipo_merc values(1,'PELIGROSA PELIGROSA'),(2,'PELIGROSA'),(3,'REFRIGEREDA');
insert into tipo_carg values(1,'PELIGROSA PELIGROSA'),(2,'PELIGROSA'),(3,'REFRIGEREDA');
---------------------------------------------------------moneda
insert into moneda values (1,'GS.','GUARANIES'),(2,'PKS.','PESOS KUREPAS'),(3,'RS.','REALES'),(4,'$$','DOLARES');
---------------------------------------------------------accesorios
--insert into accesorios values(1,'CUERDA',1),(2,'ARNES',1),(3,'SUPERMAN',1),(4,'BATMAN',1);
---------------------------------------------------------marca tracto?
insert into marca_t values (1,'SCANIA'),(2,'VOLKSWAGEN');
---------------------------------------------------------marca semirremolque
insert into marca_s values (1,'FENIX');
---------------------------------------------------------tipo de evento
insert into tip_event values(1,'RETIRO MERCADERIA'),(2,'CONTROL ADUANA'),(3,'LLEGADA MERCADERIA');
---------------------------------------------------------pais
--insert into pais values (1,'ARGENTINA',1,5000),(2,'PARAGUAY',1,6000),(3,'BRASIL',1,8000);
---------------------------------------------------------despachante
insert into despachante values (3567414,'HELMUT','SCHAFLER','983537396');
---------------------------------------------------------cliente
--select * from cliente
insert into cliente values (3567414,'HELMUT DANIEL','SCHAFLER VALENZUELA',CURRENT_DATE,'SOME DIR','0986555262',1,1,1),(6357440,'CRISTIAN DANIEL','FALCON BOGARIN',CURRENT_DATE,'SOME DIR','0986555262',1,1,1);
---------------------------------------------------------mercaderia
insert into mercaderia values (1,'MERCA 1',1,1),(2,'MERCA 2',1,1),(3,'MERCA 3',1,1),(4,'MERCA 4',1,1);
---------------------------------------------------------aduana
insert into aduana values(1,'FALCON'),(2,'PAKSA'),(3,'CODESA');
---------------------------------------------------------tipo semi
insert into tipo_semi values(1,1,'PLANCHA'),(2,1,'CERRADO'),(3,1,'REFRIGERADO');
---------------------------------------------------------semirremolque select * from semirremolque
insert into semirremolque values (1,'123abc',50000,28,4,12,'ABC-123',3,1,1);
insert into semirremolque values (2,'777abc',80000,32,5,15,'ABC-777',1,1,1);
---------------------------------------------------------modelo tracto
insert into modelo_t values (1,1,2016,'jahamina'),(2,2,2015,'Soy un tracto');
---------------------------------------------------------tracto select * from r_viaje
insert into tracto values (1,'qwer432432','LOL-123','123000',1,1,1);
---------------------------------------------------------itinerario
ALTER TABLE itinerario ALTER COLUMN descrip TYPE character varying; --alter para pasar a varchar el campo
insert into itinerario values(1,'ASUNCION-BS AS'),(2,'ASUNCION-SAN PABLO'),(3,'ASUNCION-MONTEVIDEO');
---------------------------------------------------------ruta select * from ruta
insert into ruta values (1,'RUTA 2','500kmts',1,1,1,1);
---------------------------------------------------------
---------------------------------------------------------
---------------------------------------------------------