#!/bin/bash
#
# Starts the server and then tests the client
#
function package_server 
{
	cd ./server
	mvn package
	if [ $? -ne 0 ]; then
		exit
	fi
	cd ..
}

function run_server 
{
	cd ./server
	java -jar ./target/server-0.1a-SNAPSHOT-jar-with-dependencies.jar &
	JavaPID=$!
	cd ..
}

function test_client 
{
	cd ./client
	mvn clean test
	if [ $? -ne 0 ]; then
		kill -9 $JavaPID
		exit 1
	fi
	cd ..
}

function run_client 
{
	cd ./client
	mvn jfx:run
	cd ..
}

function usage
{
	echo "usage: $0 [ -n ] [ -e ]"
	echo "\t-c | --compile_client\tCompile and test only the client"
	echo "\t-e | --error-put\tHalts script if any call exits abnormally"
	echo "\t-n | --no-recompile\tDoes not recompile the server or client before running them"
}

if [ "$1" == "" ]; then 
	package_server
	run_server
	test_client
	run_client
else
	while [ "$1" != "" ]; do
		case $1 in
			-n | --no-recompile )	run_server
						run_client
						;;
			-e | --error-out )	set-e
						package_server
						run_server
						test_client
						run_client
						;;	
			-c | --compile-client )	run_server
						test_client
						run_client
						;;
			* )			usage
						exit 1
						;;
		esac
		shift
	done
fi
if [ "$JavaPID" != "" ]; then
	kill -9 $JavaPID
fi
