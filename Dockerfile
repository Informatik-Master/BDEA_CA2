# IMPORTANT!! No slim image as it contains no fonts for the wordcloud 
FROM openjdk:11-jdk as build

WORKDIR /build
COPY pom.xml ./pom.xml
COPY .mvn ./.mvn
COPY mvnw ./mvnw
# IMPORTANT!! mvnw must have LF line endings, not CRLF
RUN ./mvnw dependency:resolve

COPY . .
RUN ./mvnw package -DskipTests

FROM openjdk:11-jre
WORKDIR /app
COPY --from=build /build/target/*.jar lambda-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","lambda-0.0.1-SNAPSHOT.jar"]

