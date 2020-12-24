FROM maven:3-jdk-8 as builder
RUN mvn install
WORKDIR /project
COPY productmanager .
#ADD ./target/productmanager-0.0.1-SNAPSHOT.jar ./productmanager.jar
RUN mvn package

FROM java:8-alpine
WORKDIR /app
COPY --from=builder /project/target/productmanager-0.0.1-SNAPSHOT.jar ./productmanager.jar
#ENTRYPOINT ["/bin/sh", "-c"]
CMD java -jar productmanager.jar