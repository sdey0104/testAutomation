#!/bin/sh
# We need node js and newman to be installed.
# https://www.getpostman.com/docs/v6/postman/collection_runs/command_line_integration_with_newman
echo "Exporting variables"
export /usr/local/bin
echo "Running Postman workflows....."
/usr/local/bin/newman run /Users/kabirsohel/work/yellow-sources/resources/test_automation/rest_api_postman_collection/rest_api_collection.json -e /Users/kabirsohel/work/yellow-sources/resources/test_automation/rest_api_postman_collection/config_prod.json --export-environment /tmp/tmp-newman.json --delay-request 2000
echo "Finished Running Postman workflows....."
