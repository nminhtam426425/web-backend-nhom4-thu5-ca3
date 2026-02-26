# Bước 1: Build project bằng Maven (Dùng bản Maven chạy trên Eclipse Temurin)
FROM maven:3.8.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Bước 2: Chạy ứng dụng (Thay openjdk bằng eclipse-temurin)
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
# Copy file jar đã build từ bước 1 sang
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]