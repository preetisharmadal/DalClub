FROM maven:3.8.2-eclipse-temurin-17 AS maven_build
WORKDIR /usr/src/app
COPY ./backend /usr/src/app
# Compile and package the application to an executable JAR
RUN mvn clean install
RUN mv target/*.jar target/Dal_Clubs_Group_08.jar  
RUN ls -lrt

# Using Java 17:
FROM eclipse-temurin:17_35-jdk-alpine
COPY --from=maven_build /usr/src/app/target/Dal_Clubs_Group_08.jar ./
COPY --from=maven_build /usr/src/app/target/* ./
COPY backend/src/main/resources/database.properties src/main/resources/database.properties
EXPOSE 8080
ENTRYPOINT ["java","-jar","Dal_Clubs_Group_08.jar"]
