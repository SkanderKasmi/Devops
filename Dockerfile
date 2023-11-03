FROM openjdk:11-jre-slim
WORKDIR /app
ADD target/devops-project.jar devops-project.jar
EXPOSE 8089
CMD java -jar devops-project.jar
ENTRYPOINT [ "java", "-jar", "devops-project.jar" ]