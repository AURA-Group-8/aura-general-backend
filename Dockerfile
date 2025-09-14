FROM openjdk:21-jdk-slim

# Diretório de trabalho dentro do container
WORKDIR /app

# Copia o .jar gerado pelo Maven/Gradle para dentro do container
COPY target/*.jar app.jar

# Expor a porta do Spring Boot (padrão 8080)
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]
