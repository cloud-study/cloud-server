FROM anapsix/alpine-java:8_jdk
ADD target/*.jar /app.jar
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]