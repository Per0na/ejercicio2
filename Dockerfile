# Primera etapa: compilar la aplicación
FROM openjdk:21-jdk-slim AS build
WORKDIR /app
COPY . .
RUN ./mvnw package

# Segunda etapa: ejecutar la aplicación
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
