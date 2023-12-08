# wsl --shutdown
FROM openjdk:17-jdk-slim
VOLUME /tmp
EXPOSE 8085
ADD ./target/factura-0.0.1-SNAPSHOT.jar factura-service.jar
ENTRYPOINT ["java","-jar","/factura-service.jar"]
