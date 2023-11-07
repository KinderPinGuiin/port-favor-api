FROM maven:3.8.5-openjdk-17 AS BUILDER

# Copy project files
WORKDIR /project
COPY pom.xml /project
COPY src src

# Build project
RUN mvn clean package

FROM openjdk:17.0.2-jdk

ENV SPRING_PROFILE dev

# Run project
WORKDIR /home/build
COPY --from=BUILDER /project/target/**.jar app.jar
CMD ["/bin/sh", "-c", "java -jar -Dspring.profiles.active=${SPRING_PROFILE} /home/build/app.jar"]
