FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

RUN mv target/estoque-project-0.0.1-SNAPSHOT.jar.original target/app.jar

CMD ["java", "-jar", "target/app.jar"]