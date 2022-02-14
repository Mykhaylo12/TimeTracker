FROM java:8
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-Xdebug" , "-Xrunjdwp:transport=dt_socket,address=18787,server=y,suspend=y", "-jar", "application.jar"]

