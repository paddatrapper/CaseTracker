#!/bin/bash
#
# Controls testing and running the server and client
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

function test_server 
{
	cd ./server
	mvn test
	cd ..
}

function run_server 
{
	if [ "$JavaPID" == "" ]; then
		cd ./server
		java -jar ./target/server-0.2.1-ALPHA-jar-with-dependencies.jar &
		JavaPID=$!
		cd ..
	fi
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
	echo "usage: $0 [ -e ] [ -a | -c | -s ] [ -r ]"
	echo "	-a | --test-all		Compile and test both client and server"
	echo "	-c | --test-client	Compile and test only the client"
	echo "	-e | --error-put	Halts script if any call exits abnormally"
	echo "	-r | --run		Runs the client and server"
	echo "	-c | --test-server	Compile and test only the server"
}

if [ "$1" == "" ]; then 
	package_server
	test_client
else
	while [ "$1" != "" ]; do
		case $1 in
			-a | --test-all )	package_server
						run_server
						test_client
						;;
			-c | --test-client )	run_server
						test_client
						;;
			-e | --error-out )	set -e
						;;	
			-r | --run )		run_server
						run_client
						;;
			-s | --test-server )	test_server
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
