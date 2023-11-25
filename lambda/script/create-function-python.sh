#!/usr/bin/env bash

cd ../src/main/python
zip -r function.zip  *

aws lambda create-function \
    --function-name lambda_context \
    --runtime python3.11 \
    --role arn:aws:iam::317055048278:role/LabRole  \
    --handler lambda_context.lambda_handler \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://function.zip

aws lambda create-function \
    --function-name lambda_dynamodb_writer \
    --runtime python3.11 \
    --role arn:aws:iam::317055048278:role/LabRole  \
    --handler lambda_dynamodb_writer.lambda_handler \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://function.zip

aws lambda create-function \
    --function-name lambda_hello \
    --runtime python3.11 \
    --role arn:aws:iam::317055048278:role/LabRole  \
    --handler lambda_hello.lambda_handler \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://function.zip

aws lambda create-function \
    --function-name lambda_kinesis_reader \
    --runtime python3.11 \
    --role arn:aws:iam::317055048278:role/LabRole  \
    --handler lambda_kinesis_reader.lambda_handler \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://function.zip

aws lambda create-function \
    --function-name lambda_s3_thumbnail \
    --runtime python3.11 \
    --role arn:aws:iam::317055048278:role/LabRole  \
    --handler lambda_s3_thumbnail.lambda_handler \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://function.zip

aws lambda create-function \
    --function-name lambda_sns_reader \
    --runtime python3.11 \
    --role arn:aws:iam::317055048278:role/LabRole  \
    --handler lambda_sns_reader.lambda_handler \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://function.zip

aws lambda create-function \
    --function-name lambda_sns_writer \
    --runtime python3.11 \
    --role arn:aws:iam::317055048278:role/LabRole  \
    --handler lambda_sns_writer.lambda_handler \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://function.zip

rm -rf function.zip
