#!/usr/bin/env bash

cd ../src/main/nodejs
zip -r function.zip  *

aws lambda create-function \
    --function-name lambda-function-in-nodejs-simple \
    --runtime nodejs8.10 \
    --role arn:aws:iam::020214251602:role/Politechnika  \
    --handler lambda_simple.handler \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://function.zip

aws lambda create-function \
    --function-name lambda-function-in-nodejs-s3 \
    --runtime nodejs8.10 \
    --role arn:aws:iam::020214251602:role/Politechnika  \
    --handler lambda_with_s3.handler \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://function.zip

aws lambda create-function \
    --function-name lambda-function-in-nodejs-sns-put \
    --runtime nodejs8.10 \
    --role arn:aws:iam::020214251602:role/Politechnika  \
    --handler lambda_with_sns.handler \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://function.zip

rm -rf function.zip
