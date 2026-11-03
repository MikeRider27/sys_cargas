# Sys Cargas v2

Reescritura del sistema de transporte de cargas (legacy Java Swing + MySQL/PostgreSQL en `legacy/`).

| Capa | Tecnología |
|---|---|
| Backend | Java 25, Spring Boot 4.1.2 (Web MVC, Security, JDBC, Flyway, Validation) |
| Reportes | JasperReports 7.1.0, el motor de iReport/Jaspersoft Studio, embebido. Los `.jrxml` se compilan en el build. |
| Frontend | React 19 + TypeScript + Vite, servido por nginx sin privilegios |
| Base de datos | PostgreSQL 18 |
| Contenedores | Docker Compose: `db`, `backend` y `frontend` |

## Puesta en marcha

```bash
cp .env.example .env
# Completar: DB_PASSWORD, JWT_SECRET (openssl rand -base64 48) y ADMIN_PASSWORD
docker compose up -d --build
```

Abrir http://localhost:8097 (el puerto se cambia con `APP_PORT`) e ingresar con `admin` y la clave de `ADMIN_PASSWORD`.
Si `ADMIN_PASSWORD` queda vacío, se genera una clave temporal que aparece en `docker compose logs backend`.

El esquema y los datos base se crean solos al primer arranque mediante Flyway (`backend/src/main/resources/db/migration`).
Cada cambio de esquema futuro va en un archivo `V3__...sql` nuevo; los existentes no se modifican.

## Módulos (equivalencia con el legacy)

- **Pedidos**: registrar pedido, cotizar flete, pedido de flete (con mercaderías), emitir CRT (numeración por año) y reclamos (registro, resolución y anulación).
- **Logística**: asignar equipos (tracto, semirremolque, chofer y accesorios), registrar salida, viaje (ciudades, asignaciones y fleteros), registrar llegada y eventos de carga.
- **Rendiciones**: apertura y cierre de caja, viáticos (controla el saldo de caja), comprobantes y rendición.
- **Mantenimiento**: 31 referenciales generados a partir de metadatos (`CatalogRegistry.java`) y usuarios.
- **Informes**: 7 documentos imprimibles y 3 listados por período, en PDF.

Los estados se propagan solos: al registrar la llegada se finalizan la asignación, el pedido de flete, el pedido del cliente y el viaje, si ya no quedan pendientes. Anular la llegada revierte esos cambios.

## Roles

| Rol | Permisos |
|---|---|
| ADMIN | Todo, incluida la administración de usuarios |
| OPERADOR | Alta, anulación y operaciones en todos los módulos |
| CONSULTA | Solo lectura e informes |

Las operaciones de pedidos, logística y caja quedan registradas a nombre del personal vinculado al usuario.
Un usuario sin personal vinculado puede consultar, pero no registrar documentos.

## Seguridad

- SQL siempre parametrizado. Los nombres de tablas y columnas salen de listas blancas en el código.
- Contraseñas con BCrypt, bloqueo de 15 minutos tras 5 intentos fallidos y respuesta de login en tiempo constante.
- JWT HS256 sin estado (8 h). El backend y la base no exponen puertos: la base está en una red interna sin salida.
- Contenedores sin root, de solo lectura, `no-new-privileges` y `cap_drop: ALL`.
- nginx con CSP estricta, `X-Frame-Options`, `nosniff` y `server_tokens off`.
- La imagen de Postgres se reconstruye con parches de Alpine al día y sin `gosu`.
- Los reportes se compilan en el build: la imagen de producción no incluye compilador Java.

Escaneo realizado con Trivy (todas las severidades) el 29/10/2026: `sys-cargas-backend`, `sys-cargas-frontend` y `sys-cargas-db` con **0 vulnerabilidades conocidas**, y `npm audit` sin hallazgos.
Las bases de CVEs cambian a diario, así que conviene repetir el escaneo y reconstruir las imágenes con regularidad:

```bash
docker compose build --pull
docker run --rm -v /var/run/docker.sock:/var/run/docker.sock aquasec/trivy image sys-cargas-backend:2.0.0
```

## Reportes

Los fuentes están en `backend/src/main/resources/reports/*.jrxml` (formato JasperReports 7, editable con Jaspersoft Studio 7).
Para agregar un reporte: crear el `.jrxml` con un parámetro `ID` (documento) o `DESDE` y `HASTA` (listado), y registrarlo en `ReportService.REPORTES`.

## Desarrollo local

```bash
docker compose up -d db                                   # o un Postgres local
cd backend && DB_PASSWORD=... JWT_SECRET=... mvn spring-boot:run
cd frontend && npm install && npm run dev                 # http://localhost:5173 (proxy /api -> :8080)
```

## Base de datos

El detalle de los errores del esquema legacy y sus correcciones está en [docs/CORRECCIONES_BD.md](docs/CORRECCIONES_BD.md).
Backup: `docker compose exec db pg_dump -U cargas cargas > backup.sql`.
