# microserviciocuentas
Proyecto de ingreso para NTT Data


## Requisitos Previos

Antes de comenzar, asegúrate de tener lo siguiente instalado en tu máquina:

1. **Java JDK** (versión 8 o superior)
2. **Maven** (para gestionar las dependencias del proyecto)
3. **MySQL** (o cualquier otra base de datos que utilices)
4. **Git** (para clonar el repositorio)
5. **Un IDE** compatible con Java (NetBeans, IntelliJ IDEA, Eclipse, VSCode, etc.)

---
## Dependencias
Este proyecto utiliza las siguientes dependencias principales:

Spring Boot

Spring Data JPA

MySQL Connector

JUnit (para pruebas)

---

## Instalación y Configuración

### 1. Clonar el Repositorio
Primero, clona este repositorio en tu máquina local usando el siguiente comando:

```bash
git clone https://github.com/nullam/microserviciocuentas.git
```

### 2. Instalación y Configuración Base de Datos

Dentro de la carpeta DB se encuentra el archivo dump para que se pueda restaurar la base de datos utilizada para las pruebas.

En el archivo src/main/resources/application.properties, asegúrate de configurar correctamente la conexión a la base de datos con tus credenciales y el nombre de la base de datos creada.

### 3. Compilación del Proyecto
Una vez que el repositorio esté clonado y configurada la base de datos, navega a la raíz del proyecto y ejecuta el siguiente comando para compilarlo usando Maven:
```bash
mvn clean install
```

### 4. Ejecución del Proyecto
Para ejecutar la aplicación, utiliza el siguiente comando desde la carpeta raíz del proyecto:
```bash
mvn spring-boot:run
```
La aplicación estará disponible en http://localhost:8080 por defecto.

Si el puerto 8080 está en uso, puedes cambiarlo en application.properties:
server.port=numero_puerto_disponible

## Ejecución de Pruebas
Para correr las pruebas unitarias del proyecto, ejecuta:
```bash
mvn test
```

##Despliegue
Si deseas desplegar esta aplicación en un servidor de producción, sigue estos pasos:

1. Construye el archivo .jar para despliegue:
```bash
mvn clean package
```
2. Encuentra el archivo .jar en la carpeta target/, luego puedes ejecutarlo con:
```bash
java -jar target/microserviciocuentas.jar
```







