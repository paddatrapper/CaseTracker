#!/bin/bash
#
# Starts the server and then tests the client
#
#set -e

cd ./server
mvn package
java -jar ./target/server-0.1a-SNAPSHOT-jar-with-dependencies.jar &
JavaPID=$!

cd ../client
mvn test

kill -9 $JavaPID
