FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 8080
VOLUME /tmp
ADD target/biddingApi-0.0.1-SNAPSHOT.jar bidding.jar
ENTRYPOINT ["java","-jar","/bidding.jar"]
