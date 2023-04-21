FROM eclipse-temurin:17-jre-alpine

LABEL maintener="Nor"

EXPOSE 8080

WORKDIR /back

COPY ./target/gazouilleur-back-0.0.1-SNAPSHOT.jar gazouilleur-back.jar

CMD ["java", "-jar",  "gazouilleur-back.jar"]
