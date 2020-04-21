#1
FROM gradle:6.3-jdk14 AS BUILD_IMAGE
RUN mkdir apps
COPY --chown=gradle:gradle . /apps
WORKDIR /apps

RUN gradle clean assemble jar

#2
FROM openjdk:14-alpine
COPY --from=BUILD_IMAGE /apps/build/libs/wrapper-ripple-0.0.1-SNAPSHOT.jar .
RUN mkdir script
COPY scripts/ scripts/.
COPY startup.sh .

RUN apk update && apk add --no-cache bash
RUN apk add curl; curl -sL https://deb.nodesource.com/setup_13.x | bash -; apk add --update nodejs npm
RUN cd scripts/; npm i --save; cd ..

EXPOSE 8511:8511
CMD bash startup.sh