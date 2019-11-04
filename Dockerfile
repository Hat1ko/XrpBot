#1
FROM gradle:5.4.1-jdk8 AS BUILD_IMAGE
RUN mkdir apps
COPY --chown=gradle:gradle . /apps
WORKDIR /apps

RUN gradle clean build

#2
FROM openjdk:8-jre
COPY --from=BUILD_IMAGE /apps/build/libs/wrapper-ripple-0.0.1-SNAPSHOT.jar .
RUN mkdir script
COPY scripts/ scripts/.
COPY startup.sh .

RUN apt-get install curl; curl -sL https://deb.nodesource.com/setup_13.x | bash -; apt-get -y install nodejs
RUN cd scripts/; npm i --save; cd ..

CMD bash startup.sh