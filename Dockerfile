FROM maven:3.8.5-openjdk-18
# Used to be openjdk:8

# Created by
LABEL maintainer="trzcinski.tomasz.1988@gmail.com"

# Change work directory to code
ADD . /code
WORKDIR /code

# Call mvn to build application
RUN ["mvn", "clean", "install", "-X"]

# Endpoint's server
EXPOSE 4567
# MySQL port
EXPOSE 3306

# Run as java application
CMD ["java", "-jar", "target/parse-jar-with-dependencies.jar"]
