#!/bin/bash
#
# Stops the server
#
VERSION=$1

pkill -f "java -jar target/server-$VERSION-jar-with-dependencies.jar"
