FROM tomcat

RUN apt-get update -y

RUN apt-get install -f

RUN apt-get install git -y

RUN apt-get install nodejs -y

RUN apt-get install npm -y

RUN mkdir -p /tmp/build

WORKDIR /tmp/build/

RUN git clone https://martalribeiro@bitbucket.org/martalribeiro/devops-19-20-a-1191779.git

WORKDIR /tmp/build/devops-19-20-a-1191779/ca3/Parte2/tut_basic_gradle

RUN chmod u+x gradlew

RUN ./gradlew clean build

RUN cp build/libs/basic-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/

EXPOSE 8080
