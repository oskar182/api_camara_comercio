# API Integración RIT - Cámara de Comercio de Cali

Servicio REST empaquetado como **WAR** con Maven y Java 17, diseñado para ejecutarse en **Payara** y ser abierto desde **NetBeans**. Implementa la emisión de token JWT con vigencia de 30 minutos y el registro de la información recibida en las tablas `TABLA_INFOCACO` (cabecera) y `TABLA_INFOCCPR` (detalle) de Oracle.

## Requisitos
- Java 17
- Maven 3.9+
- Payara (Jakarta EE 10)
- JNDI a Oracle configurado como `jdbc/ritDS` con permisos de escritura sobre las tablas destino

## Construcción
```bash
mvn clean package
```
Genera `target/api-rit-ccc.war` listo para desplegarse.

## Endpoints principales
- `POST /api/auth/token`: recibe `{ "usuario": "...", "contrasena": "..." }` y retorna un JWT válido por 30 minutos. Las credenciales y mensajes provienen de `src/main/resources/messages.properties`.
- `POST /api/rit/registros`: recibe el payload JSON definido por RIT, exige `Authorization: Bearer <token>`, persiste cabecera/detalle y devuelve el identificador interno.

## Configuración
Todos los textos y credenciales están en `src/main/resources/messages.properties`. El secreto JWT (`security.token.secret`) debe tener la entropía suficiente para HS256.

## Estructura de persistencia
- `TABLA_INFOCACO`: almacena token usado, JSON completo (`RAW_DATA`), estado/código y fecha de creación.
- `TABLA_INFOCCPR`: almacena cada par campo/valor extraído del JSON, ligado a la cabecera mediante `INFOCACO_ID`.

Ajuste los nombres de columna en las entidades si la base difiere de esta convención.
