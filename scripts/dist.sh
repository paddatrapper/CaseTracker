#!/bin/bash
#
# Creates a distributable .zip archive
#
VERSION=$1

mvn clean package
mkdir -p CaseTracker/{server,client}
cp client/target/client-$VERSION-jar-with-dependencies.jar CaseTracker/client/CaseTrackerClient.jar
cp client/target/config.properties CaseTracker/client/config.properties

cp server/target/server-$VERSION-jar-with-dependencies.jar CaseTracker/server/CaseTrackerServer.jar
cp server/target/config.properties CaseTracker/server/config.properties

zip -r CaseTracker.zip CaseTracker/*

rm -r CaseTracker
