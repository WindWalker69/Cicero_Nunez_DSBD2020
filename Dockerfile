FROM maven:3-jdk-8 as builder
WORKDIR /project
COPY . .
RUN mvn package

FROM java:8-alpine
WORKDIR /app
COPY --from=builder /project/target/productmanager-0.0.1-SNAPSHOT.jar ./productmanager.jar
CMD java -jar productmanager.jar

#ENTRYPOINT ["/bin/sh", "-c"]
#CMD  [ "java -jar target/productmanager-0.0.1-SNAPSHOT.jar" ]