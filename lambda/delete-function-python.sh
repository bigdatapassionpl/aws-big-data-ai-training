#!/usr/bin/env bash

aws lambda delete-function \
    --function-name lambda-function-in-python-hello

aws lambda delete-function \
    --function-name lambda-function-in-python-thumbnail

aws lambda delete-function \
    --function-name lambda-function-in-python-context

aws lambda delete-function \
    --function-name lambda-function-in-python-sns-fetch

echo -e "\nFunctions:"
aws lambda list-functions