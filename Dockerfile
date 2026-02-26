# Bước 1: Build project bằng Maven
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
# Copy file cấu hình maven và source code vào container
COPY pom.xml .
COPY src ./src
# Build ra file jar (bỏ qua chạy test để nhanh hơn)
RUN mvn clean package -DskipTests

# Bước 2: Chạy ứng dụng với JDK gọn nhẹ
FROM openjdk:17-jdk-slim
WORKDIR /app
# Copy file jar đã build từ bước 1 sang (đổi tên cho dễ quản lý)
COPY --from=build /app/target/*.jar app.jar

# Mở port (Render thường dùng port 8080 mặc định cho Spring)
EXPOSE 8080

# Lệnh thực thi ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]