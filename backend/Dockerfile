# ---------- Stage 1 – build ----------
FROM --platform=linux/amd64 maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY .mvn/ .mvn
RUN mvn -B dependency:go-offline          # now works

COPY src ./src
RUN mvn -B clean package -DskipTests      # produces target/*.jar

# ---------- Stage 2 – runtime ----------
FROM --platform=linux/amd64 eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
