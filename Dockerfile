# 1) Etapa de build: usa Temurin JDK 21 y el Maven Wrapper
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copia solo lo necesario para cachear dependencias
COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN chmod +x mvnw \
    && ./mvnw -B dependency:go-offline

# Copia el resto del código y empaqueta sin tests
COPY src src
RUN ./mvnw -B package -DskipTests

# 2) Etapa de runtime: JRE 21 ligero
FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app

# Copia el JAR construido
COPY --from=build /app/target/blackjack-webflux-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto y arranca la app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
