FROM openjdk:11-jdk

RUN useradd -ms /bin/bash javauser
WORKDIR /usr/src/archcnl
RUN chown -R javauser:javauser /usr/src/archcnl
ADD ./target/web-ui-0.0.1-SNAPSHOT-jar-with-dependencies.jar /usr/src/archcnl/web-ui-0.0.1-SNAPSHOT-jar-with-dependencies.jar
EXPOSE 8080
USER javauser
CMD java -jar /usr/src/archcnl/web-ui-0.0.1-SNAPSHOT-jar-with-dependencies.jar