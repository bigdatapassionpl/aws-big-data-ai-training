#!/usr/bin/env bash

cd ..
mvn clean install

export AWS_ROLE='arn:aws:iam::012235137533:role/LabRole'

aws lambda create-function \
    --function-name lambda-function-in-java-dynamodb \
    --runtime java8 \
    --role $AWS_ROLE  \
    --handler com.bigdatapassion.DynamoDBLambda \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://target/lambda-1.0-SNAPSHOT.jar

aws lambda create-function \
    --function-name lambda-function-in-java-hello \
    --runtime java8 \
    --role $AWS_ROLE  \
    --handler com.bigdatapassion.HelloLambda \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://target/lambda-1.0-SNAPSHOT.jar

aws lambda create-function \
    --function-name lambda-function-in-java-simple \
    --runtime java8 \
    --role $AWS_ROLE  \
    --handler com.bigdatapassion.SimpleLambda \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://target/lambda-1.0-SNAPSHOT.jar

aws lambda create-function \
    --function-name lambda-function-in-java-stream \
    --runtime java8 \
    --role $AWS_ROLE  \
    --handler com.bigdatapassion.StreamLambda \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://target/lambda-1.0-SNAPSHOT.jar
