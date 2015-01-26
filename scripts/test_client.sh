#!/bin/bash
#
# Starts the server and then tests the client
#
set -ev
cd ./server
mvn package
java -jar ./target/server-0.1a-SNAPSHOT-jar-with-dependencies.jar &

cd ../client
mvn test
