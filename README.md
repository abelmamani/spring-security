# Spring boot + Spring security

## Tecnologías utilizadas

- **Spring Boot**: Framework de desarrollo en Java para crear aplicaciones basadas en microservicios.
- **MYSQL**: Base de datos
- **JWT (JSON Web Token)**: Autenticación y autorización segura basada en tokens.
- **Spring Security**: Framework de seguridad para la protección de endpoints.

## Requisitos previos

Antes de comenzar, asegúrate de tener instalados los siguientes requisitos en tu máquina:

- **JDK 17**
- **Maven**
- **MYSQL** 
- **Postman** o cualquier otra herramienta para realizar pruebas de la API (opcional)

## Instalación

### 1. Clona el repositorio:

```bash
git clone url
```

### 2. Configura el archivo application.properties

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/auth
spring.datasource.username=root
spring.datasource.password=root
```
### 3. Construye el proyecto:
```bash
mvn clean install
```
