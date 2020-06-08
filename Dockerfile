FROM openjdk:8
# Used to be java:8

# Created by
LABEL maintainer="trzcinski.tomasz.1988@gmail.com"

RUN apt-get update
RUN apt-get install -y maven

ADD . /code
WORKDIR /code

RUN ["mvn", "clean", "install", "-X"]

# Endpoint's server
EXPOSE 4567
# MySQL port
EXPOSE 3306

CMD ["java", "-jar", "target/parse-jar-with-dependencies.jar"]
