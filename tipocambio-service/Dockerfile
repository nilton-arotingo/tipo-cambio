FROM openjdk:8-jdk-slim
COPY "./target/tipocambio-service.jar" "tipocambio-service.jar"
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "tipocambio-service.jar"]