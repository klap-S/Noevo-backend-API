# Backend - NOEVO

Este es el backend de **Noevo**, una app creada con **Spring Boot** y **Java**.

Se encarga de manejar todo lo que pasa detrás que es la lógica, guardar los datos y conectar con APIs que convierten voz en texto y texto en voz.

---

## Estructura del proyecto

BACKEND/

├── src/

│ └── main/

│ ├── java/proyect/

│ │ ├── controller/ # Controladores REST

│ │ ├── model/ # Clases de entidad

│ │ ├── repository/ # Interfaces para acceder a la base de datos

│ │ └── service/ # Lógica

│ └── resources/

│ ├── static/ # Archivos estáticos

│ ├── templates/ # Plantillas (ej. Thymeleaf, no usado)

│ └── application.properties # Configuración del proyecto

---

## Requisitos

- Java 17

- Maven

---

## Ejecución en local

1. Clonar el repositorio:

2. Instale dependencias

```bash
./mvnw clean install
```

3. Configure la base de datos en `application.properties`

```bash
spring.datasource.url=jdbc:mysql://localhost:.../...

spring.datasource.username=

spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true

server.port=8080
```

4. Ejecutar la aplicación

```bash
./mvnw spring-boot:run
```

---

## Nota

- La aplicación se ejecutará por defecto en http://localhost:8080.

---

#

Desarrollado por Oliver SSS
