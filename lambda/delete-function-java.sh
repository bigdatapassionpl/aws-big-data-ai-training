#!/usr/bin/env bash

aws lambda delete-function \
    --function-name lambda-function-in-java

echo -e "\nFunctions:"
aws lambda list-functions