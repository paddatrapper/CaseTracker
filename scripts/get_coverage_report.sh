#!/bin/bash
#
# Generates test coverage report for coveralls
#

cd server
mvn clean test jacoco:report coveralls:report
java -jar ./target/server-0.1a-SNAPSHOT-jar-with-dependencies.jar &
JavaPID=$!
cd ..

cd client
mvn clean test jacoco:report coveralls:report
kill -9 $JavaPID
