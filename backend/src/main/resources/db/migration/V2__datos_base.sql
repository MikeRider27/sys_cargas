-- =====================================================================
-- Datos base (reemplaza legacy/INSERTS BASICOS OLIMPUERCOS.sql).
-- El usuario administrador NO se inserta aqui: lo crea la aplicacion al
-- primer arranque con la clave definida en ADMIN_PASSWORD.
-- =====================================================================

INSERT INTO estado (id_est, descrip) VALUES
    (0, 'ANULADO'),
    (1, 'ACTIVO'),
    (2, 'PENDIENTE'),
    (3, 'EN PROCESO'),
    (4, 'FINALIZADO');

INSERT INTO rol (codigo, descrip) VALUES
    ('ADMIN',    'ADMINISTRADOR DEL SISTEMA'),
    ('OPERADOR', 'OPERADOR (PEDIDOS, LOGISTICA Y RENDICIONES)'),
    ('CONSULTA', 'SOLO CONSULTA E INFORMES');

INSERT INTO sucursal (descrip, direc, telef, correo) VALUES
    ('CASA CENTRAL', 'ASUNCION', '021000000', NULL);

INSERT INTO cargo (descrip) VALUES
    ('ADMINISTRADOR'), ('GERENTE'), ('OPERADOR LOGISTICO'), ('CHOFER'), ('CAJERO');

INSERT INTO personal (nro_doc, nombres, apellidos, direcc, telef, id_car, id_suc)
SELECT 'ADMIN', 'ADMINISTRADOR', 'DEL SISTEMA', 'ASUNCION', '021000000', c.id_car, s.id_suc
FROM cargo c, sucursal s
WHERE c.descrip = 'ADMINISTRADOR' AND s.descrip = 'CASA CENTRAL';

INSERT INTO nacionalidad (descrip) VALUES
    ('PARAGUAYA'), ('ARGENTINA'), ('BRASILEÑA'), ('PERUANA'), ('BOLIVIANA'),
    ('COLOMBIANA'), ('ALEMANA'), ('URUGUAYA'), ('ITALIANA'), ('AUSTRIACA'), ('CHILENA');

INSERT INTO tipo_cli (descrip) VALUES ('PERSONA FISICA'), ('EMPRESA');

INSERT INTO tipo_doc (descrip) VALUES ('CIP'), ('DNI'), ('CIB'), ('RUC'), ('PASAPORTE');

-- Tarifas por pais tomadas del legacy (usadas en la cotizacion de flete).
INSERT INTO pais (descrip, tarifa) VALUES
    ('PARAGUAY', 5000), ('ARGENTINA', 3000), ('BRASIL', 7500),
    ('URUGUAY', 6000), ('BOLIVIA', 6500), ('CHILE', 8000);

INSERT INTO estad_prov (id_pais, descrip)
SELECT p.id_pais, d.descrip
FROM pais p,
     (VALUES ('CAPITAL'), ('CENTRAL'), ('ALTO PARANA'), ('CORDILLERA'), ('ÑEEMBUCU'),
             ('SAN PEDRO'), ('AMAMBAY'), ('MISIONES'), ('ITAPUA')) AS d(descrip)
WHERE p.descrip = 'PARAGUAY';

INSERT INTO ciudad (id_prov, descrip)
SELECT e.id_prov, c.ciudad
FROM estad_prov e
JOIN (VALUES ('CAPITAL', 'ASUNCION'),
             ('CENTRAL', 'LUQUE'), ('CENTRAL', 'SAN LORENZO'), ('CENTRAL', 'LAMBARE'),
             ('CENTRAL', 'MARIANO ROQUE ALONSO'), ('CENTRAL', 'LIMPIO'),
             ('ALTO PARANA', 'CIUDAD DEL ESTE'), ('AMAMBAY', 'PEDRO JUAN CABALLERO'),
             ('ITAPUA', 'ENCARNACION')) AS c(prov, ciudad)
  ON c.prov = e.descrip;

INSERT INTO moneda (abrev, descrip) VALUES
    ('PYG', 'GUARANIES'), ('ARS', 'PESOS ARGENTINOS'), ('BRL', 'REALES'), ('USD', 'DOLARES');

INSERT INTO aduana (descrip, id_ciud)
SELECT a.descrip, c.id_ciud
FROM (VALUES ('ADUANA DE ASUNCION', 'ASUNCION'),
             ('ADUANA DE CIUDAD DEL ESTE', 'CIUDAD DEL ESTE'),
             ('ADUANA DE ENCARNACION', 'ENCARNACION'),
             ('ADUANA DE PEDRO JUAN CABALLERO', 'PEDRO JUAN CABALLERO')) AS a(descrip, ciudad)
JOIN ciudad c ON c.descrip = a.ciudad;

INSERT INTO itinerario (descrip) VALUES
    ('ASUNCION - BUENOS AIRES'), ('ASUNCION - SAN PABLO'), ('ASUNCION - MONTEVIDEO');

INSERT INTO ruta (descrip, extension_km, id_ciud)
SELECT 'RUTA PY02', 500, id_ciud FROM ciudad WHERE descrip = 'ASUNCION';

INSERT INTO tipo_merc (descrip) VALUES ('GENERAL'), ('PELIGROSA'), ('REFRIGERADA'), ('PERECEDERA');

INSERT INTO tipo_carg (descrip) VALUES
    ('CARGA GENERAL'), ('CARGA PELIGROSA'), ('CARGA REFRIGERADA'), ('CARGA A GRANEL'), ('CONTENEDOR');

INSERT INTO tip_event (descrip) VALUES
    ('RETIRO DE MERCADERIA'), ('CONTROL ADUANERO'), ('LLEGADA DE MERCADERIA'), ('ENTREGA AL DESTINATARIO');

INSERT INTO marca_t (descrip) VALUES ('SCANIA'), ('VOLKSWAGEN'), ('VOLVO'), ('MERCEDES-BENZ');
INSERT INTO marca_s (descrip) VALUES ('FENIX'), ('RANDON');
INSERT INTO tipo_semi (descrip) VALUES ('PLANCHA'), ('CERRADO'), ('REFRIGERADO'), ('TANQUE');

INSERT INTO accesorios (descrip) VALUES
    ('CINTAS DE AMARRE'), ('LONA'), ('CADENAS'), ('EXTINTOR'), ('CONOS DE SEÑALIZACION');

INSERT INTO caja (descrip, id_suc)
SELECT 'CAJA PRINCIPAL', id_suc FROM sucursal WHERE descrip = 'CASA CENTRAL';
