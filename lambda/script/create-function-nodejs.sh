#!/usr/bin/env bash

cd ../src/main/nodejs
zip -r function.zip  *

export AWS_ROLE='arn:aws:iam::012235137533:role/LabRole'

aws lambda create-function \
    --function-name lambda-function-in-nodejs-simple \
    --runtime nodejs16.x \
    --role $AWS_ROLE  \
    --handler lambda_simple.handler \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://function.zip

aws lambda create-function \
    --function-name lambda-function-in-nodejs-s3 \
    --runtime nodejs16.x \
    --role $AWS_ROLE  \
    --handler lambda_with_s3.handler \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://function.zip

aws lambda create-function \
    --function-name lambda-function-in-nodejs-sns-put \
    --runtime nodejs16.x \
    --role $AWS_ROLE  \
    --handler lambda_with_sns.handler \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://function.zip

rm -rf function.zip
