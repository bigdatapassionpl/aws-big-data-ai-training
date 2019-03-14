#!/usr/bin/env bash

aws lambda invoke \
    --function-name lambda-function-in-java \
    --payload '{"firstName":"value1","lastName" : "value2"}' \
    lambda-output.json

echo -e "\nResult:\n"
cat lambda-output.json
echo -e "\n"