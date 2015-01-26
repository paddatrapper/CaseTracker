#!/bin/bash
#
# Updates the site documentation on http://kritsit.ddns.net/CaseTracker/
#
set -e

# Create updated site documentation
cd ./server
mvn clean package site

cd ../client
mvn clean package site

cd ..

# Remove old files from server
echo "Removing old site files"
ssh kyle@kritsit.ddns.net "rm -rf /var/www/kritsit/public_html/casetracker/{client,server}; mkdir -p /var/www/kritsit.ddns.net/public_html/casetracker/{client,server};"

# Copy new files to server
echo "Copying new client site from developer"
scp -r ./client/target/site/* kyle@kritsit.ddns.net:/var/www/kritsit.ddns.net/public_html/casetracker/client/

echo "Copying new server site from developer"
scp -r ./server/target/site/* kyle@kritsit.ddns.net:/var/www/kritsit.ddns.net/public_html/casetracker/server/
