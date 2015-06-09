#!/bin/bash
#
# Starts the server in the given directory
#

cd $1/server
java -jar target/server-0.1a-SNAPSHOT-jar-with-dependencies.jar > /dev/null 2>&1 &
