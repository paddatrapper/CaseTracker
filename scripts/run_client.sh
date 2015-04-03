#!/bin/bash
#
# Starts the server and then tests the client
#
if [ '$1' == '-e' ]; then
	set -e
fi

cd ./server
mvn package

if [ $? -ne 0 ]; then
	exit
fi
java -jar ./target/server-0.1a-SNAPSHOT-jar-with-dependencies.jar &
JavaPID=$!

cd ../client
mvn clean jfx:run

kill -9 $JavaPID
