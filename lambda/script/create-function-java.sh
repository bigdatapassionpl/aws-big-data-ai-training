#!/usr/bin/env bash

cd ..
mvn clean install

export ACCOUNT_ID=$(aws sts get-caller-identity --query Account --output text)
export AWS_ROLE="arn:aws:iam::${ACCOUNT_ID}:role/LabRole"

aws lambda create-function \
    --function-name lambda-function-in-java-dynamodb \
    --runtime java25 \
    --role $AWS_ROLE  \
    --handler com.bigdatapassion.DynamoDBLambda \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://target/lambda-1.0-SNAPSHOT.jar

aws lambda create-function \
    --function-name lambda-function-in-java-hello \
    --runtime java25 \
    --role $AWS_ROLE  \
    --handler com.bigdatapassion.HelloLambda \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://target/lambda-1.0-SNAPSHOT.jar

aws lambda create-function \
    --function-name lambda-function-in-java-simple \
    --runtime java25 \
    --role $AWS_ROLE  \
    --handler com.bigdatapassion.SimpleLambda \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://target/lambda-1.0-SNAPSHOT.jar

aws lambda create-function \
    --function-name lambda-function-in-java-stream \
    --runtime java25 \
    --role $AWS_ROLE  \
    --handler com.bigdatapassion.StreamLambda \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://target/lambda-1.0-SNAPSHOT.jar
