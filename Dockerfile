FROM openjdk:17-jdk-alpine
MAINTAINER galo_ortega
COPY target/AccountBP-0.0.1-SNAPSHOT.jar AccountBP-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/AccountBP-0.0.1-SNAPSHOT.jar"]