#!/bin/bash
#
# Controls testing and running the server and client
#

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
	mvn test
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
	echo "usage: $0 [ -e ] ( test | verify | client | server | run )"
	echo "	-e | --error-halt	Halts script if any call exits abnormally"
	echo "	test			Compile and test both client and server"
	echo "	verify			Compile and verify both client and server"
	echo "	client			Compile and test only the client"
	echo "	server			Compile and test only the server"
	echo "	run			Runs the client and server"
}

if [ "$1" == "" ]; then 
	mvn verify
	run_server
	run_client
else
	while [ "$1" != "" ]; do
		case $1 in
			-e | --error-halt )	set -e
						;;	
			test )			mvn test
						;;
			verify )			mvn verify
						;;
			client )		run_server
						test_client
						;;
			server )		test_server
						;;
			run)			run_server
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
