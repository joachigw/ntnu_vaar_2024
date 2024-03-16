FROM openjdk:latest

COPY Java.java /app/

WORKDIR /app

RUN javac Java.java

CMD ["java", "Java"]