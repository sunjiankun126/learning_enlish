FROM hub.pml.com/common/openjdk:8
ENV PORT 8806
ENV CLASSPATH /opt/lib
ARG MYSQL_PASSWORD
ENV MYSQL_PASSWORD ${MYSQL_PASSWORD}

COPY target/*.jar /opt/app.jar
COPY pom.xml /opt/lib/

WORKDIR /opt
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar --spring.profiles.active=dev --spring.datasource.password=${MYSQL_PASSWORD}
