# ------------ BUILD ------------
FROM gradle:8.5-jdk17-alpine AS build
WORKDIR /app
COPY . .
RUN gradle clean build -x test

# ------------ RUN ------------
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

# Render usa la variable de entorno PORT automáticamente
ENV PORT=8080
EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
