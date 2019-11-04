#!/bin/bash -ex
java -jar -Dspring.profiles.active=${ENVIRONMENT} wrapper-ripple-0.0.1-SNAPSHOT.jar
#build/libs/wrapper-ripple.jar
