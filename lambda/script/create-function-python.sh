#!/usr/bin/env bash

cd ../src/main/python
zip -r function.zip  *

aws lambda create-function \
    --function-name lambda-function-in-python-hello \
    --runtime python3.6 \
    --role arn:aws:iam::317055048278:role/LabRole  \
    --handler hello-lambda.my_handler \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://function.zip

aws lambda create-function \
    --function-name lambda-function-in-python-thumbnail \
    --runtime python3.6 \
    --role arn:aws:iam::317055048278:role/LabRole  \
    --handler create-thumbnail.handler \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://function.zip

aws lambda create-function \
    --function-name lambda-function-in-python-context \
    --runtime python3.6 \
    --role arn:aws:iam::317055048278:role/LabRole  \
    --handler context-lambda.get_my_log_stream \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://function.zip

aws lambda create-function \
    --function-name lambda-function-in-python-sns-fetch \
    --runtime python2.7 \
    --role arn:aws:iam::317055048278:role/LabRole  \
    --handler sns-fetch-lambda.lambda_handler \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://function.zip

rm -rf function.zip
