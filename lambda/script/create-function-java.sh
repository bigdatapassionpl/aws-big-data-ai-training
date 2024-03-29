#!/usr/bin/env bash

cd ..
mvn clean install

aws lambda create-function \
    --function-name lambda-function-in-java-dynamodb \
    --runtime java8 \
    --role arn:aws:iam::317055048278:role/LabRole  \
    --handler com.bigdatapassion.DynamoDBLambda \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://target/lambda-1.0-SNAPSHOT.jar

aws lambda create-function \
    --function-name lambda-function-in-java-hello \
    --runtime java8 \
    --role arn:aws:iam::317055048278:role/LabRole  \
    --handler com.bigdatapassion.HelloLambda \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://target/lambda-1.0-SNAPSHOT.jar

aws lambda create-function \
    --function-name lambda-function-in-java-simple \
    --runtime java8 \
    --role arn:aws:iam::317055048278:role/LabRole  \
    --handler com.bigdatapassion.SimpleLambda \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://target/lambda-1.0-SNAPSHOT.jar

aws lambda create-function \
    --function-name lambda-function-in-java-stream \
    --runtime java8 \
    --role arn:aws:iam::317055048278:role/LabRole  \
    --handler com.bigdatapassion.StreamLambda \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://target/lambda-1.0-SNAPSHOT.jar
