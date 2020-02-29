FROM openjdk:8-jdk-alpine

WORKDIR /app

# The application's jar file
ARG JAR_FILE=target/cart-data-svc-*.jar

# Add the application's jar to the container

ADD ${JAR_FILE} cart-data-svc.jar

EXPOSE 6060

ENTRYPOINT ["java", "-jar", "/app/cart-data-svc.jar"]