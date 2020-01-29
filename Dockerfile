FROM maven:3.6.2-jdk-8
WORKDIR /app
COPY src/ src/
COPY pom.xml .
RUN sed "s|spring.data.mongodb.host=localhost|spring.data.mongodb.host=database|g" -i src/main/resources/database.properties
RUN sed "s|activemq.host=localhost|activemq.host=activemq|g" -i src/main/resources/activemq.properties