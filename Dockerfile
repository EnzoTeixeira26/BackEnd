# ============================================
# ETAPA 1: BUILD (compila o projeto com Maven)
# ============================================
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia o pom.xml primeiro (aproveita cache de dependências)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte
COPY src ./src

# Compila o projeto (pula testes)
RUN mvn clean package -DskipTests

# ============================================
# ETAPA 2: RUNTIME (executa o .jar)
# ============================================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia o .jar da etapa de build
COPY --from=build /app/target/*.jar app.jar

# Porta que a aplicação vai expor
EXPOSE 8080

# Comando pra iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]