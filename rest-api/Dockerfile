FROM amazoncorretto:17

ADD target/*.jar app.jar

EXPOSE 8080

ARG DBUSER
ENV DBUSER=$DBUSER

ARG DBPASSWORD
ENV DBPASSWORD=$DBPASSWORD

CMD ["java","-jar","-Dspring.profiles.active=production", "app.jar"]