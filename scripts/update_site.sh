#!/bin/bash

# Create updated site documentation
cd ~/github/CaseTracker/server
mvn clean package site

cd ~/github/CaseTracker/client
mvn clean package site

# Remove old files from server
echo "Removing old site files"
ssh kyle@kritsit.ddns.net "rm -rf /var/www/kritsit/public_html/casetracker/{client,server}; mkdir -p /var/www/kritsit.ddns.net/public_html/casetracker/{client,server};"

# Copy new files to server
echo "Copying new client site from developer"
scp -r ~/github/CaseTracker/client/target/site/* kyle@kritsit.ddns.net:/var/www/kritsit.ddns.net/public_html/casetracker/client/

echo "Copying new server site from developer"
scp -r ~/github/CaseTracker/server/target/site/* kyle@kritsit.ddns.net:/var/www/kritsit.ddns.net/public_html/casetracker/server/
