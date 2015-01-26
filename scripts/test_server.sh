#!/bin/bash
#
# Runs tests on the server
#
set -e
cd ./server
mvn test
