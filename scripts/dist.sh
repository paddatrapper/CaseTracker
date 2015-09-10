#!/bin/bash
#
# Creates a distributable .zip archive
#

mvn clean package
mkdir -p dist/{server,client}
cp client/target/client-*-jar-with-dependencies.jar dist/client/CaseTrackerClient.java
cp client/target/config.properties dist/client/config.properties

cp server/target/server-*-jar-with-dependencies.jar dist/server/CaseTrackerServer.java
cp server/target/config.properties dist/server/config.properties

zip -r CaseTracker.zip dist/*

rm -r dist
