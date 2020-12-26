FROM maven:3-jdk-8
WORKDIR /app
COPY . .
RUN mvn package


ENTRYPOINT ["/bin/sh", "-c"]
CMD  [ "java -jar target/productmanager-0.0.1-SNAPSHOT.jar" ]


