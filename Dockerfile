FROM openjdk:11-jre-slim
WORKDIR /app
ADD DevOps_Project-1.0.jar devops-project.jar
EXPOSE 8089
CMD java -jar devops-project.jar
ENTRYPOINT [ "java", "-jar", "devops-project.jar" ]