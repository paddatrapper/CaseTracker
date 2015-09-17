#!/bin/bash
#
# Starts the server in the given directory
#

cd $1/server
java -jar target/server-0.2.1-ALPHA-jar-with-dependencies.jar > /dev/null 2>&1 &
