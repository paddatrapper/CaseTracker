#!/bin/bash
#
# Updates the site documentation on http://kritsit.ddns.net/casetracker/
#
# Create updated site documentation
mvn clean site
# Remove old files from server
echo "Removing old site files"
ssh kyle@kritsit.ddns.net -p 443 "rm -rf /var/www/kritsit.ddns.net/public_html/casetracker; mkdir -p /var/www/kritsit.ddns.net/public_html/casetracker/{client,server};"

# Copy new files to server
echo "Copying new project site from developer"
scp -r -P 443 ./target/site/* kyle@kritsit.ddns.net:/var/www/kritsit.ddns.net/public_html/casetracker/

echo "Copying new client site from developer"
scp -r -P 443 ./client/target/site/* kyle@kritsit.ddns.net:/var/www/kritsit.ddns.net/public_html/casetracker/client/

echo "Copying new server site from developer"
scp -r -P 443 ./server/target/site/* kyle@kritsit.ddns.net:/var/www/kritsit.ddns.net/public_html/casetracker/server/
