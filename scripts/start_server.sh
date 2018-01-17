#!/bin/bash
#
# Starts the server in the given directory
#
VERSION=$1

cd $2/server
java -jar target/server-$VERSION-jar-with-dependencies.jar > /dev/null 2>&1 &
