FROM openjdk:23

COPY target/measurements*.jar /measurement.jar

CMD ["java", "-jar", "measurement.jar"]