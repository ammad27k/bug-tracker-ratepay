#FROM adoptopenjdk/openjdk11
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

FROM maven:3-jdk-11
WORKDIR /bug-tracker-ratepay
COPY . .
RUN mvn clean install
CMD mvn spring-boot:run