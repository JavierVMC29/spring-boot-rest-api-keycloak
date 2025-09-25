# Usamos Java 21, ligero y oficial
FROM eclipse-temurin:21-jdk-alpine

# Directorio de trabajo
WORKDIR /app

# Copiamos el JAR generado por Maven
ARG JAR_FILE=target/spring-boot-companies-keycloak-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Puerto expuesto por Spring Boot
EXPOSE 8080

# Ejecutamos la app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]