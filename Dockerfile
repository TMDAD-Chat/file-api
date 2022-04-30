FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ADD ./tmdad-chat-app-firebase-adminsdk.json ./
ENTRYPOINT ["java","-jar","/app.jar"]