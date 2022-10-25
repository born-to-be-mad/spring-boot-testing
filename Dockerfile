FROM openjdk:11
EXPOSE 8000
COPY "/target/testing-microservice.jar" app.jar
CMD [ "java", "-jar", "app.jar" ]