#!/usr/bin/env bash

cd ../src/main/python
zip -r function.zip  *

export AWS_ROLE='arn:aws:iam::932771310344:role/LabRole'

aws lambda create-function \
    --function-name lambda_context \
    --runtime python3.11 \
    --role $AWS_ROLE  \
    --handler lambda_context.lambda_handler \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://function.zip

aws lambda create-function \
    --function-name lambda_dynamodb_writer \
    --runtime python3.11 \
    --role $AWS_ROLE  \
    --handler lambda_dynamodb_writer.lambda_handler \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://function.zip

aws lambda create-function \
    --function-name lambda_hello \
    --runtime python3.11 \
    --role $AWS_ROLE  \
    --handler lambda_hello.lambda_handler \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://function.zip

aws lambda create-function \
    --function-name lambda_kinesis_reader \
    --runtime python3.11 \
    --role $AWS_ROLE  \
    --handler lambda_kinesis_reader.lambda_handler \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://function.zip

aws lambda create-function \
    --function-name lambda_kinesis_writer \
    --runtime python3.11 \
    --role $AWS_ROLE  \
    --handler lambda_kinesis_writer.lambda_handler \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://function.zip

aws lambda create-function \
    --function-name lambda_s3_thumbnail \
    --runtime python3.11 \
    --role $AWS_ROLE  \
    --handler lambda_s3_thumbnail.lambda_handler \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://function.zip

aws lambda create-function \
    --function-name lambda_sns_reader \
    --runtime python3.11 \
    --role $AWS_ROLE  \
    --handler lambda_sns_reader.lambda_handler \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://function.zip

aws lambda create-function \
    --function-name lambda_sns_writer \
    --runtime python3.11 \
    --role $AWS_ROLE  \
    --handler lambda_sns_writer.lambda_handler \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://function.zip

rm -rf function.zip
